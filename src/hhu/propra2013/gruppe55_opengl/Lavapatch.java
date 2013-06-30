package hhu.propra2013.gruppe55_opengl;

public class Lavapatch extends DungeonObject{
	
	public int lavaDmg = 20;		// Lavadmg - Sehr hoch weil sehr Heiﬂ!

	public Lavapatch(double x, double y){
		super(x,y);
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.lavapatch, false, false, true);  // Das Bild des Lavafeldes
	}
	
	public void onCollision(DungeonObject d){	 // Kollision mit dem Lavafeld
    	if(d instanceof	Player){
    		// Wenn der Spieler den Hut auf hat passiert nichts!
			if(((Player)d).gethat()==true){
				return;
			}
			// Ansonsten DMG!
			else{
				((LivingObject)d).getHit(lavaDmg);
				}
			}
    	}
} 


