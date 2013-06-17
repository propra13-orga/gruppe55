package hhu.propra2013.gruppe55_opengl;

import javax.swing.*;	//Imports
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JFrame implements ActionListener{	// ActionListener implementiert

	private static final long serialVersionUID = 1L;
	//Deklarationen
	private JPanel jp, settings;											// JPanel
	private JComboBox cb;										// Combobox im Jpanel
	private JButton start, ende;								
	private JCheckBox fullscreen;
	private Level lvl;
	
	public static void main(String[] args) {
		GameMenu gm = new GameMenu();							// Menue und Spielfenster konstruieren
	}
	// Erstellen des Spielmenues
	public GameMenu(){											// Konstruktor dieser Klasse
		super("Spielmenue");									// Aufruf des JFrame-Konstruktors
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));					// 1,2 GridLayout
		
		settings = new JPanel();
			settings.setLayout(new GridLayout(1,2));
			cb = new JComboBox();
				cb.addItem("TestLevel");
				cb.addItem("Level 1");
				cb.addItem("Level 2");
				cb.addItem("Level 3");
				cb.setSelectedIndex(0);
			settings.add(cb);
			fullscreen = new JCheckBox();
				fullscreen.setText("Fullscreen");
				fullscreen.setEnabled(false);
			settings.add(fullscreen);
		
		jp = new JPanel();										
			jp.setLayout(new GridLayout(2,1));
			start = new JButton("Spiel starten");				// Jbuttons konstruieren
				start.addActionListener(this);
			jp.add(settings);
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
	public void actionPerformed(ActionEvent e){				//ActionListenerfunktion
		if(e.getSource() == start){							//Wenn start gedrueckt, Menue unsichtbar machen & Spiel sichtbar machen
			this.setVisible(false);
			lvl = new Level(960, 650, this, cb.getSelectedIndex());
		}
		else{System.exit(1);}								//Wenn ende gedrueckt, Programm beenden
	}
}
