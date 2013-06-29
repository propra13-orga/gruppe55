package hhu.propra2013.gruppe55_opengl;

public class WallSecret extends WallObject {

	// Diese Klasse scheint leer zu sein. Alle wichtigen Methoden und Attribute sind im DungeonObject implementiert
	public WallSecret(double x, double y, String[] triggerKeys) {
		super(x, y);
		
		for(String key:triggerKeys)
			triggerNames.add(key);
	}
	
	public void triggerAction(String key){
		if(allTriggersTrue())
			state[currState].visible	=	false;
		else
			state[currState].visible	=	true;
	}
}
