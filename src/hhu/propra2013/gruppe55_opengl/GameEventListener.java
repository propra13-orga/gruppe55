package hhu.propra2013.gruppe55_opengl;

/**
 * Die Interface-Klasse GameEventListener.
 * Diese Klasse implementiert den Listener fuer verschiedene Ereignisse im Spiel.
 */

public interface GameEventListener {
	
	/**
	 * Die Methode newTreasure.
	 * Diese Methode implementiert die Funktion, mit der besiegte Gegner loot droppen. 
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 */
	
	// Vorlauefiges Beute-Event
	public void newTreasure(double x, double y);
	
	/**
	 * Die Methode newStatic.
	 * Diese Methode implementiert die Funktion, mit der der StaticList des Levels Objekte hinzugefuegt werden koennen.
	 * @param o Die Methode erwartet das hinzuzufuegende DungeonObject
	 */
	
	// Boss droppt Ziel
	public void newStatic(DungeonObject d);
	
	/**
	 * Die Methode newGoal.
	 * Diese Methode implementiert die Funktion, mit der besiegte Bosse das GoalObject droppen.
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 */
	
	// Boss droppt Ziel
	public void newGoal(double x, double y);

	/**
	 * Die Methode shootProjectile.
	 * Diese Methode implementiert die Funktion, mit der ein Projektil abgefeuert wird.
	 * @param p  Die Methode erwartet die Uebergabe eines Objektes vom Typ Projectile
	 */
	
	// Geschosse werden geschossen
	public void shootProjectile(Projectile p);
	
	/**
	 * Die Methode levelCleared.
	 * Diese Methode implementiert die Funktion, mit der das Level erfolgreich abgeschlossen wurde und das naechste Level geladen wird.
	 */
	
	// Level Beendet:
	public void levelCleared();
	
	/**
	 * Die Methode checkPointReached.
	 * Diese Methode implementiert die Funktion, zur Mitteilung dass der Checkpoint erreicht wurde.
	 */
	
	// Spieler erreicht einen Checkpoint!
	public void checkPointReached();
	
	/**
	 * Die Methode triggerFired.
	 * Diese Methode implementiert die Funktion, mit der ein Trigger abgefeuert wird.
	 * @param key
	 */
	
	// Ein Trigger wurde gefeuert
	public void triggerFired(String key);
	
	
	/**
	 * Diese Methode implementiert eine Funktion, die es ermoeglicht Dialoge im Level auszugeben
	 * @param dialog Der entsprechende Dialog, zeilenweise im Array
	 */
	public void showDialog(String[] dialog);
	
	/**
	 * Diese Methode laesst den Ingame-Shop aufrufen
	 */
	public void openShop();
	
	public void giveWeaponToPlayer();
}
