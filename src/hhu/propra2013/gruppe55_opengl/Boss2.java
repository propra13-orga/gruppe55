package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

public class Boss2 extends Creature {
	
	protected int sx, sy;					// Erscheinungskoordinaten
	
    public Boss2(int spawnX, int spawnY, int mX, int mY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss2);   // Bild der Lebendigen Kreatur laden
		moveAreaX = mX;              // Maximale Bewegung auf der X-Achse
		moveAreaY = mY;              // Maximale Bewegung auf der Y-Achse
		detectionRange = 360;          // Reichwite ab wann der Boss angreift
	}
	
	// Aktion (wie in der Creature_Bow)
	public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schießen
			shoot((int)angle%360);
		}
	}
	
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(482, 320); // Der Boss droppt beim Tod das Zielobjekt in der Nische auf unserer Seite der Fallen
    		}
    	}
    }
}