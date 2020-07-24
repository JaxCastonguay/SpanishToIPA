package representative;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PhonemicWord implements Word{
	
	private String spanishWord;
	private List<Letter> letters;
	
	PhonemicWord(String inputWord){
		spanishWord = inputWord;
		letters = new ArrayList<Letter>();
		
		//ListIterator li = letters.listIterator(); //should I use this?
		
		
		for(char c : inputWord.toCharArray()) {
			Letter letter = new LetterImpl(c);
			letters.add(letter);
			
		}	
		
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
