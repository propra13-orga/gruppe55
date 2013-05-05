package hhu.propra2013.gruppe55;

import javax.swing.*;	//Imports
import java.awt.*;

public class Level01 extends JPanel{
	
	private static final long serialVersionUID = 1L;

	private Spieler spieler;
	
	
	
	public Level01(){									//Konstruktor dieser Klasse
		super();
		spieler = new Spieler();
		this.setSize(800, 800);
		this.setBackground(new Color(255,211,155));		//Hintergrundfarbe ("Boden")
		
	}
	
	@Override
	public void paintComponent(Graphics g){				//Zeichenfunktion des Levels
		super.paintComponent(g);						//Zeichnen des Panels (Superklasse)
		Graphics2D g2d = (Graphics2D) g;				//Graphics2D Objekt konstruieren	
														//Level1 zeichnen
		g2d.setColor(Color.BLACK);						//Wandfarbe
		g2d.fillRect(0, 0, 900, 80);					//Obere Wand
		g2d.fillRect(0, 795, 900, 80);					//Untere Wand
		g2d.fillRect(820, 0, 80, 900);					//Rechte Wand
		g2d.fillRect(0, 0, 80, 700);					//Linke Wand
		g2d.fillRect(550, 200, 80, 600);				//"Labyrinth" 
		g2d.fillRect(0, 620, 450, 80);
		g2d.fillRect(200, 200, 400, 80);
		
		g2d.drawImage(spieler.getImage(), spieler.getx(), spieler.gety(), this); // Spielfigur zeichnen
		
	}
}
