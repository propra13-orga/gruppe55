package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse LivingObject.
 * Diese Klasse erbt von der Klasse MovingObject und spezifiziert die Art des Objektes weiter (als lebendes sich bewegendes Objekt).
 * @see MovingObject
 */

public abstract class LivingObject extends MovingObject {
	
	// Lebenspunkte Management
   
	/** Die Statuswertde Lebendigen Objekts. */
	
	protected int hp, hpMax,  def, atk, mana, manaMax, energy, energyMax;	// Statuswerte
	
	// Variablen fuer Handhabung von Unverwundbarkeit
	
	/** Abfrage, ob das Objekt unverwundbar ist. */
	
	protected boolean invulnerable	=	false;		// Unverwundbarkeitszustand des Objekts
	
	/** Dauer der Unverwundbarkeit in Millisekunden. */
	
	protected int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	
	// Schadensberechnungsvariablen (welch ein Wort!)
	
	/** Die Basis-Chance auf einen kritischen Treffer. */
	
	protected int critBase	=	20;	// Basis-Chance auf kritische Treffer
 	
	/** Der Max-/Min-Schaden/Critbonus. */
	
	protected int critBonus, minDmg, maxDmg;
 	
	/** Die Widerstaende gegen die Elemente. */
	
	protected int[][] resistances;	// Speichert die Widerstaende gegen bestimmte Elemente ab
 	
	// Heilattribute fuer Leben und Mana
 	
	/** Der Bonus auf Heilung/Manarueckgewinnung. */
	
	protected int healBonus=0,manaBonus=0;		// erhoehen die Werte fuer generiertes Leben/Mana
	
	// fermkampfattribute
	
	/** Die Reichweite, in der Gegner anfangen zu schiessen in Pixeln. */
	
	protected int detectionRange	=	200;	// Wenn ZielObjekte die gegebene Anzahl an Pixeln oder weniger entfernt sind=feuern
	
	/** Wird zur Erzeugung von Schuessen genutzt und gibt somit den Projektiltyp an. */
	
	protected Projectile projectile	=	new Projectile(0,0,0,0);	// Wird zur Erzeugung von Schuessen genutzt und gibt somit den Projektiltyp an
	
	/** 
	 * Der Konstruktor fuer ein LivingObject.
	 * Beim Aufruf werden dem Konstruktor die Werte x, y, h, atk und def uebergeben.
	 * Des Weiteren werden die benoetigten States, sowie das Array fuer die ResetValues definiert.
	 * @param x  Die x Koordinate fuer das LivingObject
	 * @param y  Die y Koordinate fuer das LivingObject
	 * @param h  Der HP Wert, mit dem das LivingObject generiert wird
	 * @param atk  Der Angriffswert, mit dem das LivingObject generiert wird
	 * @param def  Der Verteidigungswert, mit dem das LivingObject generiert wird
	 */
	
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
		
		// Elementarresistenzen
		resistances	=	new int[3][2];
		resistances[0][0]	=	0;	// Widerstand gegen Element 1
		resistances[0][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 1 Immun
		resistances[1][0]	=	0;	// Widerstand gegen Element 2
		resistances[1][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 2 Immun
		resistances[2][0]	=	0;	// Widerstand gegen Element 3
		resistances[2][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 3 Immun
		
		// wir brauchen mehr Reset-Werte
		resetValues	=	new int[6];
	}
	
	/**
	 * Die Methode dealDamage.
	 * Diese Methode berechnet den Schaden, den ein Objekt an einem Anderen anrichtet. 
	 * @param l  Diese Methode erwartet die Uebergabe eines Objektes l von Typ LivingObject
	 */
	
	// Methode um Schaden auszuteilen
	public void dealDamage(LivingObject l){
		// Schaden berechnen, denn wir haben tolle Formeln dafuer
		int dmg	=	(int) (Math.random()*(maxDmg+1-minDmg))+minDmg;	// Extraschaden
		dmg	+=	(int)( (Math.random()*100<=(critBase+critBonus) ) ? 2*atk : atk);	// Grundschaden (mit Crits)
		
		// Schaden setzen
		l.getHit(dmg,element);
	}
	
	/**
	 * Die Methode action.
	 * Diese Methode wird verwendet um verschiedene individuelle Aktionen auszufuehren.
	 * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX 
	 * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY
	 */
	
	// Funktion fuer Individuelle Aktionen
	public void action(int pX, int pY){
	}
	
	/**
	 * Die Methode shoot.
	 * Diese Methode loest den Schuss (Fernkampf) aus.
	 * @param angle  Die Methode erwartet die Uebergabe eines int Werts angle
	 */

	// Methode zum Fernkampf
	public void shoot(int angle){
		shoot(angle, projectile);
	}
	
	/**
	 * Die eigentliche Methode shoot.
	 * Diese Methode erwartet zusaetzlich zum int angle noch ein Objekt, das verschossen werden soll (bspw. fuer Zauber).
	 * @param angle  Die Methode erwartet die Uebergabe eines int Werts angle
	 * @param p  Die Methode erwartet ein Objekt p des Typs Projectile
	 */

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
	
	/**
	 * Die Methode getHit.
	 * Diese Methode berechnet den Schaden (mit def-Wert) und uebertraegt diesen an das Objekt und setzt ggf. den Status auf "tot".
	 * @param dmg  Diese Methode erwartet die Uebergabe eines int Werts dmg
	 * @param e Es wird ein nummerischer Wert erwartet, der den Elementtyp des eintreffenden Schadens beschreibt
	 */

	
	// Schaden bei Treffer
	public void getHit(int dmg, int e){
    	// Nichts tun bei Unverwundbarkeit
    	if(invulnerable || (e>0 && resistances[e-1][1]>0))
    		return;
    	
    	// Setze Unverwundbarkeit
    	invulnerable	=	true;
    	setInvulnerability(invulTime);
    	
    	// Einkommenden Schaden berechnen
    	int iDmg	=	dmg;	// i fuer incomming!
    	// DEF beruecksichtigen
    	iDmg-=def;	// DEF
    	
    	// Widerstaende beruecksichtigen
    	if(e>0 && element>0){	// Beim neutralen Typ gibt es keine Widerstaende/Vor- oder Nachteile
        	iDmg-=resistances[e-1][0];	// Widerstand vom Gesamtschaden abziehen
        	
        	// Vor-/Nachteile
        	if(e!=element){
        		// 1>2>3>1
        		iDmg	=	(e<element%3) ? iDmg*2 : iDmg/2;
        	}
    	}
    	
	    
	    // Schaden berechnen
		hp-=	(iDmg>0)	?	iDmg : 1;	// Mindestens muss 1 Schaden gemacht werden
		if(hp<=0){
			hp	=	0;	// HP auf 0 setzen - ist sauberer
			switchState(0); // Wechsel auf tot
		}
	}
	
	/**
	 * Die Methode getHealed.
	 * Diese Methode heilt das Objekt um einen bestimmten Wert (heal).
	 * @param heal  Diese Methode erwartet die Uebergabe eines int Werts heal
	 */
	
	// Heilung bei Aufnahme einer Potion
	public void getHealed(int heal){
		// Heilung durchführen
		hp+=heal+healBonus;
		// aber nicht zu viel heilen!
		if(hp>hpMax)
			hp = hpMax;
	}
	
	/**
	 * Die Methode fillmana.
	 * Diese Methode fuellt das Mana des Objekts wieder um einen bestimmten Wert auf (ma).
	 * @param ma  Diese Methode erwartet die Uebergabe eines int Werts ma
	 */
	
	// Erhoeht das Mana
	public void fillmana(int ma){ 
		mana+=ma+manaBonus;
		if(mana>manaMax)
			mana = manaMax;
	}
	
	/**
	 * Die Methode raisehp
	 * Diese Methode erhoeht die maximal HP des Players.
	 */
	
	// Erhoeht die MaximalHP
	public void raisehp(){
		hpMax +=2;
		if(hpMax >20){
			hpMax = 20;
		}
	}
    
	/**
	 * Die Methode setInvulnerability.
	 * Diese Methode macht Objekte nach einem Treffer fuer eine kurze Zeit (sleepTime) unverwundbar.
	 * @param sleepTime  Diese Methode erwartet die Uebergabe eines final int Werts sleepTime
	 */
	
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
	
	/**
	 * Die Methode getHP 
	 * Diese Methode uebergibt den Wert HP des Objektes
	 * @return Der aktuelle Hp Wert als int.
	 */
	
    public int getHP(){return(hp);}
    
	/**
	 * Die Methode getHPMax 
	 * Diese Methode uebergibt den Wert HPMax des Objektes
	 * @return Der maximale Hp Wert als int.
	 */
    
    public int getHPMax(){return(hpMax);}
    
	/**
	 * Die Methode getAtk 
	 * Diese Methode uebergibt den Wert atk des Objektes
	 * @return Der atk Wert als int.
	 */
    
    public int getAtk(){return(atk);}
    
	/**
	 * Die Methode getDef
	 * Diese Methode uebergibt den Wert def des Objektes
	 * @return Der def Wert als int.
	 */
    
    public int getDef(){return(def);}
    
	/**
	 * Die Methode getMana 
	 * Diese Methode uebergibt den Wert mana des Objektes
	 * @return Der aktuelle mana Wert als int.
	 */
    
    public int getMana(){return(mana);}
    
	/**
	 * Die Methode getManaMax 
	 * Diese Methode uebergibt den Wert ManaMax des Objektes
	 * @return Der maximale mana Wert als int.
	 */
    
    public int getManaMax(){return(manaMax);}
    
	/**
	 * Die Methode getEnergy 
	 * Diese Methode uebergibt den Wert energy des Objektes
	 * @return Der energy Wert als int.
	 */
    
    public int getEnergy(){return(energy);}
    
	/**
	 * Die Methode getEnergyMax 
	 * Diese Methode uebergibt den Wert energyMax des Objektes
	 * @return Der maximale energy Wert als int.
	 */
    
    public int getEnergyMax(){return(energyMax);}
}
