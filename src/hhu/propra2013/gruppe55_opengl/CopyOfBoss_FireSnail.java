package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss3.
 * @see Creature
 */

public class CopyOfBoss_FireSnail extends Creature {
	// Attribute
	
	// Kontrollvariablen fuer Phase 3
	
	/** Boolean, ob bereits ein Warnschuss abgegeben wurde. */
	
	private boolean warning1	=	false;	// Boolean, ob bereits ein Warnschuss abgegeben wurde
	
	/** Boolean, ob bereits ein zweiter Warnschuss abgegeben wurde. */
	
	private boolean warning2	=	false;	// Boolean, ob bereits ein zweiter Warnschuss abgegeben wurde
	
	/** Winkel, der gewarnt wurde. */
	
	private int warnedAngle = 0;		// Winkel, der gewarnt wurde
	
	/** Abfrage ob der Boss von einer Wand abgebounced ist. */
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist

	/** Zaehler fuer den Angriffstakt, da einige Takte uebersprungen werden sollen */
	private int actionCounter1=0;
	
	/** Zaehler fuer den Lavaspurtakt, da einige Takte uebersprungen werden sollen */
	private int actionCounter2=0;
	
	/** Bewegungswinkel */
	private int moveAngle=180;
	
	/** Zustand ob der Boss am Feuerspeien ist */
	private boolean isAttacking=false;
	
	/**
	 * Der Konstruktor fuer den dritten Boss. 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird an dieser Stelle der State fuer den Boss gesetzt und das Bild geladen, sowie die Bewegungsrichtung, die Geschwindigkeit und die Schussreichweite initialisiert.
	 * @param spawnX  Die x-Koordinate, an der der Boss gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Boss gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Boss generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Boss generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Boss generiert wird.
	 */
 
    public CopyOfBoss_FireSnail(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss_snail); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =-1;									// Bewegung wird initialisiert x - Richung
		dy =0;									// Bewegung wird initialisiert y - Richung								
		speed = 1;								// Startgeschwindigkeit
		detectionRange = 300;					// Groessere Schussreichweite
		element=1;								// Feuerkreatur
		resistances[0][1]=1;					// Immun gegeueber Feuerschaden
		
		projectile	=	new Fireball(0,0,0,0);
		
		// Resetrelevante Werte
		//resetValues	=	new int[9];	// atk und detectionRange und speed kommen dazu, sonst wird nach dem Enrage nicht zurueck gesetzt
    }
    
    /**
     * Die Methode fuer die Bewegung.
     * Diese Methode ueberschreibt Methode move aus der Mutterklasse Creature und definiert das Bewegungsmuster. Ausserdem wird an dieser Stelle das Abprallen von den W‰nden realisiert.
     * @see Creature
     */

    public void move(){
    	if(isAttacking) return;
    	// Berechnung der neuen Bewegung
    	if(bounce==true){
    		moveAngle=(moveAngle-95)%360;
    		dx=Math.cos(Math.toDegrees(moveAngle)); // Beschleunigung in x Richtung
    		dy=Math.sin(Math.toDegrees(moveAngle));	// Beschleunigung in y Richtung
    		bounce=false;	// Bounce wird wieder auf false gesetzt
    	}
		// bewegung ausfuehren
    	if(state[currState].moveable){
    		x+=speed*dx;
    		y+=speed*dy;
    	}
    }
    
    /**
     * Die Methode action.
     * Diese Methode definiert das Kampfverhalten des Bosses. Der Bosskampf unterteilt sich in 3 Phasen, die abhaengig von seiner HP beginnen und die sich in seinem Schussmuster unterscheiden. 
     * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX
     * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY
     */
    
    public void action(int pX, int pY){
    	// Zaehler erhoehen
    	actionCounter1++;
    	
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Es soll nicht immer auf den Spieler geschossen werden
			if(actionCounter1 >= 8){
				// Angriff einleiten
				isAttacking=true;
				// Zurueck setzen
				actionCounter1=0;
				// Winkel berechnen
				int[] center	=	getCenter();
				final double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
				// Schieﬂen - oder besser gesagt Feuer speien! 
				new Thread(){
					public void run(){
						int maxShots=20;
						for(int i=1;i<maxShots;i++){
							shoot((int)angle, projectile);
							// Sleep-Timer setzen
			    			try {
								Thread.sleep(1000/maxShots);
							}catch (InterruptedException e) {e.printStackTrace();}
						}
						isAttacking=false;
					}
				}.start();
			}
		}
		
		/// Schleimspur? Pah! LAVASPUR
		for(GameEventListener gel : evtList){
    		gel.newStatic(new Lavapatch(x, y));
    	}
	}
    
    /**
     * Die Methode setBack.
     * Diese Methode ruft die Methode setBack der Mutterklasse Creature auf und sorgt dafuer, dass der Boss von der Wand abprallt.
     * @see MovingObject
     */

    // Hier wird auch nur ein kleines Detail ergaenzt (genau wie bei getHit)
	public void setBack(){
		bounce = true;		// bounce wird auf true gesetzt damit das Monster weiss, dass es die Richtung wechseln soll
		super.setBack();	// Aufrufen der Muttermethode
		
	}
      
    public void getHit(int dmg, int e){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg,e);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.triggerFired("FireSnailDown");		// Der Boss feuert das Event seines Todes
    		}
    	}
    }
    
    /**
     * Die Methode setResetValues.
     * Diese Methode ruft die Methode setResetValues aus der Mutterklasse LivingObject auf um die ResetValues zu setzen und erweitert diese dann noch um weitere Eintr‰ge (atk, detectionRange, ...).
     */
    
    // Resetmethoden bearbeiten
    public void setResetValues(){
    	super.setResetValues();
    	//resetValues[6]	=	atk;
    	//resetValues[7]	=	detectionRange;
    	//resetValues[8]	=	(int) speed;
    }
    
    /**
	 * Die Methode reset.
	 * Diese Methode ruft die Methode reset aus der Mutterklasse LivingObject auf um die Werte zu resetten und resettet anschliessend auch noch die Boss3 spezifischen, die zuvor in setResetValues im Boss3 gesetzt wurden.
	 */
    
    public void reset(){
    	super.reset();
    	//atk	=	resetValues[6];
    	//detectionRange	=	resetValues[7];
    	//speed	=	resetValues[8];
		state[1].changeMoveable(true);
    }
    
    public void onCollision(DungeonObject d){
		// Dem Spieler Schaden zufuegen
		if(d instanceof	Player){
			dealDamage((Player)d);
			}
		
		// Test auf Massive-Attribut in super.onCollision
		super.onCollision(d);
	}
}