package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class variousWordTest {
	
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
	
	@Test
	void accentedWordShouldntHaveAccentInIPA() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "día";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "'di.a";
			assertEquals(expected, output);
			
			input = "sábado";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "'sa.ba.do";
			assertEquals(expected, output);
			
			input = "pasé";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "pa.'se";
			assertEquals(expected, output);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void cvcvShouldBeSplitInTwo() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "vato";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "'ba.to";
			assertEquals(expected, output);
			
			input = "gustar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "gus.'taɾ";
			assertEquals(expected, output);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void cvcvcvShouldBeSplitInThree() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "pareja";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "pa.'ɾe.xa";
			assertEquals(expected, output);
			
			input = "parejas";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "pa.'ɾe.xas";
			assertEquals(expected, output);
			
			input = "especifica";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "es.pe.si.'fi.ka";
			assertEquals(expected, output);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void cvccvcShouldBeSplitBetweenCs() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "formar";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "foɾ.'maɾ";
			assertEquals(expected, output);
			
			input = "perdir";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "peɾ.'diɾ";
			assertEquals(expected, output);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void vccvcShouldBeSplitBeforeCs() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "abrir";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "a.'bɾiɾ";
			assertEquals(expected, output);
			
			input = "hacer";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "a.'seɾ";
			assertEquals(expected, output);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/*#################
	###SPECIAL CASES###
	###################*/
	@Test
	void spShouldSplit() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "espalda";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "es.'pal.da";
			assertEquals(expected, output);
						
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void kstShouldSplitKs() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "extra";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "'eks.tɾa";
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/*#############
	###Mechanics###
	###############*/
	@Test
	void getSpanishWordReturnsOrginalWord() throws Exception{
		PhonemicWord phonemicWord;
		try {
			String input = "extra";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getSpanishWord();
			String expected = "extra";
			assertEquals(expected, output);
			
			input = "desde";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getSpanishWord();
			expected = "desde";
			assertEquals(expected, output);
			
			input = "abcdefghijklmnopqrstuvwxyzllch";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getSpanishWord();
			expected = "abcdefghijklmnopqrstuvwxyzllch";
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	//#####################################
	
	@Test
	void syllablesTests() throws Exception {
		//Recall this is for phonemic words and not phonetic. No approximations yet.
		PhonemicWord phonemicWord;
		try {
			String input = "espalda";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "es.'pal.da";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);
			
			input = "desde";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "'des.de";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);
			
			input = "escuchar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "es.ku.'ʧaɾ";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);
			
			input = "ver";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "'beɾ";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);

			input = "extrañar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "eks.tɾa.'ɲaɾ";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);

			input = "traer";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "tɾa.'eɾ";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);

			input = "bien";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "'bjen";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);

			input = "querer";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "ke.'ɾeɾ";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);

			input = "llamar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "ya.'maɾ";
			assertEquals(expected, output);
			System.out.println(input + ": " + output);
	
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
