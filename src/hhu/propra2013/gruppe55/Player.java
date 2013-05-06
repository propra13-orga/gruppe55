package hhu.propra2013.gruppe55;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends LivingObject {
// attributes
	// images
	private String imgPath	=	"img/player.png";  // player-image
	private String ripPath	=	"img/dead.png";	// rip-image
	
// constructor
    public Player(int spawnX, int spawnY) {
		super(spawnX, spawnY);
		
		// set states
		state[0].changeImg(ripPath);
		state[1].changeImg(imgPath);
		
	}
	
	
// following methods allow keyboard control of player
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			dx = -1;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dx = +1;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dy = +1;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			dy = -1;
		}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			dx = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dy = 0;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			dy = 0;
		}
	}
	
}