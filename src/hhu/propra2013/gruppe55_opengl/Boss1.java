package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

public class Boss1 extends Creature {
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
 
    public Boss1(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss1); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 3;								// Startgeschwindigkeit
    }

    public void move(){    	
		// bewegungsgeschwindigkeit berechnen
    	// Wenn der Boss weniger als 5 HP hat
    	if(hp<=5){
    		speed = 6.0;
    	}
    	// Wenn der Boss weniger als 10 HP hat
    	else if(hp<=10){
    		speed = 4.5;
    	}
    	// wenn der Boss mehr als 10 HP hat
    	else 
    		speed = 3;
    	// Berechnung des Winkels beim abprallen von einer Wand
    	if(bounce==true){
    		double angle=Math.random()*360; // [0,360[
    		dx=Math.cos(Math.toDegrees(angle)); // Beschleunigung in x Richtung
    		dy=Math.sin(Math.toDegrees(angle));	// Beschleunigung in y Richtung
    		bounce=false;	// Bounce wird wieder auf false gesetzt
    	}
		// bewegung ausfuehren
		x+=speed*dx;
		y+=speed*dy;
		
    }

    // Hier wird auch nur ein kleines Detail ergaenzt (genau wie bei getHit)
	public void setBack(){
		bounce = true;		// bounce wird auf true gesetzt damit das Monster weiss, dass es die Richtung wechseln soll
		super.setBack();	// Aufrufen der Muttermethode
	}
      
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);		// Der Boss droppt an der Stelle seines Todes das Zielobjekt (bis wir nahtlosen Übergang zwischen den Leveln haben)
    		}
    	}
    }
     
    @Override
    public void draw(){
    	// Nichts zeichnen, wenn Kreatur unsichtbar
    	if(!state[currState].visible)
    		return;
    	// Zeichnen der HP-Leiste ueber den Koepfen der Kreaturen
    	glDisable(GL_TEXTURE_2D);
    	glColor3f(1f, 1f, 1f);
    	glBegin(GL_QUADS);
    		glVertex2d(x, y-8);
    		glVertex2d(x, y-3);
    		glVertex2d(x+24, y-3);
    		glVertex2d(x+24, y-8);
    	glEnd();
    	glColor3f(1f, 0f, 0f);
    	glBegin(GL_QUADS);
			glVertex2d(x+1, y-7);
			glVertex2d(x, y-4);
			glVertex2d(x+(int)(24*((double)hp/hpMax)), y-4);
			glVertex2d(x+(int)(24*((double)hp/hpMax)), y-7);
		glEnd();
    	glColor3f(1f, 1f, 1f);
    	glEnable(GL_TEXTURE_2D);
    	// Zeichnen der Monster
    	glBindTexture(GL_TEXTURE_2D, state[currState].getTexture().getTextureID());
    	super.draw();
    }
    
public void onCollision(DungeonObject d){
	// Dem Spieler Schaden zufuegen
	if(d instanceof	Player){
		dealDamage((Player)d);
		}
	
	// Test auf Massive-Attribut in super.onCollision
	super.onCollision(d);
	}
}