package hhu.propra2013.gruppe55;

import java.awt.event.*;	//Imports

import javax.swing.*;

public class GameWindow extends JFrame implements KeyListener, ActionListener{	//Key&ActionListener einbinden
	
	private static final long serialVersionUID = 1L;
	private Level01 lvl01; 														//1. Level deklarieren
	
	public GameWindow(){														//Konstruktor dieser Klasse
		super("Game");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);												//KeyListener adden
		lvl01 = new Level01();													//1. Level konstruieren & adden
		this.getContentPane().add(lvl01);
		this.pack();															//Fenstereigenschaften setzen
		this.setSize(800, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);										//Spielfeld zentriert
	}

	@Override
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

	@Override
	public void actionPerformed(ActionEvent e) {								//ActionListenerfunktion
	}

}
