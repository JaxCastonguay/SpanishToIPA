package representative;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class variousWordTest {

	@Test
	void syllablesTests() throws Exception {
		//Recall this is for phonemic words and not phonetic. No approximations yet.
		PhonemicWord phonemicWord;
		try {
			String input = "espalda";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getIPAWithSyllables();
			String expected = "es.pal.da";
			assertEquals(expected, output);
			
			input = "desde";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "des.de";
			assertEquals(expected, output);
			
			input = "escuchar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "es.ku.ʧaɾ";
			assertEquals(expected, output);
			
			input = "ver";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "beɾ";
			assertEquals(expected, output);
			
			input = "extrañar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "eks.tɾa.ɲaɾ";
			assertEquals(expected, output);
			
			input = "traer";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "tɾa.eɾ";
			assertEquals(expected, output);
			
			input = "bien";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "bjen";
			assertEquals(expected, output);
			
			input = "querer";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getIPAWithSyllables();
			expected = "ke.ɾeɾ";
			assertEquals(expected, output);
						
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
