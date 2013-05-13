package hhu.propra2013.gruppe55;

import java.awt.event.*;

public class Player extends LivingObject {

	// images
	private String imgPath	=	"img/player.png";  // player-image
	private String ripPath	=	"img/dead.png";	// rip-image
	// Variable zur Zielabfrage
	protected int goal;

	// constructor
    public Player(int spawnX, int spawnY) {
		super(spawnX, spawnY);

		// set states
		state[0].changeImg(ripPath);
		state[1].changeImg(imgPath);
		state[2].changeImg(imgPath);
		goal = 0;
		
		// activate 1st state
		switchState(1);

	}
    
    // method to teleport the player to specific coordinates
    public void teleport(int x, int y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
    // method to revive player after death
    public void revive(){
    	hp	=	hpMax;
    	switchState(1);
    	// at this point of game development no more code needed here
    }
    // spieler berührt das ziel und gewinnt das spiel
    public void reachgoal(){ 
    	goal +=1; 
    	switchState(2); // Spieler wird unbeweglich
    }
    // Wert der goal variable wird uebergeben
    public int getgoal(){
    	return goal;
    }

// following methods allow keyboard control of player
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {dx = -1;}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {dx = +1;}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {dy = +1;}
		if (e.getKeyCode() == KeyEvent.VK_UP) {dy = -1;}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {dx = 0;}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {dx = 0;}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {dy = 0;}
		if (e.getKeyCode() == KeyEvent.VK_UP) {dy = 0;}
	}

}