package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse WallSecret.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert eine Wand, die ueber Schalter geoeffnet werden kann.
 * @see DungeonObject
 */

public class WallSecret extends DungeonObject {

	/**
	 * Der Konstruktor fuer WallSecret.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden die States fuer WallSecret gesetzt und das Bild geladen.
	 * @param x  Die x-Koordinate an der WallSecret gezeichnet wird
	 * @param y  Die y-Koordinate an der WallSecret gezeichnet wird
	 */
	
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
	
	/**
	 * Die Methode triggerAction.
	 * Diese Methode fragt ab, ob alle Trigger auf true gesetzt wurden und switched ggfs. den State.
	 * @param key Die Methode erwartet die Uebergabe eines Strings key.
	 */
	
	public void triggerAction(String key){
		if(allTriggersTrue())
			switchState(1);
		else
			switchState(0);
	}
}
