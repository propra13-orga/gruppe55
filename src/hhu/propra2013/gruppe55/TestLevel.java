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

import java.io.*;

public class TestLevel extends JPanel implements ActionListener {

	// Levelobjekte
	private Player player;				// Spielerobjekt
	private HUD hud;					//HUD
	private int room;					// pointer to current room
	private ArrayList<ArrayList<LivingObject>> creatureList;	// liste der Gegner
	private ArrayList<ArrayList<DungeonObject>> staticList;	// liste der Waende/Gegenstaende/etc
	private ArrayList<ArrayList<Teleporter>> teleportList;	// list of all teleports (for a couple of reasons not in staticList)
	// actions timer
	private Timer timer;
	// variables for important game events
	private boolean lose	=	false;	// true on player dead
	private boolean clear	=	false;	// true if player cleared the level
	private String gameoverPath	=	"img/gameover.png";	// image to show on player death
	private String youwinPath = "img/youwin.png"; // image to show on game win
	private Image gameoverImg	=	(new ImageIcon(gameoverPath)).getImage();	// the preloaded image
	private Image youwinImg	=	(new ImageIcon(youwinPath)).getImage();  //s.o.
	// variables important in case of reload
	private int playerSpawnX, playerSpawnY;		// coordinates of player's first appearance
	private int centerX, centerY;				//Fenstermittelpunkt
	
	private Runtime rt;
	private Image wall = (new ImageIcon("img/wall.png")).getImage();
	
	
// constructor
	public TestLevel(int x, int y) {
		// TODO Konstruktor: Hier dann irgendwie spaeter mal ne syntax, die das level aus ner datei l�d
		// Mittelpunkt des Fensters
		centerX = x;
		centerY = y;
		
		rt = Runtime.getRuntime();
		
		String line;
		String lineints[];
		int[][][] lvlData = null;
		
		// Parser für Leveldateien
		try {
			//.txt einlesen
			FileReader fread = new FileReader("lvl/testlvl.txt");
			BufferedReader in = new BufferedReader(fread);
			
			for(int i=0; (line = in.readLine()) != null; i++){
				if(i==0){
					// Mit 1. Line Array initialisieren
					String w[] = new String[3];
					w = line.split(",");
					lvlData = new int[Integer.parseInt(w[0])][Integer.parseInt(w[1])][Integer.parseInt(w[2])];
				}
				else{
					// Lines mit Strings in LevelData-Array als int konvertieren
					lineints = (String[])line.split(",");
					for(int j=0; j<=lineints.length-1; j++){
							lvlData[0][j][i] = Integer.parseInt(lineints[j]);
					}
				}
			}
			in.close();
		} catch (IOException e) {e.printStackTrace();}
		
		// set pointer to first room
		room	=	0;

		// generate ArrayLists
		staticList		=	new ArrayList<ArrayList<DungeonObject>>(0);
		creatureList	=	new ArrayList<ArrayList<LivingObject>>(0);
		teleportList	=	new ArrayList<ArrayList<Teleporter>>(0);
				
		//1: Wall
		//2: Creature
		//3: Player
		//4: Teleporter
		//5: Falle
		//6: Ziel
		//7: Potion
		// loop that generates the level
		for(int r=0; r<lvlData.length;r++){
			// initialize new room dimension in the array lists
			staticList.add(new ArrayList<DungeonObject>(0));
			creatureList.add(new ArrayList<LivingObject>(0));
			teleportList.add(new ArrayList<Teleporter>(0));
			// create the objects
			for(int i=0;i<=lvlData[0].length-1;i++){
				for(int j=0;j<=lvlData[0][0].length-1;j++){
					if(lvlData[r][i][j] == 1)
						staticList.get(r).add(new WallObject(i*32, j*32));
					else if(lvlData[r][i][j] == 2)
						creatureList.get(r).add(new Creature(i*32+5, j*32-5, 100, 10, 0, 100, 0));
					else if(lvlData[r][i][j] == 3){
						playerSpawnX	=	i*32-5;
						playerSpawnY	=	j*32-5;
						player	=	new Player(playerSpawnX, playerSpawnY, 100, 25, 0, 100, 100);
					}
					else if(lvlData[r][i][j] == 5)
						staticList.get(r).add(new TrapObject(i*32, j*32));
					else if(lvlData[r][i][j] == 5)
						teleportList.get(r).add(new Teleporter(i*32, j*32, 1, 2*32, 0*32));
					else if(lvlData[r][i][j] == 6)
						staticList.get(r).add(new GoalObject(i*32, j*32));
					else if(lvlData[r][i][j] == 7)
						staticList.get(r).add(new PotionObject(i*32, j*32)); 
				}
			}
		}
		// that's a loop, that loops a loops looping loop. yo dawg, i heard u like loops...

		//Konstruiere HUD
		hud = new HUD();
		
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
			if(staticList.get(room).get(i).getBorder().intersects(player.getBorder())){
				staticList.get(room).get(i).onCollision(player);
			}
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
		//Fallen zurücksetzen
		for(int r=0; r<staticList.size(); r++){
			for(int i=0; i<staticList.get(room).size(); i++)
				staticList.get(room).get(i).switchState(0);
		}
		// of course set lose=false
		lose	=	false;
	}
	
	/*
	 * Zeichenmethode f�r das Level
	 */
	public void paint(Graphics g){
		// aufruf urspr�nglicher Funktion
		super.paint(g);
		// wir arbeiten mit Java2d
		Graphics2D g2d = (Graphics2D)g;
		
		//Offset fü den Bildlauf vorberechnen
		int offsetX = player.getX()-centerX;
		int offsetY = player.getY()-centerY;
		
		//staticList.get(room).get(i).getImg()
		
		// paint static objects
		for(int i=0; i<staticList.get(room).size(); i++)
			g2d.drawImage(wall, staticList.get(room).get(i).getX()-offsetX, staticList.get(room).get(i).getY()-offsetY, this);
		// paint creatures
		for(int i=0; i<creatureList.get(room).size(); i++)
			creatureList.get(room).get(i).draw(g2d, creatureList.get(room).get(i).getX()-offsetX, creatureList.get(room).get(i).getY()-offsetY);
		// Spieler zeichnen
		player.draw(g2d, centerX, centerY);
		
		//Draw HUD
		hud.draw(g2d, player.getHP(), player.getHPMax(), player.getAusd(), player.getAusdMax(), player.getMana(), player.getManaMax());
		
		// draw game over/win screen on demand
		if(lose)
			g2d.drawImage(gameoverImg, 32*10, 32*10, this);
		else if(clear)
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
			lose	=	true;	// he did not deserve any better...
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
		System.out.println("T: " + rt.maxMemory() + " - U:" + (rt.maxMemory()-rt.freeMemory()) + " - F: " + rt.freeMemory());
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
			if(!lose && (k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT))
				player.keyPressed(e);
			
			// Space-Taste abfragen
			if(k == KeyEvent.VK_SPACE)
				// on player death
				if(lose)
					reload();
				else if(clear){
					System.exit(1);
				}
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
