package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;

public abstract class LivingObject extends DungeonObject {
	// Lebenspunkte Management
    protected int hp, hpMax,  def, atk, mana, manaMax, energy, energyMax;	// Statuswerte
	// Variablen fuer Handhabung von Unverwundbarkeit
	private boolean invulnerable	=	false;		// Unverwundbarkeitszustand des Objekts
	private int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	// Bewegung
	protected int dx, dy;		// direction coordinates (dx: -1, move left; 1 move right;; dy: -1, move up, 1, move down
	protected int speed=3;		// speed of object (2 normal speed -> 2px/actionPerfordmed)
	
	
	
// Konstruktor
		// x,y: Koordinaten zum Erscheinen
	public LivingObject(int x, int y, int h, int atk, int def, int energy, int mana){
		super(x, y);
		
		// Array um den Status zu aendern
		state	=	new State[3];
		// Tod-Status
		state[0] = new State(Data.potionused, false, true, false);
		// Leben-Status
		state[1] = new State(Data.potionused, true, false, true);
		// Extra-Status
		state[2] = new State(Data.potionused, false, false, false);
		
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
    	// Nichts tun bei Unverwundbarkeit
    	if(invulnerable)
    		return;
    	
    	// Setze Unverwundbarkeit
    	invulnerable	=	true;
    	setInvulnerability(invulTime);
	    
	    // Schaden berechnen
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
    
    // Methode zum Unverwundbar machen des Objekts
    public void setInvulnerability(final int sleepTime){
    	// Erstelle neuen Thread
    	new Thread() {
			@Override
			public void run() {
				// nach sleepTime Millisekunden unverwundbarkeit zurücksetzen
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				invulnerable = false;
			}
		}.start();
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
