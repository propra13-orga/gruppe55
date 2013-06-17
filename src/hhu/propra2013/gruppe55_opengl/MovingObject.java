package hhu.propra2013.gruppe55_opengl;

public abstract class MovingObject extends DungeonObject {
// Attribute
	// Bewegung
	protected double dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	protected double speed=3;		// speed of object (2 normal speed -> 2px/actionPerfordmed)

// Konstruktor
	public MovingObject(double x, double y) {
		super(x, y);
	}

// Methoden
	// Methode zur Bewegeung
	public void move(){
		// Test ob das Objekt lebendig ist
		if(state[currState].moveable == false)
			return;
		
		// Bewegungmethode ausfuehren
		x+=speed*dx;
		y+=speed*dy;
		
		// Richtung pruefen
		checkDirection();
	}
	
	// Methode zur Richtungskontrolle (Bewegungsabhaengig)
	protected void checkDirection(){
		// Richtung in den States wiederspiegeln
		if(dx==0 && dy==0) return;
		int oldDirection	=	direction;
		if(dy>0)	// nach unten
			direction	=	0;
		else if(dx<0)		// nach links
			direction	=	1;
		else if(dx>0)		// nach rechts
			direction	=	2;
		else if(dx==0 && dy<0)	// nach oben
			direction	=	3;
		
		// States der Richtung anpassen
		if(direction!=oldDirection)
			changeDirection(direction);
	}
	
	// Methode zur Richtungsberechnung bei gegebenem Winkel
	protected void setDirectionByAngle(int angle){
		int oldDirection = direction;

		// Winkel anpassen
		angle	%=	360;
		angle	=	(angle<0)?360-angle:angle;

		// Richtung berechnen
		if(angle >225 && angle < 315){	// nach oben
			direction = 3;
		}
		else if(angle >135 && angle <=225){	// nach links
			direction = 1;
		}
		else if(angle >=315 || angle <45){	// nach rechts
			direction = 2;
		}
		else{ 								// nach unten
			direction = 0;
		}
		// Richtung anpassen (grafisch)
		if(direction!=oldDirection){
			changeDirection(direction);
		}
	}

	
	// Waende haben Pushback (man wird einige Pixel zurueckgesetz)
	public void setBack(){
		x-=dx;
		y-=dy;
	}

}
