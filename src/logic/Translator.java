package logic;

import java.util.ArrayList;
import java.util.List;

import errors.PhonemNotFoundException;
import representative.Letter;
import representative.LetterImpl;
import sounds.AlternatePronunciations;
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
					if(letter.getPhonem() == "ɟ")//ll needs the next skipped.
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
	
	
	
	
	
	public String translateIntoBasePhonetics(char[] charArray, List<AlternatePronunciations> alternatePronunciations) {
		for(int i = 0; i < charArray.length; i++) {
			
			if(charArray[i] == 'b') {
				charArray[i] = bPhoneticExamination(charArray, i);
			}
			else if(charArray[i] == 'd') {
				charArray[i] = dPhoneticExamination(charArray, i);
			}
			else if(charArray[i] == 'g') {
				charArray[i] = gPhoneticExamination(charArray, i);
			}
			else if(charArray[i] == 's') {
				charArray[i] = sPhoneticExamination(charArray, i);
			}
		}
			
		 return null;
	}
	
	public String translateIntoCustomPhonetics(char[] charArray, List<CustomPhoneticsDTO> customPhonetics) {
		List<Character> bases = new ArrayList<Character>();
		if(customPhonetics != null && customPhonetics.size() > 0) {
		for(int i = 0; i< customPhonetics.size(); i++)
			bases.add(customPhonetics.get(i).getBase());	
		}

		
		
		for (int i = 0; i < charArray.length; i++) {
			if (bases.contains(charArray[i])) {
				//Get DTO index based on base
				int index = bases.indexOf(charArray[i]);
				CustomPhoneticsDTO phonetic = customPhonetics.get(index);
				
				//Check if before or after
				if(phonetic.determineIsCheckingBefore()) {
					//check if previous exists and is changer
					
					if (i == 0 && phonetic.determineisEffectedByPause()
							&& !phonetic.determineisChangesUnlessModifiers()) {
						charArray[i] = phonetic.getReplacement();
					} else if(i - 1 >=0 
							&& phonetic.getModifiers().contains(charArray[i - 1])) {
						charArray[i] = phonetic.getReplacement();
					}else if(i - 1 >=0 
							&& phonetic.determineisChangesUnlessModifiers() //If size is not zero then we will change if nonModifiers DOESN'T contain
							&& !phonetic.getModifiers().contains(charArray[i - 1])) {
						charArray[i] = phonetic.getReplacement();
					}
				} else {
					//check if next exists and is changer
					if(i + 1 < charArray.length 
							&& phonetic.getModifiers().contains(charArray[i + 1])) {
						charArray[i] = phonetic.getReplacement();
					}//TODO: Written but untested.
					else if(i + 1 < charArray.length
							&& phonetic.determineisChangesUnlessModifiers()
							&& !phonetic.getModifiers().contains(charArray[i + 1])) {
						charArray[i] = phonetic.getReplacement();
					}
				}
			}
		}

		return new String(charArray);
	}
	
	
	
	
	private char bPhoneticExamination(char[] charArray, int i) {
		if (i != 0 
				&& !CharacterClassification.bdgNonModifiers.contains(charArray[i - 1])) {
			return 'ß';
		} else {
			return 'b';
		}
	}
	
	private char dPhoneticExamination(char[] charArray, int i) {
		if (i != 0 
				&& !CharacterClassification.bdgNonModifiers.contains(charArray[i - 1]) 
				&& charArray[i - 1] != 'l') {
			return 'ð';
		} else {
			return 'd';
		}
	}
	
	private char gPhoneticExamination(char[] charArray, int i) {
		if (i != 0 
				&& !CharacterClassification.bdgNonModifiers.contains(charArray[i - 1])) {
			return 'Ɣ';
		} else {
			return 'g';
		}
	}
	
	private char sPhoneticExamination(char[] charArray, int i) {
		if (i < charArray.length - 1
				&& CharacterClassification.sonoras.contains(charArray[i + 1])) {
			return 'z';
		} else {
			return 's';
		}
	}
	
	//TODO: This is one valid config. But many places just use all ʝ.
	private char ɟPhoneticExamination(char[] charArray, int i) {
		if (i != 0 
				&& !CharacterClassification.bdgNonModifiers.contains(charArray[i - 1]) 
				&& charArray[i - 1] != 'l') {
			return 'ʝ';
		} else {
			return 'ɟ';
		}
	}
	
	//TODO: n
	
	//TODO: e
	
	//TODO: neutralization> p>b, t>d, k>g (all to approx)
	
	//TODO: nasal
	
	
	
	
	/*##########################################################################################
	 *### Phonemic modifiers ###################################################################
	  ##########################################################################################*/
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
			//turn to y.(ɟ)
			letter = new LetterImpl(charArray[i]);
			letter.setPhonem("ɟ");
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
		letter.setPhonem("ɟ");
		return letter;
	}
	private Letter zModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("s");
		return letter;
	}
}
