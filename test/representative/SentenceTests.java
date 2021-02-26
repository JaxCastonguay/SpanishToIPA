package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import errors.PhonemNotFoundException;

public class SentenceTests {

	@Test
	public void listOfWordsShouldBeCreated() throws PhonemNotFoundException {
		Sentence sentence = new Sentence("Esto es una oración");
		
		String output = sentence.getPhoneticSentence();
		String expected = "/'es.to.'e.'su.na.o.ɾa.'sjon/";
		assertEquals(expected, output);
		
		sentence = new Sentence("Este ojo");
		output = sentence.getPhoneticSentence();
		expected = "/'es.te.'o.xo/";
		assertEquals(expected, output);

	}
	
	@Test
	public void numbersInSentenceShouldThrowError() {
		Sentence sentence = new Sentence("Tengo 2 gatos");
		//This is a bad way to test. Fix this later.
		Boolean exceptionTriggered = false;
		try {
			sentence.getPhoneticSentence();
		} catch (PhonemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exceptionTriggered = true;
		}
		
		assertTrue(exceptionTriggered);
	}
	
	//commented cause not positive on result expected
//	@Test
//	public void SyllablesShouldJoinWithLikeLetter() throws PhonemNotFoundException {
//		Sentence sentence = new Sentence("para abrir");
//		
//		String output = sentence.getPhoneticSentence();
//		String expected = "/'pa.ɾa.bɾiɾ/";
//		assertEquals(expected, output);
//
//	}
	
	//TODO: uncomment this once returning to working on sentences
//	@Test
//	public void OuterSyllablesShouldEffectOtherWords() throws PhonemNotFoundException {
//		Sentence sentence = new Sentence("el taco");
//		
//		String output = sentence.getPhoneticSentence();
//		String expected = "/'ɜl̪.'ta.ko/";
//		assertEquals(expected, output);
//
//	}
}
