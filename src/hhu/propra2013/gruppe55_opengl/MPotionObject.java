package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse MPotionObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert den Manatrank, mit dem der Player seine Mana wieder auffuellen kann.
 * @see DungeonObject
*/

public class MPotionObject extends DungeonObject {
	
	/** Die Menge an wiederhergestelltem Mana. */
	
	protected int ma = 1;
	
	/**
	 * Der Konstruktor fuer das MPotionObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer das MPotionObject gesetzt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der das MPotionObject gezeichnet wird
	 * @param y  Die y-Koordinate an der das MPotionObject gezeichnet wird
	 */
	
	public MPotionObject(double x, double y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.mpotion,false, false, true);			// Die Potion
		state[1]	=	new State(Data_Textures.mpotion, false, false, false);		// Verschwundene Potion
		
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Player das MPotionObject einsammeln kann, dass das eingesammelte MPotionObject "verschwindet" und dass sich die Mana des Players auffuellt.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Player handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Aufnahme des Manatrankes bei Kollision
			// der Spieler bekommt Mana zurueck
	    	if(d instanceof	Player){
				((Player)d).fillmana(ma);
				// Wechsel des Status auf "verschwundene" Potion
				switchState(1);
	    		}
	} 

}
