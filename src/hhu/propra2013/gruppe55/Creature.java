package hhu.propra2013.gruppe55;

public class Creature extends LivingObject {
// attributes
	// images
    private String imgPath	=	"img/creature.png";  // player-image
    private String ripPath	=	"img/dead.png";	// rip-image
    // coordinates
    private int sx, sy;			// Spawn coordinates
    private int moveAreaX	=	180;		// maximal vertical movement to the right
    private int moveAreaY	=	120;		// ... and upwards
	
// constructor
    public Creature(int spawnX, int spawnY) {
		super(spawnX, spawnY);
		// set states
		state[0].visible	=	false;
		state[1].changeImg(imgPath);
		sx	=	spawnX;
		sy	=	spawnY;
		// set hit points
		hpMax	=	1;
		hp		=	hpMax;
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
    
    public void onCollision(DungeonObject d){
		// hurt the player, yay!
    	if(d instanceof	Player)
			((Player)d).getHit();
    	
    	// massive check in super.onCollision
		super.onCollision(d);
    }
}