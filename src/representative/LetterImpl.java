package representative;

import sounds.CharacterClassification;

public class LetterImpl implements Letter{
	
	private Character spanishLetter;
	private String phonem;
	private Boolean hasAccent;

	public LetterImpl(char letter){
		spanishLetter = letter;
		if(CharacterClassification.accentedVowels.contains(letter)) {
			hasAccent = true;
		}
		else {
			hasAccent = false;
		}
	}
	

	@Override
	public Boolean isVowel() {
		
		if(CharacterClassification.vowels.contains(spanishLetter) 
				|| CharacterClassification.accentedVowels.contains(spanishLetter))
			return true;
		else
			return false;
	}
	
	public static Boolean isVowel(Character c) {
		
		if(CharacterClassification.vowels.contains(c) 
				|| CharacterClassification.accentedVowels.contains(c))
			return true;
		else
			return false;
	}


	@Override
	public Character getSpanishLetter() {
		return spanishLetter.charValue();
	}

	@Override
	public String getPhonem() {
		return phonem;
	}


	@Override
	public void setPhonem(String phonem) {
		this.phonem = phonem;
		
	}
	
	@Override
	public Boolean isAccented() {
		return hasAccent;
	}

}
