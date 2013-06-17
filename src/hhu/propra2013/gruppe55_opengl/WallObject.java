package hhu.propra2013.gruppe55_opengl;

public class WallObject extends DungeonObject {

	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public WallObject(double x, double y) {
		super(x, y);

		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.wall, false, true, true);
		// pointer setzen
		switchState(0);
	}
}
