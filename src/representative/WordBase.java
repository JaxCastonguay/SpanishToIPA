package representative;

public interface WordBase {
	String getSpanishWord();
	String getIPAWord();
	Letter getFirstLetter();
	Letter getLastLetter();
	Letter getLetter(int index);
}
