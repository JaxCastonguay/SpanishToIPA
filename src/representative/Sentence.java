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
		
		//TODO: add the chars already withing the if
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
			//TODO: many of these assume y has accentuation, should it be?
			//TODO: beginning accent doesn't seem to be checked.
			if(words.get(i).equals("'ɟ") || words.equals("'ʝ")) {
				char previousWordLastChar = safeReturnPreviousWordLastChar(words, i);
				char nextWordFirstChar = safeReturnNextWordFirstChar(words, i);
				//C y C -> Ci.C
				if(!LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					//Add char in current word
					String replacementWord = "'" + String.valueOf(previousWordLastChar) + "i";
					words.set(i, replacementWord);
					//does previous C need a re-examine? it's no longer a coda.
					//Yes although the only changes are k, p, t back to original letters. But we don't know at this point what the original was, k/g, p/b, t/d
					
					removePreviousWordLastChar(words, i);
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
					stripWordOfPotentialNasalAccent(words, i + 1);
					//Remove old y word
					words.remove(i);
					i--;
				}
				// V y C -> Vj.C
				else if(LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					yJoinVowelConsonant(words, i);
					//Remove old y word
					words.remove(i);
					i--;
				}
				//C*(not /s/) y V -> .cjv. (joined)
				else if(!LetterImpl.isVowel(previousWordLastChar) && previousWordLastChar != 's' && previousWordLastChar != 'z'
						&& LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					
					yJoinConsonantVowel(words, i, previousWordLastChar);
					if(words.get(i + 1).length() == 0) {
						words.remove(i + 1);
						i--;
					}
				}
				//C*s y V -> z.ʝv
				else if(!LetterImpl.isVowel(previousWordLastChar) && (previousWordLastChar == 's' || previousWordLastChar == 'z')
						&& LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar != '|') {
					words.set(i + 1, safeInsertFirst(words.get(i + 1), 'ʝ'));
					
					stripWordOfPotentialNasalAccent(words, i + 1);
					//Remove old y word
					words.remove(i);
					i--;
				}
				//|| y V -> ʝv
				else if(previousWordLastChar == '|' && LetterImpl.isVowel(nextWordFirstChar)
						&& nextWordFirstChar != '|') {
					words.set(i + 1, safeInsertFirst(words.get(i + 1), 'ʝ'));
					//Vowel no longer qualifies as nasal from leading ||
					stripWordOfPotentialNasalAccent(words, i + 1);
					//Remove old y word
					words.remove(i);
					i--;
				}
				// C y || Not given in examples. Want to change to i
				else if(!LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar == '|') {
					//Add char in current word
					String replacementWord = "'" + String.valueOf(previousWordLastChar) + "i";
					words.set(i, replacementWord);
					//Remove char in past char
					removePreviousWordLastChar(words, i);
				}
				// V y || Not given in examples. Want to change to j? maybe separate syllable i?
				else if(LetterImpl.isVowel(previousWordLastChar) && !LetterImpl.isVowel(nextWordFirstChar)
						&& previousWordLastChar != '|' && nextWordFirstChar == '|') {
					//Doing the same this as V y C, so reuse method
					yJoinVowelConsonant(words, i);
					//Remove old y word
					words.remove(i);
					i--;
				}
				// || y || Hyper specific. Sentence of jus Y
				else if(previousWordLastChar == '|' && nextWordFirstChar == '|') {
					words.set(i, "'i");
				}
			}
		}
	}

	private void yJoinVowelConsonant(List<String> words, int i) {
		//If the last vowel was an e it will change since it changes with codas.
		if(words.get(i - 1).charAt(words.get(i - 1).length() - 1) == 'e') {
			String replacementWord = words.get(i-1).substring(0, words.get(i-1).length() - 1);
			replacementWord = replacementWord.concat("ɛj");
			words.set(i -1, replacementWord);
		}else {
			words.set(i - 1, words.get(i - 1).concat("j"));
		}
	}

	private void yJoinConsonantVowel(List<String> words, int i, char previousWordLastChar) {
		//1)Add left consonant to j
		//TODO: if y doesn't require accent by itself we should only add it if following syll accentuated
		String replacementWord = "'" + String.valueOf(previousWordLastChar) + "j";
		//2) Add Next word's syllable
		//2.1) strip accent
		boolean wordBeganWithApostrophy = (words.get(i + 1).charAt(0) == '\'');
		if(wordBeganWithApostrophy) {
			words.set(i + 1, removeCharAt(words.get(i + 1), 0));
		}
		
		//2.2) strip nasal
		stripWordOfPotentialNasalAccent(words, i + 1);
		
		//2.3)Add right syllable
		String firstSyllableOfNextWord = getFirstSyllable(words.get(i + 1));
		replacementWord = replacementWord.concat(firstSyllableOfNextWord);
		
		//3) set replacement
		words.set(i, replacementWord);
		//4) remove left end
		removePreviousWordLastChar(words, i);
		//5) remove right beginning syllable
		words.set(i + 1, words.get(i + 1).substring(firstSyllableOfNextWord.length()));
		//remove dot. Dot will not exist if syllable removed length = word length.
		if(words.get(i + 1).length() > 0 && words.get(i + 1).charAt(0) == '.') {
			words.set(i + 1, removeCharAt(words.get(i + 1), 0));
		}
	}

	private void removePreviousWordLastChar(List<String> words, int currentIndex) {
		String replacementWord = words.get(currentIndex -1).substring(0, words.get(currentIndex - 1).length() -1);
		
		words.set(currentIndex - 1, replacementWord);
	}
	
	private String getFirstSyllable(String word) {
		int firstDot = word.indexOf('.');
		if(firstDot > 0) {
			return word.substring(0, firstDot);
		}else {
			//No dot, 1 syllable word;
			return word;
		}
	}

	private void stripWordOfPotentialNasalAccent(List<String> words, int i) {
		
		int nasalPoint = words.get(i).indexOf('̃');
		if(nasalPoint > -1) {//length higher than -1 means it does exist.
			words.set(i, removeCharAt(words.get(i), nasalPoint));
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
