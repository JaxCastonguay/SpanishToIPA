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
			expected = "u";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getFirstLetter().getPhonem());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "eu";
			expected = "ew";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ua";
			expected = "wa";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "tu";
			expected = "tu";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "i";
			expected = "i";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ei";
			expected = "ej";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ia";
			expected = "ja";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ti";
			expected = "ti";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
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
	void accentedLetterShouldReturnDifferentPhonems() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "íe";
			String expected = "ie";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			assertEquals(true, phonemicWord.getFirstLetter().isAccented());
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
	void qShouldReturnK() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "que";
			String expected = "ke";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "iraq";
			expected = "iɾak";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void rShouldReturnDifferentPhonems() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "ra";
			String expected = "ra";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "erre";
			expected = "ere";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			input = "ere";
			expected = "eɾe";
			phonemicWord = new PhonemicWord(input);
			assertEquals(expected, phonemicWord.getIPAWord());
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + phonemicWord.getIPAWord());
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void xShouldReturnKs() throws Exception {
		PhonemicWord phonemicWord;
		try {
			String input = "xa";
			String expected = "ksa";
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
	
}
