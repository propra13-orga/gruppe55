package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Level extends JPanel implements ActionListener {

	// Levelobjekte
	private Player player;							// Spielerobjekt
	private ArrayList<Creature> creatureList;		// liste der Gegner
	private ArrayList<DungeonObject> staticList;	// liste der Waende/Gegenstaende/etc
	// actions timer
	private Timer timer;
	
// constructor
	public Level(int lvlNum) {
		// TODO Konstruktor: Hier dann irgendwie sp�ter mal ne syntax, die das level aus ner datei l�d
		// vorerst fixes test level
		
		// Spieler initialisieren
		player	=	new Player(290, 220);
		
		// TODO Konstruktor: staticList und creatureList generieren

		// generate staticList
		staticList	=	new ArrayList<DungeonObject>(1);
		staticList.add(new WallObject(0, 0));
		staticList.add(new WallObject(32, 0));
		staticList.add(new WallObject(64, 0));
		staticList.add(new WallObject(96, 0));
		staticList.add(new WallObject(128, 0));
		staticList.add(new WallObject(160, 0));
		staticList.add(new WallObject(192, 0));
		staticList.add(new WallObject(224, 0));
		staticList.add(new WallObject(256, 0));
		staticList.add(new WallObject(288, 0));
		staticList.add(new WallObject(320, 0));
		staticList.add(new WallObject(352, 0));
		staticList.add(new WallObject(384, 0));
		staticList.add(new WallObject(0, 32));
		staticList.add(new WallObject(0, 64));
		staticList.add(new WallObject(64, 64));
		staticList.add(new WallObject(96, 64));
		staticList.add(new WallObject(128, 64));
		staticList.add(new WallObject(160, 64));
		staticList.add(new WallObject(192, 64));
		staticList.add(new WallObject(224, 64));
		staticList.add(new WallObject(224, 96));
		staticList.add(new WallObject(224, 128));
		staticList.add(new WallObject(224, 160));
		staticList.add(new WallObject(224, 192));
		staticList.add(new WallObject(224, 224));
		staticList.add(new WallObject(0, 96));
		staticList.add(new WallObject(0, 128));
		staticList.add(new WallObject(0, 160));
		staticList.add(new WallObject(0, 192));
		staticList.add(new WallObject(32, 192));
		staticList.add(new WallObject(64, 192));
		staticList.add(new WallObject(96, 192));
		staticList.add(new WallObject(128, 192));
		staticList.add(new WallObject(160, 192));
		staticList.add(new WallObject(0, 256));
		staticList.add(new WallObject(32, 256));
		staticList.add(new WallObject(64, 256));
		staticList.add(new WallObject(96, 256));
		staticList.add(new WallObject(128, 256));
		staticList.add(new WallObject(160, 256));
		staticList.add(new WallObject(192, 256));
		staticList.add(new WallObject(224, 256));
		staticList.add(new WallObject(256, 256));
		staticList.add(new WallObject(288, 256));
		staticList.add(new WallObject(320, 256));
		staticList.add(new WallObject(352, 256));
		staticList.add(new WallObject(384, 256));
		staticList.add(new WallObject(384, 32));
		staticList.add(new WallObject(384, 64));
		staticList.add(new WallObject(384, 96));
		staticList.add(new WallObject(384, 128));
		staticList.add(new WallObject(384, 160));
		staticList.add(new WallObject(384, 192));
		staticList.add(new WallObject(384, 224));
		staticList.add(new WallObject(384, 256));
		// generate creatureList
		creatureList	=	new ArrayList<Creature>(1);
		creatureList.add(new Creature(35, 150));
		
		// panel properties
		setFocusable(true);
		setBackground(new Color(255,211,155));
		setDoubleBuffered(true);
		
		// add KeyListener 
		addKeyListener(new KeyControll());
		
		// start timer
		timer	=	new Timer(5, this);
		timer.start();
	}

// methods	
	/*
	 * Kollisionsabfrage zwischen den Dungeonobjekten aus staticList und creatureList
	 * bei Spielerkollision wird .onCollision(player) des jeweiligen Listenelements aufgerufen f�r spezielle Kollisionsbehandlung
	 */
	private void collisionCheck(){
		// check staticList for player and creatures and creatures to player
		for(int i=0; i<staticList.size(); i++){
			// check static first with player
			if(staticList.get(i).getBorder().intersects(player.getBorder()))
				staticList.get(i).onCollision(player);
			// now check wall with creatures an creatures with player
			for(int j=0; j<creatureList.size(); j++){
				// first wall to creatures
				if(staticList.get(i).getBorder().intersects(creatureList.get(j).getBorder()))
					staticList.get(i).onCollision(creatureList.get(j));
				// second creatures to player
				if(creatureList.get(j).getBorder().intersects(player.getBorder()))
					creatureList.get(j).onCollision(player);
				// done, what a loop!
			}
		}
		
	}
	
	/*
	 * Zeichenmethode f�r das Level
	 */
	public void paint(Graphics g){
		// aufruf urspr�nglicher Funktion
		super.paint(g);
		// wir arbeiten mit Java2d
		Graphics2D g2d = (Graphics2D)g;
		
		// paint static objects
		for(int i=0; i<staticList.size(); i++)
			g2d.drawImage(staticList.get(i).getImg(), staticList.get(i).getX(), staticList.get(i).getY(), this);
		// paint creatures
		for(int i=0; i<creatureList.size(); i++)
			g2d.drawImage(creatureList.get(i).getImg(), creatureList.get(i).getX(), creatureList.get(i).getY(), this);
		
		// Spieler zeichnen
		g2d.drawImage(player.getImg(), player.getX(), player.getY(), this);
		
		// blubb
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Wird vom timer aufgerufen. L�sst m�gliche bewegungen berechnen, ruft die Kollisionsabfrage auf und zeichnet das Feld neu
	 */
	public void actionPerformed(ActionEvent e) {
		// Spielerbewegung
		player.move();
		
		// kreaturenbewegung
		for(int i=0; i<creatureList.size(); i++)
			creatureList.get(i).move();
		
		// Kollisionsabfrage
		collisionCheck();
		
		// neuzeichnen
		repaint();
	}
	
	
// KEY LISTENER UNIT
	/*
	 * Controlls the KeyEventHandling of the Level
	 * player KeyListener would be an option, but in case of extra funktions (press 'p' for pause, 'm' for menu) this helps a lot
	 */
	private class KeyControll implements KeyListener{
	
		@Override
		public void keyPressed(KeyEvent e) {
			int k	=	e.getKeyCode();
			// Bewegungsbefehle an Spieler weiter leiten
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT)
				player.keyPressed(e);
				
		}
		@Override
		public void keyReleased(KeyEvent e) {
			int k	=	e.getKeyCode();
			
			// Bewegungsbefehle an Spieler weiter leiten
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT)
				player.keyReleased(e);
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}

}
