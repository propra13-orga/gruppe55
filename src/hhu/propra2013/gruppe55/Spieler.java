package hhu.propra2013.gruppe55;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Spieler {

//	private String spieler = "spieler.png";
	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;
	
	// Bild, Attribute und Bewegung Spielfigur 
	
	public Spieler() {
		image = Toolkit.getDefaultToolkit().getImage("player.png");
		x = 650;
		y = 650;					
	}
	
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public void bewegen() {
		x += dx;
		y += dy;
	}
	
	public Image getImage() {
		return image;
	}
	
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