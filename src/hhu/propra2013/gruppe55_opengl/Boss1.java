package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Boss1.
 * Diese Klasse implementiert den ersten Boss. Sie erbt von der Klasse Creature.
 * @see Creature
 */

public class Boss1 extends Creature {
	
	/** Abfrage ob der Boss von einer Wand abgebounced ist. */ 
		
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
 
	/**
	 * Der Konstruktor fuer den ersten Boss. 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird an dieser Stelle der State fuer den Boss gesetzt und das Bild geladen, sowie die Bewegungsrichtung und die Geschwindigkeit initialisiert.
	 * @param spawnX Die x-Koordinate, an der der Boss gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Boss gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Boss generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Boss generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Boss generiert wird.
	 */
	
    public Boss1(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss1); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 3;								// Startgeschwindigkeit
    }

    /**
     * Die Methode fuer die Bewegung.
     * Diese Methode definiert das Bewegungsmuster und die verschiedenen Phasen des Bosskampfes. Je nach HP des Bosses aendert dieser seine Geschwindigkeit und seinen Schaden. Ausserdem wird an dieser Stelle das Abprallen von den Wänden realisiert.
     * Die Methode move ueberschreibt die aus der Klasse Creature stammende Methode move.
     * @see Creature
     */
    
    public void move(){    	
		// bewegungsgeschwindigkeit berechnen
    	// Wenn der Boss weniger als 5 HP hat
    	if(hp<=5){
    		speed = 6.0;
    	}
    	// Wenn der Boss weniger als 10 HP hat
    	else if(hp<=10){
    		speed = 4.5;
    	}
    	// wenn der Boss mehr als 10 HP hat
    	else 
    		speed = 3;
    	// Berechnung des Winkels beim abprallen von einer Wand
    	if(bounce==true){
    		double angle=Math.random()*360; // [0,360[
    		dx=Math.cos(Math.toDegrees(angle)); // Beschleunigung in x Richtung
    		dy=Math.sin(Math.toDegrees(angle));	// Beschleunigung in y Richtung
    		bounce=false;	// Bounce wird wieder auf false gesetzt
    	}
		// bewegung ausfuehren
		x+=speed*dx;
		y+=speed*dy;
		
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
      
	/**
	 * Die Methode getHit.
	 * Diese Methode ruft zum einen die getHit Methode aus der Klasse Creature auf
	 * Die Methode ueberschreibt die Methode getHit der Mutterklasse Creature mit dem Event, dass der Boss das GoalObject droppt.
	 * @param dmg  Die Methode erwartet einen Int Wert der aus der Dmg-Berechnung hervorgeht (im LivingObject)
	 * @param e Es wird ein nummerischer Wert erwartet, der den Elementtyp des eintreffenden Schadens beschreibt
	 * @see Creature
	 */
	
    public void getHit(int dmg, int e){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg,e);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);		// Der Boss droppt an der Stelle seines Todes das Zielobjekt (bis wir nahtlosen Übergang zwischen den Leveln haben)
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