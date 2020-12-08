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
			
			List<CustomPhoneticsDTO> customPhonetics = new ArrayList<CustomPhoneticsDTO>();
			
			customPhonetics.add(AlternatePronunciations.dToð);
			customPhonetics.add(AlternatePronunciations.bToß);
			customPhonetics.add(AlternatePronunciations.sToZ);
			customPhonetics.add(AlternatePronunciations.ɟToʝ);
			
			String input = "desde";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables(customPhonetics);
			String expected = "'dez.ðe";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "bilabial";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables(customPhonetics);
			expected = "bi.la.'ßjal";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "llamar";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables(customPhonetics);
			expected = "ɟa.'maɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
