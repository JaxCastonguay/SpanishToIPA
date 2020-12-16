package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import errors.PhonemNotFoundException;

public class SentenceTests {

	@Test
	public void listOfWordsShouldBeCreated() throws PhonemNotFoundException {
		Sentence sentence = new Sentence("Esto es una oración");
		//TODO: discovered syll needs testing as oración split into ci.ón
		
		String output = sentence.getPhoneticSentence();
		String expected = "/'es.to.'e.'su.na.o.ɾa.si.'on/";//should end in sjon but testing other feature rn.
		assertEquals(expected, output);

	}
	
	@Test
	public void numbersInSentenceShouldThrowError() throws PhonemNotFoundException {
		Sentence sentence = new Sentence("Tengo 2 gatos");
		
		String result = sentence.getPhoneticSentence();
	}
}
