package hhu.propra2013.gruppe55_opengl;

public class Storyteller extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Storyteller(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].defineOffset(0, 0, 1, 5, 0);
		state[1].changeTexture(Data_Textures.storyteller); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
