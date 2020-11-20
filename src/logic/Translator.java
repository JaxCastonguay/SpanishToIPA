package logic;

import java.util.ArrayList;
import java.util.List;

import errors.PhonemNotFoundException;
import representative.Letter;
import representative.LetterImpl;
import sounds.CharacterClassification;

public class Translator {
	
	public Translator() {
		
	}
	public List<Letter> translateIntoPhonems(char[] charArray) throws PhonemNotFoundException {
		List<Letter> letters = new ArrayList<Letter>();
		boolean setNextPhonemBlank = false;
		for(int i = 0; i < charArray.length; i++) {
			Letter letter;
			if(setNextPhonemBlank) {
				letter = hModifier(charArray, i);
				setNextPhonemBlank = false;
			}
			else if(CharacterClassification.nonPhonems.contains(charArray[i]) || CharacterClassification.dependentPhonems.contains(charArray[i])
					|| CharacterClassification.switchPhonemes.contains(charArray[i]) || CharacterClassification.accentedVowels.contains(charArray[i]) 
					||charArray[i] == 'r') {
				//change on the spot?
				//Simple one possibility switch
				if(charArray[i] == 'v') {
					letter = vModifier(charArray, i);
					//TODO: add soundTypes
				}
				else if(CharacterClassification.accentedVowels.contains(charArray[i])) {
					letter = accentedModifier(charArray, i);
				}
				//The following letters do not share a character with their phonem.
				else if(charArray[i] == 'j'){
					letter = jModifier(charArray, i);
				}
				else if(charArray[i] == 'u'){
					letter = uModifier(charArray, i);
				}
				else if(charArray[i] == 'i'){
					letter = iModifier(charArray, i);
				}
				else if(charArray[i] == 'y') {
					letter = yModifier(charArray, i);
				}
				else if(charArray[i] == 'ñ'){
					letter = ñModifier(charArray, i);
				}
				else if(charArray[i] == 'z'){
					letter = zModifier(charArray, i);
				}
				//Letter-phonems that are not 1-to-1 length
				else if(charArray[i] == 'x') {
					letter = xModifier(charArray, i);
				}
				else if(charArray[i] == 'h'){
					letter = hModifier(charArray, i);
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
					letter = lModifier(charArray, i);
					if(letter.getPhonem() == "y")//ll needs the next skipped.
						setNextPhonemBlank = true;
				}
				else if(charArray[i] == 'r') {
					letter = rModifier(charArray, i);
					if(letter.getPhonem() == "r" && !(i == 0))//rr & needs to skip next, but r(first letter of word), needs to not be skipped.
						setNextPhonemBlank = true;
				}
				else if(charArray[i] == 'q') {
					letter = qModifier(charArray, i);
					//Hide next if it's 'u'
					if((charArray.length > i + 1) && (charArray[i+1] == 'u'))
						setNextPhonemBlank = true;
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
		
		return letters;
	}
	
	private Letter accentedModifier(char[] charArray, int i) throws PhonemNotFoundException {
		Letter letter;
		if(charArray[i] == 'á') {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("a");
		}else if(charArray[i] == 'é') {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("e");
		}
		else if(charArray[i] == 'í') {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("i");
		}
		else if(charArray[i] == 'ó') {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("o");
		}
		else if(charArray[i] == 'ú') {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("u");
		}
		else {
			throw new PhonemNotFoundException("Character: '" + String.valueOf(charArray[i]) + "' is not a legal character.");
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
			letter = qModifier(charArray, i);
		}
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
	
	private Letter hModifier(char[] charArray, int i) {
		Letter letter;
		//Still need to add h for Spanish word
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("");
		return letter;
	}
	
	private Letter iModifier(char[] charArray, int i) {
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
	
	private Letter lModifier(char[] charArray, int i) {
		Letter letter;
		if(charArray.length > i + 1 && charArray[i+1] == 'l') {
			//turn to y.
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("y");
			//Hide next
			//setNextPhonemBlank = true; (done after letter returned)
			
		}
		else {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("l");
		}
		return letter;
	}
	
	private Letter ñModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("ɲ");
		return letter;
	}
	
	private Letter qModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("k");
		return letter;
	}
	
	private Letter rModifier(char[] charArray, int i) {
		Letter letter;
		if((charArray.length > i + 1 && charArray[i+1] == 'r') || i == 0) {//i == 0 > first letter is r > long r
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("r");			
		}
		else {
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("ɾ");
		}
		return letter;
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
	
	private Letter vModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("b");
		return letter;
	}
	
	private Letter xModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("ks");
		return letter;
	}
	
	private Letter yModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("ʝ");
		return letter;
	}
	private Letter zModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("s");
		return letter;
	}
}