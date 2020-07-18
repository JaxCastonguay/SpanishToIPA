package sounds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CharacterClassificationTest {

	@Test
	void arraylistsShouldInitialize() {
		assertTrue(CharacterClassification.consonantBlends.get(0).equalsIgnoreCase("bl"));
		
		assertTrue(CharacterClassification.vowels.size() == 5);
	}

}
