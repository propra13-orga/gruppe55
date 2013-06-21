package hhu.propra2013.gruppe55_opengl;

public abstract class LivingObject extends MovingObject {
	// Lebenspunkte Management
    protected int hp, hpMax,  def, atk, mana, manaMax, energy, energyMax;	// Statuswerte
	// Variablen fuer Handhabung von Unverwundbarkeit
	protected boolean invulnerable	=	false;		// Unverwundbarkeitszustand des Objekts
	protected int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	// Schadensberechnungsvariablen (welch ein Wort!)
	protected int critBase	=	20;	// Basis-Chance auf kritische Treffer
 	protected int critBonus, minDmg, maxDmg;
 	// Heilattribute fuer Leben und Mana
 	protected int healBonus=0,manaBonus=0;		// erhoehen die Werte fuer generiertes Leben/Mana
	// fermkampfattribute
	protected int detectionRange	=	200;	// Wenn ZielObjekte die gegebene Anzahl an Pixeln oder weniger entfernt sind=feuern
	protected Projectile projectile	=	new Projectile(0,0,0,0);	// Wird zur Erzeugung von Schuessen genutzt und gibt somit den Projektiltyp an

	
// Konstruktor
		// x,y: Koordinaten zum Erscheinen
	public LivingObject(double x, double y, int h, int atk, int def){
		super(x, y);
		
		// Array um den Status zu aendern
		state	=	new State[3];
		// Tod-Status
		state[0] = new State(Data_Textures.potionused, false, true, false);
		// Leben-Status
		state[1] = new State(Data_Textures.potionused, true, false, true);
		// Extra-Status
		state[2] = new State(Data_Textures.potionused, false, false, false);
		
		// starten als lebendiges Objekt
		currState	=	1;
		
		// Statuswerte setzen
		hp = hpMax = h;
		this.atk	=	atk;
		this.def	=	def;
		
		// Kampfwerte setzen
		critBonus	=	0;
		minDmg		=	1;
		maxDmg		=	2;
		
		// wir brauchen mehr Reset-Werte
		resetValues	=	new int[6];
	}
	
	// Methode um Schaden auszuteilen
	public void dealDamage(LivingObject l){
		// Schaden berechnen, denn wir haben tolle Formeln dafuer
		int dmg	=	(int) (Math.random()*(maxDmg+1-minDmg))+minDmg;	// Extraschaden
		dmg	+=	(int)( (Math.random()*100<=(critBase+critBonus) ) ? 2*atk : atk);	// Grundschaden (mit Crits)
		
		// Schaden setzen
		l.getHit(dmg);
	}
	
	// Funktion fuer Individuelle Aktionen
	public void action(int pX, int pY){
	}

	// Methode zum Fernkampf
	public void shoot(int angle){
		shoot(angle, projectile);
	}

	// Eigentliche Methode, erwartet ein Objekt des zu schiessenden Typs (hilfsfunktion fuer Zauber zB)
	protected void shoot(int angle, Projectile p){
		// Flugrichtung bestimmen
		double flyX	=	Math.cos(Math.toRadians(angle%360));
		double flyY	=	Math.sin(Math.toRadians(angle%360));

		// Koordinaten festlegen
		int[] center	=	getCenter();	// Mittelpunkt
		int hOffset	=	center[0]-(int)x;	// Horizontales und..
		int vOffset	=	center[1]-(int)y;	// .. vertikales Offset bestimmen
		int radius	=	(int)Math.sqrt(Math.pow(hOffset, 2)+Math.pow(vOffset, 2))+10;

		// Geschoss feuern
		for(GameEventListener gel : evtList){
			gel.shootProjectile(p.launch(center[0]+flyX*radius,center[1]+flyY*radius,angle, maxDmg+atk));
		}
	}

	
	// Schaden bei Treffer
	public void getHit(int dmg){
    	// Nichts tun bei Unverwundbarkeit
    	if(invulnerable)
    		return;
    	
    	// Setze Unverwundbarkeit
    	invulnerable	=	true;
    	setInvulnerability(invulTime);
	    
	    // Schaden berechnen
		if(hp>0)
			hp-=(dmg-def <=1) ? 1 : dmg-def;	// DEF beruecksichtigen, aber mindestens 1 Schaden machen
		if(hp<=0){
			hp	=	0;	// HP auf 0 setzen - ist sauberer
			switchState(0); // Wechsel auf tot
		}
	}
	
	// Heilung bei Aufnahme einer Potion
	public void getHealed(int heal){
		// Heilung durchführen
		hp+=heal+healBonus;
		// aber nicht zu viel heilen!
		if(hp>hpMax)
			hp = hpMax;
	}
	
	// Erhoeht das Mana
	public void fillmana(int ma){ 
		mana+=ma+manaBonus;
		if(mana>manaMax)
			mana = manaMax;
	}
	
	// Erhoeht die MaximalHP
	public void raisehp(){
		hpMax +=2;
		if(hpMax >20){
			hpMax = 20;
		}
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

    // Methode zum Setzen der Reset-Werte
	public void setResetValues(){
		super.setResetValues();		// Werte 1-4 abhandeln
		resetValues[4]	=	hp;			// 5. Wert: HitPoints
		resetValues[5]	=	mana;		// 6. Wert: Mana
	}

	// Methode zum zuruecksetzen des Objektes
	public void reset(){
		super.reset();				// Werte 1.4 abhandeln
		hp	=	resetValues[4];		// HP zuruecksetzen
		mana	=	resetValues[5];	// Mana zuruecksetzen
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
