package hhu.propra2013.gruppe55_opengl;

public class Lavapatch extends DungeonObject{
	
	public int lavaDmg = 30;		// Lavadmg - Sehr hoch weil sehr heiﬂ!

	public Lavapatch(double x, double y){
		super(x,y);
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.lavapatch, false, false, true);  // Das Bild des Lavafeldes
		
		element	=	1;	// Lavaobjekte sollten schon irgendwie vom Typ Feuer sein, nicht?
	}
	
	public void onCollision(DungeonObject d){	 // Kollision mit dem Lavafeld
    	if(d instanceof	LivingObject){
    		((LivingObject)d).getHit(lavaDmg,element);
    	}
	}
} 


