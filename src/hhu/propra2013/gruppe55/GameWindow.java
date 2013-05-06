package hhu.propra2013.gruppe55;

import java.awt.event.*;	//Imports

import javax.swing.*;

public class GameWindow extends JFrame /*implements ActionListener, KeyListener*/{	//Key&ActionListener einbinden
// attributes
	// frame properties
	private String gameTitle	=	"SuperAwesomeGameYeah";	// TODO think of interesting title, though this is an interesting title indeed
	private int width	=	13*32;	// width in pixels
	private int height	=	10*32-5;	// height in pixels
	// eclipse thinks this is necessary
	private static final long serialVersionUID = 1L;
	// level management
	private Level[] levels;			// Array that contains our levels for quick switching
	private int maxLevels	=	1;	// number of levels in total
	private int actLevel;			// index pointer to the actual level
	//private Level01 lvl01; 														//1. Level deklarieren

// constructor
	public GameWindow(){
		// set title, call super constructor of JFrame
		super();	// why the hell does super(gameTitle); not work? =(
		setTitle(gameTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// load levels
		levels	=	new Level[maxLevels];
		for(int i=0; i<maxLevels; i++)
			levels[i]	=	new Level(i);
		
		// set pointer to first level
		actLevel	=	0;/**/
		
		// add first level
		add(levels[actLevel]);
		
		// set frame properties
		//this.addKeyListener(this);												//KeyListener adden
		//lvl01 = new Level01();													//1. Level konstruieren & adden
		//this.getContentPane().add(lvl01);
		this.pack();															//Fenstereigenschaften setzen
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);										//Spielfeld zentriert
		//setVisible(true);
	}

	
	/*
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
		else {}																	//Runter gedrückt
		lvl01.repaint();														//Level neuzeichnen
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	*/
}
