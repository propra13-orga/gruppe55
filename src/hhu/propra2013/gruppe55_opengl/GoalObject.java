package hhu.propra2013.gruppe55_opengl;

public class GoalObject extends DungeonObject {
		
	public GoalObject(int x, int y) {
		super(x,y);
		state[0].changeTexture(Data_Textures.goal);			  // Aussehen des Ziels
		state[0].massive=false;				  // Ziel wird begehbar
	}
	
	public void onCollision(DungeonObject d){	 // Spieler trifft auf das Ziel
		super.onCollision(d);
		// Event feuern - fuer den Spieler
		if(d instanceof Player){
			for(GameEventListener gel : evtList){
				gel.levelCleared();
			}
		}
	}
}
