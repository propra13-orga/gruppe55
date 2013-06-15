package hhu.propra2013.gruppe55;

public class Creature_Bow extends Creature{

	public Creature_Bow(int spawnX, int spawnY, int mX, int mY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		moveAreaX = mX;
		moveAreaY = mY;
	}
	
	public void action(int pX, int pY){
		shoot(pX, pY);
	}
	
    // Methode zum Pfeile Schieﬂen
    public void shoot(int pX, int pY){
    	
    	// Position bestimmen
    	int x	=	this.x;
    	int y	=	this.y;
    	// je nach Richtung
    	switch(direction){
	    	case	1:	// nach links
	    		y+=state[currState].getImg().getHeight(null)/2;	// vertikal zentrieren
	    		x-=state[currState].getImg().getWidth(null);
	    		break;
	    	case	2:	// nach rechts
	    		x+=state[currState].getImg().getWidth(null);	// auch wirklich rechts vom spieler
	    		y+=state[currState].getImg().getHeight(null)/2;	// vertikal zentrieren
	    		break;
	    	case	3:	// nach oben
	    		x+=state[currState].getImg().getWidth(null)/2-20;	// horizontal zentrieren
	    		break;
	    	case	0:	// nach unten
	    	default:
	    		x+=state[currState].getImg().getWidth(null)/2-20;	// vertikal zentrieren
	    		y+=state[currState].getImg().getHeight(null);
	    		break;
    	}

    	calcPlayerDirection(pX, pY);
    	
    	// Event (und damit den Pfeil) feuern!
    	for(GameEventListener gel : evtList){    		
			gel.shootProjectile(new Projectile(x, y, 180, atk+1));
		}
    }
    
    public int calcPlayerDirection(int pX, int pY){
    	int angle = 0;
    	
    	System.out.println((int)(360*Math.asin(Math.abs((y-pY))/(Math.sqrt(Math.pow(Math.abs(x-pX), 2)+Math.pow(Math.abs(y-pY), 2))))));
    	
    	return angle;
    }
}