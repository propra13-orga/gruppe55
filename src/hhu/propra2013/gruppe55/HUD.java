package hhu.propra2013.gruppe55;

import java.awt.*;
import javax.swing.*;

public class HUD {
	
	private int width, height; //Fensterdimensionen
	private String HUDPath = "img/HUD.png";	//Bildpfad
	private Image hud;						//Image
	
	//Konstruktor
	public HUD(){
		hud = (new ImageIcon(HUDPath).getImage());	//Overlay-Bild laden
	}
	
	
	public void draw(Graphics g2d, int hp, int hpMax, int ausd, int ausdMax, int mana, int manaMax){
		//Draw HP
		g2d.setColor(Color.WHITE);
		g2d.fillRect(5, 5, 340, 10);
		g2d.setColor(Color.RED);
		g2d.fillRect(5, 5, (int)(340*((double)hp/hpMax)), 10);
		//Draw Ausdauer
		g2d.setColor(Color.WHITE);
		g2d.fillRect(5, 23, 323, 10);
		g2d.setColor(Color.ORANGE);
		g2d.fillRect(5, 23, (int)(323*((double)ausd/ausdMax)), 10);
		//Draw Mana
		g2d.setColor(Color.WHITE);
		g2d.fillRect(5, 38, 305, 10);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(5, 38, (int)(305*((double)mana/manaMax)), 10);
		g2d.setColor(Color.BLACK);
		g2d.drawImage(hud, 0, 0, null);
	}

}
