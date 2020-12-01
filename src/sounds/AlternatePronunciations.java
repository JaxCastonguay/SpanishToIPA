package sounds;

import java.util.List;

import logic.CustomPhoneticsDTO;

public class AlternatePronunciations {
	public static final CustomPhoneticsDTO sToZ = new CustomPhoneticsDTO('s', 'z',List.of('b', 'd', 'ɉ', 'g', 'v', 'x', 'ß', 'ð', 'Ɣ'), false, false, false);
	public static final CustomPhoneticsDTO dToð = new CustomPhoneticsDTO('d', 'ð', CharacterClassification.bdgNonModifiers, true, true, true);
}
