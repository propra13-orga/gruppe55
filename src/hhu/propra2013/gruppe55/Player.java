package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.event.*;

public class Player extends LivingObject {

	// Variable zur Zielabfrage
	// Variablen fuer Handhabung von Unverwundbarkeit
	private int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	// Variablen fuer den Angriff
	public Weapon[] weapons	=	new Weapon[3];	// Waffen in Besitz
	private int currEquipped	=	0;			// Zeige auf aktuell ausgerüstetes Waffenset (0= Nahkampf; 1= Fernkampf)
	private boolean attacking	=	false;		// Waehrend einer Attacke true
	private int[][][] handOffsets	=	new int[4][2][4];	// Offsets fuer die Positionen der Spielerhaende
	// Besitztuemer des Spielers
	private int[] statInventory;		//Inventar für statische Objekte (Gold, Tränke, Pfeile)
	
	// Konstruktor
    public Player(int spawnX, int spawnY, int h, int atk, int def, int energy, int mana, int l) {
		super(spawnX, spawnY, h, atk, def, energy, mana);
		
		// statInventory Slots
		// 0: Lives
		// 1: Gold
		// 2: HPPotion
		// 3: MPPotion
		// 4: Arrows
		statInventory = new int[5];
		statInventory[0] = 1;
		statInventory[1] = statInventory[2] = statInventory[3] = 0;
		statInventory[4] = 15;
		
		// States definieren
		state[0].changeImg(Data_Img.dead);			// Aussehen bei Tod
		state[0].changeVisibility(true);		// sichtbar machen
		state[0].defineOffset(0,0,32,32, 0);	// Grabsteinhitbox auf 0 setzen
		// Lebendig
		state[1]	=	new State(Data_Img.player_f, Data_Img.player_l, Data_Img.player_r, Data_Img.player_b, true, false, true);
		state[1].defineOffset(15,5,0,5, 0); 	// Hitbox vorne
		state[1].defineOffset(15,5,0,5, 1); 	// Hitbox links
		state[1].defineOffset(15,5,0,5, 2); 	// Hitbox rechts
		state[1].defineOffset(15,5,0,5, 3); 	// Hitbox hinten
		// Angriff
		state[2]	=	new State(Data_Img.player_f_atk, Data_Img.player_l_atk, Data_Img.player_r_atk, Data_Img.player_b_atk, true, false, true);
		state[2].defineOffset(15,5,0,5, 0); 	// Hitbox vorne
		state[2].defineOffset(15,5,0,5, 1); 	// Hitbox links
		state[2].defineOffset(15,5,0,5, 2); 	// Hitbox rechts
		state[2].defineOffset(15,5,0,5, 3); 	// Hitbox hinten
		
		// 1. State aktivieren (lebendig)
		switchState(1);
		
		// Waffen initialisieren
		weapons[0]	=	new Weapon();	// Haupthand
		weapons[1]	=	new SimpleShield();	// Nebenhand
		weapons[2]	=	new SimpleBow();	// Fernkampfwaffe
		// drei Schwerter, Rorona Zorro, is that you? (Ist es nicht RoroNOA Zorro, jannik? :3)
		
		// Schadenswerte uebernehmen
    	minDmg	=	weapons[0].getMinDmg();
    	maxDmg	=	weapons[0].getMaxDmg();
		
		
		// Haende setzen fuer state[1] = laufender Spieler (ignoriere 0, tote Spieler haben keine Waffen!)
    	// vorne
		handOffsets[0][0][0]	=	2;	// X-Offset der Haupthand
		handOffsets[0][0][1]	=	18;	// Y-Offset der Haupthand
		handOffsets[0][0][2]	=	22;	// X-Offset der Nebenhand
		handOffsets[0][0][3]	=	18;	// Y-Offset der Nebenhand
    	// links
		handOffsets[1][0][0]	=	5;	// X-Offset der Haupthand
		handOffsets[1][0][1]	=	17;	// Y-Offset der Haupthand
		handOffsets[1][0][2]	=	12;	// X-Offset der Nebenhand
		handOffsets[1][0][3]	=	19;	// Y-Offset der Nebenhand
    	// rechts
		handOffsets[2][0][0]	=	12;	// X-Offset der Haupthand
		handOffsets[2][0][1]	=	19;	// Y-Offset der Haupthand
		handOffsets[2][0][2]	=	19;	// X-Offset der Nebenhand
		handOffsets[2][0][3]	=	18;	// Y-Offset der Nebenhand
    	// hinten
		handOffsets[3][0][0]	=	21;	// X-Offset der Haupthand
		handOffsets[3][0][1]	=	18;	// Y-Offset der Haupthand
		handOffsets[3][0][2]	=	3;	// X-Offset der Nebenhand
		handOffsets[3][0][3]	=	18;	// Y-Offset der Nebenhand
		// das Gleiche fuer state[2] = angreifender Spieler
		// vorne
		handOffsets[0][1][0]	=	5;	// X-Offset der Haupthand
		handOffsets[0][1][1]	=	22;	// Y-Offset der Haupthand
		handOffsets[0][1][2]	=	22;	// X-Offset der Nebenhand
		handOffsets[0][1][3]	=	16;	// Y-Offset der Nebenhand
		// links
		handOffsets[1][1][0]	=	2;	// X-Offset der Haupthand
		handOffsets[1][1][1]	=	17;	// Y-Offset der Haupthand
		handOffsets[1][1][2]	=	14;	// X-Offset der Nebenhand
		handOffsets[1][1][3]	=	19;	// Y-Offset der Nebenhand
    	// rechts
		handOffsets[2][1][0]	=	24;	// X-Offset der Haupthand
		handOffsets[2][1][1]	=	17;	// Y-Offset der Haupthand
		handOffsets[2][1][2]	=	19;	// X-Offset der Nebenhand
		handOffsets[2][1][3]	=	18;	// Y-Offset der Nebenhand
    	// hinten
		handOffsets[3][1][0]	=	18;	// X-Offset der Haupthand
		handOffsets[3][1][1]	=	15;	// Y-Offset der Haupthand
		handOffsets[3][1][2]	=	4;	// X-Offset der Nebenhand
		handOffsets[3][1][3]	=	20;	// Y-Offset der Nebenhand

	}
    
    // Methode zum Agriff
    public void attack(){
    	// Wenn bereits im Angriff, kein neuer Angriff
    	if(attacking) return;
    	
    	// leite Angriff ein
    	attacking	=	true;
    	
    	// Angriffsmodus aktivieren
    	switchState(2);	
    	
    	// aktuelle Angriffswaffe
    	final int aw	=	(currEquipped==0) ? 0 : 2;
    	
    	// Angriffstimer setzen!
    	new Thread(){
    		public void run(){
    			// Mit der Waffe angreifen (grafisch)
    			weapons[aw].attack();
    			    			
    			// Sleep-Timer setzen
    			try {
					Thread.sleep(weapons[aw].getAtkTime());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			// Fernkampf? Schiessen!
    			if(aw==2)
    				shoot();
    			
    			// Angriff abbrechen
    			weapons[aw].stopAttack();
    			attacking	=	false;
    			
    			// Bild aendern
    			if(hp<=0)
    				switchState(0);	// Totenbild
    			else
    				switchState(1);	// ansonsten normal
    		}
    	}.start();
    }
    
    // Methode zum Pfeile Schießen
    public void shoot(){
    	// genug Pfeile?
    	if(statInventory[4]<=0)	return;
    	
    	// Position bestimmen
    	int x	=	this.x;
    	int y	=	this.y;
    	// je nach Richtung
    	switch(direction){
    	case	1:	// nach links
    		y+=state[currState].getImg().getHeight(null)/2;	// vertikal zentrieren
    		x-=state[currState].getImg().getWidth(null);
    		break;
    	case	2:	// nach rechts
    		x+=state[currState].getImg().getWidth(null);	// auch wirklich rechts vom spieler
    		y+=state[currState].getImg().getHeight(null)/2;	// vertikal zentrieren
    		break;
    	case	3:	// nach oben
    		x+=state[currState].getImg().getWidth(null)/2;	// horizontal zentrieren
    		break;
    	case	0:	// nach unten
    	default:
    		x+=state[currState].getImg().getWidth(null)/2;	// vertikal zentrieren
    		y+=state[currState].getImg().getHeight(null);
    		break;
    	}
    	
    	// Event (und damit den Pfeil) feuern!
    	for(GameEventListener gel : evtList){
    		// Winkel berechnen
    		/*
    		 * 2->0
    		 * 3->90
    		 * 1->180
    		 * 0->270
    		 */
    		int angle;
    		if(direction==2)
    			angle	=	0;
    		else if(direction==1)
    			angle	=	180;
    		else if(direction==3)
    			angle	=	270;
    		else
    			angle	=	90;
    		
			gel.shootProjectile(new Projectile(x,y,angle, atk+weapons[2].getMaxDmg()));
		}
    	
    	// Pfeil abziehen
    	statInventory[4]--;
    }
    
    // Waffenset wechseln
    public void swapWeapons(){
    	// Gibt es eine Waffe, auf die gewechselt wird?
    	switch(currEquipped){
    	case	0:	// keine Fernkampfwaffe
    		currEquipped	=	1;
    		if(weapons[2]==null)
    			return;
    		break;
    	case	1:	// keine Nahkampfwaffen
    	default:
    		currEquipped	=	0;
    		if(weapons[0]==null && weapons[1]==null)
    			return;
    		break;
    	}
    	
    	// Schadenswerte uebernehmen
    	minDmg	=	weapons[2*currEquipped].getMinDmg();
    	maxDmg	=	weapons[2*currEquipped].getMaxDmg();
    }
    
    // Methode um den Spieler an eine bestimmte Stelle zu teleportieren
    public void teleport(int x, int y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
    // Methode zum Spieler-Zeichnen
    public void draw(Graphics2D g2d){
    	// Sichtbar?
    	if(!state[currState].visible) return;
    	
    	// Den Spieler zeichnen
    	if(currState<=0){	// Tote brauchen keine Waffen
    		g2d.drawImage(state[currState].getImg(), x,  y,  null);
    		return;
    	}
    	
    	// Richtungen der Waffen anpassen
    	if(weapons[0]!=null) weapons[0].changeDirection(direction);
    	if(weapons[1]!=null) weapons[1].changeDirection(direction);
    	if(weapons[2]!=null) weapons[2].changeDirection(direction);
    	// Nebenhand hinter Spieler zeichnen bei rechts und hinten
    	if(direction>=2 && weapons[currEquipped+1]!=null){
    		weapons[currEquipped+1].draw(g2d, x+handOffsets[direction][currState-1][2],y+handOffsets[direction][currState-1][3]);
    	}
    	// Haupthand hinter den Spieler zeichnen bei links und hinten
    	if((direction==3 || direction == 1) && currEquipped==0 && weapons[0]!=null){	
    		weapons[0].draw(g2d, x+handOffsets[direction][currState-1][0], y+handOffsets[direction][currState-1][1]);
    	}
    	// Spieler zeichnen
    	super.draw(g2d);
    	// Haupthand vor den Spieler zeichnen bei rechts und vorne
    	if((direction ==0 || direction==2) && currEquipped==0 && weapons[0]!=null){
    		weapons[0].draw(g2d, x+handOffsets[direction][currState-1][0], y+handOffsets[direction][currState-1][1]);
    	}
    	// Nebenhand vor den Spieler zeichnen bei links und vorne
    	if((direction==0 || direction==1) && weapons[currEquipped+1]!=null){
    		weapons[currEquipped+1].draw(g2d, x+handOffsets[direction][currState-1][2],y+handOffsets[direction][currState-1][3]);
    	}
    }
    
    // Methode um den Spieler "wiederzubeleben"
    public void revive(){
    	hp	=	hpMax;
    	energy = energyMax;
    	mana = manaMax;
    	switchState(1);
    	// derzeit wird hier kein weiterer Code benoetigt (das kann sich im Laufe des Projekts aendern)
    }
    
    // Abfrage des Angriffsstatus
    public boolean getAttackState(){
    	return attacking;
    }
    
    // Abfrage des aktuellen ATK-Werts des Spielers
    public int getAtk(){
    	return atk;
    }
    // Player Gold hinzufügen
    public void giveStatInventoryObject(int o, int a){
    	statInventory[o]+=a;
    }
    
    // Player Geld abfragen
    public int getStatInventoryObjectCount(int o){
    	return(statInventory[o]);
    }
    
    // Abfrage des aktuellen Waffensets
    public int getWeapSet(){
    	return currEquipped;
    }
    
    // Methoden fuer die X und Y-Koordinaten
    public int getTX(){
    	return x-state[currState].getImg().getWidth(null)/2;
    }
    public int getTY(){
    	return y-state[currState].getImg().getHeight(null)/2;
    }

// Methoden zur Steuerung des Spielers per Keyboard
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {dx = -1;}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {dx = +1;}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {dy = +1;}
		if (e.getKeyCode() == KeyEvent.VK_UP) {dy = -1;}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {dx = 0;}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {dx = 0;}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {dy = 0;}
		if (e.getKeyCode() == KeyEvent.VK_UP) {dy = 0;}
	}
	

}