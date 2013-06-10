package hhu.propra2013.gruppe55;

import hhu.propra2013.leveleditor2.LevelData;
import hhu.propra2013.leveleditor2.LevelReader;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import java.io.*;

public class Level extends JPanel implements ActionListener, GameEventListener {

	private static final long serialVersionUID = 1L;
	
	// Levelobjekte
	private Player player;				// Spielerobjekt
	private GameInterface iFace;		//GameInterface
	private int room;					// pointer to current room
	private ArrayList<ArrayList<LivingObject>> creatureList;	// liste der Gegner
	private ArrayList<ArrayList<DungeonObject>> staticList;		// liste der Waende/Gegenstaende/etc
	private ArrayList<Projectile> projectileList;			// liste der Projektile (Pfeile, Feuerbaelle, etc)
	private ArrayList<ArrayList<Teleporter>> teleportList;		// Liste aller Teleporter 
	// Timer fuer die Aktionen
	private Timer timer;
	// Spieleventvariablen
	private boolean lose, clear;	// wird auf wahr gesetzt, wenn der Spieler stirbt oder das Level erfolgreich abschliesst
	// Wichtige variablen fuer das neu Laden eines Levels
	private int playerSpawnX, playerSpawnY;		// Koordinaten des ersten Spielererscheinungspunkts
	private GameMenu gm;
	private GameWindow gw;
	private boolean freeze	=	false;		// friert das Level ein
	private int openedInterface;			// Welches Interface aufgerufen ist
	private boolean dialog;					// Ob Dialog angezeigt werden soll oder nicht
	private boolean jsonParser = true;		// Bis alles funktioniert per Default auf false gesetzt - auf true setzen um die Jsonlvl zu laden 
	private LevelData levelDataObj;
	
	
// Konstruktor
	public Level(GameMenu gm, GameWindow gw, int x, int y) {		
		// Mittelpunkt des Fensters
		
		this.gm = gm;
		this.gw = gw;
		
		String line;
		String lineints[];
		int[][][] lvlData = null;
		
		if(!jsonParser){
			// Parser fuer Leveldateien
			try {
				//.txt einlesen
				FileReader fread = new FileReader("lvl/testlvl.txt");
				BufferedReader in = new BufferedReader(fread);
				
				int k = 0; //Für 3. Arraydimension wird eigene Variable benötigt, da i immer bei 1 beginnt durch die deklarierende Zeile, die nicht im Arry landet
				
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
								lvlData[0][j][k] = Integer.parseInt(lineints[j]);
						}
						k++;
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
			projectileList	=	new ArrayList<Projectile>(0);
			
			//0: Background		
			//1: Wall
			//2: Creature
			//3: Player
			//4: Teleporter
			//5: Falle
			//6: Ziel
			//7: Potion
			//8: Manapotion
			//9: Schatz
			//10: Shopkeeper
			
			// Schleife die das Level generiert
			for(int r=0; r<lvlData.length;r++){
				// Dimension des neuen Raumes in der Arraylist initialisieren
				staticList.add(new ArrayList<DungeonObject>(0));
				creatureList.add(new ArrayList<LivingObject>(0));
				teleportList.add(new ArrayList<Teleporter>(0));
				// Objekte generieren
				for(int i=0;i<=lvlData[0].length-1;i++){
					for(int j=0;j<=lvlData[0][0].length-1;j++){
						/*if(lvlData[r][i][j] == 0){
							staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert (derzeit auskommentiert, da zu Speicherintensiv)
						}
						else */if(lvlData[r][i][j] == 1){
							staticList.get(r).add(new WallObject(i*32, j*32));		// bei 1 wird ein Wandobjekt generiert
						}
						else if(lvlData[r][i][j] == 2){
							creatureList.get(r).add(new Creature(i*32+5, j*32-5, 3, 1, 0, 100, 0));		// bei 2 wird ein Monsterobjekt generiert
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
						}
						else if(lvlData[r][i][j] == 3){
							playerSpawnX	=	i*32-5;
							playerSpawnY	=	j*32-5;
							player	=	new Player(playerSpawnX, playerSpawnY, 6, 0, 0, 100, 1, 3);		// bei 3 wird ein Spielerobjekt generiert
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
						}
						else if(lvlData[r][i][j] == 5){
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
							staticList.get(r).add(new TrapObject(i*32, j*32));		// bei 5 wird ein Fallenobjekt generiert
						}
						else if(lvlData[r][i][j] == 4){
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
							teleportList.get(r).add(new Teleporter(i*32, j*32, 1, 2*32, 0*32));
						}
						else if(lvlData[r][i][j] == 6){
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
							staticList.get(r).add(new GoalObject(i*32, j*32));		// bei 6 wird ein Zielobjekt generiert
						}
						else if(lvlData[r][i][j] == 7){
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
							staticList.get(r).add(new PotionObject(i*32, j*32)); 	// bei 7 wird ein Potionobjekt generiert
						}
						else if(lvlData[r][i][j] == 8){
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
							staticList.get(r).add(new MPotionObject(i*32, j*32)); 	// bei 8 wird ein Manapotionobject generiert
						}
						else if(lvlData[r][i][j] == 9){
							// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
							staticList.get(r).add(new TreasureObject(i*32, j*32)); 	// bei 9 wird ein Schatzobjekt generiert
						}
						else if(lvlData[r][i][j] == 10){
							creatureList.get(r).add(new Shopkeeper(32*i, 32*j, 3, 1, 0, 100, 0));
						}
					}
				}
			}
		}else{
			//lade Leveldaten durch json Parser
			levelDataObj = new LevelData();
			try {
				LevelReader levelReader = new LevelReader(new File("lvl/level1.txt"));
				levelDataObj = levelReader.getLevelData();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			// Zeiger wird auf den ersten Raum gesetzt
			room  =  0;
			
			// ArrayLists generieren
			staticList    =  new ArrayList<ArrayList<DungeonObject>>(0);
			creatureList  =  new ArrayList<ArrayList<LivingObject>>(0);
			teleportList  =  new ArrayList<ArrayList<Teleporter>>(0);
			projectileList  =  new ArrayList<Projectile>(0);
						

			//0: Background    
			//1: Wall
			//2: Creature
			//3: Player
			//4: Teleporter
			//5: Falle
			//6: Ziel
			//7: Potion
			//8: Manapotion
			//9: Schatz
			//10: Shopkeeper
			
			// Schleife die das Level generiert
			for(int r=0; r<levelDataObj.totalRooms();r++){
				// Listen um eine Dimension erweitern
				staticList.add(new ArrayList<DungeonObject>(0));
				creatureList.add(new ArrayList<LivingObject>(0));
				teleportList.add(new ArrayList<Teleporter>(0));
				// Sami, y u no comment?
				for(Map.Entry<String, ArrayList<Integer>> entry : levelDataObj.getlevelRoom(r).entrySet()){
					ArrayList<Integer> tempParameterList = entry.getValue();
					int xPos,yPos;
					String[] tempStr = entry.getKey().split(",");
					xPos = Integer.parseInt(tempStr[0]);
					yPos = Integer.parseInt(tempStr[1]);
					//Wall
					if(tempParameterList.get(0) == 1){
						//Texturparameter
						if(tempParameterList.get(1) == 0){
							staticList.get(r).add(new WallObject(xPos, yPos));
						}
					}
					//Creature
					else if(tempParameterList.get(0) == 2){
						creatureList.get(r).add(new Creature(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3), tempParameterList.get(4), tempParameterList.get(5)));    // bei 2 wird ein Monsterobjekt generiert
					}else if(tempParameterList.get(0) == 3){
						playerSpawnX = xPos;
						playerSpawnY = yPos;
						player  =  new Player(playerSpawnX, playerSpawnY, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3), tempParameterList.get(4), tempParameterList.get(5), tempParameterList.get(6));    
					}else if(tempParameterList.get(0) == 4){
						teleportList.get(r).add(new Teleporter(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));    
					}else if(tempParameterList.get(0) == 5){
						staticList.get(r).add(new TrapObject(xPos, yPos));    
					}else if(tempParameterList.get(0) == 6){
						staticList.get(r).add(new GoalObject(xPos, yPos));    
					}else if(tempParameterList.get(0) == 7){
						staticList.get(r).add(new PotionObject(xPos, yPos));    
					}else if(tempParameterList.get(0) == 8){
						staticList.get(r).add(new MPotionObject(xPos, yPos));    
					}else if(tempParameterList.get(0) == 9){
						staticList.get(r).add(new TreasureObject(xPos, yPos));    
					}else if(tempParameterList.get(0) == 10){
						creatureList.get(r).add(new Shopkeeper(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3), tempParameterList.get(4), tempParameterList.get(5)));
					}
				}
			}
		}
		// GameEventListener hinzufuegen fuer Lebendige Objekte
		for(int i=0; i<creatureList.size(); i++)
			for(LivingObject l : creatureList.get(i))
				l.addGameListener(this);
		// GameEventListener hinzufuegen fuer statische Objekte
		for(int i=0; i<staticList.size(); i++)
			for(DungeonObject l : staticList.get(i))
				l.addGameListener(this);
		// ... und den Spieler
		player.addGameListener(this);

		//Konstruiere Interface
		iFace = new GameInterface(this);
		
		//Cursor unsichtbar machen
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Data_Img.potionused, new Point(0,0), "Invisible Cursor"));
		
		
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
				// Projektile loeschen
				//projectileList.clear();
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
				// Projektile fliegen durch die Gegend
				for(int k=0; k<projectileList.size();k++){
					// Monster treffen
					if(projectileList.get(k).getBorder().intersects(creatureList.get(room).get(j).getBorder()))
						projectileList.get(k).onCollision(creatureList.get(room).get(j));
					// sonst Waende treffen
					else if(projectileList.get(k).getBorder().intersects(staticList.get(room).get(i).getBorder()))
						projectileList.get(k).onCollision(staticList.get(room).get(i));
					// sonst evtl den Spieler
					else if(projectileList.get(k).getBorder().intersects(player.getBorder()))
						projectileList.get(k).onCollision(player);
				}
				// dann Wand -> Monster
				if(staticList.get(room).get(i).getBorder().intersects(creatureList.get(room).get(j).getBorder()))
					staticList.get(room).get(i).onCollision(creatureList.get(room).get(j));
				// dann Monster -> Spieler
				if(creatureList.get(room).get(j).getBorder().intersects(player.getBorder()))
					creatureList.get(room).get(j).onCollision(player);
				// Ende der Kollisionsabfrage
			}
		}
		
		// TODO: Projektilkollisionen in eigenem Thread. Ansonsten gehts zu sehr auf die Leistung..
		
		// Spielerangriff
		if(player.getAttackState() && player.getWeapSet() == 0)
			for(int i=0; i<creatureList.get(room).size(); i++){
				// Monsterkollision mit der Waffe
				if(creatureList.get(room).get(i).getBorder().intersects(player.weapons[0].getBorder())){
					player.dealDamage(creatureList.get(room).get(i));
				}
			}
	}
		
	
	//Methode um das Level neu zu laden und das Spiel von vorne zu beginnen
	public void reload(){
		// den Spieler wiederbeleben
		player.revive();
		player.teleport(playerSpawnX, playerSpawnY);
		// Raumzeiger wieder auf den ersten Raum setzen
		room = 0;
		//Fallen zuruecksetzen
		for(int r=0; r<staticList.size(); r++){
			for(int i=0; i<staticList.get(room).size(); i++)
				staticList.get(room).get(i).switchState(0);
		}
		// Projektile loeschen
		 projectileList.clear();
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
		// Koordinatensystem an Spieler anpassen
		g2d.translate(gw.getWidth()/2-player.getTX(), gw.getHeight()/2-player.getTY());
		
		// alle objekte der staticlist zeichnen (Waende, Fallen,...)
		for(int i=0; i<staticList.get(room).size(); i++)
			staticList.get(room).get(i).draw(g2d);
		// Monster zeichnen
		for(int i=0; i<creatureList.get(room).size(); i++)
			creatureList.get(room).get(i).draw(g2d);
		// projektile
		for(int i=0; i<projectileList.size(); i++)
			projectileList.get(i).draw(g2d);
		
		// Spieler zeichnen
		player.draw(g2d);
		
		// Fuer folgende Texturen das Koordinatensystem wieder begradigen
		g2d.translate(-(gw.getWidth()/2-player.getTX()), -(gw.getHeight()/2-player.getTY()));
		
		// Interface zeichnen
		iFace.paint(g2d, player, gw.fullscreen);
		
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
		// Level gefroren?
		if(!freeze){
			// ueberpruefen ob der Spieler lebt
			if(player.getHP()<=0)
				lose	=	true;	// wird gesetzt wenn der Spieler stirbt
			
			// Spielerbewegung
			player.move();
			
			// kreaturenbewegung
			for(int i=0; i<creatureList.get(room).size(); i++)
				creatureList.get(room).get(i).move();
			// projektile
			for(int i=0; i<projectileList.size(); i++)
				projectileList.get(i).move();
			
			// Kollisionsabfrage
			collisionCheck();
		}
		
		// neuzeichnen
		repaint();
	}
	
	//GameFreeze togglen
	public void toggleFreeze(){
		freeze	=	!freeze;
	}
	
	//Dialog anzeigen ja/nein
	public void setDialog(boolean d){
		dialog = d;
	}
	
	//OpenedInterface ändern
	public void setOpenedInterface(int o){
		openedInterface = o;
	}
	
	//Dialogstatus abfragen
	public boolean getDialog(){
		return(dialog);
	}
	
	//Zu zeichnendes Interface ausgeben
	public int getOpenedInterface(){
		return(openedInterface);
	}
	
	public GameInterface getInterface(){
		return(iFace);
	}

// SPIELEREIGNISSE ABFANGEN
	@Override
	public void newTreasure(int x, int y) {
		staticList.get(room).add(new TreasureObject(x, y));
	}
	@Override
	public void levelCleared(){
		clear=true;
	}
	@Override
	public void shootProjectile(Projectile p){
		projectileList.add(p);
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
			if(!lose && !clear && (k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT)){
				player.keyPressed(e);
				// An Interface weiterleiten, wenn aktiviert
				if(dialog){
					iFace.buttonAction(k, player);
				}
			}
			else if(k	==	KeyEvent.VK_X)
				player.swapWeapons();
			// Enter-Taste abfragen
			else if(k == KeyEvent.VK_ENTER){
				// Option: Bei Sieg oder Niederlage -> zurueck zum Menue
				if(lose || clear){
					gw.dispose();
					gm.setVisible(true);
					reload();
				}
				//Dialog weiterschalten
				else if(dialog){
					iFace.buttonAction(k, player);
				}
			}
			// Space-Taste abfragen
			else if(k == KeyEvent.VK_SPACE){
				// Option: Bei Sieg oder Niederlage -> erneut beginnen
				if(lose || clear){
					reload();
				}
				else{	// let's fetz
					// Spieler Angreifen lassen
					player.attack();
				}
			}
			// Interagieren
			else if(k == KeyEvent.VK_E){
				for(int i=0; i<creatureList.get(room).size(); i++){
					if(creatureList.get(room).get(i) instanceof Shopkeeper){
						if(player.getBorder().intersects(creatureList.get(room).get(i).getBorder())){
							freeze = true;
							openedInterface = 2;
							dialog = true;
							iFace.setSelectedObject(0);
						}
					}
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				if(dialog){
					freeze = false;
					dialog = false;
					openedInterface = 0;
				}
				else{System.exit(1);}
			}
			else if(e.getKeyCode() == KeyEvent.VK_F){
				if(gw.isUndecorated()){gw.toggleFullscreen(0);}
				else{gw.toggleFullscreen(1);}
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
