package hhu.propra2013.gruppe55;

public class Storyteller extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Storyteller(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].changeImg(Data_Img.storyteller); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
