package hhu.propra2013.gruppe55_opengl;

public class Teleporter extends DungeonObject {
// Attribute
	private int dx, dy;	// Zielkoordinaten
	private int room;	// Zielraum
	
	
// Konstruktor
	// x, y: Selbsterkl�rend
	// room: Zielraum
	// dx, dy: Zielkoordinaten
	
	public Teleporter(int x, int y, int room, int dx, int dy) {
		super(x, y);
		
		state[0].massive	=	false;
		
		// Zielraum und Zielkoordinaten werden gesetzt
		this.room	=	room;
		this.dx		=	dx;
		this.dy		=	dy;
	}
	
	// gibt Teleportationsdaten zureuck fuer weitere Berechnungen
	public int[] getTeleport(){
		return (new int[] {room, dx, dy});
	}
}