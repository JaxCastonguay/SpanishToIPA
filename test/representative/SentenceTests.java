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
	
	@Test
	public void variousTestSentences() throws PhonemNotFoundException {
		Sentence sentence = new Sentence("En este mundo");
		String output = sentence.getPhoneticSentence();
		String expected = "/'ɜ̃.'nes.te.'mũn̪.do/";
		assertEquals(expected, output);
	}
	
	@Test
	public void YWordTestSentences() throws PhonemNotFoundException {
		//C y C -> Ci.C-------------------------------------
		Sentence sentence = new Sentence("los y los");
		String output = sentence.getPhoneticSentence();
		String expected = "/'lo.'si.'los/";
		assertEquals(expected, output);
		
		// || y C -> i.C-------------------------------------
		sentence = new Sentence("y tuyo");
		output = sentence.getPhoneticSentence();
		expected = "/'i.'tu.ʝo/";
		assertEquals(expected, output);
		
		// V y V -> V.ʝV--------------------------------------
		sentence = new Sentence("esa y eso");
		output = sentence.getPhoneticSentence();
		expected = "/'e.sa.'ʝe.so/";
		assertEquals(expected, output);
		
		//nasal + e change
		sentence = new Sentence("esa y entre");
		output = sentence.getPhoneticSentence();
		expected = "/'e.sa.'ʝɜn̪.tɾe/";
		assertEquals(expected, output);
		
		//C*(not /s/) y V -> .cjv. (joined)------------------
		//v doesn't have coda
		sentence = new Sentence("david y adel");
		output = sentence.getPhoneticSentence();
		expected = "/da.'ßi.'ðja.'ðɜl/";
		assertEquals(expected, output);
		
		//v doesn't have coda, but is accentuated
		sentence = new Sentence("david y ada");
		output = sentence.getPhoneticSentence();
		expected = "/da.'ßi.'ðja.ða/";
		assertEquals(expected, output);
		
		//v has coda
		sentence = new Sentence("david y admel");//weird made up name
		output = sentence.getPhoneticSentence();
		expected = "/da.'ßi.'ðjað.'mɜl/";
		assertEquals(expected, output);
		
		sentence = new Sentence("david y ad");//weird made up name
		output = sentence.getPhoneticSentence();
		expected = "/da.'ßi.'ðjað/";
		assertEquals(expected, output);
		
		//v has coda with dental accent
		sentence = new Sentence("david y ana");
		output = sentence.getPhoneticSentence();
		expected = "/da.'ßi.'ðja.na/";
		assertEquals(expected, output);
		
		//v has coda and is nasal
		sentence = new Sentence("ir y entre");
		output = sentence.getPhoneticSentence();
		expected = "/'i.'ɾjɜn̪.tɾe/";
		assertEquals(expected, output);
		//^covers the rest
		//v coda changes e
		//v coda changes nasal e
		
		//C*s y V -> z.ʝv------------------------------------
		sentence = new Sentence("los y eso");
		output = sentence.getPhoneticSentence();
		expected = "/'loz.'ʝe.so/";
		assertEquals(expected, output);
		
		//nasal + e
		sentence = new Sentence("los y entre");
		output = sentence.getPhoneticSentence();
		expected = "/'loz.'ʝɜn̪.tɾe/";
		assertEquals(expected, output);
		
		//|| y V -> ʝv---------------------------------------
		sentence = new Sentence("y Ana");
		output = sentence.getPhoneticSentence();
		expected = "/'ʝa.na/";
		assertEquals(expected, output);
		
		// V y C -> Vj.C-------------------------------------
		//e change
		sentence = new Sentence("ale y jac");
		output = sentence.getPhoneticSentence();
		expected = "/'a.lɜj.'xaƔ/";
		assertEquals(expected, output);
		
		sentence = new Sentence("ella y jac");
		output = sentence.getPhoneticSentence();
		expected = "/'e.ʝaj.'xaƔ/";
		assertEquals(expected, output);
		
		
		// V y || Not given in book. Want to change to j?------
		sentence = new Sentence("ella y");
		output = sentence.getPhoneticSentence();
		expected = "/'e.ʝaj/";
		assertEquals(expected, output);
		
		sentence = new Sentence("tome y");
		output = sentence.getPhoneticSentence();
		expected = "/'to.mɜj/";
		assertEquals(expected, output);
		
		// C y || Not given in book. Want to change to i
		sentence = new Sentence("tod y");
		output = sentence.getPhoneticSentence();
		expected = "/'to.'ði/";
		assertEquals(expected, output);
		
		// || y || Hyper specific. Sentence of just Y------------
		sentence = new Sentence("y");
		output = sentence.getPhoneticSentence();
		expected = "/'i/";
		assertEquals(expected, output);
	}
	
	
	//TODO: nasal testing in sentences really needs more refinement.
}
