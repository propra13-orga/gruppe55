package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public class PotionObject extends DungeonObject {
	
	public PotionObject(int x, int y) {
		super(x,y);
		// set state
		state	=	new State[2];
		state[0]	=	new State(Ressources.potion,false, false, true);	// Die Potion
		state[1]	=	new State(Ressources.potionused, false, false, true);	// Verschwundene Potion
		state[1].defineOffset(0,0,32,32);
		
	}
	
	public void onCollision(DungeonObject d){	 // Heilung bei Kollision!
			// heal the player, yay!
	    	if(d instanceof	Player)
				((Player)d).getHealed();
	    	// set to used
	    	switchState(1);
	} 
	
	@Override
	public void draw(Graphics2D g2d, int x, int y){
		g2d.drawImage(this.getImg(), x, y, null);
	}

}
