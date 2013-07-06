package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse SimpleSword.
 * Diese Klasse erbt von der Klasse Weapon und spezifiziert diese weiter als SimpleSword (einfaches Schwert).
 * @see Weapon
 */

public class SimpleSword extends Weapon {

	/**
	 * Der Konstruktor fuer das SimpleSword.
	 * Hier werden die Attribute der Mutterklasse Weapon aufgerufen und dann der Schaden ueberschrieben.
	 */
	
	public SimpleSword() {
		super();
		
		// Schadenswerte setzen
		minDmg	=	1;
		maxDmg	=	3;
	}

}
