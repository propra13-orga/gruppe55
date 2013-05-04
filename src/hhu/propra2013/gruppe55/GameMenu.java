package hhu.propra2013.gruppe55;

import javax.swing.*;	//Imports
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JFrame implements ActionListener{	//ActionListener implementiert

	private static final long serialVersionUID = 1L;

	static GameWindow gw;										//Deklarationen
	JButton start, ende;
	
	public static void main(String[] args) {
		GameMenu gm = new GameMenu();							//Menü und Spielfenster konstruieren
		gw = new GameWindow();
	}
	
	public GameMenu(){											//Konstruktor dieser Klasse
		super("Spielmenü");										//Aufruf des JFrame-Konstruktors
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));					//1,2 GridLayout
		
		start = new JButton("Spiel starten");					//Jbuttons konstruieren
			start.addActionListener(this);
		ende = new JButton("Spiel beenden");
			ende.addActionListener(this);
			
		this.getContentPane().add(start);						//JButtons adden
		this.getContentPane().add(ende);
		this.pack();											//Fenstereigenschaften setzen
		this.setSize(200,50);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {				//ActionListenerfunktion
		if(e.getSource() == start){								//Wenn start gedrückt, Menü unsichtbar machen & Spiel sichtbar machen
			this.setVisible(false);
			gw.setVisible(true);}
		else{System.exit(1);}									//Wenn ende gedrückt, Programm beenden
	}

}
