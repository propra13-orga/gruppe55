package hhu.propra2013.gruppe55;

public class WallObject extends DungeonObject {

	// this WallObject seems pretty empty. This is because everything is already in DungeonObject implemented
	public WallObject(int x, int y) {
		super(x, y);

		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State("wall", false, true, true);
		// pointer setzen
		switchState(0);
	}

}
