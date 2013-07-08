package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Creature_Bow.
 * Diese Klasse erbt von der Klasse Creature und spezifiziert diese weiter als Creature_Bow (Kreatur mit Bogen / Fernkampf)
 * @see Creature
 */

public class Creature_Bow extends Creature{
	
	/**
	 * Der Konstruktor fuer die Creature_Bow. 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, mX, mY, h, angr und vert uebergeben.
	 * Des Weiteren wird an dieser Stelle der State fuer die Creature_Bow gesetzt und das Bild geladen, sowie die Reichweite, in der die Creature_Bow den Spieler anschiesst und die Geschwindigkeit initialisiert.
	 * @param spawnX  Die x-Koordinate, an der die Creature_Bow gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der die Creature_Bow gezeichnet wird.
	 * @param mX  Die Variable fuer MoveAreaX, also die maximale Bewegungsreichweite auf der X-Achse.
	 * @param mY  Die Variable fuer MoveAreaY, also die maximale Bewegungsreichweite auf der Y-Achse.
	 * @param h  Der HP-Wert, mit dem die Creature_Bow generiert wird.
	 * @param angr  Der Angriffswert, mit dem die Creature_Bow generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem die Creature_Bow generiert wird.
	 */
	
	public Creature_Bow(int spawnX, int spawnY, int mX, int mY, int a, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		// Bewegungslänge und Schussachse setzen
		moveAreaX = mX;
		moveAreaY = mY;
		state[1].changeTexture(Data_Textures.creature_bow);	// Img setzen
		speed = 2;
	}
	
	/**
	 * Die Methode action.
	 * Diese Methode fuehrt die Schussfunktion auf die Mitte des Players aus, sofern dieser innerhalb der Reichweite (= Detectionrange).
	 * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX
     * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY 
	 */
	
	
	// Aktion
	public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schießen
			shoot((int)angle);
		}
	}
    
}