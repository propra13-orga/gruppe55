package hhu.propra2013.gruppe55;

import java.awt.event.*;	//Imports

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener{	//Key&ActionListener einbinden
	private static final long serialVersionUID = 1L;
	
	// frame properties 16:9
	// TO DO: Genaue Pixelberechnung ohne Rand - derzeit ~16:9
	private int width	=	960;		// width in pixels 13*32
	private int height	=	540;	    // height in pixels 10*32-5
	// level management
	private TestLevel[] levels;				// Array that contains our levels for quick switching
	private Level lvl;					// Ur-Level
	private TestLevel testlvl;			// Neues TEstLevel
	private int maxLevels	=	1;		// number of levels in total
	private int curLvl;					// index pointer to the current level
	private GameMenu gm;

// constructor
	public GameWindow(GameMenu gm){
		super("SuperAwesomeGameYeah");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.gm = gm;	//Hauptmenue �bergeben
		
		// init images
		Ressources.init();
		
		testlvl = new TestLevel(gm, this, (width/2)-26, (height/2)-25);
		lvl = new Level(gm, this);
		
		//set frame properties
		//this.addKeyListener(this);											//KeyListener adden
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);										//Spielfeld zentriert
	}

	public void setLvl(int lvl){
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
	public void actionPerformed(ActionEvent e) {								//ActionListenerfunktion
		//
	}
	public void keyTyped(KeyEvent e) {}											//KeyListenerfunktionen

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){}								//Rechts gedrückt
		else if(e.getKeyCode() == KeyEvent.VK_LEFT){}							//Links gedrückt
		else if(e.getKeyCode() == KeyEvent.VK_UP){}								//Hoch gedrückt
		else {}																	//Runter gedrückt													//Level neuzeichnen
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
