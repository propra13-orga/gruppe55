package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse Lavapatch.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Lavaflaeche.
 * @see DungeonObject
 */

public class Lavapatch extends DungeonObject{
	
	public int lavaDmg = 30;		// Lavadmg - Sehr hoch weil sehr heiﬂ!
	
	/**
	 * Der Konstruktor fuer die Lavaflaeche.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren wird hier der State fuer die Lavaflaeche gesetzt und das Bild geladen.
	 * @param x  Die x-Koordinate an der die Lavaflaeche gezeichnet wird
	 * @param y  Die y-Koordinate an der die Lavaflaeche gezeichnet wird
	 */

	public Lavapatch(double x, double y){
		super(x,y);
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.lavapatch, false, false, true);  // Das Bild des Lavafeldes
		
		element	=	1;	// Lavaobjekte sollten schon irgendwie vom Typ Feuer sein, nicht?
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode ruft die Schadensberechnung auf und uebergibt das Element der Lava dazu. Falls der Player gegen dieses Element geschuetzt ist, kann er das Feld ohne Probleme ueberqueren.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um den Spieler handelt
	 * @see DungeonObject
	 */
	
	public void onCollision(DungeonObject d){	 // Kollision mit dem Lavafeld
    	if(d instanceof	LivingObject){
    		((LivingObject)d).getHit(lavaDmg,element);
    	}
	}
} 


