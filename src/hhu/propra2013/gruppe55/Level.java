package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Level extends JPanel implements ActionListener {

	// Levelobjekte
	private Player player;				// Spielerobjekt
	private int room;					// pointer to current room
	private ArrayList<ArrayList<LivingObject>> creatureList;	// liste der Gegner
	private ArrayList<ArrayList<DungeonObject>> staticList;	// liste der Waende/Gegenstaende/etc
	private ArrayList<ArrayList<Teleporter>> teleportList;	// list of all teleports (for a couple of reasons not in staticList)
	// actions timer
	private Timer timer;
	// variables for important game events
	private boolean loose	=	false;	// true on player dead
	private boolean clear	=	false;	// true if player cleared the level
	private String youwinPath = "img/youwin.png";
	private String gameoverPath	=	"img/gameover.png";	// image to show on player death
	private Image gameoverImg	=	(new ImageIcon(gameoverPath)).getImage();	// the preloaded image
	private Image youwinImg	=	(new ImageIcon(youwinPath)).getImage();
	// variables important in case of reload
	private int playerSpawnX, playerSpawnY;		// coordinates of player's first appearance
	
	
	
// constructor
	public Level(int lvlNum) {
		// TODO Konstruktor: Hier dann irgendwie spaeter mal ne syntax, die das level aus ner datei l�d
		
		// set pointer to first room
		room	=	0;
		// vorerst fixes test level
			//1: Wall
			//2: Creature
			//3: Player
			//4: Teleporter
			//5: Falle
			//6: Ziel
		int[][][] levelData = { /* 30*16 Grid */
			{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1},
			 {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1,1},
			 {1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,1,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,1},
			 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,3,0,0,1,0,0,1},
			 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,5,0,0,1},
			 {4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
			 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			},
			{
			 {1,1,4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,5,0,5,0,5,0,5,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,1},
			 {1,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,1},
			 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,1,0,5,0,5,0,5,0,1},
			 {1,0,0,0,0,0,0,5,0,0,0,0,0,0,5,0,0,1,0,0,1,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,5,0,0,0,0,0,0,5,0,0,1,0,0,1,1,2,0,0,0,0,0,0,1},
			 {1,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,0,1,0,0,1,1,5,0,5,0,5,0,5,1},
			 {1,0,0,5,0,0,0,0,0,5,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,1},
			 {1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0},
			 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			},
			{
			 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
	    	 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,5,0,0,0,0,0,0,0,1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,5,0,0,0,0,0,0,0,0,1,0,0,0,0,2,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,5,0,0,0,5,0,0,0,0,0,1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,1},
			 {1,0,0,5,0,0,0,5,0,0,0,0,0,0,1,0,0,0,0,2,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,5,0,0,0,5,5,5,5,1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,5,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1},
			 {1,0,0,0,5,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,5,5,5,5,5,5,5,5,5,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,5,0,0,0,0,0,0,0,0,0,0,0,1,0,6,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,5,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			}};
		
		// generate ArrayLists
		staticList		=	new ArrayList<ArrayList<DungeonObject>>(0);
		creatureList	=	new ArrayList<ArrayList<LivingObject>>(0);
		teleportList	=	new ArrayList<ArrayList<Teleporter>>(0);
		
		
		// loop that generates the level
		for(int r=0; r<levelData.length;r++){
			// initialize new room dimension in the array lists
			staticList.add(new ArrayList<DungeonObject>(0));
			creatureList.add(new ArrayList<LivingObject>(0));
			// create the objects
			for(int i=0;i<=levelData[0].length-1;i++){
				for(int j=0;j<=levelData[0][0].length-1;j++){
					if(levelData[r][i][j] == 1)
						staticList.get(r).add(new WallObject(j*32, i*32));
					else if(levelData[r][i][j] == 2)
						creatureList.get(r).add(new Creature(j*32+5, i*32-5, 100, 25, 0, 100, 0));
					else if(levelData[r][i][j] == 3){
						playerSpawnX	=	j*32-5;
						playerSpawnY	=	i*32-5;
						player	=	new Player(playerSpawnX, playerSpawnY, 100, 25, 0, 100, 0);
					}
					else if(levelData[r][i][j] == 5)
						staticList.get(r).add(new TrapObject(j*32, i*32));
					else if(levelData[r][i][j] == 6)
						staticList.get(r).add(new GoalObject(j*32, i*32)); //
				}
			}
		}
		// that's a loop, that loops a loops looping loop. yo dawg, i heard u like loops...
		
		// setting up the teleporters manually
		teleportList.add(new ArrayList<Teleporter>(0));	// room 0
		teleportList.add(new ArrayList<Teleporter>(0));	// room 1
		teleportList.add(new ArrayList<Teleporter>(0));	// room 2
		// ports 0 -> 1
		teleportList.get(0).add(new Teleporter(32*-1, 32*13, 1, 29*32, 13*32));
		teleportList.get(0).add(new Teleporter(32*-1, 32*14, 1, 29*32, 14*32));
		// ports 1 -> 0
		teleportList.get(1).add(new Teleporter(30*32, 13*32, 0, 0*32, 13*32));
		teleportList.get(1).add(new Teleporter(30*32, 14*32, 0, 0*32, 14*32));
		// ports 1 -> 2
		teleportList.get(1).add(new Teleporter(2*32, -1*32, 2, 2*32, 15*32));
		teleportList.get(1).add(new Teleporter(3*32, -1*32, 2, 3*32, 15*32));
		// ports 2 -> 1
		teleportList.get(2).add(new Teleporter(2*32, 16*32, 1, 2*32, 0*32));
		teleportList.get(2).add(new Teleporter(3*32, 16*32, 1, 3*32, 0*32));
		
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
		// first of all check if player needs a teleport
		for(int i=0; i<teleportList.get(room).size(); i++)
			// if player meets teleport
			if(teleportList.get(room).get(i).getBorder().intersects(player.getBorder())){
				// get port details
				int[] portData	=	teleportList.get(room).get(i).getTeleport();
				// teleport player
				player.teleport(portData[1], portData[2]);
				// set new room
				room	=	portData[0];
				// ensure this loop ends (no more loop needed, but could f**k up what we want
				break;
			}
				
		
		// check staticList for player and creatures and creatures to player
		for(int i=0; i<staticList.get(room).size(); i++){
			// check static first with player
			if(staticList.get(room).get(i).getBorder().intersects(player.getBorder()))
				staticList.get(room).get(i).onCollision(player);
			// now check wall with creatures an creatures with player
			for(int j=0; j<creatureList.get(room).size(); j++){
				// first wall to creatures
				if(staticList.get(room).get(i).getBorder().intersects(creatureList.get(room).get(j).getBorder()))
					staticList.get(room).get(i).onCollision(creatureList.get(room).get(j));
				// second creatures to player
				if(creatureList.get(room).get(j).getBorder().intersects(player.getBorder()))
					creatureList.get(room).get(j).onCollision(player);
				// done, what a loop!
			}
		}
		
	}
	
	// method to reload the level and to begin it from start
	public void reload(){
		// revive player
		player.revive();
		player.teleport(playerSpawnX, playerSpawnY);
		// set room to first room
		room	=	0;
		// of course set lose=false
		loose	=	false;
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
		for(int i=0; i<staticList.get(room).size(); i++)
			g2d.drawImage(staticList.get(room).get(i).getImg(), staticList.get(room).get(i).getX(), staticList.get(room).get(i).getY(), this);
		// paint creatures
		for(int i=0; i<creatureList.get(room).size(); i++)
			g2d.drawImage(creatureList.get(room).get(i).getImg(), creatureList.get(room).get(i).getX(), creatureList.get(room).get(i).getY(), this);
		
		// Spieler zeichnen
		g2d.drawImage(player.getImg(), player.getX(), player.getY(), this);
		
		// draw game over screen on demand
		if(loose)
			g2d.drawImage(gameoverImg, 32*10, 32*10, this);
		if(clear)
			g2d.drawImage(youwinImg, 32*10, 32*10, this);
		
		// blubb
        Toolkit.getDefaultToolkit().sync();/**/
        g.dispose();
	}

	@Override
	/*
	 * Wird vom timer aufgerufen. Laesst moegliche Bewegungen berechnen, ruft die Kollisionsabfrage auf und zeichnet das Feld neu
	 */
	public void actionPerformed(ActionEvent e) {
		// check if player is still alive
		if(player.getHP()<=0)
			loose	=	true;	// he did not deserve any better...
		if(player.getgoal()>1)
			clear = true;		// yay! 
			
		// Spielerbewegung
		player.move();
		
		// kreaturenbewegung
		for(int i=0; i<creatureList.get(room).size(); i++)
			creatureList.get(room).get(i).move();
		
		// Kollisionsabfrage
		collisionCheck();
		
		// neuzeichnen
		repaint();
	}
	
	
// KEY LISTENER UNIT
	/*
	 * Controlls the KeyEventHandling of the Level
	 * player KeyListener would be an option, but in case of extra functions (press 'p' for pause, 'm' for menu) this helps a lot
	 */
	private class KeyControll implements KeyListener{
	
		@Override
		public void keyPressed(KeyEvent e) {
			int k	=	e.getKeyCode();
			// Bewegungsbefehle an Spieler weiter leiten
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT)
				player.keyPressed(e);
			// Space-Taste abfragen
			if(k == KeyEvent.VK_SPACE)
				// on player death
				if(loose)
					reload();
				// on win
				else if(clear)
					System.exit(1);
				// TODO: player attack						
		}
		@Override
		public void keyReleased(KeyEvent e) {
			int k	=	e.getKeyCode();
			
			// Bewegungsbefehle an Spieler weiter leiten
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT)
				player.keyReleased(e);
		}
		@Override
		public void keyTyped(KeyEvent e) {}
	}

}
