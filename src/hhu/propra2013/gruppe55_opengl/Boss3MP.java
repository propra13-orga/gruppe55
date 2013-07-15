package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss3.
 * @see Creature
 */

public class Boss3MP extends Boss3{
	// Attribute
	
	/** Bewegungswinkel */
	private int moveAngle=180;
	
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
 
    public Boss3MP(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
    }

    @Override
    public void move(){    	
    	// Berechnung des Winkels beim abprallen von einer Wand
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
}