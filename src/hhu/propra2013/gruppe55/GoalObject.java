package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public class GoalObject extends DungeonObject {
		
	public GoalObject(int x, int y) {
		super(x,y);
		state[0].changeImg(Ressources.goal);			  // Aussehen des Ziels
		state[0].massive=false;					  // Ziel wird begehbar
	}
	
	public void onCollision(DungeonObject d){	 // Spieler trifft auf das Ziel
    	if(d instanceof	Player)
			((Player)d).reachgoal();
	}
	
	@Override
	public void draw(Graphics2D g2d, int x, int y){
		g2d.drawImage(this.getImg(), x, y, null);
	}
}
