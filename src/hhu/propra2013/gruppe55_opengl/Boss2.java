package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss2.
 * @see Creature
 */

public class Boss2 extends Creature {
	
	protected int sx, sy;					// Erscheinungskoordinaten
	
	/**
	 * Der Konstruktor fuer den zweiten Boss. 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, mX, mY, h, angr und vert uebergeben.
	 * Des Weiteren wird an dieser Stelle der State fuer den Boss gesetzt und das Bild geladen, sowie die Reichweite, in der der Boss den Spieler anschiesst initialisiert.
	 * @param spawnX  Die x-Koordinate, an der der Boss gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Boss gezeichnet wird.
	 * @param mX  Die Variable fuer MoveAreaX, also die maximale Bewegungsreichweite auf der X-Achse.
	 * @param mY  Die Variable fuer MoveAreaY, also die maximale Bewegungsreichweite auf der Y-Achse.
	 * @param h  Der HP-Wert, mit dem der Boss generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Boss generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Boss generiert wird.
	 */
	
    public Boss2(int spawnX, int spawnY, int mX, int mY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss2);   // Bild der Lebendigen Kreatur laden
		moveAreaX = mX;              // Maximale Bewegung auf der X-Achse
		moveAreaY = mY;              // Maximale Bewegung auf der Y-Achse
		detectionRange = 360;          // Reichwite ab wann der Boss angreift
	}
    
	/**
	 * Die Methode action.
	 * Diese Methode fuehrt die Schussfunktion auf die Mitte des Players aus, sofern dieser innerhalb der Reichweite (= Detectionrange).
	 * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX
     * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY 
	 */
	
	// Aktion (wie in der Creature_Bow)
	public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schießen
			shoot((int)angle%360);
		}
	}
	
	/**
	 * Die Methode getHit.
	 * Diese Methode ruft zum einen die getHit Methode aus der Klasse Creature auf und ueberschreibt die Methode getHit der Mutterklasse Creature mit dem Event, dass der Boss das GoalObject droppt.
	 * @param dmg  Die Methode erwartet einen Int Wert der aus der Dmg-Berechnung hervorgeht (im LivingObject)
	 * @see Creature
	 */
	
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(482, 320); // Der Boss droppt beim Tod das Zielobjekt in der Nische auf unserer Seite der Fallen
    		}
    	}
    }
}