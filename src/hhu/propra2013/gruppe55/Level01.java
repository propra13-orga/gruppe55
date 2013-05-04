package hhu.propra2013.gruppe55;

import javax.swing.*;	//Imports
import java.awt.*;

public class Level01 extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public Level01(){									//Konstruktor dieser Klasse
		super();
		this.setSize(800, 800);
	}
	
	@Override
	public void paintComponent(Graphics g){				//Zeichenfunktion des Levels
		super.paintComponent(g);						//Zeichnen des Panels (Superklasse)
		Graphics2D g2d = (Graphics2D) g;				//Graphics2D Objekt konstruieren	
		g2d.drawLine(0, 0, 800, 800);					//Einige Testzeichnungen
		g2d.drawLine(800, 0, 0, 800);
		g2d.drawRect(100, 100, 600, 600);
		g2d.drawRect(200, 200, 400, 400);
		g2d.drawRect(300, 300, 200, 200);
	}
}
