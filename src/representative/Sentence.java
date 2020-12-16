package representative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import errors.PhonemNotFoundException;

public class Sentence {
	
	private String spanishSentence;
	
	
	public Sentence(String SpanishSentence) {
		this.spanishSentence = SpanishSentence;
	}
	
	public String getPhoneticSentence() throws PhonemNotFoundException {
		//Scrub input
		//lowercase - done
		//space - done
		//no numbers
		//period - done
		//no special chars besides '|'
		if(spanishSentence.matches("\\d+")) {//TODO: this isn't working
			throw new PhonemNotFoundException("Numbers symbols are not allowed.");
		}
		if(spanishSentence.contains(".") || spanishSentence.contains("!") || spanishSentence.contains("?")) {//TODO: want to put other non chars/period catch here
			spanishSentence.replace('!', '|');
			spanishSentence.replace('?', '|');
			spanishSentence.replace('.', '|');
		}
		//TODO: catch all non-pause special characters
		
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
			PhonemicWord word = new PhonemicWord(list.get(i));
			words.add(word.getPhoneticsWithSyllables(null));
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
