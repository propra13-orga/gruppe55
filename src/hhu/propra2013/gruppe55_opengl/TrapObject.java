package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse TrapObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Fallen.
 * @see DungeonObject
 */

public class TrapObject extends DungeonObject {

// Attribute

	/** Der Schaden den die Falle anrichtet (spezifiziert als trapDmg). */
	
	protected int trapDmg	=	1;	// Schaden, den diese Falle auslöst
	
	/**
	 * Der Konstruktor fuer das TrapObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden die States fuer das TrapObject gesetzt und die Bilder geladen.
	 * @param x  Die Methode erwartet die Uebergabe eines double Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines double Werts y
	 */

	public TrapObject(double x, double y) {
		super(x,y);
		// States definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.trap,false, false, true);		// Die nicht ausgeloeste Falle
		state[0].defineOffset(7,0,0,0,0);								// Hitbox der nicht ausgeloesten Falle
		state[1]	=	new State(Data_Textures.trapact, false, false, true);	// Die ausgeloeste Falle
		
	}
	
	/**
	 * Die Methode onCollision.
	 * Diese Methode ruft die Schadensberechnung auf und uebergibt das Element der Falle dazu.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Spieler handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// Dem Spieler oder Monstern Schaden zufuegen
	    	if(d instanceof	LivingObject)
				((LivingObject)d).getHit(trapDmg,element);
	    	// Statewechsel zur ausgeloesten Falle
	    	switchState(1);
	} 
}
