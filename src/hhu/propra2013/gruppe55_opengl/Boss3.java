package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Boss3 extends Creature {
	// Attribute
	private Projectile alternativeProjectile	=	new Fireball(0,0,0,0);
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
 
    public Boss3(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss3); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 3;								// Startgeschwindigkeit
		detectionRange = 300;					// Groessere Schussreichweite
		
		// Resetrelevante Werte
		resetValues	=	new int[7];	// atk kommt dazu, sonst wird sie nach dem Enrage nicht zurueck gesetzt
    }

    public void move(){    	
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
    
    public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schie�en
			if(hp>10){
				shoot((int)angle%360);
			}
			// Auf Feuerbaelle umsteigen - so als Warnung
			else if(hp>5){
				shoot((int)angle%360, alternativeProjectile);
			}
			// Am Ende soll es schwieriger sein, uebertreiben wir also!
			else{
				shoot((int)(angle+10)%360, alternativeProjectile);
				shoot((int)(angle+20)%360, alternativeProjectile);
				shoot((int)(angle+30)%360, alternativeProjectile);
				shoot((int)(angle+40)%360, alternativeProjectile);
				shoot((int)(angle-40)%360, alternativeProjectile);
				shoot((int)(angle-30)%360, alternativeProjectile);
				shoot((int)(angle-20)%360, alternativeProjectile);
				shoot((int)(angle-10)%360, alternativeProjectile);
			}
		}
	}

    // Hier wird auch nur ein kleines Detail ergaenzt (genau wie bei getHit)
	public void setBack(){
		bounce = true;		// bounce wird auf true gesetzt damit das Monster weiss, dass es die Richtung wechseln soll
		super.setBack();	// Aufrufen der Muttermethode
	}
      
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);		
    	
    	// bewegungsgeschwindigkeit berechnen
    	// Wenn der Boss weniger als 5 HP hat
    	if(hp<=5){
    		speed = 7.0;
    		// ENRAGE WUAAAAARGH
    		atk	=	(int)((double)atk*1.5);	// Staerker werden
    	}
    	// Wenn der Boss weniger als 10 HP hat
    	else if(hp<=10){
    		speed = 4.5;
    	}
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);		// Der Boss droppt an der Stelle seines Todes das Zielobjekt (bis wir nahtlosen �bergang zwischen den Leveln haben)
    		}
    	}
    }
    
    // Resetmethoden bearbeiten
    public void setResetValues(){
    	super.setResetValues();
    	resetValues[6]	=	atk;
    }
    public void reset(){
    	super.reset();
    	atk	=	resetValues[6];
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