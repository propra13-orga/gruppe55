package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Boss1.
 * Diese Klasse implementiert den ersten Boss. Sie erbt von der Klasse Creature.
 * @see Creature
 */

public class Boss1MP extends Boss1 {
	
	/** Bewegungswinkel */
	private int moveAngle=180;
	
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
	
    public Boss1MP(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);							// Startgeschwindigkeit
    }
    
    @Override
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
    		moveAngle=(moveAngle-95)%360;
    		dx=Math.cos(Math.toDegrees(moveAngle)); // Beschleunigung in x Richtung
    		dy=Math.sin(Math.toDegrees(moveAngle));	// Beschleunigung in y Richtung
    		bounce=false;	// Bounce wird wieder auf false gesetzt
    	}
		// bewegung ausfuehren
		x+=speed*dx;
		y+=speed*dy;
    }
}