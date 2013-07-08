package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse ArrowObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert das Pfeilobjekt, mit dem der Player schiesst.
 * @see DungeonObject
*/

public class ArrowObject extends DungeonObject {
		
	/**
	 * Der Konstruktor fuer das ArrowObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer das Pfeilobjekt gesetzt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der das Pfeilobjekt gezeichnet wird
	 * @param y  Die y-Koordinate an der das Pfeilobjekt gezeichnet wird
	 */
	
	public ArrowObject(double x, double y) {
		super(x,y);
		// States des Schatzes definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.lootarrow,false, false, true);			// Aufsammelbarer Pfeil
		state[1]	=	new State(Data_Textures.lootarrow, false, false, false);			// Aufgesammelter Pfeil
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Player das Pfeilobjekt einsammeln kann, dass der eingesammelte Pfeil "verschwindet" und dass sich die Anzahl seiner Pfeile erhoeht.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Player handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Pfeilanzahl erhoehen bei Kollision
		// der Spieler erhaelt Gold
    	if(d instanceof	Player)
			((Player)d).giveStatInventoryObject(4, +5);
    	
    	// Wechsel auf Status "eingesammelter Pfeil"
    	switchState(1);
	}
}