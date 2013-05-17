package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public abstract class LivingObject extends DungeonObject {

	Ressources res;
	// hitpoint management
    protected int hp, hpMax,  def, atk, mana, manaMax, energy, energyMax;	// Statuswerte
	// movement
	protected int dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	protected double speed=1.00;	// speed of object (1.00 normal speed -> 1px/actionPerfordmed
	
	
	
// constructor
		// x,y: coordinates to spawn
	public LivingObject(int x, int y, int h, int atk, int def, int energy, int mana){
		super(x, y);
		
		// modify state-array
		state	=	new State[3];
		// dead state
		state[0] = new State("potionused", false, true, false);
		// alive
		state[1] = new State("potionused", true, false, true);
		// game won
		state[2] = new State("potionused", false, true, false);
		
		// start as living thing
		currState	=	1;
		
		// set stats
		hp = hpMax = h;
		this.atk	=	atk;
		this.def	=	def;
		this.mana = manaMax = mana;
		this.energy = energyMax = energy;
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
	
	// Get Stats
    public int getHP(){return(hp);}
    public int getHPMax(){return(hpMax);}
    public int getAtk(){return(atk);}
    public int getDef(){return(def);}
    public int getMana(){return(mana);}
    public int getManaMax(){return(manaMax);}
    public int getEnergy(){return(energy);}
    public int getEnergyMax(){return(energyMax);}
}
