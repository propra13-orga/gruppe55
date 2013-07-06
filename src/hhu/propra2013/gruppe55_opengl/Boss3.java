package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss3.
 * @see Creature
 */

public class Boss3 extends Creature {
	// Attribute
	private Projectile alternativeProjectile	=	new Fireball(0,0,0,0);
	// Kontrollvariablen fuer Phase 3
	private boolean warning1	=	false;	// Boolean, ob bereits ein Warnschuss abgegeben wurde
	private boolean warning2	=	false;	// Boolean, ob bereits ein zweiter Warnschuss abgegeben wurde
	private int warnedAngle = 0;		// Winkel, der gewarnt wurde
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
	
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
 
    public Boss3(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss3); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 3;								// Startgeschwindigkeit
		detectionRange = 300;					// Groessere Schussreichweite
		
		// Resetrelevante Werte
		resetValues	=	new int[9];	// atk und detectionRange und speed kommen dazu, sonst wird nach dem Enrage nicht zurueck gesetzt
    }
    
    /**
     * Die Methode fuer die Bewegung.
     * Diese Methode ueberschreibt Methode move aus der Mutterklasse Creature und definiert das Bewegungsmuster. Ausserdem wird an dieser Stelle das Abprallen von den Wänden realisiert.
     * @see Creature
     */

    public void move(){    	
    	// Berechnung des Winkels beim abprallen von einer Wand
    	if(bounce==true){
    		double angle=Math.random()*360; // [0,360[
    		dx=Math.cos(Math.toDegrees(angle)); // Beschleunigung in x Richtung
    		dy=Math.sin(Math.toDegrees(angle));	// Beschleunigung in y Richtung
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
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schießen
			if(hp>10){
				shoot((int)angle%360);
			}
			// Auf Feuerbaelle umsteigen - so als Warnung
			else if(hp>5){
				shoot((int)angle%360, alternativeProjectile);
			}
			// Am Ende soll es schwieriger sein, uebertreiben wir also!
			else{
				// Warnschuss um dem Spieler ne Chance zu geben!
				// Warnschuss um dem Spieler ne Chance zu geben!
				if(!warning1 || (warning1 && !warning2) ){
					// Warnung flaggen
					warning2	=	warning1; // sichert, dass wir 2x zur Warnung schiessen
					warning1	=	true;
					warnedAngle	=	(!warning2)?(int)angle:warnedAngle;
					// Warnschuesse abgeben, die den 120° Bereich einleiten, der von der Feuerballsalve abgedeckt wird
					shoot(warnedAngle+60%360, alternativeProjectile);
					shoot(warnedAngle-60%360, alternativeProjectile);
				}
				else{
					// die naechsten Schuesse muessen wieder Warnungen sein
					warning1	=	false;
					warning2	=	false;
					// Nun decken wir den 120° Bereich (oben genannt) mit Feuer ein
					shoot(warnedAngle%360+60, alternativeProjectile);
					shoot(warnedAngle%360+50, alternativeProjectile);
					shoot(warnedAngle%360+40, alternativeProjectile);
					shoot(warnedAngle%360+30, alternativeProjectile);
					shoot(warnedAngle%360+20, alternativeProjectile);
					shoot(warnedAngle%360+10, alternativeProjectile);
					shoot(warnedAngle%360, alternativeProjectile);
					shoot(warnedAngle%360-10, alternativeProjectile);
					shoot(warnedAngle%360-20, alternativeProjectile);
					shoot(warnedAngle%360-30, alternativeProjectile);
					shoot(warnedAngle%360-40, alternativeProjectile);
					shoot(warnedAngle%360-50, alternativeProjectile);
					shoot(warnedAngle%360-60, alternativeProjectile);
				}
			}
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
      
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);		
    	
    	// bewegungsgeschwindigkeit berechnen
    	// Wenn der Boss weniger als 5 HP hat
    	if(hp<=5){
    		// stoppe Bewegung
    		speed = 7;
    		state[1].changeMoveable(false);
    		// ENRAGE WUAAAAARGH
    		atk	*=2;	// Staerker werden
    		// Groessere Range
    		detectionRange	=	600;
    	}
    	// Wenn der Boss weniger als 10 HP hat
    	else if(hp<=10){
    		speed = 4.5;
    	}
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);		// Der Boss droppt an der Stelle seines Todes das Zielobjekt (bis wir nahtlosen Übergang zwischen den Leveln haben)
    		}
    	}
    }
    
    /**
     * Die Methode setResetValues.
     * Diese Methode ruft die Methode setResetValues aus der Mutterklasse LivingObject auf um die ResetValues zu setzen und erweitert diese dann noch um weitere Einträge (atk, detectionRange, ...).
     */
    
    // Resetmethoden bearbeiten
    public void setResetValues(){
    	super.setResetValues();
    	resetValues[6]	=	atk;
    	resetValues[7]	=	detectionRange;
    	resetValues[8]	=	(int) speed;
    }
    
    /**
	 * Die Methode reset.
	 * Diese Methode ruft die Methode reset aus der Mutterklasse LivingObject auf um die Werte zu resetten und resettet anschliessend auch noch die Boss3 spezifischen, die zuvor in setResetValues im Boss3 gesetzt wurden.
	 * @see setResetValues
	 */
    
    public void reset(){
    	super.reset();
    	atk	=	resetValues[6];
    	detectionRange	=	resetValues[7];
    	speed	=	resetValues[8];
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