package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse PotionObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert den Heiltrank, mit dem der Player seine HP wieder auffuellen kann.
 * @see DungeonObject
*/

public class PotionObject extends DungeonObject {
// Attribute
	protected int heal	=	2;	// zu heilender Wert
	
	/**
	 * Der Konstruktor fuer das PotionObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer das PotionObject gesetzt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der das PotionObject gezeichnet wird
	 * @param y  Die y-Koordinate an der das PotionObject gezeichnet wird
	 */
	
	public PotionObject(double x, double y) {
		super(x,y);
		// States der Potion definieren
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.potion,false, false, true);			// Die Potion
		state[1]	=	new State(Data_Textures.potionused, false, false, false);	// Verschwundene Potion
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Player das PotionObject einsammeln kann, dass das eingesammelte PotionObject "verschwindet" und dass sich die HP des Players auffuellt.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Player handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Heilung bei Kollision!
			// der Spieler wird geheilt
	    	if(d instanceof	Player){
				((Player)d).getHealed(heal);
				// Wechsel des Status auf "verschwundene" Potion
				switchState(1);
	    	}
	} 

}
