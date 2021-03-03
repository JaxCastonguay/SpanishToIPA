package logic;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class TranslatorTest {
	@Test
	public void getPhoneticBasedOnNextCharTest() {
		Translator translator = new Translator();
		assertEquals(translator.getPhoneticBasedOnNextChar('e', 'r', true), 'ɜ');
		assertEquals(translator.getPhoneticBasedOnNextChar('e', 'r', false), 'ɜ');
		assertEquals(translator.getPhoneticBasedOnNextChar('e', 's', true), 'e');
		assertEquals(translator.getPhoneticBasedOnNextChar('e', 't', false), 'e');
		
		//Note: for s, isCoda does not matter
		assertEquals(translator.getPhoneticBasedOnNextChar('s', 't', false), 's');
		assertEquals(translator.getPhoneticBasedOnNextChar('s', 't', true), 's');
		assertEquals(translator.getPhoneticBasedOnNextChar('s', 'd', false), 'z');
		assertEquals(translator.getPhoneticBasedOnNextChar('s', 'd', true), 'z');
		
		//just a random char
		assertEquals(translator.getPhoneticBasedOnNextChar('a', 'd', false), 'a');
		assertEquals(translator.getPhoneticBasedOnNextChar('b', 'd', true), 'b');
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
	public void getPhoneticsWithArrayResizeTest() {
		Translator translator = new Translator();
		assertEquals(translator.getPhoneticsWithArrayResize("l", 't'), "l̪");
		assertEquals(translator.getPhoneticsWithArrayResize("l", 's'), "l");
		
		assertEquals(translator.getPhoneticsWithArrayResize("s", 'r'), "");
		assertEquals(translator.getPhoneticsWithArrayResize("s", 't'), "s");
		
		assertEquals(translator.getPhoneticsWithArrayResize("d", 't'), "d");
	}
}
