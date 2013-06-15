package hhu.propra2013.gruppe55;

public interface GameEventListener {
	
	// Vorlauefiges Beute-Event
	public void newTreasure(int x, int y);
	
	// Boss droppt Ziel
	public void newGoal(int x, int y);
	
	// Geschosse werden geschossen
	public void shootProjectile(Projectile p);
	
	// Level Beendet:
	public void levelCleared();
	
}
