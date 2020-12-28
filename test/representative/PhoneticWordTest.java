package representative;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import logic.CustomPhoneticsDTO;
import sounds.AlternatePronunciations;
import sounds.CharacterClassification;

public class PhoneticWordTest {
	@Test
	void basicPhoneticSwitch() throws Exception{
		PhonemicWord phonemicWord;
		try {
			//CUSTOM TEST
			List<CustomPhoneticsDTO> customPhonetics = new ArrayList<CustomPhoneticsDTO>();
			
			customPhonetics.add(AlternatePronunciations.dToð);
			customPhonetics.add(AlternatePronunciations.bToß);
			customPhonetics.add(AlternatePronunciations.sToZ);
			customPhonetics.add(AlternatePronunciations.ɟToʝ);
			
			String input = "desde";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getCustomPhoneticsWithSyllables(customPhonetics);
			String expected = "'dez.ðe";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "bilabial";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getCustomPhoneticsWithSyllables(customPhonetics);
			expected = "bi.la.'ßjal";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "llamar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getCustomPhoneticsWithSyllables(customPhonetics);
			expected = "ɟa.'maɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			
			//STANDARD TEST
			//b/d/g
			input = "desde";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'dez.ðe";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "bilabial";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "bi.la.'ßjal";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			//ɟ
			input = "llamar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "ɟa.'maɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			//e
			//TODO: ɜ needs to be added as a vowel
			input = "vender";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "bɜn.'dɜɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "venir";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "be.'niɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "desde";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'dez.ðe";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
