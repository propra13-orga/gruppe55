package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Wizard.
 * Diese Klasse erbt von der Klasse Npc und spezifiert diese weiter als Wizard.
 * @see Npc
 */

public class Wizard extends Npc {

	/**
	 * Der Konstruktor fuer den Wizard.
	 * Beim Aufruf werden dem Wizard die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird hier der State gesetzt, das Bild geladen und die Hitbox geaendert, sodass man mit dem Wizard interagieren, aber nicht durch diesen hindurchlaufen kann.
	 * @param spawnX  Die x-Koordinate, an der der Wizard gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Wizard gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Wizard generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Wizard generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Wizard generiert wird.
	 */
	
	// Konstruktor fuer den Wizard
    public Wizard(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].defineOffset(0, 0, 1, 5, 0);
		state[1].changeTexture(Data_Textures.wizard); 	// Platzhalterbild fuer den Wizard
		storyToTell	=	Data_String.wizard;
		super.getBorder();
		
    }
    
	/**
	 * Die interaction-Methode von NPC-Objekten kontrolliert die Ausgabe eines Dialoges bzw das Abrufen des Shops.
	 * In diesem Fall wird noch das Event gefeuert, dass der Spieler das Schwert erhält.
	 * @param px X-Koordinate des Spielers
	 * @param py Y-Koordinate des Spielers
	 * @see LivingObject
	 */
    
    public void interaction(int px, int py){
    	super.interaction(px, py);
    	if(distanceBetween(px, py) <= interactionRange){
    		for(GameEventListener gel : evtList){
				gel.giveWeaponToPlayer();
			}
    	}
    }
    
}