package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class PhonemicTranslationsTests {
	
	@BeforeClass
    public static void beforeAllTestMethods() {
        
    }
	
	@Test
	void lettersShouldReturnSpanishLettersAssigned() throws Exception{
		Word phonemicWord;
		try {
			phonemicWord = new Word("test");
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
		Word phonemicWord;
		try {
			String input = "v";
			String expected = "b";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "j";
			expected = "x";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "u";
			expected = "u";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "eu";
			expected = "ew";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ua";
			expected = "wa";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "tu";
			expected = "tu";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "i";
			expected = "i";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ei";
			expected = "ej";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ia";
			expected = "ja";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ti";
			expected = "ti";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void ksShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {			
			String input = "ce";
			String expected = "se";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ch";
			expected = "ʧ";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ca";
			expected = "ka";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "doctor";
			expected = "dogtoɾ";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void gsShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {
			String input = "g";
			String expected = "g";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ge";
			expected = "xe";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ga";
			expected = "ga";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void lsShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {
			String input = "l";
			String expected = "l";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "lla";
			expected = "ɟa";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "la";
			expected = "la";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void psShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {
			String input = "oposicion";
			String expected = "oposisjon";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "optimista";
			expected = "obtimista";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void tsShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {
			String input = "toca";
			String expected = "toka";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ritmo";
			expected = "ridmo";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void accentedLetterShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {
			String input = "íe";
			String expected = "ie";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			assertEquals(true, phonemicWord.getFirstLetter().isAccented());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void yShouldReturnDifferentPhonem() throws Exception {
		Word phonemicWord;
		try {
			String input = "y";
			String expected = "ɟ";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "yo";
			expected = "ɟo";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void qShouldReturnK() throws Exception {
		Word phonemicWord;
		try {
			String input = "que";
			String expected = "ke";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "iraq";
			expected = "iɾak";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void rShouldReturnDifferentPhonems() throws Exception {
		Word phonemicWord;
		try {
			String input = "ra";
			String expected = "ra";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "erre";
			expected = "ere";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ere";
			expected = "eɾe";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void xShouldReturnKs() throws Exception {
		Word phonemicWord;
		try {
			String input = "xa";
			String expected = "ksa";
			phonemicWord = new Word(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void hShouldNotBeAdded() throws Exception {
		Word phonemicWord;
		try {
			String input = "ha";
			String expected = "a";
			phonemicWord = new Word("ha");
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}
