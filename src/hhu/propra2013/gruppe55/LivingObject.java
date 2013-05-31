package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public abstract class LivingObject extends DungeonObject {

	Ressources res;
	// Lebenspunkte Management
    protected int hp, hpMax,  def, atk, mana, manaMax, energy, energyMax;	// Statuswerte
	// Bewegung
	protected int dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	protected int speed=5;		// speed of object (2 normal speed -> 2px/actionPerfordmed
	
	
	
// Konstruktor
		// x,y: Koordinaten zum Erscheinen
	public LivingObject(int x, int y, int h, int atk, int def, int energy, int mana){
		super(x, y);
		
		// Array um den Status zu aendern
		state	=	new State[3];
		// Tod-Status
		state[0] = new State("potionused", false, true, false);
		// Leben-Status
		state[1] = new State("potionused", true, false, true);
		// Spiel gewonnen
		state[2] = new State("potionused", false, true, false);
		
		// starten als lebendiges Objekt
		currState	=	1;
		
		// Statuswerte setzen
		hp = hpMax = h;
		this.atk	=	atk;
		this.def	=	def;
		this.mana = manaMax = mana;
		this.energy = energyMax = energy;
	}
	
	// Schaden bei Treffer
	public void getHit(){
		if(hp>0)
			hp-=1;
		if(hp<=0)
			switchState(0); // Wechsel auf tot
	}
	
	// Heilung bei Aufnahme einer Potion
	public void getHealed(){ 
		hp+=25;
		if(hp>hpMax)
			hp = hpMax;
	}
	
	// Methode zur Bewegeung
	public void move(){
		// Test ob das Objekt lebendig ist
		if(state[currState].moveable == false)
			return;
		// Bewegungmethode ausfuehren
		
		x+=speed*dx;
		y+=speed*dy;
	}
	
	// Waende haben Pushback (man wird einige Pixel zurueckgesetz)
	public void setBack(){
		x-=dx;
		y-=dy;
	}
	
	// Methoden um Statuswerte zu uebergeben
    public int getHP(){return(hp);}
    public int getHPMax(){return(hpMax);}
    public int getAtk(){return(atk);}
    public int getDef(){return(def);}
    public int getMana(){return(mana);}
    public int getManaMax(){return(manaMax);}
    public int getEnergy(){return(energy);}
    public int getEnergyMax(){return(energyMax);}
}
