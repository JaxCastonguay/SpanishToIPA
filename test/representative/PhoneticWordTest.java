package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;



import representative.PhonemicWord.PhonemNotFoundException;

class PhoneticWordTest {
	
	@BeforeClass
    public static void beforeAllTestMethods() {
        
    }
	
	@Test
	void lettersShouldReturnSpanishLettersAssigned() throws Exception{
		PhonemicWord phonemicWord;
		try {
			phonemicWord = new PhonemicWord("test");
			assertEquals(phonemicWord.getFirstLetter().getSpanishLetter(), 't');
			assertEquals(phonemicWord.getLetter(1).getSpanishLetter(), 'e');
			assertEquals(phonemicWord.getLetter(2).getSpanishLetter(), 's');
			assertEquals(phonemicWord.getLastLetter().getSpanishLetter(), 't');
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void vShouldChangeToB() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "v";
			String expected = "b";
			phonemicWord = new PhonemicWord("v");
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void hShouldNotBeAdded() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "ha";
			String expected = "a";
			phonemicWord = new PhonemicWord("ha");
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void wordShouldReturnIPAWord() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "vato";
			String expected = "bato";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
