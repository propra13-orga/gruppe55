package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Creature extends LivingObject {
// Attribute der Monster
    // Deklaration der Koordinaten 
    protected int sx, sy;			// Erscheinungskoordinaten
    protected int moveAreaX	=	5*32;		// maximale vertikale Bewegung nach rechts
    protected int moveAreaY	=	3*32;		// ... und nach oben
    	
// Konstruktor
    public Creature(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		// States setzen
		state[0].visible	=	false; 		// totes Monster wird unsichtbar
		state[1].changeImg(Data_Img.creature); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;						// Erscheinungskoordinaten
		sy	=	(int)spawnY;
		
		// Bewegung initiieren
		dx=1;
	}
    
    public void move(){
		// bewegung errechnen
		if(dx==1 && x>=sx+moveAreaX) // zu weit rechts
			if(y>sy-moveAreaY){ // nach oben, wenn gewuennscht
				dx=0;
				dy=-1;
			}
			else // ansonsten wieder nach links
				dx=-1;
		else if(dx==-1 && sx>=x) // zu weit links
			if(y<sy){ // nach unten
				dx=0;
				dy=1;
			}
			else // wieder nach rechts
				dx=1;
		else if(dy==-1 && sy-moveAreaY>=y) // zu weit oben
			if(x>sx){ // nach links
				dx=-1;
				dy=0;
			}
			else // wieder runter
				dy=1;
		else if(dy==1 && sy<=y) // zu weit unten
			if(x<sx+moveAreaX){ // nach rechts
				dx=1;
				dy=0;
			}
			else // wieder rauf
				dy=-1;
    	
		// bewegung ausfuehren
		super.move();
    }
    
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newTreasure(x, y);
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
    	g2d.fillRect((int)x, (int)y-8, 24, 5);
    	g2d.setColor(Color.BLACK);
    	g2d.drawRect((int)x, (int)y-8, 24, 5);
    	g2d.setColor(Color.RED);
    	g2d.fillRect((int)x+1, (int)y-7, (int)(24*((double)hp/hpMax)), 4);
    	g2d.setColor(Color.BLACK);
    	// Zeichnen der Monster
    	super.draw(g2d);
    }
    
    public void onCollision(DungeonObject d){
		// Dem Spieler Schaden zufuegen
    	if(d instanceof	Player)
			dealDamage((Player)d);
    	
    	// Test auf Massive-Attribut in super.onCollision
		super.onCollision(d);
    }
}