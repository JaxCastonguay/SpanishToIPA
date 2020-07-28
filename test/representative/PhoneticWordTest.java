package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import representative.PhonemicWord.PhonemNotFoundException;

class PhoneticWordTest {
	
	@Test
	void lettersShouldReturnSpanishLettersAssigned() {
		PhonemicWord phonemicWord;
		try {
			phonemicWord = new PhonemicWord("test");
			assertEquals(phonemicWord.getFirstLetter().getSpanishLetter(), 't');
			assertEquals(phonemicWord.getLetter(1).getSpanishLetter(), 'e');
			assertEquals(phonemicWord.getLetter(2).getSpanishLetter(), 's');
			assertEquals(phonemicWord.getLastLetter().getSpanishLetter(), 't');
			
		} catch (PhonemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void vShouldChangeToB() {
		PhonemicWord phonemicWord;
		try {
			phonemicWord = new PhonemicWord("vato");
			assertEquals("b", phonemicWord.getFirstLetter().getPhonem());

			
		} catch (PhonemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
