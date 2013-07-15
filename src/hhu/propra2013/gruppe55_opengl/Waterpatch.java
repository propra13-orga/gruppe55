package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse Waterpatch.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Wasserflaeche.
 * @see DungeonObject
 */

public class Waterpatch extends DungeonObject{
	
	

	
	/**
	 * Der Konstruktor fuer die Wasserflaeche.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren wird hier der State fuer die Wasserflaeche gesetzt und das Bild geladen.
	 * @param x  Die x-Koordinate an der die Wasserflaeche gezeichnet wird
	 * @param y  Die y-Koordinate an der die Wasserflaeche gezeichnet wird
	 */

	public Waterpatch(double x, double y){
		super(x,y);
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.waterpatch, false, true, true);  // Das Bild des Lavafeldes
		
		element	=	3;	// Lavaobjekte sollten schon irgendwie vom Typ Feuer sein, nicht?
	}
	

} 