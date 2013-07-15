package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss3.
 * @see Creature
 */

public class Boss_IceSnail2 extends Creature {
	// Attribute
	
	/** Abfrage ob der Boss von einer Wand abgebounced ist. */
	
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
 
    public Boss_IceSnail2(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss_icesnail); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =-1;									// Bewegung wird initialisiert x - Richung
		dy =0;									// Bewegung wird initialisiert y - Richung								
		speed = 1;								// Startgeschwindigkeit
		detectionRange = 300;					// Groessere Schussreichweite
		element=1;								// Feuerkreatur
		resistances[1][0]=6;					// Resistent gegen Eis
		
		projectile	=	new Spell_SnowFlake(0,0,0,0);
		
		// Resetrelevante Werte
		//resetValues	=	new int[9];	// atk und detectionRange und speed kommen dazu, sonst wird nach dem Enrage nicht zurueck gesetzt
    }
    
    /**
     * Die Methode fuer die Bewegung.
     * Diese Methode ueberschreibt Methode move aus der Mutterklasse Creature und definiert das Bewegungsmuster. Ausserdem wird an dieser Stelle das Abprallen von den Wänden realisiert.
     * @see Creature
     */

    public void move(){
    	// Berechnung der neuen Bewegung
    	if(bounce==true){
    		// Bewegung umsetzen
    		double tmp=dx;
    		dx=dy;
    		dy=-1*tmp;
    		
    		/*if(dx==1 && dy==0){
    			dx=0;
    			dy=-1;
    		}
    		else if(dx==0 && dy==-1){
    			dx=-1;
    			dy=0;
    		} 
    		else if(dx==-1 && dy==0){
    			dx=0;
    			dy=1;
    		} 
    		else if(dx==0 && dy==1){
    			dx=1;
    			dy=0;
    		}*/ 
    		
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
			
			shoot((int)angle,projectile);
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
    			gel.triggerFired("IceSnail2Down");		// Der Boss feuert das Event seines Todes
    		}
    	}
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