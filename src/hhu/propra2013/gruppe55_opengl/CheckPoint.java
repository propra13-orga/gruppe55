package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse Checkpoint.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert diese weiter als CheckPoint.
 * @see DungeonObject
 */

public class CheckPoint extends DungeonObject {
	
	/**
	 * Der Konstruktor fuer den CheckPoint.
	 * Beim Aufruf werden dem Konstruktor die Werte x und y uebergeben.
	 * Des Weiteren werden die States initialisiert und die Bilder der States geladen.
	 * @param x  Die x-Koordinate fuer den CheckPoint
	 * @param y  Die y-Koordinate fuer den CheckPoint
	 */

	public CheckPoint(double x, double y) {
		super(x, y);
		
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.cp_unused,false,false,true);
		state[1]	=	new State(Data_Textures.cp_used,false,false,true);
		
	}
	
	/**
	 * Die Methode onCollision
	 * Diese Methode ueberschreibt die Methode onCollision aus der Mutterklasse DungeonObject, ueberprueft ob der Player mit dem checkpoint kollidiert, aendert den State des Checkpoints und feuert das Event checkPointReached. 
	 */
	
	
	// Kollisionsabfrage
	public void onCollision(DungeonObject d){
		// Spieler beruehrt? Noch unbenutzt?
		if(d instanceof Player && currState==0){
    		// State wechseln
    		switchState(1);
			// Event feuern!
    		for(GameEventListener gel : evtList){
    			gel.checkPointReached();
    		}
		}
	}

}
