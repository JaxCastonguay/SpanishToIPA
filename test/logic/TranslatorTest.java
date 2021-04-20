package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class TranslatorTest {
	//TODO: I joined these two methods so this will have to be fixed.
	@Test
	public void getPhoneticBasedOnNextCharTest() {
		Translator translator = new Translator();
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('e'), 'r', true), "ɜ");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('e'), 'r', false), "ɜ");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('e'), 's', true), "e");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('e'), 't', false), "e");
		
		//Note: for s, isCoda does not matter
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('s'), 't', false), "s");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('s'), 't', true), "s");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('s'), 'd', false), "z");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('s'), 'd', true), "z");
		
		//just a random char
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('a'), 'd', false), "a");
		assertEquals(translator.getPhoneticsBasedOnNextChar(String.valueOf('b'), 'd', true), "b");
		
		assertEquals(translator.getPhoneticsBasedOnNextChar("l", 't', false), "l̪");
		assertEquals(translator.getPhoneticsBasedOnNextChar("l", 's', false), "l");
		
		assertEquals(translator.getPhoneticsBasedOnNextChar("s", 'r', false), "");
		assertEquals(translator.getPhoneticsBasedOnNextChar("s", 't', false), "s");
		
		assertEquals(translator.getPhoneticsBasedOnNextChar("d", 't', false), "d");
	}
	
	@Test
	public void getPhoneticBasedOnPreviousCharsTest() {
		Translator translator = new Translator();
		assertEquals(translator.getPhoneticBasedOnPreviousChars('d', 'r', 'b'), 'ß');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('d', '|', 'b'), 'b');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('d', 's', 'd'), 'ð');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('d', 'n', 'd'), 'd');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('d', 'l', 'd'), 'd');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('l', '̪', 'd'), 'd');
		
		//Note: for s, isCoda does not matter
		assertEquals(translator.getPhoneticBasedOnPreviousChars('d', 'a', 'ɟ'), 'ʝ');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('r', 'a', 'ɾ'), 'ɾ');
		assertEquals(translator.getPhoneticBasedOnPreviousChars('a', 'n', 'ɾ'), 'r');
		
		assertEquals(translator.getPhoneticBasedOnPreviousChars('l', 'a', 's'), 's');
	}
	
	

	@Test
	public void isCodaTests() {
		//V.CVC
		String string = "esos";
		assertFalse(Translator.isCoda(string.toCharArray(), 0));
		assertFalse(Translator.isCoda(string.toCharArray(), 1));
		assertFalse(Translator.isCoda(string.toCharArray(), 2));
		assertTrue(Translator.isCoda(string.toCharArray(), 3));
		//VC.CV
		string = "este";
		assertFalse(Translator.isCoda(string.toCharArray(), 0));
		assertTrue(Translator.isCoda(string.toCharArray(), 1));
		assertFalse(Translator.isCoda(string.toCharArray(), 2));
		assertFalse(Translator.isCoda(string.toCharArray(), 3));
		
		//V.CCV
		string = "atɾas";
		assertFalse(Translator.isCoda(string.toCharArray(), 0));
		assertFalse(Translator.isCoda(string.toCharArray(), 1));
		assertFalse(Translator.isCoda(string.toCharArray(), 2));
		assertFalse(Translator.isCoda(string.toCharArray(), 3));
		assertTrue(Translator.isCoda(string.toCharArray(), 4));
		//CV.CV
		string = "nena";
		assertFalse(Translator.isCoda(string.toCharArray(), 0));
		assertFalse(Translator.isCoda(string.toCharArray(), 1));
		assertFalse(Translator.isCoda(string.toCharArray(), 2));
		assertFalse(Translator.isCoda(string.toCharArray(), 3));
		//CV.CCV
		string = "detɾas";
		assertFalse(Translator.isCoda(string.toCharArray(), 0));
		assertFalse(Translator.isCoda(string.toCharArray(), 1));
		assertFalse(Translator.isCoda(string.toCharArray(), 2));
		assertFalse(Translator.isCoda(string.toCharArray(), 3));
		assertFalse(Translator.isCoda(string.toCharArray(), 4));
		assertTrue(Translator.isCoda(string.toCharArray(), 5));
		//VC.CCV
		string = "entɾaɾ";
		assertFalse(Translator.isCoda(string.toCharArray(), 0));
		assertTrue(Translator.isCoda(string.toCharArray(), 1));
		assertFalse(Translator.isCoda(string.toCharArray(), 2));
		assertFalse(Translator.isCoda(string.toCharArray(), 3));
		assertFalse(Translator.isCoda(string.toCharArray(), 4));
		assertTrue(Translator.isCoda(string.toCharArray(), 5));
		
	}
}
