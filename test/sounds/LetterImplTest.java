package sounds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import representative.Letter;
import representative.LetterImpl;

class LetterImplTest {

	@Test
	void vowelShouldReturnIsVowel() {		
		Letter letter = new LetterImpl('a');
		assertTrue(letter.isVowel());
	}
	
	@Test
	void nonvowelShouldNotReturnVowel() {
		Letter letter = new LetterImpl('b');
		assertFalse(letter.isVowel());
	}
	
	

}
