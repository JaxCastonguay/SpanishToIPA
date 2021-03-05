package representative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import errors.PhonemNotFoundException;
import logic.Translator;

public class Sentence {
	
	private String spanishSentence;
	
	
	public Sentence(String SpanishSentence) {
		this.spanishSentence = SpanishSentence;
	}
	
	public String getPhoneticSentence() throws PhonemNotFoundException {
		//Scrub input
		Pattern pattern = Pattern.compile("^[a-zA-Z·ÈÌÛ˙¡…Õ”⁄¸‹Ò—,.?ø!° ]*$");
		Matcher matcher = pattern.matcher(spanishSentence);
			
		if(!matcher.find()) {
			throw new PhonemNotFoundException("Only letter and punctuation characters are allowed");
		}
		
		if(spanishSentence.contains(".") || spanishSentence.contains("!") || spanishSentence.contains("?")) {
			//TODO: want to put other non chars/period catch here
			spanishSentence.replace('!', '|');
			spanishSentence.replace('°', '|');
			spanishSentence.replace('?', '|');
			spanishSentence.replace('ø', '|');
			spanishSentence.replace('.', '|');
			spanishSentence.replace(',', '|');
		}
		
		List<String> words = populateWordsList();
		StringBuilder string = new StringBuilder();
		
		adjustConsonantPlacements(words);
		AlterOuterPhonetics(words);
		
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
			
			//first letter will never be coda
			String currentWordLastCharUpdated = translator.getPhoneticsBasedOnNextChar(Character.toString(currentWord.charAt(currentWord.length() - 1)), nextWord.charAt(0), false);
			currentWord = currentWord.replace(currentWord.length() - 1, currentWord.length(), currentWordLastCharUpdated);
			
			char secondToLastOfCurrent = '|';
			if(currentWord.length() > 1) {
				secondToLastOfCurrent = currentWord.charAt(currentWord.length() - 2);
			}
			char nextWordFirstCharUpdated = translator.getPhoneticBasedOnPreviousChars(secondToLastOfCurrent, currentWord.charAt(currentWord.length() - 1), nextWord.charAt(0));
			nextWord = nextWord.replace(0, 1, Character.toString(nextWordFirstCharUpdated));
			
			if (wordBeganWithApostrophy) {
				nextWord.insert(0, '\'');
			}
			words.set(i, currentWord.toString());
			if(i < words.size() - 1) {
				words.set(i + 1, nextWord.toString());
			}
		}
	}
}
