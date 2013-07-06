package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse TreasureObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Schaetze.
 * @see DungeonObject
 */

public class TreasureObject extends DungeonObject {
		
	/**
	 * Der Konstruktor fuer das TreasureObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer das TreasureObject gesetzt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der das TreasureObject gezeichnet wird
	 * @param y  Die y-Koordinate an der das TreasureObject gezeichnet wird
	 */
	
	public TreasureObject(double x, double y) {
		super(x,y);
		// States des Schatzes definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.treasure,false, false, true);			// Der Schatz 
		state[0].defineOffset(0,0,6,12,0);
		state[1]	=	new State(Data_Textures.treasure, false, false, false);			// Aufgesammelter Schatz
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Player das TreasureObject einsammeln kann, dass das eingesammelte TreasureObject "verschwindet" und dass sich der Geldstand des Players erhoeht.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Player handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Goldstand erhoehen bei Kollision
		// der Spieler erhaelt Gold
    	if(d instanceof	Player)
    		((Player)d).giveStatInventoryObject(1, 10);
    	
    	// Wechsel des Status auf eingesammeltes Gold
    	switchState(1);
	}
}