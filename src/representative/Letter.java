package representative;

public interface Letter {
	
	Boolean isVowel();
	Character getSpanishLetter();
	String getPhonem();
	void setPhonem(String phonem);
	Boolean isAccented();
}