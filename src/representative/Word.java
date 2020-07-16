package representative;

import sounds.Letter;

public interface Word {
	String getSpanishWord();
	Letter getFirstLetter();
	Letter getLastLetter();
	Letter getLetter(int index);
}
