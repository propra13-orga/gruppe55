package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse MovingObject.
 * Diese Klasse dient als Mutterklasse fuer alle sich bewegenden Objekte und erbt von der Klasse DungeonObject.
 * @see DungeonObject
 */

public abstract class MovingObject extends DungeonObject {
// Attribute
	// Bewegung
	protected double dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	protected double speed=3;		// speed of object (2 normal speed -> 2px/actionPerfordmed)
	
	/**
	 * Der Konstruktor fuer ein MovingObject.
	 * Beim Aufruf werden die x und y Werte an den Konstruktor uebergeben.
	 * @param x  die x Koordinate fuer das Movingobject
	 * @param y  die y Koordinate fuer das Movingobject
	 */
	
// Konstruktor
	public MovingObject(double x, double y) {
		super(x, y);
	}

	/**
	 * Die Methode move.
	 * Diese Methode benutzen alle beweglichen Objekte um sich auf dem Spielfeld bewegen zu koennen. Die Bewegung wird dabei durch Beschleunigung in eine Richtung (dx / dy) realisiert.
	 */
	
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
	
	/**
	 * Die Methode checkDirection.
	 * Diese Methode benutzen bewegliche Objekte um den Laufweg zu errechnen. 
	 */
	
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
	
	/**
	 * Die Methode setDirectionByAngle.
	 * Diese Methode errechnet die Richtung auf Basis eines uebergebenen Winkels und aendert diese dann entsprechend.
	 * @param int angle - Die Methode erwartet die Uebergabe eines int Werts angle
	 */
	
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

	/**
	 * Die Methode setBack.
	 * Diese Methode setzt ein Objekt um einige Pixel nach zurueck (z.B. wenn der Spieler in eine Wand laeuft, wird er zurueck gesetzt).
	 */
	
	// Waende haben Pushback (man wird einige Pixel zurueckgesetz)
	public void setBack(){
		x-=dx;
		y-=dy;
	}

}
