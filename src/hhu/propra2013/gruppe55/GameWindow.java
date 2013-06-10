package hhu.propra2013.gruppe55;

import java.awt.Toolkit;
import java.awt.event.*;	//Imports

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener{	//Key&ActionListener einbinden
	private static final long serialVersionUID = 1L;
	
	// Frameeigenschaften - 16:9
	// TO DO: Genaue Pixelberechnung ohne Rand - derzeit ~16:9
	private int width	=	Toolkit.getDefaultToolkit().getScreenSize().width;		// Breite in Pixeln 13*32
	private int height	=	Toolkit.getDefaultToolkit().getScreenSize().height;	    // Hoehe in Pixeln 10*32-5
	boolean fullscreen;														// Ob fullscreen oder nicht
	// level management
	private Level[] levels;			// Array in dem die Level gespeichert werden
	private Level testlvl;			// Neues TestLevel
	private int maxLevels	=	1;		// Gesamtanzahl der Level
	private int curLvl;					// Zeiger auf das aktuelle Level
	private GameMenu gm;

// Konstruktor
	public GameWindow(GameMenu gm){
		super("SuperAwesomeGameYeah");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		
		this.gm = gm;	//Hauptmenue �bergeben
		
		testlvl = new Level(gm, this, (width/2)-26, (height/2)-25);
		
		// Eigenschaften des Frames setzen
		// this.addKeyListener(this);											// KeyListener adden
		this.setSize(width, height);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);										// Spielfeld zentriert
	}

	public void setLvl(int lvl){		
		this.getContentPane().add(testlvl);
	}
	
	//Fullscreen einstellen (nur ScrollingLvl)
	public void toggleFullscreen(int i){
		if(i == 0){
			this.dispose();
			width = 966;
			height = 650;
			fullscreen = false;
			this.setSize(width, height);
			this.setUndecorated(false);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
		else if(i == 1){
			this.dispose();
			width = Toolkit.getDefaultToolkit().getScreenSize().width;
			height = Toolkit.getDefaultToolkit().getScreenSize().height;
			fullscreen = true;
			this.setSize(width, height);
			this.setUndecorated(true);
			this.setLocation(0, 0);
			this.setVisible(true);
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
		else if(e.getKeyCode() == KeyEvent.VK_UP){}								// Hoch gedrueckt																// Runter gedrueckt													//Level neuzeichnen
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
