package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Lavahat.
 * Diese Klasse erbt von der Klasse Hat und spezifiziert diese als Lavahat.
 * @see Hat
 */

public class Lavahat extends Hat {
	
	/**
	 * Der Konstruktor fuer den Lavahat.
	 * Beim Aufrufen werden dem Konstrukor die x und y Koordinaten uebergeben.
	 * Des Weiteren wird hier auch der Wert fuer die Resistenzen, die der Lavahat besitzt uberschrieben.
	 * @param x  Die x-Koordinate an der der Lavahat gezeichnet wird
	 * @param y  Die y-Koordinate an der der Lavahat gezeichnet wird
	 */
	
	public Lavahat(double x, double y) {
		super(x,y);
		
		// Macht den Spieler gegen Feuerschaden immun
		resistances[0][1]=1;
	}
}
