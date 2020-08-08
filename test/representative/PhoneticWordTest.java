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
	void singleLettersChange() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "v";
			String expected = "b";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "j";
			expected = "x";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "u";
			expected = "w";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "i";
			expected = "j";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void ksShouldReturnDifferentPhonems() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "c";
			String expected = "k";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ce";
			expected = "se";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ch";
			expected = "ʧ";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ca";
			expected = "ka";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void gsShouldReturnDifferentPhonems() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "g";
			String expected = "g";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ge";
			expected = "xe";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ga";
			expected = "ga";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void lsShouldReturnDifferentPhonems() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "l";
			String expected = "l";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "lla";
			expected = "ya";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "la";
			expected = "la";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void yShouldReturnDifferentPhonem() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "y";
			String expected = "ʝ";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "yo";
			expected = "ʝo";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
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
