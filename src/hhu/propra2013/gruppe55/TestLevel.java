package hhu.propra2013.gruppe55;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class TestLevel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Levelobjekte
	private Player player;				// Spielerobjekt
	private HUD hud;					//HUD
	private int room;					// pointer to current room
	private ArrayList<ArrayList<LivingObject>> creatureList;	// liste der Gegner
	private ArrayList<ArrayList<DungeonObject>> staticList;		// liste der Waende/Gegenstaende/etc
	private ArrayList<ArrayList<Teleporter>> teleportList;		// Liste aller Teleporter 
	// Timer fuer die Aktionen
	private Timer timer;
	// Spieleventvariablen
	private boolean lose, clear;	// wird auf wahr gesetzt, wenn der Spieler stirbt oder das Level erfolgreich abschliesst
	// Wichtige variablen fuer das neu Laden eines Levels
	private int playerSpawnX, playerSpawnY;		// Koordinaten des ersten Spielererscheinungspunkts
	private int centerX, centerY;				// Fenstermittelpunkt
	private GameMenu gm;
	private GameWindow gw;
	
	
// Konstruktor
	public TestLevel(GameMenu gm, GameWindow gw, int x, int y) {		
		// Mittelpunkt des Fensters
		centerX = x;
		centerY = y;
		
		this.gm = gm;
		this.gw = gw;
		
		String line;
		String lineints[];
		int[][][] lvlData = null;
		
		// Parser fuer Leveldateien
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
		
		// Zeiger wird auf den ersten Raum gesetzt
		room	=	0;

		// ArrayLists generieren
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
		
		// Schleife die das Level generiert
		for(int r=0; r<lvlData.length;r++){
			// Dimension des neuen Raumes in der Arraylist initialisieren
			staticList.add(new ArrayList<DungeonObject>(0));
			creatureList.add(new ArrayList<LivingObject>(0));
			teleportList.add(new ArrayList<Teleporter>(0));
			// Objekte generieren
			for(int i=0;i<=lvlData[0].length-1;i++){
				for(int j=0;j<=lvlData[0][0].length-1;j++){
					if(lvlData[r][i][j] == 1)
						staticList.get(r).add(new WallObject(i*32, j*32));		// bei 1 wird ein Wandobjekt generiert
					else if(lvlData[r][i][j] == 2)
						creatureList.get(r).add(new Creature(i*32+5, j*32-5, 3, 10, 0, 100, 0));		// bei 2 wird ein Monsterobjekt generiert
					else if(lvlData[r][i][j] == 3){
						playerSpawnX	=	i*32-5;
						playerSpawnY	=	j*32-5;
						player	=	new Player(playerSpawnX, playerSpawnY, 5, 25, 0, 100, 100);		// bei 3 wird ein Spielerobjekt generiert
					}
					else if(lvlData[r][i][j] == 5)
						staticList.get(r).add(new TrapObject(i*32, j*32));		// bei 5 wird ein Fallenobjekt generiert
					else if(lvlData[r][i][j] == 4)
						teleportList.get(r).add(new Teleporter(i*32, j*32, 1, 2*32, 0*32));		
					else if(lvlData[r][i][j] == 6)
						staticList.get(r).add(new GoalObject(i*32, j*32));		// bei 6 wird ein Zielobjekt generiert
					else if(lvlData[r][i][j] == 7)
						staticList.get(r).add(new PotionObject(i*32, j*32)); 	// bei 7 wird ein Potionobjekt generiert
				}
			}
		}

		//Konstruiere HUD
		hud = new HUD();
		
		// Eigenschaften des Panels
		setFocusable(true);
		setBackground(new Color(255,211,155));
		setDoubleBuffered(true);
		
		// Hinzufuegen des KeyListener 
		addKeyListener(new KeyControll());
		
		// Aktionstimer wird gesetzt und gestartet
		timer	=	new Timer(1000/60, this);
		timer.start();
	}

// Methoden
	/*
	 * Kollisionsabfrage zwischen den Dungeonobjekten aus staticList und creatureList
	 * bei Spielerkollision wird .onCollision(player) des jeweiligen Listenelements aufgerufen fuer spezielle Kollisionsbehandlung
	 */
	private void collisionCheck(){
		// Ueberpruefen ob der Spieler in einen anderen Raum teleportiert werden soll
		for(int i=0; i<teleportList.get(room).size(); i++)
			// trifft der Spieler auf einen Teleporter
			if(teleportList.get(room).get(i).getBorder().intersects(player.getBorder())){
				// Teleportinformationen abfragen
				int[] portData	=	teleportList.get(room).get(i).getTeleport();
				// Spieler teleportieren
				player.teleport(portData[1], portData[2]);
				// Raumzeiger umsetzen
				room	=	portData[0];
				// Schleife beenden
				break;
			}
				
		
		// staticlist für den Spieler und das Monster ueberpruefen (erst Spieler -> Monster dann Monster -> Spieler)
		for(int i=0; i<staticList.get(room).size(); i++){
			//  ueberpruefe static mit Spieler
			if(staticList.get(room).get(i).getBorder().intersects(player.getBorder())){
				staticList.get(room).get(i).onCollision(player);
			}
			//nun ueberpruefe Wand und Monster sowie Monster und Spieler
			for(int j=0; j<creatureList.get(room).size(); j++){
				// zuerst Wand -> Monster
				if(staticList.get(room).get(i).getBorder().intersects(creatureList.get(room).get(j).getBorder()))
					staticList.get(room).get(i).onCollision(creatureList.get(room).get(j));
				// dann Monster -> Spieler
				if(creatureList.get(room).get(j).getBorder().intersects(player.getBorder()))
					creatureList.get(room).get(j).onCollision(player);
				// Ende der Kollisionsabfrage
			}
		}
		
		// Spielerangriff
		if(player.getAttackState() && player.getWeapSet() == 0)
			for(int i=0; i<creatureList.get(room).size(); i++){
				// Monsterkollision mit der Waffe
				if(creatureList.get(room).get(i).getBorder().intersects(player.weapons[0].getBorder())){
					creatureList.get(room).get(i).getHit();
				}
			}
		
	}
	
	//Methode um das Level neu zu laden und das Spiel von vorne zu beginnen
	public void reload(){
		// den Spieler wiederbeleben
		player.revive();
		player.teleport(playerSpawnX, playerSpawnY);
		player.goal = false;
		// Raumzeiger wieder auf den ersten Raum setzen
		room	=	0;
		//Fallen zuruecksetzen
		for(int r=0; r<staticList.size(); r++){
			for(int i=0; i<staticList.get(room).size(); i++)
				staticList.get(room).get(i).switchState(0);
		}
		// Endebedingungen auf false setzen
		lose	=	false;
		clear = false;
	}
	
	/*
	 * Zeichenmethode fuer das Level
	 */
	public void paint(Graphics g){
		// aufruf urspruenglicher Funktion
		super.paint(g);
		// wir arbeiten mit Java2d
		Graphics2D g2d = (Graphics2D)g;
		
		//Offset fuer den Bildlauf vorberechnen
		int offsetX = player.getX()-centerX;
		int offsetY = player.getY()-centerY;
		
		//staticList.get(room).get(i).getImg()
		
		// alle objekte der staticlist zeichnen (Waende, Fallen,...)
		for(int i=0; i<staticList.get(room).size(); i++)
				staticList.get(room).get(i).draw(g2d, offsetX, offsetY);
		// Monster zeichnen
		for(int i=0; i<creatureList.get(room).size(); i++)
			creatureList.get(room).get(i).draw(g2d, offsetX, offsetY);
		// Spieler zeichnen
		player.draw(g2d, centerX, centerY);
		
		// HUD zeichnen
		hud.draw(g2d, player.getHP(), player.getHPMax(), player.getEnergy(), player.getEnergyMax(), player.getMana(), player.getManaMax());
		
		// Gameover / Win Bildschirm zeichnen
		if(lose)
			g2d.drawImage(Data.gameover, 32*10, 32*10, this);
		else if(clear)
			g2d.drawImage(Data.win, 32*10, 32*10, this);
		
        Toolkit.getDefaultToolkit().sync();/**/
        g.dispose();
	}

	@Override
	/*
	 * Wird vom timer aufgerufen. Laesst moegliche Bewegungen berechnen, ruft die Kollisionsabfrage auf und zeichnet das Feld neu
	 */
	public void actionPerformed(ActionEvent e) {
		// ueberpruefen ob der Spieler lebt
		if(player.getHP()<=0)
			lose	=	true;	// wird gesetzt wenn der Spieler stirbt
		if(player.getgoal() == true)
			clear = true;		// wird gesetzt wenn der Spieler das Level erfolgreich abschliesst 
		
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
	 * Kontrolliert das KeyEventHandling des Levels
	 * Der Keylistener innerhalb der Playerklasse waere eine Option fuer Funktionen, wie "Press 'p' for pause etc)
	 */
	private class KeyControll implements KeyListener{
	
		@Override
		public void keyPressed(KeyEvent e) {
			int k	=	e.getKeyCode();
			// Bewegungsbefehle an Spieler weiter leiten
			if(!lose && (k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT))
				player.keyPressed(e);
			// Enter-Taste abfragen
			if(k == KeyEvent.VK_ENTER)
				// Option: Bei Sieg oder Niederlage -> zurueck zum Menue
				if(lose || clear){
					gw.setVisible(false);
					gm.setVisible(true);
					reload();
				}
			// Space-Taste abfragen
			if(k == KeyEvent.VK_SPACE)
				// Option: Bei Sieg oder Niederlage -> erneut beginnen
				if(lose || clear){
					reload();
				}
				else{	// let's fetz
					// Spieler Angreifen lassen
					player.attack();
				}
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
