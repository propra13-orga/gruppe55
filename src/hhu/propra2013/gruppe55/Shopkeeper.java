package hhu.propra2013.gruppe55;

public class Shopkeeper extends Npc {

	// Konstruktor fuer den Shopkeeper
    public Shopkeeper(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		// States setzen
		state[1].changeImg(Data_Img.shopkeeper); 	// Platzhalterbild fuer den Shopkeeper
		super.getBorder();
    }
}
