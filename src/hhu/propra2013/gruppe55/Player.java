package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.event.*;

public class Player extends LivingObject {

	// Variable zur Zielabfrage
	protected boolean goal	=	false;
	// Variablen fuer Handhabung von Unverwundbarkeit
	private int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	// Variablen fuer den Angriff
	public Weapon[] weapons	=	new Weapon[3];	// Waffen in Besitz
	private int currEquipped	=	0;			// Zeige auf aktuell ausgerüstetes Waffenset (0= Nahkampf; 1= Fernkampf)
	private boolean attacking	=	false;		// Waehrend einer Attacke true
	private int[][] handOffsets	=	new int[2][4];	// Offsets fuer die Positionen der Spielerhaende
	private int dmg, critval, minDmg, maxDmg, gold;
	private boolean crit;
	
	// Konstruktor
    public Player(int spawnX, int spawnY, int h, int atk, int def, int energy, int mana) {
		super(spawnX, spawnY, h, atk, def, energy, mana);
		
		// States definieren
		state[0].changeImg(Data.dead);			// Aussehen bei Tod
		state[0].changeVisibility(true);		// sichtbar machen
		
		state[1].changeImg(Data.player);		// Aussehen bei Leben
		state[1].defineOffset(15,3,0,5); 		// Hitbox des Spielers angepasst
		
		state[2].changeImg(Data.player_f_atk);		// Aussehen bei Angriff
		state[2].defineOffset(15,3,0,5); 		// und nochmal Hitbox des Spielers angepasst
		state[2].changeMoveable(true);			// beweglich machen
		state[2].changeVisibility(true);		// sichtbar machen
		
		// 1. State aktivieren (lebendig)
		switchState(1);
		
		// Waffen initialisieren
		weapons[0]	=	new Weapon();	// Haupthand
		weapons[1]	=	null;	// Nebenhand
		weapons[2]	=	null;	// Fernkampfwaffe
		
		// Haende setzen fuer state[1] (ignoriere 0, tote Spieler haben keine Waffen!)
		handOffsets[0][0]	=	2;	// X-Offset der Haupthand
		handOffsets[0][1]	=	18;	// Y-Offset der Haupthand
		handOffsets[0][2]	=	22;	// X-Offset der Nebenhand
		handOffsets[0][3]	=	18;	// Y-Offset der Nebenhand
		// das Gleiche fuer state[2]
		handOffsets[1][0]	=	5;	// X-Offset der Haupthand
		handOffsets[1][1]	=	25;	// Y-Offset der Haupthand
		handOffsets[1][2]	=	22;	// X-Offset der Nebenhand
		handOffsets[1][3]	=	16;	// Y-Offset der Nebenhand
		// Die Schadenswerte der ausgewaehlten Waffe einlesen
		minDmg = weapons[currEquipped].getminDmg(); // minimaler Schaden
		maxDmg = weapons[currEquipped].getmaxDmg(); // maximaler Schaden
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
    	final int aw	=	(currEquipped<=1) ? 0 : 2;
    	
    	// Angriffstimer setzen!
    	new Thread(){
    		public void run(){
    			// Mit der Waffe angreifen
    			weapons[aw].attack();
    			
    			// Sleep-Timer setzen
    			try {
					Thread.sleep(weapons[aw].getAtkTime());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			// Angriff abbrechen
    			weapons[aw].stopAttack();
    			switchState(1);
    			attacking	=	false;
    		}
    	}.start();
    }
    
    // Berechnung des Schadens bei Treffer (noch nicht vollstaendig - siehe Readme!)
    
    // Funktion um den Critwert zu berechnen
	public int critcalc(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
	// Funktion um den Schadenswert zu berechnen
	public int dmgcalc(int low, int high) {
		if(crit==true){														// Wenn der Treffer kritisch ist...
			return (int) (Math.random() * (high - low) + low) + 2*atk;}		// ... dann Randomzahl von mindmg bis maxdmg(-1) + 2*atk vom Spieler
		else 																// Wenn der Treffer nicht kritisch ist...
			return (int) (Math.random() * (high - low) + low) + atk;		// ... dann Randomzahl von mindmg bis maxdmg(-1) + atk vom Spieler
		}
    // Funktion die den Schaden mit jeweiliger Waffe ausrechnet
	public void damage(){
		critval = critcalc(1,11);											// Berechnung des Critwerts 1-10
		if(critval <=3)														// Wenn der gerollte Wert <= 3 dann wird crit wahr
			crit = true;
		else 
			crit = false;													// Ansonsten crit = False
		dmg = dmgcalc(minDmg, maxDmg);										// Aufrufen der Schadensfunktion mit den Werten der jeweiligen Waffen
		
	}
    
	public int getdmg(){
		return dmg;
	}
    
    
    // Methode um den Spieler an eine bestimmte Stelle zu teleportieren
    public void teleport(int x, int y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
    // Methode zum Spieler-Zeichnen
    public void draw(Graphics2D g2d, int x, int y){
    	// Sichtbar?
    	if(!state[currState].visible) return;
    	
    	// Den Spieler zeichnen
    	g2d.drawImage(state[currState].img, x,  y,  null);
    	
    	// noch lebendig?
    	if(currState<=0) return;	// Tote brauchen keine Waffen
    	
    	// Waffe(n) zeichnen
    	for(int i=0; i<2-currEquipped; i++){ // 2 Durchläufe bei Nahkampf, einer bei Fernkampf

    	// Waffe angelegt?
    	if(!(weapons[currEquipped+i]==null))
    		// Wenn ja, Waffe zeichnen!
    		weapons[currEquipped+i].draw(g2d, x+handOffsets[currState-1+i][0],y+handOffsets[currState-1+i][1]);
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
    // Spieler beruehrt das ziel und gewinnt das spiel
    public void reachGoal(){ 
    	goal = true;
    }
    // Wert der goal variable wird uebergeben
    public boolean getGoal(){
    	return goal;
    }
    
    // Abfrage des Angriffsstatus
    public boolean getAttackState(){
    	return attacking;
    }
    
    // Abfrage des aktuellen ATK-Werts des Spielers
    public int getAtk(){
    	return atk;
    }
    // Player wird vom gebenden Objekt Geld hinzugefügt
    public void giveMoney(int value){
    	gold+=value;
    }
    
    //Geld des Spielers abgfragen
    public int getMoney(){
    	return(gold);
    }
    
    // Abfrage des aktuellen Waffensets
    public int getWeapSet(){
    	return currEquipped;
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