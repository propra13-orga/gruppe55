package hhu.propra2013.gruppe55;

public class Creature_Bow extends Creature{

	int schussAchse;	// Achse, an der Schussrichtung zum Spieler geprüft werden soll 0-vertikal 1-horizontal
	
	public Creature_Bow(int spawnX, int spawnY, int mX, int mY, int a, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// Bewegungslänge und Schussachse setzen
		moveAreaX = mX;
		moveAreaY = mY;
		schussAchse = a;
		state[1].changeImg(Data_Img.creature_bow);	// Img setzen
	}
	// Aktion
	public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schießen
			shoot((int)angle);
		}
	}
	
    // Methode zum Pfeile Schießen
    public void shoot(int pX, int pY){
    	// Schussrichtung definieren
    	int angle = calcPlayerDirection(pX, pY);
    	
    	// Offsets fuer Projektil setzen
    	int x = (int)this.x;
    	int y = (int)this.y;
    	// je nach Schussrichtung
    	switch(angle){
	    	case	0:	// nach rechts
	    		x+=state[currState].getImg().getWidth(null)+1;
	    		y+=state[currState].getImg().getHeight(null)/2;
	    		break;
	    	case	90:	// nach unten
	    		x+=state[currState].getImg().getWidth(null)/2;
	    		y+=state[currState].getImg().getHeight(null)+1;
	    		break;
	    	case	180:	// nach links
	    		x-=17;
	    		y+=state[currState].getImg().getHeight(null)/2;
	    		break;
	    	case	270:	// nach oben
	    		x+=state[currState].getImg().getWidth(null)/2;
	    		y-=17;
	    		break;
	    	default:
	    		break;
    	}
    	
    	// Event (und damit den Pfeil) feuern!
    	for(GameEventListener gel : evtList){    		
			gel.shootProjectile(new Projectile(x, y, angle, atk+1));
		}
    }
    
    // Schussrichtung abhängig von Spielerposition und Schussachse setzen
    public int calcPlayerDirection(int pX, int pY){
    	int angle = 0;
    	
    	//angle = (int)(360*Math.asin(Math.abs((y-pY))/(Math.sqrt(Math.pow(Math.abs(x-pX), 2)+Math.pow(Math.abs(y-pY), 2)))));
    	if(schussAchse == 0){
    		if(y > pY){
    			angle = 270;
    		}
    		else{
    			angle = 90;
    		}
    	}
    	else{
    		if(x < pX){
    			angle = 0;
    		}
    		else{
    			angle = 180;
    		}
    	}
    	
    	return angle;
    }
}