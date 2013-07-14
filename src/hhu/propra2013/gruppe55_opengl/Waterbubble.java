package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Fireball.
 * Diese Klasse erbt von der Klasse Projectile und aendert das Geschoss vom Standardpfeil in einen Feuerball.
 * @see Projectile
 */

public class Waterbubble extends Projectile {

	/**
	 * Der Konstruktor fuer den Feuerball.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten, der Winkel und der Schaden uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer den Feuerball gesetzt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der der Feuerball gezeichnet wird
	 * @param y  Die y-Koordinate an der der Feuerball gezeichnet wird
	 * @param angle  Der Winkel in dem der Feuerball gefeuert wird
	 * @param dmg  Der Schadenswert fuer den Feuerball
	 */
	
	public Waterbubble(double x, double y, int angle, int dmg) {
		super(x, y, angle, dmg);
		
		// Grafiken Laden
		state[0].changeTexture(Data_Textures.bubble);
		state[1] = new State(Data_Textures.bubble,true,false,true);
		//adjustToCenter();
		element=3;
	}
	
	/**
	 * Die Methode launch.
	 * Due Methode fuer den Abschuss.
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 * @param angle  Die Methode erwartet die Uebergabe eines int Werts angle
	 * @param dmg  Die Methode erwartet die Uebergabe eines int Werts dmg
	 * @return Die Methode gibt ein Objekt vom Typ Feuerball, mit den angegebenen Parametern zurueck.
	 */
	
	// Abschuss (wenn nur als Typenreferenz genutzt
	public Waterbubble launch(double x, double y, int angle, int dmg){
		return new Waterbubble(x,y,angle,dmg);
	}

}
