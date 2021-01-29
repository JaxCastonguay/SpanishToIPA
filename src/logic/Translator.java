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
	
	
	
	
	
	public String translateIntoBasePhonetics(char[] charArray) {
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
			else if(charArray[i] == 'ɟ') {
				charArray[i] = ɟPhoneticExamination(charArray, i);
			}
			else if(charArray[i] == 'e') {
				charArray[i] = ePhoneticExamination(charArray, i);
			}
			else if(charArray[i] == 'ɾ') {
				charArray[i] = ɾPhoneticExamination(charArray, i);
			}
			else if(charArray[i] == 'l') {
				//See which is returned
				CharPair lPair = lPhoneticExamination(charArray, i);
				// return char, isDental
				//insert letter
				charArray[i] = lPair.getResponseChar();
				if(lPair.getAdditionalChar() != '*') {
					charArray = resizedCharArrayWithAddedChar(charArray, i, lPair);
					i++;
				}
			}
			else if(charArray[i] == 'm'
					|| charArray[i] == 'n'
					|| charArray[i] == 'ɲ') {
				CharPair nPair = mnɲPhoneticExamination(charArray, i);
				
				charArray[i] = nPair.getResponseChar();
				if(nPair.getAdditionalChar() != '*') {
					charArray = resizedCharArrayWithAddedChar(charArray, i, nPair);
					i++;
				}
			}
		}
			
		 return new String(charArray);
	}
	private char[] resizedCharArrayWithAddedChar(char[] charArray, int i, CharPair lPair) {
		//if needed:
		//resize array
		int newLen = charArray.length + 1;
		char[] newArray = new char[newLen];
		int dentalPos = i + 1;
		//copy vals
		for(int k = 0; k < newLen; k++) {
			if(k < dentalPos) {
				newArray[k] = charArray[k];
			}else if(k == dentalPos) {
				//insert dental
				newArray[k] = lPair.getAdditionalChar();
			}
			else {
				newArray[k] = charArray[k - 1];
			}
		}
		charArray = newArray;
		return charArray;
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
		
		if(i > 1 //1 because both index - 2 will be looked at 
				&& charArray[i - 1] == '̪'
				&& (CharacterClassification.bdgNonModifiers.contains(charArray[i - 2])
						|| charArray[i - 2] == 'l')) {
			return 'd';
		}
		else if (i != 0 
				&& !CharacterClassification.bdgNonModifiers.contains(charArray[i - 1]) 
				&& charArray[i - 1] != 'l') {
			
			return 'ð';
		}
		else {
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
	
	private char ePhoneticExamination(char[] charArray, int i) {
		
		//option 1: next is [r] or [x] //TODO: will need to refine to check if /ɾ/ changes to [r]
		if(i < charArray.length - 1
				&& (charArray[i+1] == 'r' || charArray[i+1] == 'x')) {
			return 'ɜ';
		}
		//option 2: next is coda & coda is not [s]. Hint: yes even [j] it would seem (ex: peine)
		else if(i < charArray.length - 1
				&& isCoda(charArray, i + 1)
				&& charArray[i + 1] != 's') {
			return 'ɜ';		
		}
		else {
			return 'e';
		}
	}
	
	private char ɾPhoneticExamination(char[] charArray, int i) {
		//r for nasal, lateral o sibilante
		if(isRolledR(charArray, i)) {
			return 'r';			
		}
		
		return 'ɾ';
	}
	
	private CharPair lPhoneticExamination(char[] charArray, int i) {
		//set * for no addition
		//only need to check last of the dentales strings because a '̪' is an automatic dental
		if(i < charArray.length - 1
				&& CharacterClassification.dentales.contains(String.valueOf(charArray[i + 1]))) {
			return new CharPair('l', '̪');
		}
		else if(i < charArray.length - 1
				&& CharacterClassification.palatales.contains(charArray[i + 1])) {
			return new CharPair('ʎ', '*');
		}
		return new CharPair('l', '*');
	}
	
	private CharPair mnɲPhoneticExamination(char[] charArray, int i) {
		
		if(i < charArray.length - 1
				&& CharacterClassification.bilabiales.contains(charArray[i + 1])) {
			return new CharPair('m', '*');
		}
		else if(i < charArray.length - 1
				&& CharacterClassification.labiodentales.contains(charArray[i + 1])) {
			return new CharPair('ɱ', '*');
		}
		else if(i < charArray.length - 1
				&& CharacterClassification.palatales.contains(charArray[i + 1])) {
			return new CharPair('ɲ', '*');
		}
		else if(i < charArray.length - 1
				&& CharacterClassification.velares.contains(charArray[i + 1])) {
			return new CharPair('ŋ', '*');
		}
		else if(i < charArray.length - 1
				&& (charArray[i + 1] == 'n'
					|| charArray[i + 1] == 'l')) {
			if(i < charArray.length - 2
					&& charArray[i + 2] == '̪') {
				return new CharPair('n', '̪');
			}
			else
			{
				return new CharPair('n', '*');
			}
		}
		else if(i < charArray.length - 1
				&& CharacterClassification.alveolares.contains(String.valueOf(charArray[i + 1]))) {
			return new CharPair('n', '*');
		}
		else if(i < charArray.length - 1
				&& CharacterClassification.dentales.contains(String.valueOf(charArray[i + 1]))) {
			return new CharPair('n', '̪');
		}
		
		//check aveolar before dental for easy
		
		//Only chance this happens is uvular or glotal next
		return new CharPair(charArray[i], '*');
	}
	
	
	//TODO: neutralization> p>b, t>d, k>g (all to approx)
	
	//TODO: nasal
	
	private Boolean isCoda(char[] charArray, int indexOfPotentialCoda) {
		//1) potential coda is consonant
		//2) next letter is consonant
		if(indexOfPotentialCoda + 1 < charArray.length
				&& CharacterClassification.consonants.contains(charArray[indexOfPotentialCoda])
				&& CharacterClassification.consonants.contains(charArray[indexOfPotentialCoda + 1])) {
			//3) they do not for a consonant blend
			char potentialCoda = charArray[indexOfPotentialCoda];
			char nextLetter = charArray[indexOfPotentialCoda + 1];
			String potentialBlend = String.valueOf(potentialCoda) + String.valueOf(nextLetter);
			if(!CharacterClassification.consonantBlends.contains(potentialBlend)) {
				return true;
			}
			else {
				return false;

			}
					}
		//End of word consonant
		else if(indexOfPotentialCoda == charArray.length - 1
				&& CharacterClassification.consonants.contains(charArray[indexOfPotentialCoda])) {
			return true;
		}
		//j or w as coda
		else if(indexOfPotentialCoda > 0
				&& CharacterClassification.vowels.contains(charArray[indexOfPotentialCoda - 1])
				&& CharacterClassification.diptongAsConsonants.contains(charArray[indexOfPotentialCoda])
				&& CharacterClassification.consonants.contains(charArray[indexOfPotentialCoda+1])) {
			return true;
		}
		
		return false;
	}
	
	private boolean isRolledR(char[] charArray, int i) {
		//If previous char is the last of any of these strings we return true. 
		//This is because in this specific case, any '̪' or '̺' at all results in a change,
		//and those are the only two letter combos for these classifications.
		//Note: due to this I could've just changed the classifications to a char list, but this allows easier expansion of phonetic rules later.
		return i != 0 
				&& (CharacterClassification.nasales.contains(String.valueOf(charArray[i - 1]))
				|| CharacterClassification.laterales.contains(String.valueOf(charArray[i - 1]))
				|| CharacterClassification.sibilante.contains(String.valueOf(charArray[i - 1])));
	}
	
	
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
	
	
	
	private class CharPair{
		private char responseChar;
		private char additionalChar;
		
		public CharPair(char responseChar, char additionalChar) {
			this.responseChar = responseChar;
			this.additionalChar = additionalChar;
		}

		public char getResponseChar() {
			return responseChar;
		}

		public char getAdditionalChar() {
			return additionalChar;
		}
		
	}
}
