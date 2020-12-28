package representative;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import errors.PhonemNotFoundException;
import logic.Translator;
import logic.CustomPhoneticsDTO;
import sounds.CharacterClassification;

public class PhonemicWord implements Word{
	
	private String spanishWord;
	private List<Letter> letters;
	private int accentedVowelIndex;
	
	PhonemicWord(String inputWord) throws PhonemNotFoundException{
		accentedVowelIndex = -1;
		spanishWord = inputWord;
				
		Translator translator = new Translator();
		letters = translator.translateIntoPhonems(inputWord.toCharArray());
		//Put TranslateIntoPhonetics here?
		//"general" international slurring should always be done when translated phonetically
		//input accent desired? Might want a "get oddities" method beforehand
		//How should I keep both phonemic and phonetic? Do I need to?
		//Thinking they need to re-translate if they want to go back to phonems
		
	}
	
	/*******************
	 *Syllable Creation*
	 *******************/
	public String getIPAWithSyllables() {
		return getWordWithSyllables(getIPAWord());
	}
	
	public String getCustomPhoneticsWithSyllables(List<CustomPhoneticsDTO> CustomPhonetics) {
		Translator translator = new Translator();
		
		String phonetics = translator.translateIntoCustomPhonetics(getIPAWord().toCharArray(), CustomPhonetics);
		return getWordWithSyllables(phonetics);
	}
	
	public String getPhoneticsWithSyllables() {
		Translator translator = new Translator();
		
		String phonetics = translator.translateIntoBasePhonetics(getIPAWord().toCharArray());
		return getWordWithSyllables(phonetics);
	}
	
	private String getWordWithSyllables(String word) {
		List<Integer> points = new ArrayList<Integer>();
		points = findPointPositions(word);
		
		ksJoinFix(word, points);
		
		StringBuilder sb = new StringBuilder(word);
		
		insertDots(points, sb);
				
		insertAccentuationPoint(points, sb);
		
		return sb.toString();
	}

	private void insertAccentuationPoint(List<Integer> points, StringBuilder sb) {
		//Start simple. single syllable word
		if(points.size() == 0) {
			sb.insert(0, '\'');
		}
		//Multiple syllables
		else {
			//Already know that the stress is on an accented syllable
			if(accentedVowelIndex != -1) {
				//correct accent index
				for(Integer point : points) {
					if(point < accentedVowelIndex) {
						accentedVowelIndex++;
					}
				}
				//insert accent
				String beforeAccent = sb.substring(0, accentedVowelIndex);
				int indexOfAccentuatedDot = beforeAccent.lastIndexOf('.');
				//Plus one because we would otherwise be on the wrong side of the dot.
				sb.insert(indexOfAccentuatedDot + 1, '\'');
				
			}
			//No accented vowel need to find which syllable to accentuate
			else {
				int indexOfAccentuatedDot = getAccentIndex(sb);
				sb.insert(indexOfAccentuatedDot + 1, '\'');				
			}			
		}
	}

	private void insertDots(List<Integer> points, StringBuilder sb) {
		int wordOffset = 1;
		for(int i = 0; i < points.size(); i++) {
			sb.insert(points.get(i) + wordOffset, '.');
			wordOffset++;
		}
	}

	private int getAccentIndex(StringBuilder sb) {
		//Assume last syllable stress
		int indexOfLastDot = sb.lastIndexOf(".");
		
		//Second to last syllable stressed 
		if(CharacterClassification.penultimas.contains(sb.charAt(sb.length() - 1))) {
			String lastSyllableRemoved = sb.substring(0, indexOfLastDot);
			//Now second point
			indexOfLastDot = lastSyllableRemoved.lastIndexOf('.');			
		}
		return indexOfLastDot;
	}

	private List<Integer> findPointPositions(String word) {
		List<Integer> points = new ArrayList<Integer>();
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
	
	/*****************
	 *Getters/Setters* 
	 *****************/

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
	
//	public class IllegalEndingLetterException extends Exception {
//		 
//	    public IllegalEndingLetterException(String message) {
//	        super(message);
//	    }
//	}
	
}
