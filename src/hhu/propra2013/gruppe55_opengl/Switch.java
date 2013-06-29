package hhu.propra2013.gruppe55_opengl;

public class Switch extends DungeonObject {
	protected String[] triggersToHandle;

	public Switch(double x, double y, String[] triggersToHandle) {
		super(x, y);
		
		// State neu setzen
		state	=	new State[2];
		// Testbilder
		state[0]	=	new State(Data_Textures.potion,false,true,true);
		state[1]	=	new State(Data_Textures.mpotion,false,true,true);
		
		// Namen zum abhoeren
		this.triggersToHandle	=	triggersToHandle;
		
	}
	
	// Aktuell noch keine Interaktionsmechanik -> Kollision!
	public void onCollision(DungeonObject d){
		// nur der Spieler hat die Macht!
		if(d instanceof Player){
			for(String key:triggersToHandle){
				fireTrigger(key);
				// Status wechseln
			}
			switchState(1-currState);
		}
		// Zurueckschieben
		super.onCollision(d);
	}

}
