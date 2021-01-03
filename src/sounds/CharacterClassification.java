package sounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public final static List<Character> vowels = new ArrayList<Character>(List.of('a', 'e', 'ɜ', 'i', 'o', 'u'));
	
	public final static List<Character> phoneticvowels = new ArrayList<Character>(List.of('a', 'e', 'ɜ', 'o', 'i', 'u', 'w', 'j'));

	public final static List<Character> strongVowels = new ArrayList<Character>(List.of('a', 'e', 'o'));

	public final static List<Character> diptongVowels = new ArrayList<Character>(List.of('i', 'u'));
	
	public final static List<Character> diptongAsConsonants = new ArrayList<Character>(List.of('j', 'w'));
	
	public final static List<Character> accentedVowels = new ArrayList<Character>(List.of('á','é', 'í', 'ó', 'ú'));
	
	
	
	public static final List<Character> nonPhonems = new ArrayList<Character>(List.of('c', 'h', 'q', 'v', 'x', 'y', 'z','ñ')); //v and z are pronounced in Spain.
	
	public static final List<Character> dependentPhonems = new ArrayList<Character>(List.of('c', 'g', 'l'));
	
	public static final List<Character> switchPhonemes = new ArrayList<Character>(List.of('j', 'u', 'i'));
	
	
	public static String getConsonantsAsString() {
		List<Character> str =  consonants;
        StringBuilder sb = new StringBuilder(); 
  
        for (Character ch : str) { 
            sb.append(ch); 
        } 
  
        return sb.toString(); 
	}
    

}
