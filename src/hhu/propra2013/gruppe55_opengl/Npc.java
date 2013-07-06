package hhu.propra2013.gruppe55_opengl;

import java.awt.Rectangle;

/**
 * Die Klasse Npc.
 * Diese Klasse erbt von der Klasse LivingObject und implementiert die Grundfunktionen eines Npc.
 * @see LivingObject
 */

public class Npc extends LivingObject {	

	// Interaktions-Hitbox-Offsets fuer Npc's 
	protected int offset2[] = {-15,-15, -15, -15};
	
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
    }
	
	/**
	 * Die Methode getBorder.
	 * Diese Methode aendert die Hitbox des Objektes.
	 * @return Die neue Hitbox als Rectangle(int, int, int, int).
	 */
	
	// Neue Hitbox fuer den Npc (Bereich um ihn herum zum Interagieren)
	public Rectangle getBorder(){
		// Wenn der Npc da is...
		if(state[currState].visible){
			return new Rectangle((int)x + (int)offset2[1], (int)y + (int)offset2[0], state[currState].getTexture().getTextureWidth() -offset2[1]-offset2[3], state[currState].getTexture().getTextureHeight()-offset2[0]-offset2[2]);
		}		
		   else
		// ... und wenn er nicht da ist
		return new Rectangle(0,0,0,0);
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

}
