package representative;

import sounds.CharacterClassification;

public class LetterImpl implements Letter{
	
	private Character spanishLetter;
	private String phonem;


	public LetterImpl(char letter){
		spanishLetter = letter;
	}
	

	@Override
	public Boolean isVowel() {
		
		if(CharacterClassification.vowels.contains(spanishLetter))
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
		// TODO Auto-generated method stub
		return phonem;
	}

	@Override
	public Boolean isEffectedBy(Letter next) {
		// TODO Auto-generated method stub
		return null;
	}

}
