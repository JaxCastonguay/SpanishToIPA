package sounds;

import java.util.ArrayList;
import java.util.List;

public class CharacterClassification {
	//Consonants
	public final static List<String> consonantBlends = new ArrayList<String>(List.of("bl", "fl", "cl", "gl", "pl", "cr", "br", "tr", "gr", "fr", "pr", "dr", "tl"));
	
	public final static List<String> phoneticConsonantBlends = new ArrayList<String>(List.of("bl", "ßl",  "fl", "kl", "gl", "Ɣl", "pl", "kɾ", "bɾ", "ßɾ", "tɾ", "gɾ", "Ɣɾ", "fɾ", "pɾ", "dɾ", "ðɾ" , "tl"));

	public final static List<Character> consonants = new ArrayList<Character>(List.of('b', 'ß', 'ʧ', 'd', 'ð', 'f', 'g', 'Ɣ', 'ʝ', 'ɉ', 'k', 'l', 'm', 'n', 'ɲ', 'p', 'r', 'ɾ', 's', 't', 'x', 'z')); //j & w are diptongs and are not consonants in this context
	
	//Consonant modification
	public final static List<Character> bdgNonModifiers = new ArrayList<Character>(List.of('|','m','n','ɲ'));//TODO: | is a place holder for no letter before.
	
	public final static List<Character> cModifiers = new ArrayList<Character>(List.of('e', 'i'));
	
	//Placement
	public final static List<Character> penultimas = new ArrayList<Character>(List.of('s','n','o', 'a', 'e', 'i', 'u'));
	
	//Sound Map
	//TODO: do nasales count as sonoras?
	public final static List<Character> sonoras = new ArrayList<Character>(List.of('b', 'd', 'ɉ', 'g', 'v', 'x', 'ß', 'ð', 'Ɣ'));
	
	//Vowels
	public final static List<Character> vowels = new ArrayList<Character>(List.of('a', 'e', 'i', 'o', 'u'));
	
	public final static List<Character> phoneticvowels = new ArrayList<Character>(List.of('a', 'e', 'o', 'i', 'u', 'w', 'j'));

	public final static List<Character> strongVowels = new ArrayList<Character>(List.of('a', 'e', 'o'));

	public final static List<Character> diptongVowels = new ArrayList<Character>(List.of('i', 'u'));
	
	public final static List<Character> accentedVowels = new ArrayList<Character>(List.of('á','é', 'í', 'ó', 'ú'));

}