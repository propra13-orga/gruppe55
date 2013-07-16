package hhu.propra2013.gruppe55_opengl;

import javax.swing.*;	//Imports

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import org.lwjgl.LWJGLException;

import java.awt.*;
import java.awt.event.*;

/**
 * Die Klasse GameMenu.
 * Diese Klasse implementiert das Hauptmenue fuer das Spiel.
 */

public class GameMenu extends JFrame implements ActionListener{	// ActionListener implementiert

	private static final long serialVersionUID = 1L;
	//Deklarationen
	
	/** Das Jpanel. */
	
	private JPanel jp, settings;											// JPanel
	
	/** Die JComboBox im Jpanel. */
	
	private JComboBox cb;										// Combobox im Jpanel
	
	/** Die JButtons im Jpanel. */
	
	private JButton start, ende;								
	
	/** Die JCheckBox im Jpanel. */
	
	private JCheckBox coop;
	
	/** Die ControllerComboBox im JPanel */
	
	private JComboBox controllerBox = new JComboBox();
	
	/** Das JTextField fuer die IP */
	
	private JTextField ip;
	
	/** Netzwerk-Adresse **/
	
	private String adress;
	
	/** Das aktuell zu ladende Level (Single Player oder CoOp. */
	
	private Level lvl;
	
	/**
	 * Die Methode main.
	 * Die Methode, die das Objekt GameMenu erzeugt.
	 * @param args  
	 */
	
	public static void main(String[] args) {
		GameMenu gm = new GameMenu();							// Menue und Spielfenster konstruieren
	}
	
	/**
	 * Der Konstruktor fuer das GameMenu.
	 * Hier werden alle verschiedenen Elemente des Hauptmenues implementiert (Buttons, Checkboxen, etc. ...)
	 */
	
	// Erstellen des Spielmenues
	public GameMenu(){											// Konstruktor dieser Klasse
		super("Spielmenue");									// Aufruf des JFrame-Konstruktors
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2,1));					// 1,2 GridLayout
		
		settings = new JPanel();
			settings.setLayout(new GridLayout(1,3));
			cb = new JComboBox();
//				cb.addItem("TestLevel");
				cb.addItem("Level 1");
				cb.addItem("Level 2");
				cb.addItem("Level 3");
				cb.addItem("Level 4");
				cb.setSelectedIndex(0);
			settings.add(cb);
			coop = new JCheckBox();
				coop.setText("Co-Op");
				coop.setActionCommand("coop");
				coop.addActionListener(this);
				coop.setEnabled(true);
			settings.add(coop);
			ip = new JTextField();
			ip.setEnabled(false);
		settings.add(ip);
		
		jp = new JPanel();										
			jp.setLayout(new GridLayout(3,1));
			start = new JButton("Spiel starten");				// Jbuttons konstruieren
				start.addActionListener(this);
				jp.add(settings);
				controllerBox = controllersBox();
				jp.add(controllerBox);
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
		
		//listet die verschiedenen Controller in einer ComboBox
		private JComboBox controllersBox(){
			Controller[] controller = ControllerEnvironment.getDefaultEnvironment().getControllers();
			
			//Controller controllers = new Controllers();
			JComboBox selectControllerBox = new JComboBox();

				for(int i=0;i<controller.length;i++){
					net.java.games.input.Component[] controllerComponents = controller[i].getComponents();
					if(controller[i].getType() == Controller.Type.GAMEPAD || controller[i].getType() == Controller.Type.STICK ){
						selectControllerBox.addItem(controller[i].getName());	
					}
				}
				selectControllerBox.addItem("Keyboard!");

			return selectControllerBox;
			
		}

	/**
	 * Die Methode actionPerformed.
	 * Diese Methode implementiert die Funktionen, die bei dr�cken der Buttons aufgerufen werden (z.B. Start des Spiels)
	 */
	
	@Override
	public void actionPerformed(ActionEvent e){				//ActionListenerfunktion
		if(e.getSource() == start){							//Wenn start gedrueckt, Menue unsichtbar machen & Spiel sichtbar machen
			this.setVisible(false);
			if(!coop.isSelected()){
				lvl = new Level(960, 650, this, cb.getSelectedIndex()+1, ip.getText(), controllerBox.getSelectedItem().toString());
			}
			else{
				adress = ip.getText();
				lvl = new LevelMP(960, 650, this, cb.getSelectedIndex()+1, ip.getText(), controllerBox.getSelectedItem().toString());
			}
		}
		else if(e.getActionCommand() == "coop"){
			if(coop.isSelected()){
				ip.setEnabled(true);
			}
			else{
				ip.setEnabled(false);
			}
		}
		else{System.exit(1);}								//Wenn ende gedrueckt, Programm beenden
	}
}
