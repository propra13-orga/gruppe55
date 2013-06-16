package hhu.propra2013.gruppe55;

public class Shopkeeper extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Shopkeeper(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].changeImg(Data_Img.shopkeeper); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
