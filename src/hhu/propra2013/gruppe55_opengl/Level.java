package hhu.propra2013.gruppe55_opengl;

import java.util.*;
import java.io.*;
import hhu.propra2013.leveleditor2.LevelData;
import hhu.propra2013.leveleditor2.LevelReader;
import java.util.Map;
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.DisplayMode;

public class Level implements GameEventListener{

	// Levelobjekte
	private Player player;				// Spielerobjekt
	private GameInterface iFace;					//GameInterface
	private int room;					// pointer to current room
	private int roomToRespawn;			// Raum in dem der Spieler nach Niederlage wiedererscheint
	private ArrayList<ArrayList<LivingObject>> creatureList;	// liste der Gegner
	private ArrayList<ArrayList<DungeonObject>> staticList;		// liste der Waende/Gegenstaende/etc
	private ArrayList<Projectile> projectileList;			// liste der Projektile (Pfeile, Feuerbaelle, etc)
	private ArrayList<ArrayList<Teleporter>> teleportList;		// Liste aller Teleporter 
	// Spieleventvariablen
	private boolean lose, clear, gameover, close;	// wird auf wahr gesetzt, wenn der Spieler stirbt oder das Level erfolgreich abschliesst
	// Wichtige variablen fuer das neu Laden eines Levels
	private int playerSpawnX, playerSpawnY;		// Koordinaten des ersten Spielererscheinungspunkts
	private boolean freeze = false;		// friert das Level ein
	private int openedInterface;			// Welches Interface aufgerufen ist
	private boolean dialog;					// Ob Dialog angezeigt werden soll oder nicht
	private boolean fullscreen;				// Ob Fullscreen aktiviert ist
	private DisplayMode initMode;			//Originalfenstermodus
	private boolean jsonParser = true;		// Ob der JSON Parser verwendet werden soll
	private LevelData levelDataObj;
	static Data_Textures textures;			// Grafik-Klasse
	private long lastAction;	// Timer-Variable
	
	
// Konstruktor
	public Level(int x, int y) {
		init(x, y);
		textures = new Data_Textures();
		
		if(jsonParser){
			loadLevel("level1");
		}
		else{
			loadLevel("testlvl");
		}
		
		this.play();
	}

	public void loadLevel(String file){
		String line;
		String lineints[];
		int[][][] lvlData = null;
		
		if(!jsonParser){
			// Parser fuer Leveldateien
			try {
				//.txt einlesen
				FileReader fread = new FileReader("lvl/" + file + ".txt");
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
			//11: Storyteller
			//12: Healthcontainer
			//13: Checkpoint
			//14: Creature_Bow
			
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
							creatureList.get(r).add(new Creature(i*32+5, j*32-5, 3, 1, 0));		// bei 2 wird ein Monsterobjekt generiert
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
							creatureList.get(r).add(new Shopkeeper(32*i, 32*j, 3, 1, 0));
						}
						else if(lvlData[r][i][j] == 11){
							creatureList.get(r).add(new Storyteller(32*i, 32*j, 3, 1, 0));
						}
						else if(lvlData[r][i][j] == 12){
							staticList.get(r).add(new Healthcontainer(i*32, j*32));
						}
						else if(lvlData[r][i][j] == 13){
						}
						else if(lvlData[r][i][j] == 14){
							creatureList.get(r).add(new Creature_Bow(32*i, 32*j, 0, 5*32, 1, 3, 1, 0));
						}
						else if(lvlData[r][i][j] == 15){
							creatureList.get(r).add(new Boss1(i*32, j*32, 15, 1, 0));		// bei 15 wird ein Boss1 generiert						// CheckPoint
						}
						else if(lvlData[r][i][j] == 16){
							staticList.get(r).add(new CheckPoint(i*32,j*32));
						}
						else if(lvlData[r][i][j] == 17){
							creatureList.get(r).add(new Boss2(32*i, 32*j, 10*32, 0, 3, 1, 0));
						}
						else if(lvlData[r][i][j] == 18){
							creatureList.get(r).add(new Boss3(i*32+5, j*32-5, 0, 0, 3, 1, 0));
						}
						else if(lvlData[r][i][j] == 19){
							staticList.get(r).add(new ArrowObject(i*32, j*32)); 	// bei 9 wird ein Schatzobjekt generiert
						}
					}
				}
			}
		}else{
			//lade Leveldaten durch json Parser
			levelDataObj = new LevelData();
			try {
				LevelReader levelReader = new LevelReader(new File("lvl/" + file + ".txt"));
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
			//11: Storyteller
			//12: Healthcontainer
			//13: Checkpoint
			
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
						creatureList.get(r).add(new Creature(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));    // bei 2 wird ein Monsterobjekt generiert
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
						creatureList.get(r).add(new Shopkeeper(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));
					}
					else if(tempParameterList.get(0) == 11){
						creatureList.get(r).add(new Storyteller(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));
					}
					else if(tempParameterList.get(0) == 12){
						staticList.get(r).add(new Healthcontainer(xPos, yPos));
					}
					else if(tempParameterList.get(0) == 13){
						//tempParameterList.get(1) = texturID mit Texturbild = img/textures/texturID.png
						//tempParameterList.get(2) = 1 massive ; 0 not massive
						//staticList.get(r).add(new someObject(xPos, yPos, 1));
						//Image image = new ImageIcon("img/textures/"+tempParameterList.get(1)+".png").getImage();
					}
					else if(tempParameterList.get(0) == 14){
						creatureList.get(r).add(new Creature_Bow(xPos, yPos, tempParameterList.get(6)*32, tempParameterList.get(7)*32, tempParameterList.get(8), tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));
					}
					else if(tempParameterList.get(0) == 15){
						creatureList.get(r).add(new Boss1(xPos, yPos, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));
					}
					else if(tempParameterList.get(0) == 16){
						staticList.get(r).add(new CheckPoint(xPos, yPos));    
					}
					else if(tempParameterList.get(0) == 17){
						creatureList.get(r).add(new Boss2(xPos, yPos, tempParameterList.get(6)*32, tempParameterList.get(7)*32, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));
					}
					else if(tempParameterList.get(0) == 18){
						creatureList.get(r).add(new Boss3(xPos, yPos, tempParameterList.get(6)*32, tempParameterList.get(7)*32, tempParameterList.get(1), tempParameterList.get(2), tempParameterList.get(3)));
					}
					else if(tempParameterList.get(0) == 19){
						staticList.get(r).add(new ArrowObject(xPos, yPos));    
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
		
		//Startzeit für Action-Timer initial setzen
		lastAction = Sys.getTime();
		
		// Erster CheckPoint ist der LevelEintritt
		checkPointReached();

	}
	
// Methoden
	/*
	 * Kollisionsabfrage zwischen den Dungeonobjekten aus staticList und creatureList
	 * bei Spielerkollision wird .onCollision(player) des jeweiligen Listenelements aufgerufen fuer spezielle Kollisionsbehandlung
	 */
	private void collisionCheck(){
		// Ueberpruefen ob der Spieler in einen anderen Raum teleportiert werden soll
		for(int i=0; i<teleportList.get(room).size(); i++){
			// trifft der Spieler auf einen Teleporter
			if(teleportList.get(room).get(i).getBorder().intersects(player.getBorder())){
				// Teleportinformationen abfragen
				int[] portData	=	teleportList.get(room).get(i).getTeleport();
				// Spieler teleportieren
				player.teleport(portData[1], portData[2]);
				// Raumzeiger umsetzen
				room	=	portData[0];
				// Projektile loeschen
				projectileList.clear();
				// Schleife beenden
				break;
			}
		}
		
		// staticlist Kollisionen überprüfen
		for(int i=0; i<staticList.get(room).size(); i++){
			// static mit Spieler
			if(staticList.get(room).get(i).getBorder().intersects(player.getBorder())){
				staticList.get(room).get(i).onCollision(player);
			}
			// static mit Projektilen
			for(int j=0; j<projectileList.size();j++){
				if(projectileList.get(j) != null){
					if(projectileList.get(j).getBorder().intersects(staticList.get(room).get(i).getBorder())){
						projectileList.get(j).onCollision(staticList.get(room).get(i));
					}
				}
			}
			// dann Wand -> Living
			for(int k=0; k<creatureList.get(room).size();k++){
				if(staticList.get(room).get(i).getBorder().intersects(creatureList.get(room).get(k).getBorder())){
					staticList.get(room).get(i).onCollision(creatureList.get(room).get(k));
				}
			}
		}
		
		//Kollisionen der LivingObjects
		for(int i = 0; i < creatureList.get(room).size(); i++){
			// Living mit Projektilen
			for(int j=0; j<projectileList.size();j++){
				if(projectileList.get(j).getBorder().intersects(creatureList.get(room).get(i).getBorder()))
					projectileList.get(j).onCollision(creatureList.get(room).get(i));
			}
			// Living mit Spieler
			if(creatureList.get(room).get(i).getBorder().intersects(player.getBorder())){
				creatureList.get(room).get(i).onCollision(player);
			}
		}
		
		// uebrige Spieler-Kollisionen
		// Spieler mit Projektilen
		for(int i=0; i<projectileList.size();i++){
			if(projectileList.get(i).getBorder().intersects(player.getBorder())){
				projectileList.get(i).onCollision(player);
			}
		}
		// Spielerangriff
		if(player.getAttackState() && player.getWeapSet() == 0){
			for(int i=0; i<creatureList.get(room).size(); i++){
				// Monsterkollision mit der Waffe
				if(creatureList.get(room).get(i).getBorder().intersects(player.weapons[0].getBorder())){
					player.dealDamage(creatureList.get(room).get(i));
				}
			}
		}
		
		// Projektilliste aussortieren
		for(int i=0; i<projectileList.size();i++){
			if(projectileList.get(i).getCurrState()==0){
				projectileList.remove(i);
			}
		}

	}
	
	//Methode um das Level neu zu laden und das Spiel von vorne zu beginnen
	public void reload(){
		// Spieler zuruecksetzen
		player.reset();
		// Raum setzen
		room	=	roomToRespawn;
		// Leben vom Spieler abziehen
		if(lose==true){
			player.giveStatInventoryObject(0, -1);
	    }
		// Fuer alle Raeume raus was raus kann
		for(int r=0;r<staticList.size();r++){
			// StaticList
			for(int i=0;i<staticList.get(r).size();i++){
				if(!staticList.get(r).get(i).isResetable())
					staticList.get(r).remove(i);
			}
			// CreatureList
			for(int i=0;i<creatureList.get(r).size();i++){
				if(!creatureList.get(r).get(i).isResetable())
					creatureList.get(r).remove(i);
			}
		}
		// Rest resetten
		for(int r=0;r<staticList.size();r++){
			// StaticList
			for(int i=0;i<staticList.get(r).size();i++){
				staticList.get(r).get(i).reset();
			}
			// CreatureList
			for(int i=0;i<creatureList.get(r).size();i++){
				creatureList.get(r).get(i).reset();
			}
		}	
		
		// Projektile loeschen
		 projectileList.clear();
		// Endebedingungen auf false setzen
		lose	=	false;
		clear = false;
		gameover = false;
	}

	//Display und OpenGL initialisieren/einstellen
	public void init(int x, int y){
		try {
			initMode = new DisplayMode(x, y);
			Display.setDisplayMode(initMode);
			Display.setTitle("Game");
			Display.setResizable(false);
			Display.setInitialBackground(255/255f, 211/255f, 155/255f);
			Display.create();
		}catch (LWJGLException e) {e.printStackTrace();}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, x, y, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		//Keyboard.enableRepeatEvents(true);
		//Mouse.setGrabbed(true);
	}

	// Game-Schleife
	public void play(){
		while(!close){
			if(Display.isCloseRequested()){
				close = true;
			}
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			
			input();
			engine();
			render();
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}
	
	// Eingaben abfragen
	public void input(){
		// KeyboardEvents
		
		// Tastatur-Events wï¿½hrend des Spiels
		if(!lose && !clear){		
			while(Keyboard.next()){
				
				int k = Keyboard.getEventKey();
				
				if(Keyboard.getEventKeyState()){
					switch(k){
						case Keyboard.KEY_UP:
							player.keyPressed(Keyboard.KEY_UP);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_UP, player);
							}
							break;
						case Keyboard.KEY_DOWN:
							player.keyPressed(Keyboard.KEY_DOWN);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_DOWN, player);
							}
							break;
						case Keyboard.KEY_RIGHT:
							player.keyPressed(Keyboard.KEY_RIGHT);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_LEFT, player);
							}
							break;
						case Keyboard.KEY_LEFT:
							player.keyPressed(Keyboard.KEY_LEFT);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_LEFT, player);
							}
							break;
						case 28:
							if(lose || clear || gameover){}
							else if(dialog){
								iFace.buttonAction(28, player);
							}
							break;
						case Keyboard.KEY_SPACE:
							if((lose || clear) && !gameover ){}
							else if(!gameover){
								// Spieler Angreifen lassen
								player.attack();
							}
							break;
						case Keyboard.KEY_E:
							for(int i=0; i<creatureList.get(room).size(); i++){
								// Wenn angesprochender NPC ein Shopkeeper ist
								if(creatureList.get(room).get(i) instanceof Shopkeeper){
									if(player.getBorder().intersects(creatureList.get(room).get(i).getBorder())){
										freeze = true;
										openedInterface = 2;
										dialog = true;
										iFace.setSelectedObject(0);
									}
								}
								// Wenn es ein Storyteller ist und kein Shopkeeper - Dialog aus der Textdatei holen und Dialog aufrufen!
								else if(creatureList.get(room).get(i) instanceof Storyteller){
									if(player.getBorder().intersects(creatureList.get(room).get(i).getBorder())){
										iFace.setDialog(Data_String.story1, 0);
										freeze = true;
										openedInterface = 1;
										dialog = true;
									}
								}
							}
							break;
						case Keyboard.KEY_ESCAPE:
							if(dialog){
								freeze = false;
								dialog = false;
								openedInterface = 0;
							}
							else{close = true;}
							break;
						case Keyboard.KEY_X:
							player.swapWeapons();
							break;
						case Keyboard.KEY_F:
							try {
								if(fullscreen){
									fullscreen = !fullscreen;
									Display.setDisplayMode(initMode);
									Display.setFullscreen(false);
									glMatrixMode(GL_PROJECTION);
									glLoadIdentity();
									glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
									glMatrixMode(GL_MODELVIEW);
								}
								else{
									fullscreen = !fullscreen;
									Display.setDisplayMode(Display.getDesktopDisplayMode());
									Display.setFullscreen(true);
									glMatrixMode(GL_PROJECTION);
									glLoadIdentity();
									glOrtho(0, Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), 0, -1, 1);
									glMatrixMode(GL_MODELVIEW);
								}
							} catch (LWJGLException e) {e.printStackTrace();}
							break;
						case Keyboard.KEY_C:
							player.spellCast();
							break;
						case Keyboard.KEY_A:
							if(player.getStatInventoryObjectCount(2)>0){
								player.getHealed(2);
								player.giveStatInventoryObject(2, -1);
							}
							break;
						case Keyboard.KEY_S:
							if(player.getStatInventoryObjectCount(3)>0){
								player.fillmana(1);
								player.giveStatInventoryObject(3, -1);
							}
							break;
					}
				}
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				player.keyReleased(Keyboard.KEY_UP);
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				player.keyReleased(Keyboard.KEY_LEFT);
			}
		}
		// Tastatur-Events bei Loose/Clear
		else{
			while(Keyboard.next()){
				
				int k = Keyboard.getEventKey();
				
				if(Keyboard.getEventKeyState()){
					switch(k){
						case 28:
							reload();
							break;
						case Keyboard.KEY_SPACE:
							reload();
							break;
						case Keyboard.KEY_ESCAPE:
							close = true;
							break;
					}
				}
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				player.keyReleased(Keyboard.KEY_UP);
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				player.keyReleased(Keyboard.KEY_LEFT);
			}
		}
	}
	
	// Game-Logic
	public void engine(){
		// Level gefroren?
		if(!freeze){
			// ueberpruefen ob der Spieler lebt und noch Extraleben zur verfuegung hat
			if(player.getStatInventoryObjectCount(0)==0 && player.getHP()<=0){
				gameover = true;
			}
			// ueberpruefen ob der Spieler noch lebt
			else if(player.getHP()<=0){
				lose	=	true;  // wird gesetzt wenn der Spieler stirbt
			}

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
			
			//Spiel Aktionen
			if(((Sys.getTime()-lastAction)/Sys.getTimerResolution()) >= 1){
				int[] playerCenter	=	player.getCenter();
				for(int i = 0; i < creatureList.get(room).size(); i++){
					// Alle lebenden Bogen-Gegner Schießen
					if(creatureList.get(room).get(i) instanceof Creature_Bow && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					}
					else if(creatureList.get(room).get(i) instanceof Boss2 && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					}
				}
				lastAction = Sys.getTime();
			}
		}
	}
	
	// Zeichen/Render-Funktion
	public void render(){
		// Koordinatensystem an Spieler anpassen
		glTranslatef(Display.getWidth()/2-player.getTX(), Display.getHeight()/2-player.getTY(), 0);
		
		// alle objekte der staticlist zeichnen (Waende, Fallen,...)
		for(int i=0; i<staticList.get(room).size(); i++){
			staticList.get(room).get(i).draw();
		}
		// Monster zeichnen
		for(int i=0; i<creatureList.get(room).size(); i++){
			creatureList.get(room).get(i).draw();
		}
		
		// Projektile
		for(int i=0; i<projectileList.size(); i++)
			projectileList.get(i).draw();
			
		// Spieler zeichnen
		player.draw();
		// Fuer folgende Texturen das Koordinatensystem wieder begradigen
		glTranslatef(-(Display.getWidth()/2-player.getTX()), -(Display.getHeight()/2-player.getTY()), 0);
		
		// Interface zeichnen
		iFace.paint(player, fullscreen);
		
		// Gameover / Win Bildschirm zeichnen
		if(lose){
			glBindTexture(GL_TEXTURE_2D, Data_Textures.youlose.getTextureID());
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(32*10, 32*10);
				glTexCoord2f(0, 1);
				glVertex2f(32*10, 32*10+Data_Textures.youlose.getTextureHeight());
				glTexCoord2f(1, 1);
				glVertex2f(32*10+Data_Textures.youlose.getTextureWidth(), 32*10+Data_Textures.youlose.getTextureHeight());
				glTexCoord2f(1, 0);
				glVertex2f(32*10+Data_Textures.youlose.getTextureWidth(), 32*10);
			glEnd();
		}
		if(clear){
			glBindTexture(GL_TEXTURE_2D, Data_Textures.win.getTextureID());
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(32*10, 32*10);
				glTexCoord2f(0, 1);
				glVertex2f(32*10, 32*10+Data_Textures.win.getTextureHeight());
				glTexCoord2f(1, 1);
				glVertex2f(32*10+Data_Textures.win.getTextureWidth(), 32*10+Data_Textures.win.getTextureHeight());
				glTexCoord2f(1, 0);
				glVertex2f(32*10+Data_Textures.win.getTextureWidth(), 32*10);
			glEnd();
		}
		if(gameover){
			glBindTexture(GL_TEXTURE_2D, Data_Textures.gameover.getTextureID());
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(32*10, 32*10);
				glTexCoord2f(0, 1);
				glVertex2f(32*10, 32*10+Data_Textures.gameover.getTextureHeight());
				glTexCoord2f(1, 1);
				glVertex2f(32*10+Data_Textures.gameover.getTextureWidth(), 32*10+Data_Textures.gameover.getTextureHeight());
				glTexCoord2f(1, 0);
				glVertex2f(32*10+Data_Textures.gameover.getTextureWidth(), 32*10);
			glEnd();
		}
	}
	
	//GameFreeze togglen
	public void toggleFreeze(){
		freeze	=	!freeze;
	}
	
	//Dialog anzeigen ja/nein
	public void setDialog(boolean d){
		dialog = d;
	}
	
	//OpenedInterface aendern
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

	// SPIELEREIGNISSE ABFANGEN
	@Override
	public void newTreasure(double x, double y) {
		staticList.get(room).add(new TreasureObject(x, y));
	}

	// Der Boss droppt das Zielobjekt 
	@Override
	public void newGoal(double x, double y) {
		GoalObject goal = new GoalObject(x, y);
		goal.addGameListener(this);
		staticList.get(room).add(goal);
	}

	
	@Override
	public void shootProjectile(Projectile p){
		projectileList.add(p);
	}
	
	@Override
	public void checkPointReached(){
		// Spieler speichern
		player.setResetValues();
		// Raumnummer speichern
		roomToRespawn	=	room;
		// Listen abklappern
		new Thread(){
			public void run(){
				for(int r=0;r<staticList.size();r++){
					// StaticList
					for(int i=0;i<staticList.get(r).size();i++){
						staticList.get(r).get(i).setResetValues();
					}
					// CreatureList
					for(int i=0;i<creatureList.get(r).size();i++){
						creatureList.get(r).get(i).setResetValues();
					}
				}
			}
		}.start();
	}


	@Override
	public void levelCleared() {
		clear=true;
	}
}
