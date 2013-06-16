package hhu.propra2013.gruppe55;

public class Grass extends DungeonObject {

	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public Grass(double x, double y) {
		super(x, y);

		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Img.grass, false, false, true);
		// pointer setzen
		switchState(0);
	}

}
