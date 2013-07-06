package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Torch.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Torch, die fuer die Fackelraetsel gebraucht werden.
 * @see DungeonObject
*/

public class Torch extends DungeonObject {
	
	/**
	 * Der Konstruktor fuer die Torch.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer die Torch gesetzt, der Trigger zur Triggerliste hinzugefuegt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der die Torch gezeichnet wird.
	 * @param y  Die y-Koordinate an der die Torch gezeichnet wird.
	 */

	public Torch(double x, double y, String[] triggerKeys) {
		super(x, y);
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.torch, false, true, true);
		state[1]	=	new State(Data_Textures.torch_lit, false, true, true);
		element=1;
		
		for(String key:triggerKeys)
			triggerNames.add(key);
	}
	
	/**
	 * Die Methode triggerAction.
	 * Diese Methode implementiert, dass sich der Status der Fackel aendert, wenn der entsprechende Trigger gefeuert wurde.
	 * @param key  Die Methode erwartet die Uebergabe eines Strings key
	*/
	
	// Die Fackel soll sich magischerweise Entzuenden. Dadadam!
	public void triggerAction(String key){
		// der Entsprechende Trigger wurde geaendert, also muessen wir reagieren
		int newState	=	(isTriggerSet(key)) ? 1 : 0;	// Wenn Trigger auf true steht Fackel an, sonst Fackel aus
		// Status wechseln nicht vergessen
		switchState(newState);
	}
}