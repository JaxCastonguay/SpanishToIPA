package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhoneticWordTest {
	
	@Test
	void lettersShouldReturnSpanishLettersAssigned() {
		PhonemicWord phonemicWord = new PhonemicWord("test");
		assertEquals(phonemicWord.getFirstLetter().getSpanishLetter(), 't');
		assertEquals(phonemicWord.getLetter(1).getSpanishLetter(), 'e');
		assertEquals(phonemicWord.getLetter(2).getSpanishLetter(), 's');
		assertEquals(phonemicWord.getLastLetter().getSpanishLetter(), 't');
	}

}
