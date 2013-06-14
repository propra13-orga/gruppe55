package hhu.propra2013.gruppe55_opengl;

public class Storyteller extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Storyteller(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		// States setzen
		state[1].changeTexture(Data_Textures.storyteller); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
