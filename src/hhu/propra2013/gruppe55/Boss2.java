package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Boss2 extends Creature {
	
	protected int sx, sy;					// Erscheinungskoordinaten
	
    public Boss2(int spawnX, int spawnY, int mX, int mY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeImg(Data_Img.boss2); 	// Bild der Lebendigen Kreatur laden
		moveAreaX = mX;							// Maximale Bewegung auf der X-Achse
		moveAreaY = mY;							// Maximale Bewegung auf der Y-Achse
		detectionRange = 360;					// Reichwite ab wann der Boss angreift
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
    
    @Override
    public void draw(Graphics2D g2d){
    	// Nichts zeichnen, wenn Kreatur unsichtbar
    	if(!state[currState].visible)
    		return;
    	// Zeichnen der HP-Leiste ueber den Koepfen der Kreaturen
    	g2d.setColor(Color.WHITE);
    	g2d.fillRect((int)x, (int)y-8, 58, 5);
    	g2d.setColor(Color.BLACK);
    	g2d.drawRect((int)x, (int)y-8, 58, 5);
    	g2d.setColor(Color.RED);
    	g2d.fillRect((int)x, (int)y-7, (int)(58*((double)hp/hpMax)), 4);
    	g2d.setColor(Color.BLACK);
    	// Zeichnen der Monster
    	g2d.drawImage(state[currState].getImg(), (int)x, (int)y, null);
    }

}