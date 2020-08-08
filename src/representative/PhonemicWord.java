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
		boolean setNextPhonemBlank = false;
				
		char[] charArray = inputWord.toCharArray();
		
		for(int i = 0; i < charArray.length; i++) {
			Letter letter;
			//NUMBER 1 TODO: GET BASIC LOOP TO GET RID OF NON-PHONEMIC LETTERS
			if(setNextPhonemBlank) {
				letter = new LetterImpl(charArray[i]);
				letter.setPhonem("");
				setNextPhonemBlank = false;
			}
			else if(CharacterClassification.nonPhonems.contains(charArray[i]) || CharacterClassification.dependentPhonems.contains(charArray[i])
					|| CharacterClassification.switchPhonemes.contains(charArray[i]) || charArray[i] == 'r') {
				//change on the spot?
				//Simple one possibility switch
				if(charArray[i] == 'v') {
					letter = vModifier(charArray, i);
					//TODO: add soundTypes
				}
				//The following letters do not share a character with their phonem.
				else if(charArray[i] == 'j'){
					letter = jModifier(charArray, i);
				}
				else if(charArray[i] == 'u'){
					letter = uModifier(charArray, i);
				}
				else if(charArray[i] == 'i'){
					letter = imodifier(charArray, i);
				}
				else if(charArray[i] == 'y') {
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("ʝ");
				}
				//Letters that change depending on the following characters
				else if(charArray[i] == 'c'){
					letter = cModifier(charArray, i);
				}
				else if(charArray[i] == 'g') {
					letter = gModifier(charArray, i);
				}
				//Letters that require word length change
				else if(charArray[i] == 'l') {
					if(charArray.length > i + 1 && charArray[i+1] == 'l') {
						//turn to y.
						letter = new LetterImpl(charArray[i]);
						letter.setPhonem("y");
						//Hide next
						setNextPhonemBlank = true;
						
					}
					else {
						letter = new LetterImpl(charArray[i]);
						letter.setPhonem("l");
					}
				}
				else if(charArray[i] == 'r') {
					if((charArray.length > i + 1 && charArray[i+1] == 'r') || i == 0) {//i == 0 > first letter is r > long r
						letter = new LetterImpl(charArray[i]);
						letter.setPhonem("r");
						//Hide next
						if(!(i == 0)) {
							setNextPhonemBlank = true;
						}
						
					}
					else {
						letter = new LetterImpl(charArray[i]);
						letter.setPhonem("ɾ");
					}
				}
				else if(charArray[i] == 'q') {
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("k");
					//Hide next
					if((charArray.length > i + 1) && (charArray[i+1] == 'u'))
						setNextPhonemBlank = true;
				}
				else if(charArray[i] == 'x') {
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("ks");
				}
				else if(charArray[i] == 'h'){
					//Still need to add h for Spanish word
					letter = new LetterImpl(charArray[i]);
					letter.setPhonem("");
				}
				else{
					throw new PhonemNotFoundException("Character: '" + String.valueOf(charArray[i]) + "' is not a legal character.");
				}
				
			}
			else {//a, b, d, e, f, k, m, n, o, p, s, t, w?,  
				letter = new LetterImpl(charArray[i]);
				letter.setPhonem(String.valueOf(charArray[i]));
			}
			//System.out.println(String.valueOf(charArray[i]) + " char results in: " + letter.getPhonem());
			letters.add(letter);
			
		}
		//System.out.println("End of method: letter count: " + String.valueOf(letters.size() + " word: " + getIPAWord()));
		
	}

	private Letter uModifier(char[] charArray, int i) {
		Letter letter;
		if(((i > 0) && CharacterClassification.strongVowels.contains(charArray[i - 1])) //Previous
				|| ((charArray.length > i + 1) && CharacterClassification.strongVowels.contains(charArray[i + 1]))) {//Next
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("w");
		}
		else {						
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("u");
		}
		return letter;
	}

	private Letter imodifier(char[] charArray, int i) {
		Letter letter;
		if(((i > 0) && CharacterClassification.strongVowels.contains(charArray[i - 1])) //Previous
				|| ((charArray.length > i + 1) && CharacterClassification.strongVowels.contains(charArray[i + 1]))) {//Next
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("j");
		}
		else {						
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("i");
		}
		return letter;
	}

	private Letter jModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("x");
		return letter;
	}

	private Letter vModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("b");
		return letter;
	}

	private Letter gModifier(char[] charArray, int i) {
		Letter letter;
		if(charArray.length > i + 1 && (charArray[i+1] == 'i' || charArray[i+1] == 'e')) {
			letter = jModifier(charArray, i);
		}else {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("g");
		}
		return letter;
	}

	private Letter cModifier(char[] charArray, int i) {
		Letter letter;
		if(charArray.length > i + 1 && charArray[i+1] == 'h') {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("ʧ");
		}
		else if(charArray.length > i + 1 && (charArray[i+1] == 'i' || charArray[i+1] == 'e')) {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("s");
		}
		else{
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("k");
		}
		return letter;
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
		for(int i = 0; i < letters.size(); i++) {
			word += letters.get(i).getPhonem();
		}
		return word;
	}
	
	public class PhonemNotFoundException extends Exception {
		 
	    public PhonemNotFoundException(String message) {
	        super(message);
	    }
	}
	
	public class IllegalEndingLetterException extends Exception {
		 
	    public IllegalEndingLetterException(String message) {
	        super(message);
	    }
	}
	
}
