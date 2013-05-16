package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.event.*;

public class Player extends LivingObject {

	// Variable zur Zielabfrage
	protected int goal;

	// constructor
    public Player(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		
		// set states
		state[0].changeImg(Ressources.dead);
		state[1].changeImg(Ressources.player);
		state[2].changeImg(Ressources.player);
		state[1].defineOffset(15,3,15,12); // Hitbox des Spielers angepasst
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
    	ausdauer = ausdauerMax;
    	mana = manaMax;
    	switchState(1);
    	// at this point of game development no more code needed here
    }
    // spieler ber�hrt das ziel und gewinnt das spiel
    public void reachgoal(){ 
    	goal +=1; 
    	switchState(2); // Spieler wird unbeweglich
    }
    // Wert der goal variable wird uebergeben
    public int getgoal(){
    	return goal;
    }
    
    //Drawing the Player
    public void draw(Graphics2D g2d, int x, int y){
    	g2d.drawImage(this.getImg(), x, y, null);
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