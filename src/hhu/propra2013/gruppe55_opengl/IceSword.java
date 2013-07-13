package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse IceSword.
 * Diese Klasse erbt von der Klasse Weapon und spezifiziert diese weiter als SimpleSword (einfaches Schwert).
 * @see Weapon
 */

public class IceSword extends Weapon {

	/**
	 * Der Konstruktor fuer das IceSword.
	 * Hier werden die Attribute der Mutterklasse Weapon aufgerufen und dann der Schaden ueberschrieben.
	 */
	
	public IceSword(double x, double y) {
		super(x,y);
		
		this.x = x;
		this.y = y;
	
		// States setzen
		state	=	new State[3];
		state[0]	=	new State(Data_Textures.icesworddrop, false, false, true);
		state[0].defineOffset(0,0,6,12,0);
		state[1]	=	new State(Data_Textures.icesword, false, false, true);
			state[1].defineOffset(0, 0, 5, 6, 0);
			state[1].defineOffset(0, 0, 5, 6, 1);
			state[1].defineOffset(0, 0, 5, 6, 2);
			state[1].defineOffset(0, 0, 5, 6, 3);
		state[2]	=	new State(Data_Textures.icesword_f_atk, Data_Textures.icesword_l_atk, Data_Textures.icesword_r_atk, Data_Textures.icesword_u_atk, false, false, true);
			state[2].defineOffset(0, 0, 10, 6, 0);
			state[2].defineOffset(0, 0, 6, 10, 1);
			state[2].defineOffset(0, 0, 6, 10, 2);
			state[2].defineOffset(0, 0, 10, 6, 3);
		
		switchState(1);
		
		
		// Schadenswerte setzen
		minDmg	=	2;
		maxDmg	=	4;
		atk=0;			// Extra ATK
		def=0;			// extra DEF
		hpMax=0;		// extra auf die maximale Gesundheit
		manaMax=0;		// extra auf das maximale Mana
		critBonus=5;	// Extraprozentpunkte fuer die kritische Trefferchance
		healBonus=0;	// gewaehrte extraheilung bei Traenken zB
		manaBonus=0;	// gewaehrtes extramana bei Traenken zB 
		element = 2;
		
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
	 * Diese Methode gibt die Stats, des Schwerts zureuck.
	 * @return Die Stats als int[].
	 */
	
	// Werte uebergeben
	public int[] getStats(){
		return new int[] {atk,def,hpMax,manaMax,critBonus,healBonus,manaBonus};
	}
	
	/**
	 * Die Methode getResistances.
	 * Diese Methode gibt die Resistances, des Schwerts zureuck.
	 * @return Die Resistenzen als int.
	 */
	
	// Widerstaende uebergeben
	public int[][]getResistances(){
		return resistances;
	}
	


}
