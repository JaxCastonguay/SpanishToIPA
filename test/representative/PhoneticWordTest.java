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
	void customPhoneticSwitch() throws Exception{
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
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void bdgPhoneticSwitch() throws Exception{
		PhonemicWord phonemicWord;
		try {
			
			String input = "desde";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables();
			String expected = "'dez.ðe";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "bilabial";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "bi.la.'ßjal";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);	
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void jPhoneticSwitch() throws Exception{
		PhonemicWord phonemicWord;
		try {
			
			//ɟ
			String input = "llamar";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables();
			String expected = "ɟa.'maɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	void ePhoneticSwitch() throws Exception{
		PhonemicWord phonemicWord;
		try {
			
			//e
			String input = "vender";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables();
			String expected = "bɜn.'dɜɾ";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "viene";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'bje.ne";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "peine";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'pɜj.ne";
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
	
	@Test
	void ɾPhoneticSwitch() throws Exception{
		PhonemicWord phonemicWord;
		try {
			//Stay ɾ
			String input = "coro";
			phonemicWord = new PhonemicWord(input);
			String output = phonemicWord.getPhoneticsWithSyllables();
			String expected = "'ko.ɾo";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "arte";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'aɾ.te";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "tres";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'tɾes";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			//Stay r
			input = "carro";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'ka.ro";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			//ɾ to r
			//first one should be handled by phonemic.
			input = "rico";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "'ri.ko";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
			
			input = "enrique";
			phonemicWord = new PhonemicWord(input);
			output = phonemicWord.getPhoneticsWithSyllables();
			expected = "ɜn.'ri.ke";
			System.out.println("Input: " + input + ", Expected: " + expected + ", actual: " + output);
			assertEquals(expected, output);
					
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}
