package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Healthcontainer.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert den Healthcontainer, den der Player einsammeln kann, um seine MaxHP zu erhoehen.
 * @see DungeonObject
 */

public class Healthcontainer extends DungeonObject {
// Attribute
	
	/**
	 * Der Konstruktor fuer den Lavahat.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer den Healthcontainer gesetzt und die Bilder geladen.
	 * @param x  Die x-Koordinate an der der Healthcontainer gezeichnet wird
	 * @param y  Die y-Koordinate an der der Healthcontainer gezeichnet wird
	 */
	
	public Healthcontainer(double x, double y) {
		super(x,y);
		// Bild des Containers laden
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.healthcontainer,false, false, true);	
		state[1]	=	new State(Data_Textures.healthcontainer, false, false, false);	// der Eingesammelte Container

	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass der Spieler den Healthcontainer einsammeln kann, dass der eingesammelte Healthcontainer "verschwindet" und dass sich seine MaxHP erhoeht.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Spieler handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Mehr MaxHp bei Kollision!
			// der Spieler erhaelt mehr MaxHp
	    	if(d instanceof	Player){
				((Player)d).raisehp();
				// Wechsel des Status auf "verschwundener" container
				switchState(1);
	    	}
	} 

}