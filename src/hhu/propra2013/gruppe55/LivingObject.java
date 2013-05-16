package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public abstract class LivingObject extends DungeonObject {

	// hitpoint management
    protected int hp, hpMax,  verteidigung, angriff, mana, manaMax, ausdauer, ausdauerMax;	// Statuswerte
	// movement
	protected int dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	
	
	
// constructor
		// x,y: coordinates to spawn
	public LivingObject(int x, int y, int h, int angr, int vert, int ausd, int man){
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
		
		// set stats
		hp = hpMax = h;
		verteidigung = vert;
		angriff = angr;
		mana = manaMax = man;
		ausdauer = ausdauerMax = ausd;
	}
	
	// getting a hit
	public void getHit(){
		if(hp>0)
			hp-=1;
		if(hp<=0)
			switchState(0); // dead
	}
	
	public void getHealed(){
		hp+=25;
		if(hp>hpMax)
			hp = hpMax;
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
	
	public void draw(Graphics2D g2d, int x, int y){}
	
	// Get Stats
    public int getHP(){return(hp);}
    public int getHPMax(){return(hpMax);}
    public int getAngr(){return(angriff);}
    public int getVert(){return(verteidigung);}
    public int getMana(){return(mana);}
    public int getManaMax(){return(manaMax);}
    public int getAusd(){return(ausdauer);}
    public int getAusdMax(){return(ausdauerMax);}
}
