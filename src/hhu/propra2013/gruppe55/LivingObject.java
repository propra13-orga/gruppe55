package hhu.propra2013.gruppe55;

public abstract class LivingObject extends DungeonObject {

	// hitpoint management
	protected int hp, hpMax;	// actual and maximal hit points of this object
	// movement
	protected int dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	
	
	
// constructor
		// x,y: coordinates to spawn
	public LivingObject(int x, int y){
		super(x, y);
		
		// modify state-array
		state	=	new State[3];
		// dead state
		state[0] = new State(imgPath, false, true, false);
		// alive
		state[1] = new State(imgPath, true, false, true);
		// game won
		state[2] = new State(imgPath, false, true, false);
		
		// start as living thing
		currState	=	1;
		
		// set hp
		hpMax	=	1;
		hp		=	hpMax;		
	}
	
	// getting a hit
	public void getHit(){
		if(hp>0)
			hp-=1;
		if(hp<=0)
			switchState(0); // dead
	}
	
	// apply movement, actually pretty simple
	public void move(){
		// test if still alive
		if(state[currState].moveable == false)
			return;
		// run movement method
		
		x+=dx;
		y+=dy;
	}
	
	// walls do not like you, they push you back a few pixels
	public void setBack(){
		x-=dx;
		y-=dy;
	}
	
	// read hit points externally
	public int getHP(){return hp;}
}
