package representative;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
		//TODO: This is a bad way to test. Fix this later.
		Boolean exceptionTriggered = false;
		try {
			sentence.getPhoneticSentence();
		} catch (PhonemNotFoundException e) {
			//e.printStackTrace();
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
	
	@Test
	public void OuterSyllablesShouldEffectOtherWords() throws PhonemNotFoundException {
		Sentence sentence = new Sentence("el taco");
		String output = sentence.getPhoneticSentence();
		String expected = "/'ɜl̪.'ta.ko/";
		assertEquals(expected, output);
		
		sentence = new Sentence("los ricos");
		output = sentence.getPhoneticSentence();
		expected = "/'lo.'ri.kos/";
		assertEquals(expected, output);
		
		sentence = new Sentence("el drogo");
		output = sentence.getPhoneticSentence();
		expected = "/'ɜl̪.'dɾo.Ɣo/";
		assertEquals(expected, output);
		
		sentence = new Sentence("los drogos");
		output = sentence.getPhoneticSentence();
		expected = "/'loz.'ðɾo.Ɣos/";
		assertEquals(expected, output);
		
		sentence = new Sentence("tan dramatica");
		output = sentence.getPhoneticSentence();
		expected = "/'tan.dɾa.ma.'ti.ka/";
		assertEquals(expected, output);

	}
}
