package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.event.*;

public class Player extends LivingObject {

	// Variable zur Zielabfrage
	protected boolean goal;

	// Konstruktor
    public Player(int spawnX, int spawnY, int h, int atk, int def, int energy, int mana) {
		super(spawnX, spawnY, h, atk, def, energy, mana);
		
		// States definieren
		state[0].changeImg("dead");			// Aussehen bei Tod
		state[1].changeImg("player");		// Aussehen bei Leben  
		state[2].changeImg("player");		// Aussehen bei abgeschlossenem Level
		state[1].defineOffset(15,3,0,5); 	// Hitbox des Spielers angepasst
		goal = false;
		
		// 1. State aktivieren (lebendig)
		switchState(1);

	}
    
    // Methode um den Spieler an eine bestimmte Stelle zu teleportieren
    public void teleport(int x, int y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
    public void draw(Graphics2D g2d, int x, int y){
    	g2d.drawImage(Ressources.lib.get(state[currState].img), x,  y,  null);
    }
    
    // Methode um den Spieler "wiederzubeleben"
    public void revive(){
    	hp	=	hpMax;
    	energy = energyMax;
    	mana = manaMax;
    	switchState(1);
    	// derzeit wird hier kein weiterer Code benoetigt (das kann sich im Laufe des Projekts aendern)
    }
    // Spieler beruehrt das ziel und gewinnt das spiel
    public void reachgoal(){ 
    	goal = true; 
    	switchState(2); // Spieler wird unbeweglich
    }
    // Wert der goal variable wird uebergeben
    public boolean getgoal(){
    	return goal;
    }

// Methoden zur Steuerung des Spielers per Keyboard
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