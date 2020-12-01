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
			//TODO: issue encountered. Approxes use NOTs to determine replacements
			
			customPhonetics.add(AlternatePronunciations.dToð);
			customPhonetics.add(AlternatePronunciations.sToZ);
			
			String input = "desde";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables(customPhonetics);
			String expected = "'dez.ðe";
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
