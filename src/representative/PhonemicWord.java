package representative;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import representative.PhonemicWord.PhonemNotFoundException;
import sounds.CharacterClassification;

public class PhonemicWord implements Word{
	
	private String spanishWord;
	private List<Letter> letters;
	
	PhonemicWord(String inputWord) throws PhonemNotFoundException{
		spanishWord = inputWord;
		letters = new ArrayList<Letter>();
				
		char[] charArray = inputWord.toCharArray();
		
		for(int i = 0; i < charArray.length; i++) {
			Letter letter;
			//NUMBER 1 TODO: GET BASIC LOOP TO GET RID OF NON-PHONEMIC LETTERS			
			if(CharacterClassification.nonPhonems.contains(charArray[i]) || CharacterClassification.dependentPhonems.contains(charArray[i])
					|| CharacterClassification.switchPhonemes.contains(charArray[i])) {
				//change on the spot?
				//Simple one possibility switch
				if(charArray[i] == 'v') {
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("b");
					//TODO: add soundTypes
				}
				//The following letters do not share a character with their phonem.
				else if(charArray[i] == 'j'){
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("x");
				}
				else if(charArray[i] == 'u'){
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("u");
				}
				else if(charArray[i] == 'i'){
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("j");
				}
				//Things that require word length change
				else if(charArray[i] == 'h'){
					//Still need to add h for spanish word
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("");
				}else{
				
					throw new PhonemNotFoundException("Character: '" + String.valueOf(charArray[i]) + "' is not a legal character.");
				}
				
			}
			else {//a, b, d, e, f, k, m, n, o, p, s, t, w?,  
				letter = new LetterImpl(charArray[i]);
				letter.setPhonem(String.valueOf(charArray[i]));
			}
			
			letters.add(letter);
			
		}	
		
	}

	@Override
	public String getSpanishWord() {
		return spanishWord;
	}

	@Override
	public Letter getFirstLetter() {
		return letters.get(0);
	}

	@Override
	public Letter getLastLetter() {
		return letters.get(letters.size() - 1);
	}

	@Override
	public Letter getLetter(int index) {
		return letters.get(index);
	}
	
	@Override
	public String getIPAWord() {
		String word = "";
		for(Letter letter : letters) {
			word += letter.getPhonem();
		}
		return word;
	}
	
	public class PhonemNotFoundException extends Exception {
		 
	    public PhonemNotFoundException(String message) {
	        super(message);
	    }
	}
	
}
