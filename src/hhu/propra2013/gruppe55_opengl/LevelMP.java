package hhu.propra2013.gruppe55_opengl;

import java.util.*;
import java.io.*;
import hhu.propra2013.leveleditor.LevelData;
import hhu.propra2013.leveleditor.LevelReader;
import java.util.Map;
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.DisplayMode;

/**
 * Die Klasse LevelMP.
 * Die Hauptklasse, in der das ganze Level mit s�mtlichen Objekten, Funktionen, etc. generiert wird.
 */

public class LevelMP extends Level implements GameEventListener{

	// Levelobjekte
	
	/** Der Netzwerkclient. */
	
	private Client c;					//Network-Client
	
	/** Das Spielerobjekt, fuer Spieler1 und Spieler2. */
	
	protected Player player1, player2;				//Spielerobjekt
	
	/** Ob beim Start auf den 2. Spieler gewartet wird. */
	
	private boolean waiting;	// Ob beim Start auf den 2. Spieler gewartet wird
	
	/** Die Booleans fuer das Interagieren */
	
	protected boolean alreadyInteracted1, alreadyInteracted2 = alreadyInteracted1 = false;
	
	/** Die send-Timer Variable */
	
	protected long lastPositionSent;
	
	/**
	 * Der Konstruktor fuer das LevelMP.
	 * Beim Aufruf werden dem Konstruktor die x und y koordinaten, das GameMenu und die Levelnummer uebergeben.
	 * Diese Methode liest die Level selbst aus einer Textdatei aus und parsed diese entweder mit dem JsonParser oder mit einer eigenen Parserversion, die allerdings nur fuer Testlevel gebraucht werden sollte. Die Richtigen Level entstehen alle mit Hilfe der JsonParserschleife.
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 * @param gm  Die Methode erwartet die Uebergabe eines Objektes gm vom Typ GameMenu
	 * @param lvl  Die Methode erwartet die Uebergabe eines int Werts lvl
	 * @param ControllerID  Die Methode erwartet die Uebergabe eines int Werts ControllerID
	 */
	
// Konstruktor
	public LevelMP(int x, int y, GameMenu gm, int lvl, String a, int ControllerID) {
		super(x, y, gm, lvl, a, ControllerID);
	}

	@Override
	public void loadLevel(String file){
		String line;
		String lineints[];
		int[][][] lvlData = null;
		
		close = freeze = lose = clear = gameover = false;
		
		if(file == "testlvl"){
			jsonParser = false;
			// Parser fuer Leveldateien
			try {
				//.txt einlesen
				FileReader fread = new FileReader("lvl/" + file + ".txt");
				BufferedReader in = new BufferedReader(fread);
				
				int k = 0; //F�r 3. Arraydimension wird eigene Variable ben�tigt, da i immer bei 1 beginnt durch die deklarierende Zeile, die nicht im Arry landet
				
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
			// ...
			//20: WallSecret
			//21: Torch
			//22: fireelemental
			//23: switch
			
			// Das Testlevel ist sehr sehr undynamisch was Konstruktoren angeht
			// daher muss man manuell etwas Dynamik hier herein zwingen
			
			int torchCounter=0;	// Zaehlt die Position im TriggerArray ab, die uebergeben werden soll
			String[][] torchTrigger={{"f1"},{"f2"},{"f3"},{"f4"},{"f5"},{"f6"}}; // Array der TriggerKeyArrays fuer die Fackeln
			int switchCounter=0; // Wie der torchCounter nur fuer Schalter
			String[][] switchTrigger={{"f1","f4","f6"},{"f2","f4","f5"},{"f1","f2","f6"},{"f2","f4","f5","f6"},{"f2","f3","f4"},{"f2","f5","f6"}};	// Array der TriggerKeyArrays fuer die Schalter
			
			
			// Schleife die das Level generiert
			for(int r=0; r<lvlData.length;r++){
				// Dimension des neuen Raumes in der Arraylist initialisieren
				staticList.add(new ArrayList<DungeonObject>(0));
				creatureList.add(new ArrayList<LivingObject>(0));
				teleportList.add(new ArrayList<Teleporter>(0));
				// Objekte generieren
				for(int i=0;i<=lvlData[0].length-1;i++){
					for(int j=0;j<=lvlData[0][0].length-1;j++){
						if(lvlData[r][i][j] == 1){
							staticList.get(r).add(new SimpleStaticObject(i*32, j*32, true, 0));		// bei 1 wird ein Wandobjekt generiert
						}
						else if(lvlData[r][i][j] == 2){
							creatureList.get(r).add(new Creature(i*32+5, j*32-5, 3, 1, 0));		// bei 2 wird ein Monsterobjekt generiert
						}
						else if(lvlData[r][i][j] == 3){
							if(player1 == null){
								playerSpawnX	=	i*32-5;
								playerSpawnY	=	j*32-5;
								player1	=	new Player(playerSpawnX, playerSpawnY, 6, 0, 0, 100, 1, 3);		// bei 3 wird ein Spielerobjekt generiert
								player2	=	new Player(playerSpawnX, playerSpawnY, 6, 0, 0, 100, 1, 3);		// Player 2 konstruieren
							}
							else{
								player1.teleport(i*32-5, j*32-5);
								player2.teleport(i*32-5, j*32-5);
							}
						}
						else if(lvlData[r][i][j] == 5){
							staticList.get(r).add(new TrapObject(i*32, j*32));		// bei 5 wird ein Fallenobjekt generiert
						}
						else if(lvlData[r][i][j] == 4){
							teleportList.get(r).add(new Teleporter(i*32, j*32, 1, 2*32, 0*32));
						}
						else if(lvlData[r][i][j] == 6){
							staticList.get(r).add(new GoalObject(i*32, j*32));		// bei 6 wird ein Zielobjekt generiert
						}
						else if(lvlData[r][i][j] == 7){
							staticList.get(r).add(new PotionObject(i*32, j*32)); 	// bei 7 wird ein Potionobjekt generiert
						}
						else if(lvlData[r][i][j] == 8){
							staticList.get(r).add(new MPotionObject(i*32, j*32)); 	// bei 8 wird ein Manapotionobject generiert
						}
						else if(lvlData[r][i][j] == 9){
							staticList.get(r).add(new TreasureObject(i*32, j*32)); 	// bei 9 wird ein Schatzobjekt generiert
						}
						else if(lvlData[r][i][j] == 10){
							creatureList.get(r).add(new Shopkeeper(32*i, 32*j, 3, 1, 0)); // Bei 10 wird ein Shopkeeper generiert
						}
						else if(lvlData[r][i][j] == 11){
							creatureList.get(r).add(new Storyteller(32*i, 32*j, 3, 1, 0, 1));	// Bei 11 wird ein Storyteller generiert
						}
						else if(lvlData[r][i][j] == 12){
							staticList.get(r).add(new Healthcontainer(i*32, j*32));		// Bei 12 wird ein Healthcontainer generiert
						}
						else if(lvlData[r][i][j] == 13){	// leerer Platzhalter fuer ggf zukuenftige Implementation
						}
						else if(lvlData[r][i][j] == 14){
							creatureList.get(r).add(new Creature_Bow(32*i, 32*j, 0, 5*32, 1, 3, 1, 0));		// bei 14 wird ein Bogenmonster generiert
						}
						else if(lvlData[r][i][j] == 15){
							creatureList.get(r).add(new Boss1(i*32, j*32, 15, 1, 0));		// bei 15 wird ein Boss1 generiert						
						}
						else if(lvlData[r][i][j] == 16){
							staticList.get(r).add(new CheckPoint(i*32,j*32));		// bei 16 wird ein CheckPoint generiert
						}
						else if(lvlData[r][i][j] == 17){
							creatureList.get(r).add(new Boss2(32*i, 32*j, 10*32, 0, 3, 1, 0));		// bei 17 wird ein Boss2 generiert		
						}
						else if(lvlData[r][i][j] == 18){
							creatureList.get(r).add(new Boss3(i*32, j*32, 15, 1, 0));		// bei 18 wird ein Boss3 generiert		
						}
						else if(lvlData[r][i][j] == 19){
							staticList.get(r).add(new ArrowObject(i*32, j*32)); 	// bei 19 wird ein Pfeilobjekt generiert
						}
						else if(lvlData[r][i][j] == 20){
							staticList.get(r).add(new WallSecret(i*32, j*32, new String[]{"f1","f2","f3","f4","f5","f6"})); // bei 20 wird eine "Geheimwand" generiert (Die Schalter auf die diese Tuer hoert muessen angegeben werden)
						}
						else if(lvlData[r][i][j] == 21){
							staticList.get(r).add(new Torch(i*32, j*32, torchTrigger[torchCounter]));		// bei 21 wird eine Fackel generiert
							torchCounter++;	// erhoehen, wir wollen ja variieren!
						}
						else if(lvlData[r][i][j] == 22) {
							creatureList.get(r).add(new FireElemental(i*32, j*32, 15, 1, 0));		// bei 22 wird ein Feuerelementar generiert
						}
						else if(lvlData[r][i][j] == 23){
							staticList.get(r).add(new Switch(i*32, j*32, switchTrigger[switchCounter]));		// bei 23 wird ein Schalter generiert
							switchCounter++;	// erhoehen, wir wollen ja variieren!
						}
						else if(lvlData[r][i][j] == 24){
							staticList.get(r).add(new Lavahat(i*32, j*32)); 	// bei 24 wird ein Lavahut generiert
						}
						else if(lvlData[r][i][j] == 25){
							staticList.get(r).add(new Lavapatch(i*32, j*32));		// bei 25 wird ein Lavafeld generiert
						}
						else if(lvlData[r][i][j] == 26){  // Feuerschnecke (Boss)
							creatureList.get(r).add(new Boss_FireSnail(i*32, j*32, 15, 1, 0));  
						}
						else if(lvlData[r][i][j] == 30){
							staticList.get(r).add(new Wizard(32*i, 32*j, 3, 1, 0, 1));
						}
						else if(lvlData[r][i][j] == 31){
							staticList.get(r).add(new Waterpatch(i*32, j*32));
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
			} catch (FileNotFoundException e) {e.printStackTrace();}
			

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
				for(Map.Entry<String, ArrayList<String>> entry : levelDataObj.getlevelRoom(r).entrySet()){
					ArrayList<String> tempParameterList = entry.getValue();
					int xPos,yPos;
					String[] tempStr = entry.getKey().split(",");
					xPos = Integer.parseInt(tempStr[0]);
					yPos = Integer.parseInt(tempStr[1]);
					//Wall
					if(tempParameterList.get(0).equals("1")){
						//Texturparameter
						if(tempParameterList.get(1).equals("0") || tempParameterList.get(1).equals("texture1") || tempParameterList.get(1).equals("")){
							staticList.get(r).add(new SimpleStaticObject(xPos, yPos, true, 0));
						}
					}
					//Creature
					else if(tempParameterList.get(0).equals("2")){
						creatureList.get(r).add(new Creature(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));    // bei 2 wird ein Monsterobjekt generiert
					}else if(tempParameterList.get(0).equals("3") ){
						if(player1 == null){
							playerSpawnX = xPos;
							playerSpawnY = yPos;
							player1  =  new Player(playerSpawnX, playerSpawnY, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3)),  Integer.parseInt(tempParameterList.get(4)), Integer.parseInt(tempParameterList.get(5)), Integer.parseInt(tempParameterList.get(6)));    
							player2  =  new Player(playerSpawnX, playerSpawnY, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3)),  Integer.parseInt(tempParameterList.get(4)), Integer.parseInt(tempParameterList.get(5)), Integer.parseInt(tempParameterList.get(6)));    
						}
						else{
							player1.teleport(xPos, yPos);
							player2.teleport(xPos, yPos);
					}
					}else if(tempParameterList.get(0).equals("4")){
						teleportList.get(r).add(new Teleporter(xPos, yPos,  Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));    
					}else if(tempParameterList.get(0).equals("5")){
						staticList.get(r).add(new TrapObject(xPos, yPos));    
					}else if(tempParameterList.get(0).equals("6")){
						staticList.get(r).add(new GoalObject(xPos, yPos));    
					}else if(tempParameterList.get(0).equals("7")){
						staticList.get(r).add(new PotionObject(xPos, yPos));    
					}else if(tempParameterList.get(0).equals("8")){
						staticList.get(r).add(new MPotionObject(xPos, yPos));    
					}else if(tempParameterList.get(0).equals("9")){
						staticList.get(r).add(new TreasureObject(xPos, yPos));    
					}else if(tempParameterList.get(0).equals("10")){
						creatureList.get(r).add(new Shopkeeper(xPos, yPos,  Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("11")){
						creatureList.get(r).add(new Storyteller(xPos, yPos,  Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3)), Integer.parseInt(tempParameterList.get(4))));
					}
					else if(tempParameterList.get(0).equals("12")){
						staticList.get(r).add(new Healthcontainer(xPos, yPos));
					}
					else if(tempParameterList.get(0).equals("13")){
						//tempParameterList.get(1) = texturID mit Texturbild = img/textures/texturID.png
						//tempParameterList.get(2) = 1 massive ; 0 not massive
						//staticList.get(r).add(new someObject(xPos, yPos, 1));
						//Image image = new ImageIcon("img/textures/"+tempParameterList.get(1)+".png").getImage();
					}
					else if(tempParameterList.get(0).equals("14")){
						creatureList.get(r).add(new Creature_Bow(xPos, yPos, Integer.parseInt(tempParameterList.get(6))*32, Integer.parseInt(tempParameterList.get(7))*32, Integer.parseInt(tempParameterList.get(8)),Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("15")){
						creatureList.get(r).add(new Boss1MP(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("16")){
						staticList.get(r).add(new CheckPoint(xPos, yPos));    
					}
					else if(tempParameterList.get(0).equals("17")){
						creatureList.get(r).add(new Boss2(xPos, yPos, Integer.parseInt(tempParameterList.get(6))*32, Integer.parseInt(tempParameterList.get(7))*32, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("18")){
						creatureList.get(r).add(new Boss3MP(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("19")){
						staticList.get(r).add(new ArrowObject(xPos, yPos));    
					}
					else if(tempParameterList.get(0).equals("20")){
						String[] tempStr2 = tempParameterList.get(1).split(" ");
						staticList.get(r).add(new WallSecret(xPos, yPos, tempStr2)); // bei 20 wird eine "Geheimwand" generiert (Die Schalter auf die diese Tuer hoert muessen angegeben werden)
					}
					else if(tempParameterList.get(0).equals("21")){
						String[] tempStr2 = tempParameterList.get(1).split(" ");
						staticList.get(r).add(new Torch(xPos, yPos, tempStr2));    // bei 21 wird eine Fackel generiert
					}
					else if(tempParameterList.get(0).equals("22")) {
						creatureList.get(r).add(new FireElemental(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));		// bei 22 wird ein Feuerelementar generiert
					}
					else if(tempParameterList.get(0).equals("23")){
						String[] tempStr2 = tempParameterList.get(1).split(" ");
						staticList.get(r).add(new Switch(xPos, yPos,tempStr2));    // bei 23 wird ein Schalter generiert  
					}
					else if(tempParameterList.get(0).equals("24")){
						// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
						staticList.get(r).add(new Lavahat(xPos, yPos)); 	// bei 24 wird ein Lavahut generiert
					}
					else if(tempParameterList.get(0).equals("25")){
						// staticList.get(r).add(new Grass(i*32, j*32));		// bei 0 wird Grass generiert
						staticList.get(r).add(new Lavapatch(xPos, yPos));		// bei 25 wird ein Lavafeld generiert
					}
					else if(tempParameterList.get(0).equals("26")){ // Feuerschnecke (BOSS)
						creatureList.get(r).add(new Boss_FireSnail(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("27")) {	// Eisgegner
						creatureList.get(r).add(new Creature_Ice(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));		// bei 22 wird ein Feuerelementar generiert
					}
					else if(tempParameterList.get(0).equals("30")){
					creatureList.get(r).add(new Wizard(xPos, yPos,  Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("31")){
						staticList.get(r).add(new Waterpatch(xPos, yPos));		// bei 25 wird ein Lavafeld generiert
					}
					else if(tempParameterList.get(0).equals("32")){
						creatureList.get(r).add(new Creature_Water(xPos, yPos, Integer.parseInt(tempParameterList.get(6))*32, Integer.parseInt(tempParameterList.get(7))*32, Integer.parseInt(tempParameterList.get(8)),Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
					}
					else if(tempParameterList.get(0).equals("33")){
						creatureList.get(r).add(new Boss_Water(xPos, yPos, Integer.parseInt(tempParameterList.get(1)), Integer.parseInt(tempParameterList.get(2)), Integer.parseInt(tempParameterList.get(3))));
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
		player1.addGameListener(this);
		player2.addGameListener(this);

		//Konstruiere Interface
		iFace = new GameInterface(this);
		
		//Startzeit f�r Action-Timer initial setzen
		lastAction = Sys.getTime();
		
		// Erster CheckPoint ist der LevelEintritt
		checkPointReached();
		
		//NetzwerkClient starten
		if(adress != "local"){
			//NetzwerkClient starten
			waiting = true;
			iFace.setDialog("Waiting for 2nd Player ... ");
			freeze = true;
			setOpenedInterface(1);
			setDialog(true);
			c = new Client(this, adress);
			c.start();
		}
	}
	
// Methoden
	
	/**
	 * Die Methode CollisionCheck.
	 * Diese Methode fragt die Kollision zwischen Objekten aus der static- und creatureList ab. Bei der Kollision zwischen Spieler und einem Objekt wird .onCollision(player) des jeweiligen Listenelements aufgerufen fuer eine spezielle Kollisionsbehandlung.
	 */
	
	/*
	 * Kollisionsabfrage zwischen den Dungeonobjekten aus staticList und creatureList
	 * bei Spielerkollision wird .onCollision(player) des jeweiligen Listenelements aufgerufen fuer spezielle Kollisionsbehandlung
	 */

	private void collisionCheck(){
		// Ueberpruefen ob der Spieler in einen anderen Raum teleportiert werden soll
		for(int i=0; i<teleportList.get(room).size(); i++){
			// trifft der Spieler auf einen Teleporter
			if(teleportList.get(room).get(i).getBorder().intersects(player1.getBorder()) || teleportList.get(room).get(i).getBorder().intersects(player2.getBorder())){
				// Teleportinformationen abfragen
				int[] portData	=	teleportList.get(room).get(i).getTeleport();
				// Spieler teleportieren
				player1.teleport(portData[1], portData[2]);
				player2.teleport(portData[1], portData[2]);
				// Raumzeiger umsetzen
				room	=	portData[0];
				// Projektile loeschen
				projectileList.clear();
				// Schleife beenden
				break;
			}
		}
		
		// staticlist Kollisionen ueberpruefen
		for(int i=0; i<staticList.get(room).size(); i++){
			// static mit Spieler
			if(staticList.get(room).get(i).getBorder().intersects(player1.getBorder())){
				staticList.get(room).get(i).onCollision(player1);
			}
			if(staticList.get(room).get(i).getBorder().intersects(player2.getBorder())){
				staticList.get(room).get(i).onCollision(player2);
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
					if(creatureList.get(room).get(k) instanceof Boss1){
						c.send("0,boss,"+creatureList.get(room).get(k).dx+","+creatureList.get(room).get(k).dy);
					}
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
			if(creatureList.get(room).get(i).getBorder().intersects(player1.getBorder())){
				creatureList.get(room).get(i).onCollision(player1);
			}
			if(creatureList.get(room).get(i).getBorder().intersects(player2.getBorder())){
				creatureList.get(room).get(i).onCollision(player2);
			}
		}
		
		// uebrige Spieler-Kollisionen
		// Spieler mit Projektilen
		for(int i=0; i<projectileList.size();i++){
			if(projectileList.get(i).getBorder().intersects(player1.getBorder())){
				projectileList.get(i).onCollision(player1);
			}
			if(projectileList.get(i).getBorder().intersects(player2.getBorder())){
				projectileList.get(i).onCollision(player2);
			}
		}
		// Spielerangriff
		if(player1.getAttackState() && player1.getWeapSet() == 0){
			for(int i=0; i<creatureList.get(room).size(); i++){
				// Monsterkollision mit der Waffe
				if(creatureList.get(room).get(i).getBorder().intersects(player1.weapons[0].getBorder())){
					player1.dealDamage(creatureList.get(room).get(i));
				}
			}
		}
		if(player2.getAttackState() && player2.getWeapSet() == 0){
			for(int i=0; i<creatureList.get(room).size(); i++){
				// Monsterkollision mit der Waffe
				if(creatureList.get(room).get(i).getBorder().intersects(player2.weapons[0].getBorder())){
					player2.dealDamage(creatureList.get(room).get(i));
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
	
	/**
	 * Die Methode reload.
	 * Diese Methode laedt das Level neu (ggf. vom letzten CheckPoint, den der Player vor seinem Tod erreicht hat).
	 */
	
	//Methode um das Level neu zu laden und das Spiel von vorne zu beginnen
	public void reload(){
		// Spieler zuruecksetzen
		player1.reset();
		player2.reset();
		// Raum setzen
		room	=	roomToRespawn;
		// Leben vom Spieler abziehen
		if(lose==true){
			player1.giveStatInventoryObject(0, -1);
			player2.giveStatInventoryObject(0, -1);
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
	
	/**
	 * Die Methode play.
	 * Diese Methode ist der Gameloop, der die Funktionen input, engine und render aufruft, die das Spielen ermoeglichen.
	 */

	// Game-Schleife
	public void play(){
		while(!close){
			if(Display.isCloseRequested()){
				close = true;
			}			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			
			if(!waiting){
				input();
				engine();
			}
			render();
			
			Display.update();
			Display.sync(60);
		}
		c.end();	//Netzwerk-Client stoppen
		Display.destroy();
		super.gm.setVisible(true);
	}
	
	/**
	 * Die Methode input.
	 * Diese Methode implementiert die Steuerung und mapped die verschiedenen Tasten und Funktionen im Spiel zusammen.
	 */
	
	// Eingaben abfragen
	public void input(){
		// KeyboardEvents
		
		// Tastatur-Events w�hrend des Spiels
		if(!lose && !clear && !gameover){		
			while(Keyboard.next()){				
				int k = Keyboard.getEventKey();
				
				if(Keyboard.getEventKeyState()){
					switch(k){
						case Keyboard.KEY_UP:
							player1.keyPressed(Keyboard.KEY_UP);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_UP, player1);
							}
							c.send("1,0,"+(int)player1.dx+","+(int)player1.dy);
							break;
						case Keyboard.KEY_DOWN:
							player1.keyPressed(Keyboard.KEY_DOWN);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_DOWN, player1);
							}
							c.send("1,0,"+(int)player1.dx+","+(int)player1.dy);
							break;
						case Keyboard.KEY_RIGHT:
							player1.keyPressed(Keyboard.KEY_RIGHT);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_LEFT, player1);
							}
							c.send("1,0,"+(int)player1.dx+","+(int)player1.dy);
							break;
						case Keyboard.KEY_LEFT:
							player1.keyPressed(Keyboard.KEY_LEFT);
							if(dialog){
								iFace.buttonAction(Keyboard.KEY_LEFT, player1);
							}
							c.send("1,0,"+(int)player1.dx+","+(int)player1.dy);
							break;
						case 28:
							if(dialog){
								iFace.buttonAction(28, player1);
							}
							break;
						case Keyboard.KEY_SPACE:
							if((lose || clear) && !gameover ){
								c.send("1,2,5");
								reload();
							}
							else if(!gameover){
								// Spieler Angreifen lassen
								c.send("1,2,5");
								player1.attack();
							}
							break;
						case Keyboard.KEY_E:
							c.send("1,2,2");
							player1.keyPressed(Keyboard.KEY_E);
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
							c.send("1,2,4");
							player1.swapWeapons();
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
							c.send("1,2,1");
							player1.spellCast();
							break;
						case Keyboard.KEY_A:
							c.send("1,2,0");
							if(player1.getStatInventoryObjectCount(2)>0){
								player1.getHealed(2);
								player1.giveStatInventoryObject(2, -1);
							}
							break;
						case Keyboard.KEY_S:
							c.send("1,2,3");
							if(player1.getStatInventoryObjectCount(3)>0){
								player1.fillmana(1);
								player1.giveStatInventoryObject(3, -1);
							}
							break;
					}
				}
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				player1.keyReleased(Keyboard.KEY_UP);
				c.send("1,0,"+(int)player1.dx+","+(int)player1.dy);
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				player1.keyReleased(Keyboard.KEY_LEFT);
				c.send("1,0,"+(int)player1.dx+","+(int)player1.dy);
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_E)){
				player1.keyReleased(Keyboard.KEY_E);
			}
		}
		// Tastatur-Events bei Loose/Clear
		else{
			while(Keyboard.next()){
				
				int k = Keyboard.getEventKey();
				
				if(Keyboard.getEventKeyState()){
					switch(k){
						case 28:
							//Beenden
							if(lose || clear || gameover){
								c.send("1,6");
								close = true;
							}
							break;
						case Keyboard.KEY_SPACE:
							// Naechstes Level
							c.send("1,2,5");
							if(clear){
								currLvl++;
								if(currLvl <= 5){
									loadLevel("Level"+currLvl);
								}
							}
							// Reload
							else if(!gameover){
								reload();
							}
							break;
						case Keyboard.KEY_ESCAPE:
							// Beenden
							close = true;
							break;
					}
				}
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				player1.keyReleased(Keyboard.KEY_UP);
			}
			if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				player1.keyReleased(Keyboard.KEY_LEFT);
			}
		}
	}
	
	/**
	 * Die Methode setPalyerDirection
	 * Diese Methode benutzt der Netzwerk-Client, um dem 2. Spieler die Directions zu uebergeben.
	 * @param x Die Methode erwartet den Integer x fuer die x-Direction
	 * @param y Die Methode erwartet den Integer y fuer die y-Direction.
	 */
	
	public void setPlayerDirection(int x, int y){
		player2.setDX(x);
		player2.setDY(y);
	}
	
	/**
	 * Die Methode input2.
	 * Diese Methode implementiert die Steuerung und mapped die verschiedenen Tasten und Funktionen im Spiel zusammen (Fuer Spieler2).
	 */
	
	// Eingaben fuer Player2
	public void input2(int key){
		
		//Direction
		if(!lose && !clear && !gameover){
			if(key == 5){
				if(!gameover){
					player2.attack();
				}
			}
			else if(key == 2){
				player2.keyPressed(Keyboard.KEY_E);
			}
			else if(key == 4){
				player2.swapWeapons();
			}
			else if(key == 1){
				player2.spellCast();
			}
			else if(key == 0){
				if(player2.getStatInventoryObjectCount(2)>0){
					player2.getHealed(2);
					player2.giveStatInventoryObject(2, -1);
				}
			}
			else if(key == 3){
				if(player2.getStatInventoryObjectCount(3)>0){
					player2.fillmana(1);
					player2.giveStatInventoryObject(3, -1);
				}
			}
		}
		// Tastatur-Events bei Loose/Clear
		else{
			if(key == 6){
				//Beenden
				if(lose || clear || gameover){
					close = true;
				}
			}
			if(key == 5){
				// Naechstes Level
				if(clear){
					currLvl++;
					if(currLvl <= 5){
						loadLevel("Level"+currLvl);
					}
				}
				// Reload
				else if(!gameover){
					reload();
				}
			}
			player2.keyReleased(Keyboard.KEY_UP);
			player2.keyReleased(Keyboard.KEY_LEFT);
		}
	}
	
	/**
	 * Die Methode engine.
	 * Diese Methode bearbeitet die Spielmechaniken und Ereignisse, wie die Spieler- und Kreaturenbewegung, die Kollisionsabfrage, Projektile, etc.
	 */
	
	// Game-Logic
	public void engine(){
		// Level gefroren?
		if(!freeze){
			// ueberpruefen ob der Spieler lebt und noch Extraleben zur verfuegung hat
			if(player1.getStatInventoryObjectCount(0)==0 && player1.getHP()<=0 || player2.getStatInventoryObjectCount(0)==0 && player2.getHP()<=0){
				gameover = true;
			}
			// ueberpruefen ob der Spieler noch lebt
			else if(player1.getHP()<=0 || player2.getHP()<=0){
				lose	=	true;  // wird gesetzt wenn der Spieler stirbt
			}

			// Spielerbewegung
			player1.move();
			System.out.println((Sys.getTime()-lastPositionSent));
			if(((Sys.getTime()-lastPositionSent)) >= 30){
				System.out.println("sent");
				c.send("1,1,"+player1.x+","+player1.y);
				lastPositionSent = Sys.getTime();
			}
			player2.checkDirection();
			
			// kreaturenbewegung
			for(int i=0; i<creatureList.get(room).size(); i++)
				creatureList.get(room).get(i).move();
			
			// projektile
			for(int i=0; i<projectileList.size(); i++)
				projectileList.get(i).move();

			// Interaktionsabfragen Player1
			if(player1.wantsToInteract() && !alreadyInteracted1){
				int[] coords=player1.getCenter();
				int px=coords[0];
				int py=coords[1]; 
				
				for(int i=0; i<staticList.get(room).size(); i++)
					staticList.get(room).get(i).interaction(px, py);
				for(int i=0; i<creatureList.get(room).size(); i++)
					creatureList.get(room).get(i).interaction(px, py);
				
				alreadyInteracted1=true;
			}
			else if(!player1.wantsToInteract()){
				alreadyInteracted1=false;
			}
			
			// Interaktionsabfragen Player2
			if(player2.wantsToInteract() && !alreadyInteracted2){
				int[] coords=player2.getCenter();
				int px=coords[0];
				int py=coords[1]; 
				
				for(int i=0; i<staticList.get(room).size(); i++)
					staticList.get(room).get(i).interaction(px, py);
				
				alreadyInteracted2=true;
				player2.keyReleased(Keyboard.KEY_E);
			}
			else if(!player2.wantsToInteract()){
				alreadyInteracted2=false;
			}	
				
			// Kollisionsabfrage
			collisionCheck();			
			
			//Spiel Aktionen
			if(((Sys.getTime()-lastAction)/Sys.getTimerResolution()) >= 1){

				c.send("alive");	//Lebenssignal an Server senden
				int[] playerCenter	=	player1.getCenter();
				for(int i = 0; i < creatureList.get(room).size(); i++){
					// Alle lebenden Bogen-Gegner Schie�en
					if(creatureList.get(room).get(i) instanceof Creature_Bow && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					}
					else if(creatureList.get(room).get(i) instanceof Boss2 && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					}
					else if(creatureList.get(room).get(i) instanceof Boss3 && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					}
					else if(creatureList.get(room).get(i) instanceof FireElemental && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					}
					else if(creatureList.get(room).get(i) instanceof Boss_FireSnail && creatureList.get(room).get(i).getCurrState() == 1){
						creatureList.get(room).get(i).action(playerCenter[0], playerCenter[1]);
					} 
				}
				lastAction = Sys.getTime();
			}
		}
	}
	
	/**
	 * Die Methode render.
	 * Diese Methode zeichnet das Level, also alle Objekte sowie das Interface und Win/lose/Gameoverscreen.
	 */
	
	// Zeichen/Render-Funktion
	public void render(){
		// Koordinatensystem an Spieler anpassen
		glTranslatef(Display.getWidth()/2-player1.getTX(), Display.getHeight()/2-player1.getTY(), 0);
		
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
		player1.draw();
		player2.draw();
		// Fuer folgende Texturen das Koordinatensystem wieder begradigen
		glTranslatef(-(Display.getWidth()/2-player1.getTX()), -(Display.getHeight()/2-player1.getTY()), 0);
		
		// Interface zeichnen
		iFace.paint(player1, fullscreen);
		
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
	
	//Waiting setzen
	public void setWaiting(boolean w){
		waiting	=	w;
	}

	/**
	 * Die Methode checkPointReached.
	 * Diese Methode speichert alle Zustaende, die beim erreichen eines CheckPoints gesichert werden muessen, damit der Player dort neu anfangen kann.
	 */
	
	@Override
	public void checkPointReached(){
		// Spieler speichern
		player1.setResetValues();
		player2.setResetValues();
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
}
