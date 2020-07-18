package representative;

public interface Word {
	String getSpanishWord();
	Letter getFirstLetter();
	Letter getLastLetter();
	Letter getLetter(int index);
}
