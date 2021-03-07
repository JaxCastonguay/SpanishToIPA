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
					|| charArray[i] == 'r' || charArray[i] == 'p' || charArray[i] == 't' || charArray[i] == 'k') {
				//Simple one possibility switch
				if(charArray[i] == 'v') {
					letter = vModifier(charArray, i);
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
				else if(charArray[i] == 'p') {
					letter = pModifier(charArray, i);
				}
				else if(charArray[i] == 't') {
					letter = tModifier(charArray, i);
				}
				else if(charArray[i] == 'k') {
					letter = kModifier(charArray, i);
				}
				else{
					throw new PhonemNotFoundException("Character: '" + String.valueOf(charArray[i]) + "' is not a legal character.");
				}
				
			}
			else {//a, b, d, e, f, m, n, o, s, w?,  
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
				charArray[i] = bPhoneticExamination(safeReturnPreviousChar(charArray, i));
			}
			else if(charArray[i] == 'd') {
				charArray[i] = dPhoneticExamination(safeReturnPreviousChar(charArray, i), safeReturnPreviousChar(charArray, i-1));
			}
			else if(charArray[i] == 'g') {
				charArray[i] = gPhoneticExamination(safeReturnPreviousChar(charArray, i));
			}
			else if(charArray[i] == 's') {
				if(i + 1 < charArray.length
						&& charArray[i+1] == 'r') {
					//remove here
					charArray = resizedCharArrayRemoveI(charArray, i);
				}else {
					charArray[i] = sPhoneticExamination(safeReturnNextChar(charArray, i));
				}
			}
			else if(charArray[i] == 'ɟ') {
				charArray[i] = ɟPhoneticExamination(safeReturnPreviousChar(charArray, i));
			}
			else if(charArray[i] == 'e') {
				charArray[i] = ePhoneticExamination(safeReturnNextChar(charArray, i), isCoda(charArray, i+1));
				if(isNasalVowel(safeReturnPreviousChar(charArray, i), safeReturnNextChar(charArray, i))) {
					charArray = resizedCharArrayWithAddedChar(charArray, i, '̃');
					i++;
				}
			}
			else if(charArray[i] == 'ɾ') {
				charArray[i] = ɾPhoneticExamination(safeReturnPreviousChar(charArray, i));
			}
			else if(charArray[i] == 'l') {
				//See which is returned
				CharPair lPair = lPhoneticExamination(safeReturnNextChar(charArray, i));
				// return char, isDental
				//insert letter
				charArray[i] = lPair.getResponseChar();
				if(lPair.getAdditionalChar() != '*') {
					charArray = resizedCharArrayWithAddedChar(charArray, i, lPair.getAdditionalChar());
					i++;
				}
			}
			else if(charArray[i] == 'm'
					|| charArray[i] == 'n'
					|| charArray[i] == 'ɲ') {
				CharPair nPair = mnɲPhoneticExamination(charArray[i], safeReturnNextChar(charArray, i), safeReturnNextChar(charArray, i+1));
				
				charArray[i] = nPair.getResponseChar();
				if(nPair.getAdditionalChar() != '*') {
					charArray = resizedCharArrayWithAddedChar(charArray, i, nPair.getAdditionalChar());
					i++;
				}
			}
			else if(CharacterClassification.vowels.contains(charArray[i])) {
				//Note: e will be handled above.
				if(isNasalVowel(safeReturnPreviousChar(charArray, i), safeReturnNextChar(charArray, i))) {
					charArray = resizedCharArrayWithAddedChar(charArray, i, '̃');
					i++;
				}
			}
		}
			
		 return new String(charArray);
	}
		
	public String getPhoneticsBasedOnNextChar(String currentChar, char nextChar, boolean nextCharIsCoda) {
		if(currentChar.equals("l")) {
			CharPair lPair = lPhoneticExamination(nextChar);
			// return char, isDental
			//insert letter
			currentChar = Character.toString(lPair.getResponseChar());
			if(lPair.getAdditionalChar() != '*') {
				currentChar+= Character.toString(lPair.getAdditionalChar());
			}
		}else if(currentChar.equals("s")){
			if(nextChar == 'r') {
				//remove here
				currentChar = "";
			}
			else {
				return Character.toString(sPhoneticExamination(nextChar));
			}
		} else if(currentChar.equals("e")) {
			return Character.toString(ePhoneticExamination(nextChar, nextCharIsCoda));
		}
		
		return currentChar;
	}
	
	public char getPhoneticBasedOnPreviousChars(char previousPreviousChar, char previousChar, char currentChar) {
		if(currentChar == 'b') {
			return bPhoneticExamination(previousChar);
		}
		else if(currentChar == 'd') {
			return dPhoneticExamination(previousChar, previousPreviousChar);
		}
		else if(currentChar == 'g') {
			return gPhoneticExamination(previousChar);
		}
		else if(currentChar == 'ɟ') {
			return ɟPhoneticExamination(previousChar);
		}
		else if(currentChar == 'ɾ') {
			return ɾPhoneticExamination(previousChar);
		}
		
		return currentChar;
	} 
	
	//##############################################
	//Logic Helpers
	//##############################################
	
	private char[] resizedCharArrayWithAddedChar(char[] charArray, int i, char newChar) {
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
				newArray[k] = newChar;
			}
			else {
				newArray[k] = charArray[k - 1];
			}
		}
		charArray = newArray;
		return charArray;
	}
	
	private char[] resizedCharArrayRemoveI(char[] charArray, int i) {
		//if needed:
		//resize array
		int newLen = charArray.length - 1;
		char[] newArray = new char[newLen];
		//copy vals
		for(int k = 0; k < newLen; k++) {
			if(k < i) {
				newArray[k] = charArray[k];
			}
			else { //k >= i
				newArray[k] = charArray[k + 1];
			}
		}
		charArray = newArray;
		return charArray;
	}
	
	//NOTE: currently does not handle if a word was already translated and has syllable markings (.)
	public Boolean isCoda(char[] charArray, int indexOfPotentialCoda) {
		//TODO: this should really get more testing
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
				&& indexOfPotentialCoda + 1 < charArray.length
				&& CharacterClassification.vowels.contains(charArray[indexOfPotentialCoda - 1])
				&& CharacterClassification.diptongAsConsonants.contains(charArray[indexOfPotentialCoda])
				&& CharacterClassification.consonants.contains(charArray[indexOfPotentialCoda+1])) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRolledR(char previousChar) {
		//If previous char is the last of any of these strings we return true. 
		//This is because in this specific case, any '̪' or '̺' at all results in a change,
		//and those are the only two letter combos for these classifications.
		//Note: due to this I could've just changed the classifications to a char list, but this allows easier expansion of phonetic rules later.
		return CharacterClassification.nasales.contains(String.valueOf(previousChar))
				|| CharacterClassification.laterales.contains(String.valueOf(previousChar))
				|| CharacterClassification.sibilante.contains(String.valueOf(previousChar));
	}
		
	private char safeReturnPreviousChar(char[] charArray, int i) {
		if(i > 0) {
			return charArray[i-1];
		}
		else {
			return '|';
		}
	}
	
	private char safeReturnNextChar(char[] charArray, int i) {
		if(i < charArray.length - 1) {
			return charArray[i+1];
		}
		else {
			return '|';
		}
	}
	
	private boolean isNasalVowel(char previousChar, char nextChar) {
		
		if((CharacterClassification.nasales.contains(String.valueOf(previousChar))
				|| previousChar == '|')
			&& CharacterClassification.nasales.contains(String.valueOf(nextChar))) {
			return true;
		}
		
		return false;
	}

// I've move away from this logic but may want to revisit it later
//	public String translateIntoCustomPhonetics(char[] charArray, List<CustomPhoneticsDTO> customPhonetics) {
//		List<Character> bases = new ArrayList<Character>();
//		if(customPhonetics != null && customPhonetics.size() > 0) {
//		for(int i = 0; i< customPhonetics.size(); i++)
//			bases.add(customPhonetics.get(i).getBase());	
//		}
//
//		
//		
//		for (int i = 0; i < charArray.length; i++) {
//			if (bases.contains(charArray[i])) {
//				//Get DTO index based on base
//				int index = bases.indexOf(charArray[i]);
//				CustomPhoneticsDTO phonetic = customPhonetics.get(index);
//				
//				//Check if before or after
//				if(phonetic.determineIsCheckingBefore()) {
//					//check if previous exists and is changer
//					
//					if (i == 0 && phonetic.determineisEffectedByPause()
//							&& !phonetic.determineisChangesUnlessModifiers()) {
//						charArray[i] = phonetic.getReplacement();
//					} else if(i - 1 >=0 
//							&& phonetic.getModifiers().contains(charArray[i - 1])) {
//						charArray[i] = phonetic.getReplacement();
//					}else if(i - 1 >=0 
//							&& phonetic.determineisChangesUnlessModifiers() //If size is not zero then we will change if nonModifiers DOESN'T contain
//							&& !phonetic.getModifiers().contains(charArray[i - 1])) {
//						charArray[i] = phonetic.getReplacement();
//					}
//				} else {
//					//check if next exists and is changer
//					if(i + 1 < charArray.length 
//							&& phonetic.getModifiers().contains(charArray[i + 1])) {
//						charArray[i] = phonetic.getReplacement();
//					}//TODO: Written but untested.
//					else if(i + 1 < charArray.length
//							&& phonetic.determineisChangesUnlessModifiers()
//							&& !phonetic.getModifiers().contains(charArray[i + 1])) {
//						charArray[i] = phonetic.getReplacement();
//					}
//				}
//			}
//		}
//
//		return new String(charArray);
//	}
	
	//#############################################
	//PHONETIC examinations
	//###########################################
	
	private char bPhoneticExamination(char previousChar) {
		//check char before
		if (!CharacterClassification.bdgNonModifiers.contains(previousChar)) {
			return 'ß';
		} else {
			return 'b';
		}
	}
	
	//private char dPhoneticExamination(char[] charArray, int i) {
	private char dPhoneticExamination(char iMinus1, char iMinus2) {	
		
		if(iMinus2 != '|' //1 because both index - 2 will be looked at 
				&& iMinus1 == '̪'
				&& (CharacterClassification.bdgNonModifiers.contains(iMinus2)
						|| iMinus2 == 'l')) {
			return 'd';
		}
		else if (iMinus1 != '|' 
				&& !CharacterClassification.bdgNonModifiers.contains(iMinus1) 
				&& iMinus1 != 'l') {
			
			return 'ð';
		}
		else {
			return 'd';
		}
	}
	
	private char gPhoneticExamination(char previousChar) {
		if (!CharacterClassification.bdgNonModifiers.contains(previousChar)) {
			return 'Ɣ';
		} else {
			return 'g';
		}
	}
	
	private char sPhoneticExamination(char nextChar) {
		if (CharacterClassification.sonoras.contains(nextChar)) {
			return 'z';
		} else {
			return 's';
		}
	}
	
	//TODO: This is one valid config. But many places just use all ʝ.
	private char ɟPhoneticExamination(char previousChar) {
		if (!CharacterClassification.bdgNonModifiers.contains(previousChar) 
				&& previousChar != 'l') {
			return 'ʝ';
		} else {
			return 'ɟ';
		}
	}
	
	private char ePhoneticExamination(char nextChar, boolean nextCharIsCoda) {
		
		//option 1: next is [r] or [x]
		if(nextChar == 'r' || nextChar == 'x') {
			return 'ɜ';
		}
		//option 2: next is coda & coda is not [s]. Hint: yes even [j] it would seem (ex: peine)
		else if(nextCharIsCoda
				&& nextChar != 's') {
			return 'ɜ';		
		}
		else {
			return 'e';
		}
	}
	
	private char ɾPhoneticExamination(char previousChar) {
		//r for nasal, lateral o sibilante
		if(isRolledR(previousChar)) {
			return 'r';			
		}
		
		return 'ɾ';
	}
	
	private CharPair lPhoneticExamination(char nextChar) {
		//set * for no addition
		//only need to check last of the dentales strings because a '̪' is an automatic dental
		if(CharacterClassification.dentales.contains(String.valueOf(nextChar))) {
			return new CharPair('l', '̪');
		}
		else if(CharacterClassification.palatales.contains(nextChar)) {
			return new CharPair('ʎ', '*');
		}
		return new CharPair('l', '*');
	}
	
	private CharPair mnɲPhoneticExamination(char currentChar, char nextChar, char nextNextChar) {
		
		if(CharacterClassification.bilabiales.contains(nextChar)) {
			return new CharPair('m', '*');
		}
		else if(CharacterClassification.labiodentales.contains(nextChar)) {
			return new CharPair('ɱ', '*');
		}
		else if(CharacterClassification.palatales.contains(nextChar)) {
			return new CharPair('ɲ', '*');
		}
		else if(CharacterClassification.velares.contains(nextChar)) {
			return new CharPair('ŋ', '*');
		}
		else if(nextChar == 'n'
					|| nextChar == 'l') {
			if(nextNextChar == '̪') {
				return new CharPair('n', '̪');
			}
			else
			{
				return new CharPair('n', '*');
			}
		}
		else if(CharacterClassification.alveolares.contains(String.valueOf(nextChar))) {
			return new CharPair('n', '*');
		}
		else if(CharacterClassification.dentales.contains(String.valueOf(nextChar))) {
			return new CharPair('n', '̪');
		}
		
		//check aveolar before dental for easy
		
		//Only chance this happens is uvular or glotal next
		return new CharPair(currentChar, '*');
	}
		
	//TODO: nasal (vowels)
	
	/*##########################################################################################
	 *### PHONEMIC modifiers ###################################################################
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
		Letter letter = new LetterImpl(charArray[i]);
		if(charArray.length > i + 1 && charArray[i+1] == 'h') {
			letter.setPhonem("ʧ");
		}
		else if(charArray.length > i + 1 && (charArray[i+1] == 'i' || charArray[i+1] == 'e')) {
			letter.setPhonem("s");
		}
		else if(isCoda(charArray, i)) {
			letter.setPhonem("g");
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
		Letter letter = new LetterImpl(charArray[i]);
		if(((i > 0) && (CharacterClassification.strongVowels.contains(charArray[i - 1]) || CharacterClassification.accentedVowels.contains(charArray[i - 1]))) //Previous
				|| ((charArray.length > i + 1) && (CharacterClassification.strongVowels.contains(charArray[i + 1]) || CharacterClassification.accentedVowels.contains(charArray[i + 1])))) {//Next
			letter.setPhonem("j");
		}
		else {						
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
	
	private Letter kModifier(char[] charArray, int i) {
		Letter letter = new LetterImpl(charArray[i]);
		if(isCoda(charArray, i)) {
			letter.setPhonem("g");
		}else {
			letter.setPhonem("k");
		}
		return letter;
	}
	
	private Letter lModifier(char[] charArray, int i) {
		Letter letter = new LetterImpl(charArray[i]);
		if(charArray.length > i + 1 && charArray[i+1] == 'l') {
			//turn to y.(ɟ)
			letter.setPhonem("ɟ");
			//Hide next
			//setNextPhonemBlank = true; (done after letter returned)
			
		}
		else {
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
	
	private Letter pModifier(char[] charArray, int i) {
		Letter letter = new LetterImpl(charArray[i]);
		if(isCoda(charArray, i)) {
			letter.setPhonem("b");
		}else {
			letter.setPhonem("p");
		}
		return letter;
	}
	
	private Letter qModifier(char[] charArray, int i) {
		Letter letter;
		letter = new LetterImpl(charArray[i]);
		letter.setPhonem("k");
		return letter;
	}
	
	private Letter rModifier(char[] charArray, int i) {
		Letter letter = new LetterImpl(charArray[i]);
		if((charArray.length > i + 1 && charArray[i+1] == 'r') || i == 0) {//i == 0 > first letter is r > long r
			letter.setPhonem("r");			
		}
		else {
			letter.setPhonem("ɾ");
		}
		return letter;
	}
	
	private Letter tModifier(char[] charArray, int i) {
		Letter letter = new LetterImpl(charArray[i]);
		if(isCoda(charArray, i)) {
			letter.setPhonem("d");
		}else {
			letter.setPhonem("t");
		}
		return letter;
	}
	
	private Letter uModifier(char[] charArray, int i) {
		Letter letter = new LetterImpl(charArray[i]);
		if(((i > 0) && (CharacterClassification.strongVowels.contains(charArray[i - 1]) || CharacterClassification.accentedVowels.contains(charArray[i - 1]))) //Previous
				|| ((charArray.length > i + 1) && (CharacterClassification.strongVowels.contains(charArray[i + 1]) || CharacterClassification.accentedVowels.contains(charArray[i + 1])))) {//Next
			letter.setPhonem("w");
		}
		else {						
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
