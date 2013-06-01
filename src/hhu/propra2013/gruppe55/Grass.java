package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public class Grass extends DungeonObject {

	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public Grass(int x, int y) {
		super(x, y);

		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data.grass, false, false, true);
		// pointer setzen
		switchState(0);
	}

}
