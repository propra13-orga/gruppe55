package hhu.propra2013.gruppe55;

import java.awt.Rectangle;

public class Npc extends LivingObject {	

	// Interaktions-Hitbox-Offsets fuer Npc's 
	protected int offset2[] = {-15,-15, -15, -15};
	// Kontruktor fuer den Npc
	public Npc(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		state[1].massive = true;
    }
	// Neue Hitbox fuer den Npc (Bereich um ihn herum zum Interagieren)
	public Rectangle getBorder(){
		// Wenn der Npc da is...
		if(state[currState].visible){
			return new Rectangle(x + offset2[1], y + offset2[0], state[currState].getImg().getWidth(null) -offset2[1]-offset2[3], state[currState].getImg().getHeight(null)-offset2[0]-offset2[2]);
		}		
		   else
		// ... und wenn er nicht da ist
		return new Rectangle(0,0,0,0);
	}
	
	protected void onCollision(DungeonObject d){
		// standart fuer ungefaehrliche Objekte
			while(super.getBorder().intersects(d.getBorder())){
				// solange Kollision wird das Objekt zurückgeschoben
				((LivingObject)d).setBack();
			}
			System.out.println("blobb"); // Anzeige zum Pruefen der Hitbox - kannst du spaeter loeschen
	}

}
