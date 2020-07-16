package sounds;

import java.util.List;

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