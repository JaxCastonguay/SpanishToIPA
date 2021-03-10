package representative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import errors.PhonemNotFoundException;
import logic.Translator;
import sounds.CharacterClassification;

public class Sentence {
	
	private String spanishSentence;
	
	
	public Sentence(String SpanishSentence) {
		this.spanishSentence = SpanishSentence;
	}
	
	public String getPhoneticSentence() throws PhonemNotFoundException {
		//Scrub input
		Pattern pattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ,.?¿!¡ ]*$");
		Matcher matcher = pattern.matcher(spanishSentence);
			
		if(!matcher.find()) {
			throw new PhonemNotFoundException("Only letter and punctuation characters are allowed");
		}
		
		if(spanishSentence.contains(".") || spanishSentence.contains("!") || spanishSentence.contains("?")) {
			//TODO: want to put other non chars/period catch here
			spanishSentence.replace('!', '|');
			spanishSentence.replace('¡', '|');
			spanishSentence.replace('?', '|');
			spanishSentence.replace('¿', '|');
			spanishSentence.replace('.', '|');
			spanishSentence.replace(',', '|');
		}
		
		List<String> words = populateWordsList();
		StringBuilder string = new StringBuilder();
		
		if(words.contains("'ɟ") || words.contains("'ʝ")) {
			moveYToWord(words);
		}
		AlterOuterPhonetics(words);
		adjustConsonantPlacements(words);
		
		joinWordsIntoString(words, string);
		
		string.append("/");
		string.insert(0, '/');
		
		return string.toString();
	}

	private List<String> populateWordsList() throws PhonemNotFoundException {
		String[] array = spanishSentence.toLowerCase().split(" ");
		List<String> list = Arrays.asList(array);
		List<String> words = new ArrayList<>();
		//Fill words with phonetic words with syllables.
		for(int i = 0; i < list.size(); i++) {		
			Word word = new Word(list.get(i));
			words.add(word.getPhoneticsWithSyllables());
		}
		return words;
	}

	private void joinWordsIntoString(List<String> words, StringBuilder string) {
		for(int i = 0; i < words.size(); i++) {
			if(i != 0)
				string.append(".");
			string.append(words.get(i));
		}
	}
	
	private void adjustConsonantPlacements(List<String> words) {
		for(int i = 1; i < words.size(); i++) {
			//Prep
			StringBuilder currentWord = new StringBuilder(words.get(i));
			boolean wordBeganWithApostrophy = (currentWord.charAt(0) == '\'');
			if(wordBeganWithApostrophy) {
				currentWord = new StringBuilder(currentWord.substring(1, currentWord.length()));
			}
			Letter currentWordFirstLetter = new LetterImpl(currentWord.charAt(0));
			
			String previousWord = words.get(i - 1);
			Letter previousWordLastLetter = new LetterImpl(previousWord.charAt(previousWord.length() - 1));
			
			//Check if consonant should be moved
			if (currentWordFirstLetter.isVowel() && !previousWordLastLetter.isVowel()) {
				currentWord.insert(0, previousWordLastLetter.getSpanishLetter());
				if (wordBeganWithApostrophy) {
					currentWord.insert(0, '\'');
				}
				words.set(i, currentWord.toString());
				previousWord = previousWord.substring(0, previousWord.length() - 1);
				words.set(i - 1, previousWord);
			}					
		}
	}
	
	public void AlterOuterPhonetics(List<String> words) {
		for(int i = 0; i < words.size(); i++) {
			//1) prep
			Translator translator = new Translator();
			StringBuilder currentWord = new StringBuilder(words.get(i));
			StringBuilder nextWord = new StringBuilder("|");
			if(i < words.size() - 1) {
				nextWord = new StringBuilder(words.get(i + 1));
			}
			boolean wordBeganWithApostrophy = (nextWord.charAt(0) == '\'');
			if(wordBeganWithApostrophy) {
				nextWord = new StringBuilder(nextWord.substring(1, nextWord.length()));
			}
			
			//2) Adjust current word
			//first letter will never be coda
			String currentWordLastCharUpdated = translator.getPhoneticsBasedOnNextChar(Character.toString(currentWord.charAt(currentWord.length() - 1)), nextWord.charAt(0), false);			
			currentWord = currentWord.replace(currentWord.length() - 1, currentWord.length(), currentWordLastCharUpdated);
			
			//3) Prep for next word
			char secondToLastOfCurrent = '|';
			if(currentWord.length() > 1) {
				secondToLastOfCurrent = currentWord.charAt(currentWord.length() - 2);
			}
			
			//4) Adjust next word
			char nextWordFirstCharUpdated = translator.getPhoneticBasedOnPreviousChars(secondToLastOfCurrent, currentWord.charAt(currentWord.length() - 1), nextWord.charAt(0));
			nextWord = nextWord.replace(0, 1, Character.toString(nextWordFirstCharUpdated));
			//if next word was given nasal accent we need to check if it still applies.
			if(isNextWordHasExtraNasalAccent(nextWord, currentWordLastCharUpdated)) {
				//remove nasal accent
				nextWord.replace(1, 2, "");
				//Why would his occur? if a word starts with a vowel plus nasal it will be nasalized as an individual word
				// but, once put in a sentence the "beginning pause" will be replaced by the end of the word before it
			}
			
			
			//5) clean up / set words
			if (wordBeganWithApostrophy) {
				nextWord.insert(0, '\'');
			}
			
			words.set(i, currentWord.toString());
			if(i < words.size() - 1) {
				words.set(i + 1, nextWord.toString());
			}
		}
	}

	private boolean isNextWordHasExtraNasalAccent(StringBuilder nextWord, String currentWordLastCharUpdated) {
		return nextWord.length() > 3 && nextWord.charAt(1) == '̃' 
				&& !CharacterClassification.nasales.contains(Character.toString(currentWordLastCharUpdated.charAt(currentWordLastCharUpdated.length() - 1)));
	}
	
	
	//The word y is usually tacked onto a surrounding word
	private void moveYToWord(List<String> words) {
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).equals("'ɟ") || words.contains("'ʝ")) {
				char previousWordLastChar = safeReturnPreviousWordLastChar(words, i);
				char nextWordFirstChar = safeReturnNextWordFirstChar(words, i);
				//C y C -> Ci.C
				if(!LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					//Add char in current word
					String replacementWord = "'" + String.valueOf(previousWordLastChar) + "i";
					words.set(i, replacementWord);
					//Remove char in past char
					words.set(i - 1, words.get(i -1).substring(0, words.get(i - 1).length() -1));
				}
				// || y C -> i.C
				else if(previousWordLastChar == '|' && !LetterImpl.isVowel(nextWordFirstChar)
						&& nextWordFirstChar != '|') {
					words.set(i, "'" + "i");
				}
				// V y V -> V.ʝV
				else if(LetterImpl.isVowel(previousWordLastChar) && LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					words.set(i + 1, safeInsertFirst(words.get(i + 1), 'ʝ'));
					//Remove old y word
					words.remove(i);
					i--;
				}
				// V y C -> Vj.C
				else if(LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					
				}
				//C*(not /s/) y V -> .cjv. (joined)
				else if(!LetterImpl.isVowel(previousWordLastChar) && previousWordLastChar != 's' && previousWordLastChar != 'z'
						&& LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					
				}
				//C*s y V -> z.ʝv
				else if(!LetterImpl.isVowel(previousWordLastChar) && (previousWordLastChar == 's' || previousWordLastChar == 'z')
						&& LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					words.set(i + 1, safeInsertFirst(words.get(i + 1), 'ʝ'));
					//Remove old y word
					words.remove(i);
					i--;
				}
				//|| y V -> ʝv
				else if(previousWordLastChar == '|' && LetterImpl.isVowel(nextWordFirstChar)
						&& nextWordFirstChar != '|') {
					words.set(i + 1, safeInsertFirst(words.get(i + 1), 'ʝ'));
					
					//TODO: this is ugly but I want to move on for a bit. Make this nicer later
					//Vowel no longer qualifies as nasal from leading ||
					if(words.get(i + 1).length() > 2 && words.get(i + 1).charAt(2) == '̃') {//no accent before
						words.set(i + 1, removeCharAt(words.get(i + 1), 2));
					}
					else if(words.get(i + 1).length() > 2 && words.get(i + 1).charAt(3) == '̃') {//accent before
						words.set(i + 1, removeCharAt(words.get(i + 1), 3));
					}
					//Remove old y word
					words.remove(i);
					i--;
				}
				// C y || Not given in book. Want to change to i
				else if(!LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar == '|') {
					
				}
				// V y || Not given in book. Want to change to j? maybe separate syllable i?
				else if(LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar == '|') {
					
				}
				// || y || Hyper specific. Sentence of jus Y
				else if(previousWordLastChar == '|' && nextWordFirstChar == '|') {
					
				}
			}
		}
	}
	
	private String removeCharAt(String word, int i) {
		StringBuilder sb = new StringBuilder(word);
		sb.replace(i, i+1, "");
		return sb.toString();
	}
	
	private String safeInsertFirst(String word, char c) {
		StringBuilder sb = new StringBuilder(word);
		if(word.charAt(0) == '\'') {
			sb.insert(1, c);
		} else {
			sb.insert(0, c);
		}
		return sb.toString();
	}
	
	private char safeReturnPreviousWordLastChar(List<String> words, int currentIndex) {	
		if(currentIndex <= 0) {
			return '|';
		}else {
			return words.get(currentIndex - 1).charAt(words.get(currentIndex - 1).length() - 1);
		}
	}
	
	private char safeReturnNextWordFirstChar(List<String> words, int currentIndex) {
		if(currentIndex >= words.size() - 1) {
			return '|';
		}else if(words.get(currentIndex + 1).charAt(0) == '\'') {
			return words.get(currentIndex + 1).charAt(1);
		}
		else {
			return words.get(currentIndex + 1).charAt(0);
		}
	}
	
}
