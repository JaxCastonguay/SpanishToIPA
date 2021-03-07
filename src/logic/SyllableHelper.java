package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sounds.CharacterClassification;

public class SyllableHelper {
	//##########################################
	//###Finding syllable break location########
	//##########################################

	public static List<Integer> findPointPositions(String word) {
		List<Integer> points = new ArrayList<Integer>();
		//V (Any type of vowel)
		for(int i = 0; i < word.length() - 1; i++) {
			if(CharacterClassification.phoneticvowels.contains(word.charAt(i))) {
				evaluateIndexAsVowel(word, points, i);//TODO: nasal vowel break will be fixed somewhere in here
			}
			//C
			else {
				evaluateIndexAsConsonant(word, points, i);
			}
			
		}
		
		return points;
	}

	private static void evaluateIndexAsConsonant(String word, List<Integer> points, int i) {
		//CC
		if(!CharacterClassification.phoneticvowels.contains(word.charAt(i+1))) {
			StringBuilder sb = new StringBuilder();
			sb.append(word.charAt(i));
			sb.append(word.charAt(i+1));
			//Not cons blend -> C.C
			if(!CharacterClassification.phoneticConsonantBlends.contains(sb.toString())
					&& word.charAt(i) != '̃') {
				points.add(i);
			}
			else if(word.charAt(i) == '̃' 
					&& i+2 < word.length() && CharacterClassification.phoneticvowels.contains(word.charAt(i+2))) {//we can assume i + 1 is a cons cause it has to be for accent at i
				//Special case for how nasal accents are read.
				//case V~CV -> V~.CV
				points.add(i);
			}
			//Cons blend
			else {
				//Do nothing
			}
		}
		//CV
		else {
			
		}
	}

	private static void evaluateIndexAsVowel(String word, List<Integer> points, int i) {
		//[V]V Next is any type of vowel
		if(CharacterClassification.phoneticvowels.contains(word.charAt(i+1))) {
			evaluateIndexAsVowelWithVowelNext(word, points, i);
			//else next or current is diptong and no change is needed.
		}
		
		//[V]C
		//if VCC(joinable)
		//V.CC (add)
		//if VCC(not join)
		//VC.C (nothing)
		//if VC_(empty)
		//nothing
		//if VCV
		//V.CV (add)
		else {
			evaluateIndexAsVowelWithConsonantNext(word, points, i);
		}
	}

	private static void evaluateIndexAsVowelWithConsonantNext(String word, List<Integer> points, int i) {
		//Len check
		if(word.length() > i + 2) {
			//VCV -> V.CV
			if(CharacterClassification.phoneticvowels.contains(word.charAt(i+2))) {
				points.add(i);
			}
			//VCC
			else {
				
				StringBuilder sb = new StringBuilder();
				sb.append(word.charAt(i+1));
				sb.append(word.charAt(i+2));
				
				//VCC (joinable) -> V.CC
				if(CharacterClassification.phoneticConsonantBlends.contains(sb.toString())) {
					//could be l̪ ex: al̪.to
					if(word.charAt(i+2) != '̪' &&word.charAt(i+2) != '̺') {
						points.add(i);
					}
				}
				//VC.C (handled by CC below)			
			}
		}
		else //VC_ (do nothing)
		{}
	}

	private static void evaluateIndexAsVowelWithVowelNext(String word, List<Integer> points, int i) {
		//[V]V -> V.V
		//Current and next are both strong vowels. Separate.
		if(CharacterClassification.vowels.contains(word.charAt(i))//TODO: Examine. Should this be comparing vowels or strong vowels?
				&& CharacterClassification.vowels.contains(word.charAt(i+1))) {
			points.add(i);
		}
	}
	
	
	//####################################
	//##Dot insertion and correction######
	//####################################
	
	public static String getWordWithSyllables(String word, int accentedVowelIndex) {
		List<Integer> points = new ArrayList<Integer>();
		points = SyllableHelper.findPointPositions(word);
		
		SyllableHelper.ksJoinFix(word, points);
		
		StringBuilder sb = new StringBuilder(word);
		
		SyllableHelper.insertDots(points, sb);
				
		SyllableHelper.insertAccentuationPoint(points, sb, accentedVowelIndex);
		
		return sb.toString();
	}
	
	private static void ksJoinFix(String word, List<Integer> points) {
		List<Integer> ruleBreakers = new ArrayList<Integer>();
		
		//ex: extraño. -> eks.tɾa.ɲo. st/sp/sk should never join
		Pattern p = Pattern.compile("ks["+ CharacterClassification.getConsonantsAsString() +"]"); 
		Matcher m = p.matcher(word);
		int temp = -1;
		
		if(m.find())
			temp = m.start();
		
		if(temp > 0)
			ruleBreakers.add(temp);
		
		for(Integer rb : ruleBreakers) {
			points.remove(rb - 1);
		}
		
	}
	
	
	private static void insertDots(List<Integer> points, StringBuilder sb) {
		int wordOffset = 1;
		for(int i = 0; i < points.size(); i++) {
			sb.insert(points.get(i) + wordOffset, '.');
			wordOffset++;
		}
	}
	
	private static void insertAccentuationPoint(List<Integer> points, StringBuilder sb, int accentedVowelIndex) {
		//Start simple. single syllable word
		if(points.size() == 0) {
			sb.insert(0, '\'');
		}
		//Multiple syllables
		else {
			//Already know that the stress is on an accented syllable
			if(accentedVowelIndex != -1) {
				//correct accent index
				for(Integer point : points) {
					if(point < accentedVowelIndex) {
						accentedVowelIndex++;
					}
				}
				//insert accent
				String beforeAccent = sb.substring(0, accentedVowelIndex);
				int indexOfAccentuatedDot = beforeAccent.lastIndexOf('.');
				//Plus one because we would otherwise be on the wrong side of the dot.
				sb.insert(indexOfAccentuatedDot + 1, '\'');
				
			}
			//No accented vowel need to find which syllable to accentuate
			else {
				int indexOfAccentuatedDot = getAccentIndex(sb);
				sb.insert(indexOfAccentuatedDot + 1, '\'');				
			}			
		}
	}
	
	private static int getAccentIndex(StringBuilder sb) {
		//Assume last syllable stress
		int indexOfLastDot = sb.lastIndexOf(".");
		
		//Second to last syllable stressed 
		if(CharacterClassification.penultimas.contains(sb.charAt(sb.length() - 1))) {
			String lastSyllableRemoved = sb.substring(0, indexOfLastDot);
			//Now second point
			indexOfLastDot = lastSyllableRemoved.lastIndexOf('.');			
		}
		return indexOfLastDot;
	}
}
