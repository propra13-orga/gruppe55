package hhu.propra2013.gruppe55_opengl;

public class Lavahat extends DungeonObject {
	
	public Lavahat(double x, double y) {
		super(x,y);
		
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.lavahat,false, false, true);			// der Hut
		state[0].defineOffset(0,0,6,12,0);
		state[1]	=	new State(Data_Textures.lavahat, false, false, false);			// der aufgesammelte Hut
	}
	
	public void draw(int x, int y){
		//x, y Werte setzen
		this.x	=	(x-2);
		this.y	=	(y-23);
		super.draw();
	}
	
	public void onCollision(DungeonObject d){	 // Aufnahme des Hutes bei Collision
    	if(d instanceof	Player){
			((Player)d).collecthat();
			// Wechsel des Status auf "verschwundene" Potion
			switchState(1);
    		}
} 
}
