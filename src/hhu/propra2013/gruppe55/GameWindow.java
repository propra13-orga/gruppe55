package hhu.propra2013.gruppe55;

import java.awt.event.*;	//Imports

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener{	//Key&ActionListener einbinden
	private static final long serialVersionUID = 1L;
	
	// Frameeigenschaften - 16:9
	// TO DO: Genaue Pixelberechnung ohne Rand - derzeit ~16:9
	private int width	=	1920;		// Breite in Pixeln 13*32
	private int height	=	1080;	    // Hoehe in Pixeln 10*32-5
	// level management
	private TestLevel[] levels;			// Array in dem die Level gespeichert werden
	private Level lvl;					// Ur-Level
	private TestLevel testlvl;			// Neues TestLevel
	private int maxLevels	=	1;		// Gesamtanzahl der Level
	private int curLvl;					// Zeiger auf das aktuelle Level
	private GameMenu gm;

// Konstruktor
	public GameWindow(GameMenu gm){
		super("SuperAwesomeGameYeah");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.gm = gm;	//Hauptmenue übergeben
		
		// Bilder initialisieren
		Ressources.init();
		
		testlvl = new TestLevel(gm, this, (width/2)-26, (height/2)-25);
		lvl = new Level(gm, this);
		
		// Eigenschaften des Frames setzen
		// this.addKeyListener(this);											// KeyListener adden
		this.setSize(width, height);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);										// Spielfeld zentriert
	}

	public void setLvl(int lvl){												// Auswahl des richtigen Levels
		if(lvl==0){
			this.getContentPane().remove(testlvl);
			this.getContentPane().add(this.lvl);
		}
		else{
			this.getContentPane().remove(this.lvl);
			this.getContentPane().add(testlvl);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {								// ActionListenerfunktion
		//
	}
	public void keyTyped(KeyEvent e) {}											// KeyListenerfunktionen

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){}								// Rechts gedrueckt
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){}							// Links gedrueckt
		else if(e.getKeyCode() == KeyEvent.VK_UP){}								// Hoch gedrueckt
		else {}																	// Runter gedrueckt													//Level neuzeichnen
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
