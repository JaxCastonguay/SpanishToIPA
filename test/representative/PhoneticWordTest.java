package representative;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import logic.CustomPhoneticsDTO;

public class PhoneticWordTest {
	@Test
	void basicPhoneticSwitch() throws Exception{
		PhonemicWord phonemicWord;
		try {
			
			List<CustomPhoneticsDTO> customPhonetics = new ArrayList<CustomPhoneticsDTO>();
			//TODO: issue encountered. Approxes use NOTs to determine replacements
			CustomPhoneticsDTO s = new CustomPhoneticsDTO('s', 'z',List.of('b', 'd', 'ɉ', 'g', 'v', 'x', 'ß', 'ð', 'Ɣ'), false, false);
			
			customPhonetics.add(s);
			
			String input = "desde";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables(customPhonetics);
			String expected = "'dez.de";//TEMP
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
