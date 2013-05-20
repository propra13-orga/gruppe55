package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Creature extends LivingObject {
// Attribute der Monster
    // Deklaration der Koordinaten 
    private int sx, sy;			// Erscheinungskoordinaten
    private int moveAreaX	=	180;		// maximale vertikale Bewegung nach rechts
    private int moveAreaY	=	120;		// ... und nach oben
    	
// Konstruktor
    public Creature(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		
		// States setzen
		state[0].visible	=	false; 		// totes Monster wird unsichtbar
		state[1].changeImg("creature"); 	// Bild der Lebendigen Kreatur laden
		sx	=	spawnX;						// Erscheinungskoordinaten
		sy	=	spawnY;
		
		// Bewegung initiieren
		dx=1;
	}
    
    // TODO: Methode fuer Bewegung �berdenken
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
    
    
    @Override
    public void draw(Graphics2D g2d, int offsetX, int offsetY){
    	// Zeichnen der HP-Leiste ueber den Koepfen der Kreaturen
    	g2d.setColor(Color.WHITE);
    	g2d.fillRect(x-offsetX, y-offsetY-8, 24, 5);
    	g2d.setColor(Color.BLACK);
    	g2d.drawRect(x-offsetX, y-offsetY-8, 24, 5);
    	g2d.setColor(Color.RED);
    	g2d.fillRect(x+1-offsetX, y-offsetY-7, (int)(24*((double)hp/hpMax)), 4);
    	g2d.setColor(Color.BLACK);
    	// Zeichnen der Monster
    	super.draw(g2d, offsetX, offsetY);
    }
    
    public void onCollision(DungeonObject d){
		// Dem Spieler Schaden zufuegen
    	if(d instanceof	Player)
			((Player)d).getHit();
    	
    	// Test auf Massive-Attribut in super.onCollision
		super.onCollision(d);
    }
}