package logic;

import java.util.ArrayList;
import java.util.List;

import sounds.CharacterClassification;

public class SyllableHelper {
	public static List<Integer> findPointPositions(String word) {
		List<Integer> points = new ArrayList<Integer>();
		//V (Any type of vowel)
		for(int i = 0; i < word.length() - 1; i++) {
			if(CharacterClassification.phoneticvowels.contains(word.charAt(i))) {
				evaluateIndexAsVowel(word, points, i);
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
			if(!CharacterClassification.phoneticConsonantBlends.contains(sb.toString())) {//TODO: can I add the dental here in the cons blends?
				points.add(i);
			}
			//Cons blend
			else {
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
					if(word.charAt(i+2) != '̪') {
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
		if(CharacterClassification.vowels.contains(word.charAt(i))//TODO: error found. These should both be strong vowels
				&& CharacterClassification.vowels.contains(word.charAt(i+1))) {
			points.add(i);
		}
	}
}
