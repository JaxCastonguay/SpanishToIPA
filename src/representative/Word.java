package representative;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import errors.PhonemNotFoundException;
import logic.Translator;
import logic.CustomPhoneticsDTO;
import logic.SyllableHelper;
import sounds.CharacterClassification;

public class Word implements WordBase{
	
	private String spanishWord;
	private List<Letter> letters;
	private int accentedVowelIndex;
	
	Word(String inputWord) throws PhonemNotFoundException{
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
		return SyllableHelper.getWordWithSyllables(getIPAWord(), accentedVowelIndex);
	}
	
//	public String getCustomPhoneticsWithSyllables(List<CustomPhoneticsDTO> CustomPhonetics) {
//		Translator translator = new Translator();
//		
//		String phonetics = translator.translateIntoCustomPhonetics(getIPAWord().toCharArray(), CustomPhonetics);
//		return SyllableHelper.getWordWithSyllables(phonetics, accentedVowelIndex);
//	}
	
	public String getPhoneticsWithSyllables() {
		Translator translator = new Translator();
		
		String phonetics = translator.translateIntoBasePhonetics(getIPAWord().toCharArray());
		return SyllableHelper.getWordWithSyllables(phonetics, accentedVowelIndex);
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
