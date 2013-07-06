package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse Grass.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Bodentextur "Grass".
 * @see DungeonObject
 */

public class Grass extends DungeonObject {
	
	/**
	 * Der Konstruktor fuer das Grass.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren wird hier der State fuer das Grass gesetzt und das Bild geladen.
	 * @param x  Die x-Koordinate an der das Grass gezeichnet wird
	 * @param y  Die y-Koordinate an der das Grass gezeichnet wird
	 */

	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public Grass(double x, double y) {
		super(x, y);

		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.grass, false, false, true);
		// pointer setzen
		switchState(0);
	}

}
