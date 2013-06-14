package hhu.propra2013.gruppe55_opengl;

public class Healthcontainer extends DungeonObject {
// Attribute
	
	public Healthcontainer(int x, int y) {
		super(x,y);
		// Bild des Containers laden
		state[0].changeTexture(Data_Textures.healthcontainer);
	}
	
	public void onCollision(DungeonObject d){	 // Mehr MaxHp bei Kollision!
			// der Spieler erhaelt mehr MaxHp
	    	if(d instanceof	Player){
				((Player)d).raisehp();
				// Wechsel des Status auf "verschwundener" container
				state[0].visible = false;
	    	}
	} 

}