package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Creature_Bow.
 * Diese Klasse erbt von der Klasse Creature und spezifiziert diese weiter als Creature_Bow (Kreatur mit Bogen / Fernkampf)
 * @see Creature
 */

public class Creature_Water extends Creature{
	
	/**
	 * Der Konstruktor fuer die Creature_water. 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, mX, mY, h, angr und vert uebergeben.
	 * Des Weiteren wird an dieser Stelle der State fuer die Creature_water gesetzt und das Bild geladen, die Geschwindigkeit initialisiert.
	 * @param spawnX  Die x-Koordinate, an der die Creature_Water gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der die Creature_Water gezeichnet wird.
	 * @param mX  Die Variable fuer MoveAreaX, also die maximale Bewegungsreichweite auf der X-Achse.
	 * @param mY  Die Variable fuer MoveAreaY, also die maximale Bewegungsreichweite auf der Y-Achse.
	 * @param h  Der HP-Wert, mit dem die Creature_Water generiert wird.
	 * @param angr  Der Angriffswert, mit dem die Creature_Water generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem die Creature_Water generiert wird.
	 */
	
	public Creature_Water(int spawnX, int spawnY, int mX, int mY, int a, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// Bewegungslänge und Schussachse setzen
		moveAreaX = mX;
		moveAreaY = mY;
		state[1].changeTexture(Data_Textures.creature_water);	// Img setzen
		speed = 4;
		element=3;								// Eiskreatur
		resistances[1][0]=3;					// Eisresistenz
	}
}