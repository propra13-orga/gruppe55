package hhu.propra2013.gruppe55_opengl;

public class Lavahat extends Hat {
	
	public Lavahat(double x, double y) {
		super(x,y);
		
		// Macht den Spieler gegen Feuerschaden immun
		resistances[0][1]=1;
	}
}
