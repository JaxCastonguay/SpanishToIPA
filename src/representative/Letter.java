package representative;

import java.util.List;

import sounds.soundType;

public interface Letter {
	Boolean hasNext();
	Boolean hasPrevious();
	Boolean isVowel();
	Boolean isEffectedByNext(Letter next);
	Boolean isEffectedByPrevious(Letter prev);
	public final List< soundType> soundTypes = null;
	Character getSpanishLetter();
	String getPhonem();
	
}