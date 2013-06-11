package hhu.propra2013.gruppe55_opengl;

public class Shopkeeper extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Shopkeeper(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		// States setzen
		state[0].defineOffset(0, 0, 1, 5, 0);
		state[1].changeTexture(Data_Textures.shopkeeper); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
