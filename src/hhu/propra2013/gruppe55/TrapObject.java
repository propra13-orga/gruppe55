package hhu.propra2013.gruppe55;

import java.awt.Rectangle;



public class TrapObject extends DungeonObject {

	 private String imgPath	=	"img/trap.png";  			// trap-image
	 private String actPath	=	"img/trapActivated.png";	// trap if activated
	
	public TrapObject(int x, int y) {
		super(x,y);
		// set state
		state	=	new State[2];
		state[0]	=	new State(imgPath,false, false, true);	// trap itself
		state[0].defineOffset(7,0,0,0);							// Hitbox der eingefahrenen Falle
		state[1]	=	new State(actPath, false, false, true);	// the activated trap
		
	}
	
	public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// hurt the player, yay!
	    	if(d instanceof	Player)
				((Player)d).getHit();
	    	// set to activated
	    	switchState(1);
	} 
	
	/* public Rectangle getBorder(){
		// modified to match image
		return new Rectangle(x, y+7, state[currState].img.getWidth(null), state[currState].img.getHeight(null)-7);
	}*/
	  
}
