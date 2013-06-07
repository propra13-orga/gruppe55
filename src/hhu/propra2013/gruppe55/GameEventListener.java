package hhu.propra2013.gruppe55;

public interface GameEventListener {
	
	// Vorlauefiges Beute-Event
	public void newTreasure(int x, int y);
	
	// Level Beendet:
	public void levelCleared();
	
}
