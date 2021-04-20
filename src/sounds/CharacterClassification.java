package sounds;

import java.util.Arrays;
import java.util.List;

public class CharacterClassification {
	//Consonants
	//public final static List<String> consonantBlends = new ArrayList<String>(List.of("bl", "fl", "cl", "gl", "pl", "cr", "br", "tr", "gr", "fr", "pr", "dr", "tl"));
	public final static List<String> consonantBlends = Arrays.asList("bl", "fl", "cl", "gl", "pl", "cr", "br", "tr", "gr", "fr", "pr", "dr", "tl");
	
	//public final static List<String> phoneticConsonantBlends = new ArrayList<String>(List.of("bl", "ßl",  "fl", "kl", "gl", "Ɣl", "pl", "kɾ", "bɾ", "ßɾ", "tɾ", "gɾ", "Ɣɾ", "fɾ", "pɾ", "dɾ", "ðɾ" , "tl", "t̪", "l̪", "n̪"));//TODO: change extract: the dental feels cheap... Not sure this is how it should be handle
	public final static List<String> phoneticConsonantBlends = Arrays.asList("bl", "ßl",  "fl", "kl", "gl", "Ɣl", "pl", "kɾ", "bɾ", "ßɾ", "tɾ", "gɾ", "Ɣɾ", "fɾ", "pɾ", "dɾ", "ðɾ" , "tl", "t̪", "l̪", "n̪");//TODO: change extract: the dental feels cheap... Not sure this is how it should be handle
	
	//public final static List<String> nasalVowels = new ArrayList<String>(List.of("ã",  "ẽ", "ĩ", "õ", "ũ", "ɜ̃"));
	public final static List<String> nasalVowels = Arrays.asList("ã",  "ẽ", "ĩ", "õ", "ũ", "ɜ̃");

	
	//public final static List<Character> consonants = new ArrayList<Character>(List.of('b', 'ß', 'c', 'ʧ', 'd', 'ð', 'f', 'g', 'Ɣ', 'ʝ', 'ɉ', 'k', 'l', 'm', 'n', 'ɲ', 'p', 'r', 'ɾ', 's', 't', 'x', 'z')); //j & w are diptongs and are not consonants in this context
	public final static List<Character> consonants = Arrays.asList('b', 'ß', 'c', 'ʧ', 'd', 'ð', 'f', 'g', 'Ɣ', 'ʝ', 'ɉ', 'k', 'l', 'm', 'n', 'ɲ', 'p', 'r', 'ɾ', 's', 't', 'x', 'z'); //j & w are diptongs and are not consonants in this context

	
	//Consonant modification
	//public final static List<Character> bdgNonModifiers = new ArrayList<Character>(List.of('|','m','n','ɲ'));//TODO: | is a place holder for no letter before.
	public final static List<Character> bdgNonModifiers = Arrays.asList('|','m','n','ɲ');//TODO: | is a place holder for no letter before.

	
	//public final static List<Character> cModifiers = new ArrayList<Character>(List.of('e', 'ɜ', 'i'));
	public final static List<Character> cModifiers = Arrays.asList('e', 'ɜ', 'i');
	
	//Placement
	//public final static List<Character> penultimas = new ArrayList<Character>(List.of('s','n','o', 'a', 'e', 'i', 'u'));
	public final static List<Character> penultimas = Arrays.asList('s','n','o', 'a', 'e', 'i', 'u');

	
	//Sound Map
	//TODO: do nasales count as sonoras?
	//lip placement
	//public final static List<Character> sonoras = new ArrayList<Character>(List.of('b', 'd', 'ɉ', 'ʝ', 'g', 'v', 'x', 'ß', 'ð', 'Ɣ'));
	public final static List<Character> sonoras = Arrays.asList('b', 'd', 'ɉ', 'ʝ', 'g', 'v', 'x', 'ß', 'ð', 'Ɣ');
	
	//public final static List<Character> bilabiales = new ArrayList<Character>(List.of('p', 'b', 'β', 'm', 'ɸ'));
	public final static List<Character> bilabiales = Arrays.asList('p', 'b', 'β', 'm', 'ɸ');
	
	//public final static List<Character> labiodentales = new ArrayList<Character>(List.of('f', 'v', 'ɱ'));
	public final static List<Character> labiodentales = Arrays.asList('f', 'v', 'ɱ');
	
	//public final static List<String> dentales = new ArrayList<String>(List.of("t", "d", "ð", "θ", "̪l", "n̪"));
	public final static List<String> dentales = Arrays.asList("t", "d", "ð", "θ", "̪l", "n̪");

	
	//public final static List<String> alveolares = new ArrayList<String>(List.of("s", "z", "s̺", "z̺", "n", "ɾ", "r", "l"));
	public final static List<String> alveolares = Arrays.asList("s", "z", "s̺", "z̺", "n", "ɾ", "r", "l");
	
	//public final static List<Character> palatales = new ArrayList<Character>(List.of('ɉ', 'ʝ', 'ʒ', 'ʃ', 'ʧ', 'ɲ', 'ɲ'));
	public final static List<Character> palatales = Arrays.asList('ɉ', 'ʝ', 'ʒ', 'ʃ', 'ʧ', 'ɲ', 'ɲ');

	//public final static List<Character> velares = new ArrayList<Character>(List.of('k', 'g', 'x', 'ɣ', 'ŋ'));
	public final static List<Character> velares = Arrays.asList('k', 'g', 'x', 'ɣ', 'ŋ');

	//air movement
	//public final static List<String> nasales = new ArrayList<String>(List.of("n", "m", "ɱ", "ŋ", "ɲ", "n̪"));
	public final static List<String> nasales = Arrays.asList("n", "m", "ɱ", "ŋ", "ɲ", "n̪");
	
	//public final static List<String> laterales = new ArrayList<String>(List.of("l", "ʎ", "̪l"));
	public final static List<String> laterales = Arrays.asList("l", "ʎ", "̪l");
	
	//public final static List<String> sibilante = new ArrayList<String>(List.of("s", "z", "s̺", "z̺", "ʒ", "ʃ"));
	public final static List<String> sibilante = Arrays.asList("s", "z", "s̺", "z̺", "ʒ", "ʃ");	
	
	//Vowels
	//public final static List<Character> vowels = new ArrayList<Character>(List.of('a', 'e', 'ɜ', 'i', 'o', 'u'));
	public final static List<Character> vowels = Arrays.asList('a', 'e', 'ɜ', 'i', 'o', 'u');

	//public final static List<Character> phoneticvowels = new ArrayList<Character>(List.of('a', 'e', 'ɜ', 'o', 'i', 'u', 'w', 'j'));
	public final static List<Character> phoneticvowels = Arrays.asList('a', 'e', 'ɜ', 'o', 'i', 'u', 'w', 'j');

	//public final static List<Character> strongVowels = new ArrayList<Character>(List.of('a', 'e', 'ɜ', 'o'));
	public final static List<Character> strongVowels = Arrays.asList('a', 'e', 'ɜ', 'o');

	//public final static List<Character> diptongVowels = new ArrayList<Character>(List.of('i', 'u'));
	public final static List<Character> diptongVowels = Arrays.asList('i', 'u');
	
	//public final static List<Character> diptongAsConsonants = new ArrayList<Character>(List.of('j', 'w'));
	public final static List<Character> diptongAsConsonants = Arrays.asList('j', 'w');
	
	//public final static List<Character> accentedVowels = new ArrayList<Character>(List.of('á','é', 'í', 'ó', 'ú'));
	public final static List<Character> accentedVowels = Arrays.asList('á','é', 'í', 'ó', 'ú');	
	
	
	//public static final List<Character> nonPhonems = new ArrayList<Character>(List.of('c', 'h', 'q', 'v', 'x', 'y', 'z','ñ')); //v and z are pronounced in Spain.
	public static final List<Character> nonPhonems = Arrays.asList('c', 'h', 'q', 'v', 'x', 'y', 'z','ñ'); //v and z are pronounced in Spain.
	
	//public static final List<Character> dependentPhonems = new ArrayList<Character>(List.of('c', 'g', 'l'));
	public static final List<Character> dependentPhonems = Arrays.asList('c', 'g', 'l');

	//public static final List<Character> switchPhonemes = new ArrayList<Character>(List.of('j', 'u', 'i'));
	public static final List<Character> switchPhonemes = Arrays.asList('j', 'u', 'i');
	
	
	public static String getConsonantsAsString() {
		List<Character> str =  consonants;
        StringBuilder sb = new StringBuilder(); 
  
        for (Character ch : str) { 
            sb.append(ch); 
        } 
  
        return sb.toString(); 
	}
    

}
