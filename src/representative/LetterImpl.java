package representative;

import sounds.CharacterClassification;

public class LetterImpl implements Letter{
	
	protected boolean hasNext;
	protected boolean hasPrevious;
	private Character spanishLetter;
	private String phonem;

	public LetterImpl(char letter){
		spanishLetter = letter;
	}
	
	@Override
	public Boolean hasNext() {
		return hasNext;
	}

	@Override
	public Boolean hasPrevious() {
		return hasPrevious;
	}

	@Override
	public Boolean isVowel() {
		CharacterClassification cc = new CharacterClassification();
		
		if(cc.vowels.contains(spanishLetter))
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
	public Boolean isEffectedByPrevious(Letter prev) {
		// TODO Auto-generated method stub
		//Should be able to classify letter consonant sound and compare to other letter consonant sound with map?
		return null;
	}

	@Override
	public Boolean isEffectedByNext(Letter next) {
		// TODO Auto-generated method stub
		return null;
	}

}
