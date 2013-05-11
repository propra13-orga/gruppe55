package hhu.propra2013.gruppe55;

public class Teleporter extends DungeonObject {
// ATTRIBUTES
	private int dx, dy;	// destination coordinates
	private int room;	// destination room
	
	
// CONSTRUCTOR
	// x, y: self explaining
	// room: destination room
	// dx, dy: destination coordinates
	public Teleporter(int x, int y, int room, int dx, int dy) {
		super(x, y);
		
		state[0].massive	=	false;
		
		// set destination and it's coordinates
		this.room	=	room;
		this.dx		=	dx;
		this.dy		=	dy;
	}
	
	// returns port data for further "calculation"
	public int[] getTeleport(){
		// this is pretty self explaining
		return (new int[] {room, dx, dy});
	}
}
