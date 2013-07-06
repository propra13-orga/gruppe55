package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Shopkeeper.
 * Diese Klasse erbt von der Klasse Npc und spezifiert diese weiter als Shopkeeper.
 * @see Npc
 */

public class Shopkeeper extends Npc {

	/**
	 * Der Konstruktor fuer den Shopkeeper.
	 * Beim Aufruf werden dem Shopkeeper die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird hier der State gesetzt, das Bild geladen und die Hitbox geaendert, sodass man mit dem Shopkeeper interagieren, aber nicht durch diesen hindurchlaufen kann.
	 * @param spawnX  Die x-Koordinate, an der der Shopkeeper gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Shopkeeper gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Shopkeeper generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Shopkeeper generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Shopkeeper generiert wird.
	 */
	
	// Konstruktor fuer den Shopkeeper
    public Shopkeeper(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].defineOffset(0, 0, 1, 5, 0);
		state[1].changeTexture(Data_Textures.shopkeeper); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
