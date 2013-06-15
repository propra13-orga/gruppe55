package hhu.propra2013.gruppe55;

public class Creature_Bow extends Creature{

	public Creature_Bow(int spawnX, int spawnY, int mX, int mY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		moveAreaX = mX;
		moveAreaY = mY;
	}

}