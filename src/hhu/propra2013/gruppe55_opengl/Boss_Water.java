package hhu.propra2013.gruppe55_opengl;

public class Boss_Water extends Creature {

	
	/** Abfrage ob der Boss von einer Wand abgebounced ist. */ 
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
	
	/** Abfrage, ob die Bounce-Richtung bereits vom Netzwerkpartner gesetzt wurde. */
	
	protected boolean nbounced = false;
	
	/** Vom Netzwerk uebergebene Bounce-Richtungen */
 
	protected double ndx, ndy;
	
	
    public Boss_Water(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss_water1); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 1;								// Startgeschwindigkeit
		element = 3;
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
    	if(hp<=8){
    		state[1].changeTexture(Data_Textures.boss_water3);
    		speed = 4;

    		
    	}
    	else if(hp<=12){
    		state[1].changeTexture(Data_Textures.boss_water2);
    		speed = 3;

    	}
    	if(bounce==true){
    		//Ob der Netzwerkpartner vorher schon gebounced ist
    		if(!nbounced){
	    		double angle=Math.random()*360; // [0,360[
	    		dx=Math.cos(Math.toDegrees(angle)); // Beschleunigung in x Richtung
	    		dy=Math.sin(Math.toDegrees(angle));	// Beschleunigung in y Richtung
	    		bounce=false;	// Bounce wird wieder auf false gesetzt
    		}
    		//Wenn ja, dessen Werte uebernehmen
    		else{
    			System.out.println(ndx + " - " + ndy);
    			dx = ndx;
    			dy = ndy;
    			bounce = nbounced = false;
    		}
    	}
    	
    	// bewegung ausfuehren
    	x+=speed*dx;
    	y+=speed*dy;
    }
    
    
    /**
     * Die Methode setNetworkBounce.
     * Diese Methode nutzt das MP-Level, um die Bouncewerte des anderen Bosses zu synchronisieren.
     * @param ndx Die Methode erwartet eine Double Wert fuer die dx-Richtung des Bounces.
     * @param ndy Die Methode erwartet eine Double Wert fuer die dy-Richtung des Bounces.
     */
    
    public void setNetworkBounce(double ndx, double ndy){
    	nbounced = true;
    	this.ndx = ndx;
    	this.ndy = ndy;
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
    			gel.triggerFired("waterdown");		// Der Boss feuert das Event seines Todes
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
