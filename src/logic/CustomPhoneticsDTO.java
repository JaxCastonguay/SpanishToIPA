package logic;

import java.util.ArrayList;
import java.util.List;

public class CustomPhoneticsDTO {
	private Boolean isCheckingBefore;
	private char base;
	//This won't account for nasalization.
	private char replacement; //TODO: Issue as replacement could be mult phonetics and depend on modifier
	private List<Character> modifiers;
	private Boolean isEffectedByPause;
	private Boolean isChangesUnlessModifiers;
	
	public CustomPhoneticsDTO(char base, char replacement,List<Character> modifiers, Boolean isCheckingBefore, Boolean isEffectedByPause, Boolean isChangesUnlessModifiers) {
		this.isCheckingBefore = isCheckingBefore;
		this.base = base;
		this.replacement = replacement;
		this.modifiers = modifiers;
		this.isEffectedByPause = isEffectedByPause;
		this.isChangesUnlessModifiers = isChangesUnlessModifiers;
	}
	
	public Boolean determineIsCheckingBefore() {
		return isCheckingBefore;
	}
	public char getBase() {
		return base;
	}
	public char getReplacement() {
		return replacement;
	}
	
	public List<Character> getModifiers(){
		if(modifiers == null) {
			return new ArrayList<Character>();
		}
		
		return modifiers;
	}
	
	public Boolean determineisEffectedByPause() {
		return isEffectedByPause;
	}
	
	public Boolean determineisChangesUnlessModifiers() {
		return isChangesUnlessModifiers;
	}
}
