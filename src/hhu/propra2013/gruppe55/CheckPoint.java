package hhu.propra2013.gruppe55;

public class CheckPoint extends DungeonObject {

	public CheckPoint(int x, int y) {
		super(x, y);
		
		state	=	new State[2];
		state[0]	=	new State(Data_Img.cp_unused,false,false,true);
		state[1]	=	new State(Data_Img.cp_used,false,false,true);
		
	}
	
	// Kollisionsabfrage
	public void onCollision(DungeonObject d){
		// Spieler beruehrt? Noch unbenutzt?
		if(d instanceof Player && currState==0){
    		// State wechseln
    		switchState(1);
			// Event feuern!
    		for(GameEventListener gel : evtList){
    			gel.checkPointReached();
    		}
		}
	}

}
