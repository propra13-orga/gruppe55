package hhu.propra2013.gruppe55_opengl;

import java.awt.Rectangle;

/**
 * Die abstrake Klasse Weapon.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert das Grundgeruest fuer die Waffen.
 * @see DungeonObject
 */

public abstract class Weapon extends DungeonObject {

// Attribute
	// Spezifikation der Waffe
	
	/** Der Waffentyp (0=Nahkampf, Haupthand;; 1=Nahkampf, Nebenhand;; 2=Fernkampf). */
	
	protected int type	=	0;		// Waffentyp (0=Nahkampf, Haupthand;; 1=Nahkampf, Nebenhand;; 2=Fernkampf)
	
	/** Der Name der Waffe. */
	
	protected String name	=	"Simples Schwert";	// Waffenname
	
	// Angriffswerte
	
	/** Die Angriffszeit in Millisekunden. */
	
	protected int atkTime	=	250;	// Angriffszeit in Millisekunden
	
	/** Der Minimalschaden der Waffe. */
	
	protected int minDmg	=	0;		// Mindestschaden
	
	/** Der Maximalschaden der Waffe. */
	
	protected int maxDmg	=	0;		// Maximalschaden
	
	/** Abfrage, ob der Spieler angreift. */
	
	protected boolean attacking	=	false;	// Waehrend des Angriffs true
	
	// Offsetwerte zum zeichnen
	
	/** Die Offsets, wie die Waffe in der Spielerhand gehalten werden soll. */
	
	protected int[][] weapOffsets	=	new int[4][6];	// Die Offsets wie die Waffe in der Spielerhand gehalten wird	
	
	// Statuswerte die durch die Waffe erhoeht werden
	
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
	 * Der Konstruktor fuer die Weapon.
	 * Beim Aufruf werden dem Konstruktor die Werte (0,0) uebergeben. Das liegt daran, dass sie im Player angepasst werden.
	 * Des Weiteren werden alle wichtigen Attribute und Offsets der Waffen hier initialisiert (und in den Unterklassen dann gesetzt), die States gesetzt und die Bilder geladen.
	 */
	
	// Konstruktor
	public Weapon(double x, double y) {
		super(0, 0);	// Scheint merkwuerdig, macht aber Sinn (wird dem Spieler angepasst)
		
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
		
		// States setzen
		state	=	new State[3];
		state[0]	=	new State(Data_Textures.basicsword, false, false, true);
			state[0].defineOffset(0, 0, 5, 6, 0);
		state[1]	=	new State(Data_Textures.basicsword, false, false, true);
			state[1].defineOffset(0, 0, 5, 6, 0);
			state[1].defineOffset(0, 0, 5, 6, 1);
			state[1].defineOffset(0, 0, 5, 6, 2);
			state[1].defineOffset(0, 0, 5, 6, 3);
		state[2]	=	new State(Data_Textures.basicsword_f_atk, Data_Textures.basicsword_l_atk, Data_Textures.basicsword_r_atk, Data_Textures.basicsword_u_atk, false, false, true);
			state[2].defineOffset(0, 0, 10, 6, 0);
			state[2].defineOffset(0, 0, 6, 10, 1);
			state[2].defineOffset(0, 0, 6, 10, 2);
			state[2].defineOffset(0, 0, 10, 6, 3);
		
		switchState(1);
		
		// Offsets zeichnen - von vorne
		weapOffsets[0][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[0][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[0][2]	=	3;	// X-offset von gehaltener Waffe
		weapOffsets[0][3]	=	21;	// Y-Offset von gehaltener Waffe
		weapOffsets[0][4]	=	3;	// X-Offset beim Angriff
		weapOffsets[0][5]	=	-3;	// Y-offset beim Angriff
		// Offsets zeichnen - von links
		weapOffsets[1][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[1][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[1][2]	=	3;	// X-offset von gehaltener Waffe
		weapOffsets[1][3]	=	21;	// Y-Offset von gehaltener Waffe
		weapOffsets[1][4]	=	23;	// X-Offset beim Angriff
		weapOffsets[1][5]	=	2;	// Y-offset beim Angriff
		// Offsets zeichnen - von rechts
		weapOffsets[2][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[2][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[2][2]	=	3;	// X-offset von gehaltener Waffe
		weapOffsets[2][3]	=	21;	// Y-Offset von gehaltener Waffe
		weapOffsets[2][4]	=	-3;	// X-Offset beim Angriff
		weapOffsets[2][5]	=	3;	// Y-offset beim Angriff
		// Offsets zeichnen - von hinten
		weapOffsets[3][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[3][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[3][2]	=	3;	// X-offset von gehaltener Waffe
		weapOffsets[3][3]	=	21;	// Y-Offset von gehaltener Waffe
		weapOffsets[3][4]	=	3;	// X-Offset beim Angriff
		weapOffsets[3][5]	=	27;	// Y-offset beim Angriff
	}
	
	/**
	 * Die Methode getAtkTime.
	 * Diese Methode gibt die Angriffsdauer zurueck.
	 * @return Der Wert atkTime als int.
	 */
	
	// Rueckgabe der Angriffsdauer
	public int getAtkTime(){
		return atkTime;
	}
	
	/**
	 * Die Methode draw.
	 * Diese Methode zeichnet die Waffe mit den uebergebenen Offsets.
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 */
	// Zeichenmethode
	public void draw(int x, int y){
		//x, y Werte setzen
		this.x	=	(x-(weapOffsets[direction][currState*2+0]));
		this.y	=	(y-(weapOffsets[direction][currState*2+1]));
		super.draw();
	}
	
	/**
	 * Die Methode attack.
	 * Diese Methode setzt den Angriffsmodus.
	 */
	
	// Angriffsmodus setzen
	public void attack(){
		// auf Angriff setzen
		this.attacking	=	true;
		switchState(2);
	}
	
	/**
	 * Die Methode stopAttack.
	 * Diese Methode stoppt den Angriff.
	 */
	
	// Angriffsmodus abbrechen
	public void stopAttack(){
		// auf harmlos setzen
		this.attacking	=	false;
		switchState(1);
	}
	
	
	/**
	 * Die Methode getMinDmg.
	 * Diese Methode gibt den MinDmg zurueck. 
	 * @return Der Wert minDmg als int.
	 */
	
	public int getMinDmg(){
		return minDmg;
	}
	
	/**
	 * Die Methode getMaxDmg.
	 * Diese Methode gibt den MaxDmg zurueck. 
	 * @return Der Wert maxDmg als int.
	 */
	
	public int getMaxDmg(){
		return maxDmg;
	}
	
	/**
	 * Die Methode getBorder.
	 * Diese Methode erstellt beim Anriff eine Hitbox. Ansonsten gibt es keine Hitbox. 
	 * @return Die neue Hitbox, wenn der Player angreift.
	 * @return Keine Hitbox, wenn der Player nicht angreift.
	 */
	
	// Hitbox
	public Rectangle getBorder(){
		// bei Angriff macht die Hitbox Sinn!
		if(attacking)
			return super.getBorder();
		// ansonsten keine Hitbox
		return new Rectangle(0,0,0,0);
	}
	
	/**
	 * Die Methode getStats.
	 * Diese Methode gibt die Stats der Waffe zurueck.
	 * @return Die Stats der Waffe als int[].
	 */
	
	// Werte uebergeben
	public int[] getStats(){
		return new int[] {atk,def,hpMax,manaMax,critBonus,healBonus,manaBonus};
	}
	
	/**
	 * Die Methode getResistances.
	 * Diese Methode gibt die Resistances der Waffe zurueck.
	 * @return Die Resitenzen der Waffe als int.
	 */
	
	// Widerstaende uebergeben
	public int[][]getResistances(){
		return resistances;
	}

}
