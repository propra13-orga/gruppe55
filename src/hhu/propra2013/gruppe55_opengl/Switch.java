package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Switch.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Funktion eines Schalters.
 * @see DungeonObject
 */

public class Switch extends DungeonObject {
	
	/** Das Array mit den zu behandelnden Triggern. */
	
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
	 * Die Methode interact. 
	 * Diese Methode sorgt dafuer, dass der Player den Schalter betaetigen kann. Beim Betaetigen des Schalters wird das Bild des Schalters geaendert und das Triggerevent gefeuert. 
	 * Die Methode interact ueberschreibt die aus der Klasse DungeonObject stammende Methode interact.
	 * @param px X-Koordinate des Spielers
	 * @param py Y-Koordinate des Spielers
	 */
	public void interaction(int px, int py){
		// Abfrage ob der Spieler in Interagtionsreichweite intergieren will! 
		if(distanceBetween(px, py)<=interactionRange){
			for(String key:triggersToHandle){
				fireTrigger(key);
				// Status wechseln
			}
			switchState(1-currState);
		}
	}

}
