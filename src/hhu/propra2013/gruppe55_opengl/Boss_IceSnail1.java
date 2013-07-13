package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss3.
 * @see Creature
 */

public class Boss_IceSnail1 extends Creature {
	// Attribute
	
	/** Abfrage ob der Boss von einer Wand abgebounced ist. */
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist

	/** Zaehler fuer den Angriffstakt, da pro Takt anders interagiert werden soll */
	private int actionCounter=0;
	
	/** Zustand ob der Boss am Eisspeien ist */
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
 
    public Boss_IceSnail1(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss_icesnail); 	// Bild der Lebendigen Kreatur laden
		state[1].moveable=false;				// Diese Schnecke bewegt sich nicht
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =-1;									// Bewegung wird initialisiert x - Richung
		dy =0;									// Bewegung wird initialisiert y - Richung								
		speed = 1;								// Startgeschwindigkeit
		detectionRange = 500;					// Groessere Schussreichweite
		element=1;								// Feuerkreatur
		resistances[0][1]=1;					// Immun gegeueber Feuerschaden
		
		projectile	=	new Spell_SnowFlake(0,0,0,0);
		
		// Resetrelevante Werte
		//resetValues	=	new int[9];	// atk und detectionRange und speed kommen dazu, sonst wird nach dem Enrage nicht zurueck gesetzt
    }
    
    /**
     * Die Methode action.
     * Diese Methode definiert das Kampfverhalten des Bosses. Der Bosskampf unterteilt sich in 3 Phasen, die abhaengig von seiner HP beginnen und die sich in seinem Schussmuster unterscheiden. 
     * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX
     * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY
     */
    
    public void action(int pX, int pY){
    	// Zaehler erhoehen
    	actionCounter++;
    	
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Es soll nicht immer auf den Spieler geschossen werden
			if(actionCounter >= 6 && !isAttacking){
				// Schieﬂen - oder besser gesagt Eis "speien"! 
				new Thread(){
					public void run(){
						int maxShots=20;
						for(int i=1;i<maxShots;i++){
							shoot(0+45, projectile);
							shoot(5+45, projectile);
							shoot(-5+45, projectile);
							
							shoot(90+45, projectile);
							shoot(95+45, projectile);
							shoot(85+45, projectile);

							shoot(180+45, projectile);
							shoot(185+45, projectile);
							shoot(175+45, projectile);
							
							shoot(270+45, projectile);
							shoot(275+45, projectile);
							shoot(265+45, projectile);
							// Sleep-Timer setzen
			    			try {
								Thread.sleep(1000/maxShots);
							}catch (InterruptedException e) {e.printStackTrace();}
						}
						isAttacking=false;
						// Zurueck setzen
						actionCounter=0;
					}
				}.start();
			}
		}
		shoot(0, projectile);
		shoot(90, projectile);
		shoot(180, projectile);
		shoot(270, projectile);
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
    			gel.triggerFired("IceSnail1Down");		// Der Boss feuert das Event seines Todes
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