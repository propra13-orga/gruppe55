package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse SimpleStaticObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert alle simplen, statischen Objekte.
 * @see DungeonObject
 */

public class SimpleStaticObject extends DungeonObject {

	/**
	 * Der Konstruktor fuer das WallObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren wird der State fuer das WallObject gesetzt und das Bild geladen.
	 * @param x  Die x-Koordinate an der das WallObject gezeichnet wird
	 * @param y  Die y-Koordinate an der das WallObject gezeichnet wird
	 */
	
	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public SimpleStaticObject(double x, double y, boolean m, int texture) {
		super(x, y);

		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.getEnvTexture(texture), false, m, true);
		// pointer setzen
		switchState(0);
	}
}
