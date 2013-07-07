package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse GoalObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert das Zielobjekt.
 * @see DungeonObject
 */

public class GoalObject extends DungeonObject {
		
	/**
	 * Der Konstruktor fuer das GoalObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren wird hier der State fuer das GoalObject gesetzt und das Bild geladen.
	 * @param x  Die x-Koordinate an der das GoalObject gezeichnet wird
	 * @param y  Die y-Koordinate an der das GoalObject gezeichnet wird
	 */
	
	public GoalObject(double x, double y) {
		super(x,y);
		state[0].changeTexture(Data_Textures.goal);			  // Aussehen des Ziels
		state[0].massive=false;				  // Ziel wird begehbar
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass, wenn der Player das GoalObject beruehrt, das Event levelCleared gefeuert wird.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Spieler handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Spieler trifft auf das Ziel
		super.onCollision(d);
		// Event feuern - fuer den Spieler
		if(d instanceof Player){
			for(GameEventListener gel : evtList){
				gel.levelCleared();
			}
		}
	}
}
