package sounds;

import java.util.List;
import java.util.Arrays;

import logic.CustomPhoneticsDTO;

public class AlternatePronunciations {
	//TODO: Unimplemented. Want to expand on this later.
	public static final CustomPhoneticsDTO sToZ = new CustomPhoneticsDTO('s', 'z', Arrays.asList('b', 'd', 'ɉ', 'g', 'v', 'x', 'ß', 'ð', 'Ɣ'), false, false, false);
	public static final CustomPhoneticsDTO dToð = new CustomPhoneticsDTO('d', 'ð', addCharacterToList(CharacterClassification.bdgNonModifiers, 'l'), true, true, true);
	public static final CustomPhoneticsDTO bToß = new CustomPhoneticsDTO('b', 'ß', CharacterClassification.bdgNonModifiers, true, true, true);
	public static final CustomPhoneticsDTO gToƔ = new CustomPhoneticsDTO('g', 'Ɣ', CharacterClassification.bdgNonModifiers, true, true, true);
	public static final CustomPhoneticsDTO ɟToʝ = new CustomPhoneticsDTO('ɟ', 'ʝ', addCharacterToList(CharacterClassification.bdgNonModifiers, 'l'), true, true, true);
	
	private static List<Character> addCharacterToList(List<Character> charList, Character character){
		List<Character> newList = charList;
		newList.add(character);
		return newList;
	}
	
}
