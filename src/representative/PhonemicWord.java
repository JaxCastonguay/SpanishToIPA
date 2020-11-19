package representative;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import representative.PhonemicWord.PhonemNotFoundException;
import sounds.CharacterClassification;

public class PhonemicWord implements Word{
	
	private String spanishWord;
	private List<Letter> letters;
	private int accentedVowelIndex;
	
	PhonemicWord(String inputWord) throws PhonemNotFoundException{
		accentedVowelIndex = -1;
		spanishWord = inputWord;
		letters = new ArrayList<Letter>();
				
		char[] charArray = inputWord.toCharArray();
		
		translateIntoPhonems(charArray);
		//System.out.println("End of method: letter count: " + String.valueOf(letters.size() + " word: " + getIPAWord()));
		//Put TranslateIntoPhonetics here?
		//"general" international slurring should always be done when translated phonetically
		//input accent desired? Might want a "get oddities" method beforehand
		//How should I keep both phonemic and phonetic? Do I need to?
		//Thinking they need to re-translate if they want to go back to phonems
		
	}

	private void translateIntoPhonems(char[] charArray) throws PhonemNotFoundException {
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
	}

	
	/******************
	 *Letter Modifiers* 
	 ******************/
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
	
	/*******************
	 *Syllable Creation*
	 *******************/
	public String getIPAWithSyllables() {
		String word = getIPAWord();
		List<Integer> points = new ArrayList();
		int wordOffset = 1;
		points = findPointPositions(word);
		
		ksJoinFix(word, points);
		
		StringBuilder sb = new StringBuilder(word);
		
		for(int i = 0; i < points.size(); i++) {
			sb.insert(points.get(i) + wordOffset, '.');
			wordOffset++;
			//System.out.println(sb.toString() +", index " + Integer.toString(i));
		}
		
		for(Integer point : points) {
			if(point < accentedVowelIndex) {
				accentedVowelIndex++;
			}
		}
		if(accentedVowelIndex > -1) {
			String beforeAccent = sb.substring(0, accentedVowelIndex);
			int indexOfAccentuatedDot = beforeAccent.lastIndexOf('.');
			if(indexOfAccentuatedDot > -1) {
				//Plus one because we would otherwise be on the wrong side of the dot.
				sb.insert(indexOfAccentuatedDot + 1, '\'');
			}else {
				//has accent on first syllable. ie no dot
				sb.insert(0, '\'');
			}

		}
		
		return sb.toString();
	}

	private List<Integer> findPointPositions(String word) {
		List<Integer> points = new ArrayList();
		//V (Any type of vowel)
		for(int i = 0; i < word.length() - 1; i++) {
			if(CharacterClassification.phoneticvowels.contains(word.charAt(i))) {
				//[V]V Next is any type of vowel
				if(CharacterClassification.phoneticvowels.contains(word.charAt(i+1))) {
					//[V]V -> V.V
					//Current and next are both strong vowels. Separate.
					if(CharacterClassification.vowels.contains(word.charAt(i))
							&& CharacterClassification.vowels.contains(word.charAt(i+1))) {
						points.add(i);
					}
					//else next or current is diptong and no change is needed.
					
				}
				
				//[V]C
				//if VCC(joinable)
	            //V.CC (add)
	            //if VCC(not join)
	            //VC.C (nothing)
	            //if VC_(empty)
	            //nothing
	            //if VCV
	            //V.CV (add)
				else {
					//Len check
					if(word.length() > i + 2) {
						//VCV -> V.CV
						if(CharacterClassification.phoneticvowels.contains(word.charAt(i+2))) {
							points.add(i);
						}
						//VCC
						else {
							
							StringBuilder sb = new StringBuilder();
							sb.append(word.charAt(i+1));
							sb.append(word.charAt(i+2));
							
							//VCC (joinable) -> V.CC
							if(CharacterClassification.phoneticConsonantBlends.contains(sb.toString())) {
								points.add(i);
							}
							//VC.C (handled by CC below)
							
						}
					}
					else //VC_ (do nothing)
					{}
				}
			}
			//C
			else {
				//CC
				if(!CharacterClassification.phoneticvowels.contains(word.charAt(i+1))) {
					StringBuilder sb = new StringBuilder();
					sb.append(word.charAt(i));
					sb.append(word.charAt(i+1));
					//Not cons blend -> C.C
					if(!CharacterClassification.phoneticConsonantBlends.contains(sb.toString())) {
						points.add(i);
					}
					//Cons blend
					else {
					}
				}
				//CV
				else {
					
				}
			}
			
		}
		
		return points;
	}
	
	private void ksJoinFix(String word, List<Integer> points) {
		List<Integer> ruleBreakers = new ArrayList<Integer>();
		
		//ex: extraño. -> eks.tɾa.ɲo. st/sp/sk should never join
		Pattern p = Pattern.compile("ks["+ CharacterClassification.getConsonantsAsString() +"]"); 
		Matcher m = p.matcher(word);
		int temp = -1;
		
		if(m.find())
			temp = m.start();
		
		if(temp > 0)
			ruleBreakers.add(temp);
		
		for(Integer rb : ruleBreakers) {
			points.remove(rb - 1);
		}
		
	}
	
	
	private void findAccentuationIndex() {
		if(accentedVowelIndex > 1) {
			
		}
	}
	
	/****************************
	 *Getters/Setters/Exceptions* 
	 ****************************/

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
			if(letters.get(i).isAccented()) {
				accentedVowelIndex = i;
			}
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
