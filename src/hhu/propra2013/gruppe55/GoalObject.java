package hhu.propra2013.gruppe55;

public class GoalObject extends DungeonObject {
		
	public GoalObject(int x, int y) {
		super(x,y);
		state[0].changeImg(Data.goal);			  // Aussehen des Ziels
		state[0].massive=false;				  // Ziel wird begehbar
	}
	
	public void onCollision(DungeonObject d){	 // Spieler trifft auf das Ziel
    	if(d instanceof	Player)
			((Player)d).reachGoal();			// Funktion um Goal zu setzen (= Level abschliessen)
	}
}
