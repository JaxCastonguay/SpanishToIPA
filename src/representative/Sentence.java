package representative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import errors.PhonemNotFoundException;

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
		//TODO: add , ø °
		if(spanishSentence.contains(".") || spanishSentence.contains("!") || spanishSentence.contains("?")) {//TODO: want to put other non chars/period catch here
			spanishSentence.replace('!', '|');
			spanishSentence.replace('?', '|');
			spanishSentence.replace('.', '|');
		}
		
		List<String> words = populateWordsList();
		StringBuilder string = new StringBuilder();
		
		moveApplicableEndConsonants(words);
		
		joinWords(words, string);
		
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
			words.add(word.getCustomPhoneticsWithSyllables(null));
		}
		return words;
	}

	private void joinWords(List<String> words, StringBuilder string) {
		for(int i = 0; i < words.size(); i++) {
			if(i != 0)
				string.append(".");
			string.append(words.get(i));
		}
	}

	private void moveApplicableEndConsonants(List<String> words) {
		for(int i = 0; i < words.size(); i++) {
			if(i > 0) {
				StringBuilder currentWord = new StringBuilder(words.get(i));
				boolean startsWithApostrophy = (currentWord.charAt(0) == '\'');
				if(startsWithApostrophy) {
					currentWord = new StringBuilder(currentWord.substring(1, currentWord.length()));
				}
				Letter currentWordFirstLetter = new LetterImpl(currentWord.charAt(0));
				
				if(currentWordFirstLetter.isVowel()) {
					String previousWord = words.get(i - 1);
					Letter previousWordLastLetter = new LetterImpl(previousWord.charAt(previousWord.length() - 1));
					if(!previousWordLastLetter.isVowel()) {
						currentWord.insert(0, previousWordLastLetter.getSpanishLetter());
						if(startsWithApostrophy)
							currentWord.insert(0, '\'');
						words.set(i, currentWord.toString());
						previousWord = previousWord.substring(0, previousWord.length() - 1);
						words.set(i - 1, previousWord);
					}
				}
			}
		}
	}
}
