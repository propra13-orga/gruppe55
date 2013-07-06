package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse Teleporter.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert die Teleportermechanik zum wechseln von Leveln.
 */

public class Teleporter extends DungeonObject {
// Attribute
	private int dx, dy;	// Zielkoordinaten
	private int room;	// Zielraum
	
	
	/**
	 * Der Konstruktor fuer den Teleporter.
	 * Beim Aufruf werden die x und y Koordinaten, die Raumnummer und die Zielkoordinaten im Zielraum uebergeben.
	 * Des Weiteren wird der State des Teleporters gesetzt. 
	 * @param x  Die x Koordinate des Teleporters
	 * @param y  Die y Koordinate des Teleporters
	 * @param room  Der Zielraum
	 * @param dx  Die x Koordinate des Ziels
	 * @param dy  Die y Koordinate des Ziels
	 */
	
// Konstruktor
	// x, y: Selbsterklärend
	// room: Zielraum
	// dx, dy: Zielkoordinaten
	
	public Teleporter(double x, double y, int room, int dx, int dy) {
		super(x, y);
		
		state[0].massive	=	true;
		
		// Zielraum und Zielkoordinaten werden gesetzt
		this.room	=	room;
		this.dx		=	dx;
		this.dy		=	dy;
	}
	
	/**
	 * Die Methode getTeleport.
	 * Diese Methode gibt die Teleportationsdaten zurueck.
	 * @return Die Teleportationsdaten als int[].
	 */
	
	// gibt Teleportationsdaten zureuck fuer weitere Berechnungen
	public int[] getTeleport(){
		return (new int[] {room, (int)dx, (int)dy});
	}
}
