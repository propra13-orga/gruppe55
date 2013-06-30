package hhu.propra2013.gruppe55_opengl;

public class Switch extends DungeonObject {
	protected String[] triggersToHandle;

	public Switch(double x, double y, String[] triggersToHandle) {
		super(x, y);
		
		// State neu setzen
		state	=	new State[2];
		// Bilder der Schalter
		state[0]	=	new State(Data_Textures.button_off,false,true,true);  	// Schalter steht auf "Off"
		state[1]	=	new State(Data_Textures.button_on,false,true,true);		// Schalter steht auf "On"
		
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
