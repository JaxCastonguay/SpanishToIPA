package representative;

import java.util.List;

import sounds.soundType;

public interface Letter {
	
	Boolean isVowel();
	Boolean isEffectedBy(Letter next);
	public final List< soundType> soundTypes = null;
	Character getSpanishLetter();
	String getPhonem();
	void setPhonem(String phonem);
	Boolean hasAccent();
}