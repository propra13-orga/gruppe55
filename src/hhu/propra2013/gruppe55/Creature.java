package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Creature extends LivingObject {
// attributes
    // coordinates
    private int sx, sy;			// Spawn coordinates
    private int moveAreaX	=	180;		// maximal vertical movement to the right
    private int moveAreaY	=	120;		// ... and upwards
    
	
// constructor
    public Creature(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		
		// set states
		state[0].visible	=	false;
		state[1].changeImg("creature");
		sx	=	spawnX;
		sy	=	spawnY;
		
		// initiate movement
		dx=1;
	}
    
    // TODO: Methode f�r Bewegung �berdenken
    public void move(){
		// bewegung errechnen
		if(dx==1 && x>=sx+moveAreaX) // zu weit rechts
			if(y>sy-moveAreaY){ // nach oben, wenn gew�nscht
				dx=0;
				dy=-1;
			}
			else // wieder nach links sonst
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
    	
		// bewegung ausf�hren
		super.move();
    }
    
    //Return stats
    
    @Override
    public void draw(Graphics2D g2d, int offsetX, int offsetY){
    	//HP-Leiste
    	g2d.setColor(Color.WHITE);
    	g2d.fillRect(x-offsetX, y-offsetY-8, 24, 5);
    	g2d.setColor(Color.BLACK);
    	g2d.drawRect(x-offsetX, y-offsetY-8, 24, 5);
    	g2d.setColor(Color.RED);
    	g2d.fillRect(x+1-offsetX, y-offsetY-7, (int)(24*((double)hp/hpMax)), 4);
    	g2d.setColor(Color.BLACK);
    	// draw the creature
    	super.draw(g2d, offsetX, offsetY);
    }
    
    public void onCollision(DungeonObject d){
		// hurt the player, yay!
    	if(d instanceof	Player)
			((Player)d).getHit();
    	
    	// massive check in super.onCollision
		super.onCollision(d);
    }
}