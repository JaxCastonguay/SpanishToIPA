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
	
	/*******************
	 *Syllable Creation*
	 *******************/
	public String getIPAWithSyllables() {
		String word = getIPAWord();
		List<Integer> points = new ArrayList();
		int offset = 1;
		points = findPointPositions(word);
		
		sJoinFix(word, points);
		
		StringBuilder sb = new StringBuilder(word);
		
		for(int i = 0; i < points.size(); i++) {
			sb.insert(points.get(i) + offset, '.');
			offset++;
			System.out.println(sb.toString() +", index " + Integer.toString(i));
		}
		
		
		return sb.toString();
	}

	private List<Integer> findPointPositions(String word) {
		//WRITTEN BUT UNTESTED
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
	
	//This whole method is searching backwards.... sk isn't the issue, ks is -> explicar -> eks.plicar
	private void sJoinFix(String word, List<Integer> points) {
		List<Integer> ruleBreakers = new ArrayList<Integer>();
		
		// st/sp/sk + consonant. ex: extranjero
		Pattern p = Pattern.compile("ks["+ CharacterClassification.getConsonantsAsString() +"]"); 
		Matcher m = p.matcher(word);
		int temp = -1;
		
		if(m.find())
			temp = m.start();
		
		//int temp = word.indexOf("sp");
		if(temp > 0)
			ruleBreakers.add(temp);
		
//		temp = word.indexOf("sk");
//		
//		if(temp > 0)
//			ruleBreakers.add(temp);
//		
//		temp = word.indexOf("st");
//		
//		if(temp > 0)
//			ruleBreakers.add(temp);
		
		for(Integer rb : ruleBreakers) {
			points.remove(rb - 1);
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
