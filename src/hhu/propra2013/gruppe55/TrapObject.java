package hhu.propra2013.gruppe55;



public class TrapObject extends DungeonObject {

	 private String imgPath	=	"img/Falle.png";  // trap-image
	
	public TrapObject(int x, int y) {
		super(x,y);
		state[0].changeImg(imgPath);
	}

}
