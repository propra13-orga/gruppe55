package hhu.propra2013.gruppe55_opengl;

public class WallSecret extends WallObject {

	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public WallSecret(double x, double y, String[] triggerKeys) {
		super(x, y);
		
		// Status-Array deklarieren
		state	=	new State[2];
		// Status definieren
		state[0]	=	new State(Data_Textures.door, false, true, true);
		state[1]	=	new State(Data_Textures.door, false, true, false);
		
		for(String key:triggerKeys)
			triggerNames.add(key);
	}
	
	public void triggerAction(String key){
		if(allTriggersTrue())
			switchState(1);
		else
			switchState(0);
	}
}
