package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import representative.PhonemicWord;

class PhoneticWordTest {

	@Test
	void firstLetterShouldNotHavePrevious() {
		PhonemicWord phonemicWord = new PhonemicWord("hello");
		
		assertFalse(phonemicWord.getFirstLetter().hasPrevious());
	}

}
