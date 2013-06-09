package hhu.propra2013.gruppe55;

public abstract class MovingObject extends DungeonObject {
// Attribute
	// Bewegung
	protected int dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	protected int speed=3;		// speed of object (2 normal speed -> 2px/actionPerfordmed)

// Konstruktor
	public MovingObject(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
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
		if(dy==1)	// nach unten
			direction	=	0;
		else if(dx==-1)		// nach links
			direction	=	1;
		else if(dx==1)		// nach rechts
			direction	=	2;
		else if(dx==0 && dy==-1)	// nach oben
			direction	=	3;
		
		// States der Richtung anpassen
		if(direction!=oldDirection)
			changeDirection(direction);
	}
	
	// Waende haben Pushback (man wird einige Pixel zurueckgesetz)
	public void setBack(){
		x-=dx;
		y-=dy;
	}

}
