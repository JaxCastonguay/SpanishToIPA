package representative;

import java.util.ArrayList;
import java.util.List;

public class PhonemicWord implements Word{
	
	private String spanishWord;
	private List<LetterImpl> letters;
	
	PhonemicWord(String inputWord){
		spanishWord = inputWord;
		letters = new ArrayList<LetterImpl>();
		
		for(char c : inputWord.toCharArray()) {
			LetterImpl letter = new LetterImpl(c);
			letter.hasPrevious = true;
			letter.hasNext = true;			
			letters.add(letter);
			
		}
		
		letters.get(0).hasPrevious = false;
		letters.get(letters.size() - 1).hasNext = false;
		
	}

	@Override
	public String getSpanishWord() {
		return spanishWord;
	}

	@Override
	public Letter getFirstLetter() {
		return letters.get(0);
	}

	@Override
	public Letter getLastLetter() {
		return letters.get(letters.size() - 1);
	}

	@Override
	public Letter getLetter(int index) {
		return letters.get(index);
	}
	
}
