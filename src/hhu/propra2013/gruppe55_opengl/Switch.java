package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Switch.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Funktion eines Schalters.
 * @see DungeonObject
 */

public class Switch extends DungeonObject {
	protected String[] triggersToHandle;
	
	/**
	 * Der Konstruktor fuer den Switch.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten, sowie ein String mit Triggern uebergeben.
	 * Des Weiteren werden hier die States gesetzt und die Bilder fuer den Switch geladen.
	 * @param x  Die Methode erwartet die Uebergabe eines double Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines double Werts y
	 * @param triggersToHandle  Die Methode erwartet die Uebergabe eines Strings[] triggersToHandle
	 */

	public Switch(double x, double y, String[] triggersToHandle) {
		super(x, y);
		
		// State neu setzen
		state	=	new State[2];
		// Bilder der Schalter
		state[0]	=	new State(Data_Textures.button_off,false,true,true);  	// Schalter steht auf "Off"
		state[1]	=	new State(Data_Textures.button_on,false,true,true);		// Schalter steht auf "On"
		
		// Namen zum abhoeren
		this.triggersToHandle	=	triggersToHandle;
		
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Player den Schalter betaetigen kann. Beim Betaetigen des Schalters wird das Bild des Schalters geaendert und das Triggerevent gefeuert. 
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Player handelt
	 * @see DungeonObject
	 */
	
	// Aktuell noch keine Interaktionsmechanik -> Kollision!
	public void onCollision(DungeonObject d){
		// nur der Spieler hat die Macht!
		if(d instanceof Player){
			for(String key:triggersToHandle){
				fireTrigger(key);
				// Status wechseln
			}
			switchState(1-currState);
		}
		// Zurueckschieben
		super.onCollision(d);
	}

}
