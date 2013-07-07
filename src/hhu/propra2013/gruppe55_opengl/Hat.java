package hhu.propra2013.gruppe55_opengl;

/** 
 * Die abstrakte Klasse Hat.
 * Diese erbt von der Klasse DungeonObject und implementiert die hats fuer den Player.
 * @see DungeonObject
 */

public abstract class Hat extends DungeonObject {
// Attribute
	// Statuswerte die durch den Hut erhoeht werden
	
	/** Der Bonus auf den atk Wert. */
	
	protected int atk;			// Bonus auf die ATK
	
	/** Der Bonus auf den def Wert. */
	
	protected int def;			// Bonus auf die DEF
	
	/** Der Bonus auf den hpMax Wert. */
	
	protected int hpMax;		// Maximal-HP wird um die angegebene Zahl Containerweise erhöht
	
	/** Der Bonus auf den manaMax Wert. */
	
	protected int manaMax;		// Wie maxHP nur fuer Mana
	
	/** Der Bonus auf den critBonus Wert. */
	
	protected int critBonus;	// erhoeht die Chance auf kritische Treffer
	
	/** Der Bonus auf den healBonus Wert.  */
	
	protected int healBonus;	// erhoeht die durch Traenke erhaltene Heilung
	
	/** Der Bonus auf den manaBonus Wert. */
	
	protected int manaBonus;	// wie healBonus nur fuer Mana

	/** Der Bonus auf die Widerstaende. */
	
	protected int[][] resistances;	// Widerstaende

	/**
	 * Der Konstruktor fuer die Klasse Hat.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die States fuer den hat, sowie die Attribute, die der hat dem Player gibt und die Resistenzen gesetzt. Da es sich um die abstrakte Klasse handelt, werden die wirklichen Attribute erst in den Unterklassen richtig gesetzt.
	 * @param x  Die x Koordinate des hats
	 * @param y  Die y Koordinate des hats
	 */
	
	public Hat(double x, double y) {
		super(x, y);
		
		// States
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.lavahat,false, false, true);			// der Hut
		state[0].defineOffset(0,0,6,12,0);
		state[1]	=	new State(Data_Textures.lavahat, false, false, false);			// der aufgesammelte Hut
		
		
		// Wertzuweisungen
		atk=0;			// Extra ATK
		def=0;			// extra DEF
		hpMax=0;		// extra auf die maximale Gesundheit
		manaMax=0;		// extra auf das maximale Mana
		critBonus=0;	// Extraprozentpunkte fuer die kritische Trefferchance
		healBonus=0;	// gewaehrte extraheilung bei Traenken zB
		manaBonus=0;	// gewaehrtes extramana bei Traenken zB
		// Elementarresistenzen
		resistances	=	new int[3][2];
		resistances[0][0]	=	0;	// Widerstand gegen Element 1
		resistances[0][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 1 Immun
		resistances[1][0]	=	0;	// Widerstand gegen Element 2
		resistances[1][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 2 Immun
		resistances[2][0]	=	0;	// Widerstand gegen Element 3
		resistances[2][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 3 Immun
	}
	
	/**
	 * Die Methode getStats.
	 * Diese Methode gibt die Stats, des hats zureuck.
	 * @return Die Stats als int[].
	 */
	
	// Werte uebergeben
	public int[] getStats(){
		return new int[] {atk,def,hpMax,manaMax,critBonus,healBonus,manaBonus};
	}
	
	/**
	 * Die Methode getResistances.
	 * Diese Methode gibt die Resistances, des hats zureuck.
	 * @return Die Resistenzen als int.
	 */
	
	// Widerstaende uebergeben
	public int[][]getResistances(){
		return resistances;
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Player den hat einsammeln kann, dass der eingesammelte hat "verschwindet" und der Player den Hut nun besitzt.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Player handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Aufnahme des Hutes bei Collision
    	if(d instanceof	Player){
			((Player)d).collecthat(); // TODO: Besser implementieren
			// Wechsel des Status auf "verschwundene" Potion
			switchState(1);
    	}
	} 
	
	/**
	 * Die Methode draw.
	 * Diese Methode ueberschreibt die Methode draw aus der Mutterklasse DungeonObject und aendert die Koordinaten leicht ab.
	 * @param x  Die x-Koordinate an der der hat gezeichnet wird
	 * @param y  Die y-Koordinate an der der hat gezeichnet wird
	 */
	
	public void draw(int x, int y){
		//x, y Werte setzen
		this.x	=	(x-2);
		this.y	=	(y-23);
		super.draw();
	}
}
