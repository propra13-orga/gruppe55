package hhu.propra2013.gruppe55_opengl;

public class FloorPlate extends DungeonObject {
// Attrbute
	/** Der Schaden, den die aktive Platte ausloest */
	private int plateDmg=18;	// Es sollte toedlich sein koennen
	
	/**
	 * Der Konstruktor fuer die FloorPlate.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer die Torch gesetzt, der Trigger zur Triggerliste hinzugefuegt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der die Torch gezeichnet wird.
	 * @param y  Die y-Koordinate an der die Torch gezeichnet wird.
	 * @param invert Gibt an, ob die Aktivitaet invertiert werden soll (modulo 2)
	 */
	public FloorPlate(double x, double y, String[] triggerKeys, int invert) {
		super(x, y);
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.floorplate_active, false, true, true);
		state[1]	=	new State(Data_Textures.floorplate_inactive, false, true, true);
		
		switchState(invert%2);
		
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
		switchState( (currState+newState)%2 );
	}
	
	/**
	 * Die Methode onCollision.
	 * Diese Methode ruft die Schadensberechnung auf und uebergibt das Element der Falle dazu.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Spieler handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Tod bei Kollision
			// Dem Spieler oder Monstern Schaden zufuegen
	    	if(d instanceof	LivingObject && currState==0)
				((LivingObject)d).getHit(plateDmg,element);
	} 

}
