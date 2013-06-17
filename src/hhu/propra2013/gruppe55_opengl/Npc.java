package hhu.propra2013.gruppe55_opengl;

import java.awt.Rectangle;

public class Npc extends LivingObject {	

	// Interaktions-Hitbox-Offsets fuer Npc's 
	protected int offset2[] = {-15,-15, -15, -15};
	// Kontruktor fuer den Npc
	public Npc(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		state[1].massive = true;
    }
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
	
	protected void onCollision(DungeonObject d){
		// standart fuer ungefaehrliche Objekte
			while(super.getBorder().intersects(d.getBorder())){
				// solange Kollision wird das Objekt zurückgeschoben
				((LivingObject)d).setBack();
			}
//			System.out.println("blobb"); // Anzeige zum Pruefen der Hitbox - kannst du spaeter loeschen
	}

}
