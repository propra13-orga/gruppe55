package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Boss1 extends Creature {
	
	protected int sx, sy;					// Erscheinungskoordinaten
    protected int moveAreaX	=	3*32;		// maximale vertikale Bewegung nach rechts
    protected int moveAreaY	=	6*32;		// ... und nach oben
   

    public Boss1(int spawnX, int spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		
		state[0].visible	=	false; 		// totes Monster wird unsichtbar
		state[1].changeImg(Data_Img.boss1); 	// Bild der Lebendigen Kreatur laden
		sx	=	spawnX;						// Erscheinungskoordinaten
		sy	=	spawnY;
		dx =1;
		dy =1;
    }

    public void move(){    	
		// bewegung berechnen
    	if(dx==1 && x >=sx+moveAreaX)
    		dx = -1;
		else if(dx==-1 && y>=sy+moveAreaY)
			dy = -1;
		else if(dy==-1 && x<=sx-moveAreaX)
			dx = 1;
		else if(dy==-1 && x==sx)
			dy = 1;
		// bewegung ausfuehren
		x+=speed*dx;
		y+=speed*dy;
		
		// Hier soll der Boss spaeter eine Methode bekommen in der er den Spieler anst�rmt / sich nach mustern bewegt
    }

      
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);
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
    	g2d.fillRect(x, y-8, 58, 5);
    	g2d.setColor(Color.BLACK);
    	g2d.drawRect(x, y-8, 58, 5);
    	g2d.setColor(Color.RED);
    	g2d.fillRect(x, y-7, (int)(58*((double)hp/hpMax)), 4);
    	g2d.setColor(Color.BLACK);
    	// Zeichnen der Monster
    	g2d.drawImage(state[currState].getImg(), x, y, null);
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