package hhu.propra2013.gruppe55_opengl;

public interface GameEventListener {
	
	// Vorlauefiges Beute-Event
	public void newTreasure(double x, double y);
	
	// Boss droppt Ziel
	public void newGoal(double x, double y);

	
	// Geschosse werden geschossen
	public void shootProjectile(Projectile p);
	
	// Level Beendet:
	public void levelCleared();
	
	// Spieler erreicht einen Checkpoint!
	public void checkPointReached();
}
