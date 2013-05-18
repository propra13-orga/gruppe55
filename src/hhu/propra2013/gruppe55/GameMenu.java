package hhu.propra2013.gruppe55;

import javax.swing.*;	//Imports
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JFrame implements ActionListener{	// ActionListener implementiert

	private static final long serialVersionUID = 1L;
	//Deklarationen
	static GameWindow gw;										// Spielfenster
	private JPanel jp;											// JPanel
	private JComboBox cb;										// Combobox im Jpanel
	private JButton start, ende;								
	
	public static void main(String[] args) {
		GameMenu gm = new GameMenu();							// Menue und Spielfenster konstruieren
		gw = new GameWindow(gm);
	}
	// Erstellen des Spielmenues
	public GameMenu(){											// Konstruktor dieser Klasse
		super("Spielmenue");									// Aufruf des JFrame-Konstruktors
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));					// 1,2 GridLayout
		
		jp = new JPanel();										
			jp.setLayout(new GridLayout(2,1));
			start = new JButton("Spiel starten");				// Jbuttons konstruieren
				start.addActionListener(this);
			cb = new JComboBox();
				cb.addItem("Level01");
				cb.addItem("ScrollingLevel");
				cb.setSelectedIndex(0);
			jp.add(cb);
			jp.add(start);
		ende = new JButton("Spiel beenden");
			ende.addActionListener(this);
			
		this.getContentPane().add(jp);						// JButtons adden
		this.getContentPane().add(ende);
		this.pack();										// Fenstereigenschaften setzen
		this.setSize(400,200);								// Groesse gesetzt
		this.setResizable(false);							// Groesse nicht veraenderbar
		this.setVisible(true);								// sichtbarkeit 
		this.setLocationRelativeTo(null); 					// im Bildschirm zentriert
	}

	@Override
	public void actionPerformed(ActionEvent e) {			//ActionListenerfunktion
		if(e.getSource() == start){							//Wenn start gedrueckt, Menue unsichtbar machen & Spiel sichtbar machen
			gw.setLvl(cb.getSelectedIndex());
			this.setVisible(false);
			gw.setVisible(true);}
		else{System.exit(1);}								//Wenn ende gedrueckt, Programm beenden
	}

}
