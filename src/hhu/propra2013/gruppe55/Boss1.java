package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Boss1 extends Creature {
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
 
    public Boss1(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeImg(Data_Img.boss1); 	// Bild der Lebendigen Kreatur laden
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
    
public void onCollision(DungeonObject d){
	// Dem Spieler Schaden zufuegen
	if(d instanceof	Player){
		dealDamage((Player)d);
		}
	
	// Test auf Massive-Attribut in super.onCollision
	super.onCollision(d);
	}
}