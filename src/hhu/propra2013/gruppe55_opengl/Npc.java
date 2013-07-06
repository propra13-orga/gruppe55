package hhu.propra2013.gruppe55_opengl;

import java.awt.Rectangle;

/**
 * Die Klasse Npc.
 * Diese Klasse erbt von der Klasse LivingObject und implementiert die Grundfunktionen eines Npc.
 * @see LivingObject
 */

public class Npc extends LivingObject {	
	protected String[] storyToTell;		// Enthaelt den Dialog, den der NPC zu erzaehlen gewillt ist
	protected int interactionResponse;	// 1=Dialog, 2=Shop, sonst nichts
	
	/**
	 * Der Konstruktor fuer den Npc.
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird hier der State fuer den Npc auf massiv geaendert.
	 * @param spawnX  Die x-Koordinate, an der der Npc gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Npc gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Npc generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Npc generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Npc generiert wird.
	 */
	
	// Kontruktor fuer den Npc
	public Npc(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		state[1].massive = true;
		storyToTell	=	Data_String.story1;
		detectionRange	=	45;
    }
	
	/**
	 * Die Methode onCollision.
	 * Diese Methode sorgt dafuer, dass der Player nicht durch den Npc hindurchlaufen kann. 
	 * Die Methode onCollision ueberschreibt, die aus der Mutterklasse MovingObject stammdene, Methode onCollision. 
	 */
	
	protected void onCollision(DungeonObject d){
		// standart fuer ungefaehrliche Objekte
			while(super.getBorder().intersects(d.getBorder())){
				// solange Kollision wird das Objekt zurückgeschoben
				((LivingObject)d).setBack();
			}
	}
	
	/**
	 * Die interaction-Methode von NPC-Objekten kontrolliert die Ausgabe eines Dialoges bzw das Abrufen des Shops
	 * @param px X-Koordinate des Spielers
	 * @param py Y-Koordinate des Spielers
	 * @see LivingObject
	 */
	public void interaction(int px, int py){
		// Abfrage ob der Spieler in Interagtionsreichweite intergieren will! 
		if(distanceBetween(px, py)<=interactionRange){
			if(interactionResponse==1){
				for(GameEventListener gel : evtList){
					gel.showDialog(storyToTell);
				}
			}
			else if(interactionResponse==2){
				for(GameEventListener gel : evtList){
					gel.openShop();
				}
			}
		}
	}

}
