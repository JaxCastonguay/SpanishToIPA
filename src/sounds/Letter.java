package sounds;

import java.util.List;

public interface Letter {
	Boolean hasNext();
	Boolean hasPrevious();
	Boolean isVowel();
	Boolean isEffectedByNext();
	public final List< soundTypes> soundTypes = null;
	
}
