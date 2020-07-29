package representative;

public interface Word {
	String getSpanishWord();
	String getIPAWord();
	Letter getFirstLetter();
	Letter getLastLetter();
	Letter getLetter(int index);
}
