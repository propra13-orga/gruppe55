package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.event.*;

public class Player extends LivingObject {

	// Variable zur Zielabfrage
	protected boolean goal;

	// constructor
    public Player(int spawnX, int spawnY, int h, int atk, int def, int energy, int mana) {
		super(spawnX, spawnY, h, atk, def, energy, mana);
		
		// set states
		state[0].changeImg("dead");
		state[1].changeImg("player");
		state[2].changeImg("player");
		state[1].defineOffset(15,3,0,5); // Hitbox des Spielers angepasst
		goal = false;
		
		// activate 1st state
		switchState(1);

	}
    
    // method to teleport the player to specific coordinates
    public void teleport(int x, int y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
    public void draw(Graphics2D g2d, int x, int y){
    	g2d.drawImage(Ressources.lib.get(state[currState].img), x,  y,  null);
    }
    
    // method to revive player after death
    public void revive(){
    	hp	=	hpMax;
    	energy = energyMax;
    	mana = manaMax;
    	switchState(1);
    	// at this point of game development no more code needed here
    }
    // spieler ber�hrt das ziel und gewinnt das spiel
    public void reachgoal(){ 
    	goal = true; 
    	switchState(2); // Spieler wird unbeweglich
    }
    // Wert der goal variable wird uebergeben
    public boolean getgoal(){
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