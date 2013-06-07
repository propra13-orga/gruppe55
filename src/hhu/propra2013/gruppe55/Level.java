package hhu.propra2013.gruppe55;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Level extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// Levelobjekte
	private Player player;				// Spielerobjekt
	private Weapon[] pWeaps;			// Referenzen auf die Waffen des Spielers
	private int room;					// Zeiger auf den aktuellen Raum
	private ArrayList<ArrayList<LivingObject>> creatureList;	// Liste der Gegner
	private ArrayList<ArrayList<DungeonObject>> staticList;	// Liste der Waende/Gegenstaende/etc
	private ArrayList<ArrayList<Teleporter>> teleportList;	// Liste aller Teleporter 
	// Timer fuer die Aktionen
	private Timer timer;
	// Spieleventvariablen
	private boolean lose	=	false;	// wird auf wahr gesetzt, wenn der Spieler stirbt
	private boolean clear	=	false;	// wird auf wahr gesetzt, wenn der Spieler das level erfolgreich beendet
	// Wichtige variablen fuer das neu Laden eines Levels
	private int playerSpawnX, playerSpawnY;		// Koordinaten des ersten Spielererscheinungspunkts
	private GameMenu gm;						// Spielmenu
	private GameWindow gw;						// Spielfenster
	
	
// Konstruktor
	public Level(GameMenu gm, GameWindow gw) {
		this.gm = gm;
		this.gw = gw;
		// Zeiger wird auf den ersten Raum gesetzt
		room	=	0;
		
		// Das Level aufgeteilt in 3 Raeume fuer den ersten Meilenstein:
			//1: erschafft eine Wand
			//2: erschafft ein Monster
			//3: erschafft den Spieler
			//4: erschafft einen Teleporter
			//5: erschafft eine Falle
			//6: erschafft das Ziel
		
		int[][][] levelData = { 
			{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		// 30*16 Grid - Raum 1
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,5,0,0,5,0,0,5,0,0,5,0,0,5,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,5,0,0,0,0,0,1},
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
			 {1,1,4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		// 30*16 Grid - Raum 2
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1},
			 {1,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,1,0,0,1,1,5,0,5,0,5,0,5,1},
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
			 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		// 30*16 Grid - Raum 3
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
		
		// ArrayLists generieren
		staticList		=	new ArrayList<ArrayList<DungeonObject>>(0);
		creatureList	=	new ArrayList<ArrayList<LivingObject>>(0);
		teleportList	=	new ArrayList<ArrayList<Teleporter>>(0);
		
		
		// Schleife die das Level generiert
		for(int r=0; r<levelData.length;r++){
			// Dimension des neuen Raumes in der Arraylist initialisieren
			staticList.add(new ArrayList<DungeonObject>(0));
			creatureList.add(new ArrayList<LivingObject>(0));
			// Objekte generieren
			for(int i=0;i<=levelData[0].length-1;i++){
				for(int j=0;j<=levelData[0][0].length-1;j++){
					if(levelData[r][i][j] == 1)
						staticList.get(r).add(new WallObject(j*32, i*32));		// bei 1 wird ein Wandobjekt generiert
					else if(levelData[r][i][j] == 2)
						creatureList.get(r).add(new Creature(j*32+5, i*32-5, 3, 1, 0, 100, 0));		// bei 2 wird ein Monsterobjekt generiert
					else if(levelData[r][i][j] == 3){
						playerSpawnX	=	j*32-5;
						playerSpawnY	=	i*32-5;
						player	=	new Player(playerSpawnX, playerSpawnY, 5, 0, 0, 100, 100, 3);		// bei 3 wird ein Spielerobjekt generiert
					}
					else if(levelData[r][i][j] == 5)
						staticList.get(r).add(new TrapObject(j*32, i*32));		// bei 5 wird ein Fallenobjekt generiert
					else if(levelData[r][i][j] == 6)
						staticList.get(r).add(new GoalObject(j*32, i*32));		// bei 6 wird ein Zielobjekt generiert	
					else if(levelData[r][i][j] == 7)
						staticList.get(r).add(new PotionObject(j*32, i*32)); 		// bei 7 wird ein Potionobjekt generiert
				}
			}
		}
		// Ende der automatisierten Levelerstellung
		
		// manuelles Einrichten der Teleporter (von Raum zu Raum)
		
		teleportList.add(new ArrayList<Teleporter>(0));	// Raum 0
		teleportList.add(new ArrayList<Teleporter>(0));	// Raum 1
		teleportList.add(new ArrayList<Teleporter>(0));	// Raum 2
		// Teleport 0 -> 1
		teleportList.get(0).add(new Teleporter(32*-1, 32*13, 1, 29*32, 13*32));
		teleportList.get(0).add(new Teleporter(32*-1, 32*14, 1, 29*32, 14*32));
		// Teleport 1 -> 0
		teleportList.get(1).add(new Teleporter(30*32, 13*32, 0, 0*32, 13*32));
		teleportList.get(1).add(new Teleporter(30*32, 14*32, 0, 0*32, 14*32));
		// Teleport 1 -> 2
		teleportList.get(1).add(new Teleporter(2*32, -1*32, 2, 2*32, 15*32));
		teleportList.get(1).add(new Teleporter(3*32, -1*32, 2, 3*32, 15*32));
		// Teleport 2 -> 1
		teleportList.get(2).add(new Teleporter(2*32, 16*32, 1, 2*32, 0*32));
		teleportList.get(2).add(new Teleporter(3*32, 16*32, 1, 3*32, 0*32));
		
		// Eigenschaften des Panels
		setFocusable(true);		// Panel ist focusable
		setBackground(new Color(255,211,155));		// Hintergrundfarbe (derzeit der begehbare Boden)
		setDoubleBuffered(true);		
		
		// Hinzufuegen des KeyListener 
		addKeyListener(new KeyControll());
		
		// Aktionstimer wird gesetzt und gestartet
		timer	=	new Timer(1000/65, this);
		timer.start();
	}

// Methoden:	
	/*
	 * Kollisionsabfrage zwischen den Dungeonobjekten aus staticList und creatureList
	 * bei Spielerkollision wird .onCollision(player) des jeweiligen Listenelements aufgerufen fï¿½r spezielle Kollisionsbehandlung
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
			// ueberpruefe static mit Spieler
			if(staticList.get(room).get(i).getBorder().intersects(player.getBorder()))
				staticList.get(room).get(i).onCollision(player);
			
			// nun ueberpruefe Wand und Monster sowie Monster und Spieler
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
					player.dealDamage(creatureList.get(room).get(i));
				}
			}
	}
	
	// Methode um das Level neu zu laden und das Spiel von vorne zu beginnen
	public void reload(){
		// den Spieler wiederbeleben
		player.revive();
		player.teleport(playerSpawnX, playerSpawnY);
//		player.goal = false;
		// Raumzeiger wieder auf den ersten Raum setzen
		room	=	0;
		// Endebedingungen auf false setzen
		lose	=	false;
		clear = false;
		// Fallen zuruecksetzen (auf State 0 = nicht ausgeloest)
		for(int r=0; r<staticList.size(); r++){
			for(int i=0; i<staticList.get(r).size(); i++)
				staticList.get(r).get(i).switchState(0);
		}
	}
	
	/*
	 * Zeichenmethode fuer das Level
	 */
	public void paint(Graphics g){
		// Aufruf urspruenglicher Funktion
		super.paint(g);
		// wir arbeiten mit Java2d
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(Data_Img.bks,0,0,this);
		
		// alle objekte der staticlist zeichnen (Waende, Fallen,...)
		for(int i=0; i<staticList.get(room).size(); i++)
			staticList.get(room).get(i).draw(g2d);
		// Monster zeichnen
		for(int i=0; i<creatureList.get(room).size(); i++)
			creatureList.get(room).get(i).draw(g2d);
		
		// Spieler zeichnen
		player.draw(g2d);
		
		// Gameover / Win Bildschirm zeichnen
		if(lose)
			g2d.drawImage(Data_Img.gameover, 32*10, 32*10, this);
		if(clear)
			g2d.drawImage(Data_Img.win, 32*10, 32*10, this);
	
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
//		if(player.getGoal() == true)
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
			if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT)
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
