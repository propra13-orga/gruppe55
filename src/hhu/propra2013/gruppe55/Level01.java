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
		g2d.fillRect(0, 0, 800, 50);					//Obere Wand
		g2d.fillRect(0, 725, 800, 50);					//Untere Wand
		g2d.fillRect(745, 0, 50, 800);					//Rechte Wand
		g2d.fillRect(0, 0, 50, 580);					//Linke Wand
		g2d.fillRect(550, 200, 50, 600);				//"Labyrinth" 
		g2d.fillRect(0, 580, 450, 50);
		g2d.fillRect(200, 200, 400, 50);
		
		g2d.drawImage(spieler.getImage(), spieler.getx(), spieler.gety(), this); // Spielfigur zeichnen
		
	}
}
