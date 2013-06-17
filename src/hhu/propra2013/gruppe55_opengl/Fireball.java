package hhu.propra2013.gruppe55_opengl;


public class Fireball extends Projectile {

	public Fireball(double x, double y, int angle, int dmg) {
		super(x, y, angle, dmg);
		
		// Grafiken Laden
		state[0].changeTexture(Data_Textures.fireball);
		state[1] = new State(Data_Textures.fireball,true,false,true);
		//adjustToCenter();
	}
	// Abschuss (wenn nur als Typenreferenz genutzt
	public Fireball launch(double x, double y, int angle, int dmg){
		return new Fireball(x,y,angle,dmg);
	}

}
