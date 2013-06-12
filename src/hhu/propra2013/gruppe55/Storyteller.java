package hhu.propra2013.gruppe55;

public class Storyteller extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Storyteller(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		// States setzen
		state[1].changeImg(Data_Img.storyteller); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
