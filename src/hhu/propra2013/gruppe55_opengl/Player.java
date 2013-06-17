package hhu.propra2013.gruppe55_opengl;

import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

public class Player extends LivingObject {

	// Variablen fuer Handhabung von Unverwundbarkeit
	private int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	// Variablen fuer den Angriff
	public Weapon[] weapons	=	new Weapon[3];	// Waffen in Besitz
	private int currEquipped	=	0;			// Zeige auf aktuell ausger�stetes Waffenset (0= Nahkampf; 1= Fernkampf)
	private boolean attacking	=	false;		// Waehrend einer Attacke true
	private int[][][] handOffsets	=	new int[4][2][4];	// Offsets fuer die Positionen der Spielerhaende
	// Zaubervariablen
	private SpellObject spell;

	// Besitztuemer des Spielers
	private int[] statInventory;		//Inventar fuer statische Objekte (Gold, Traenke, Pfeile)
	
	// Konstruktor
    public Player(double spawnX, double spawnY, int h, int atk, int def, int energy, int mana, int l) {
    	super(spawnX, spawnY, h, atk, def);

    	this.mana = this.manaMax = mana;
    	this.energy = this.energyMax = energy;

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
		state[0].changeTexture(Data_Textures.dead);			// Aussehen bei Tod
		state[0].changeVisibility(true);		// sichtbar machen
		state[0].defineOffset(0,0,32,32, 0);	// Grabsteinhitbox auf 0 setzen
		// Lebendig
		state[1]	=	new State(Data_Textures.player_f, Data_Textures.player_l, Data_Textures.player_r, Data_Textures.player_b, true, false, true);
		state[1].defineOffset(15,5,1,10, 0); 	// Hitbox vorne
		state[1].defineOffset(15,5,1,10, 1); 	// Hitbox links
		state[1].defineOffset(15,5,1,10, 2); 	// Hitbox rechts
		state[1].defineOffset(15,5,1,10, 3); 	// Hitbox hinten
		// Angriff
		state[2]	=	new State(Data_Textures.player_f_atk, Data_Textures.player_l_atk, Data_Textures.player_r_atk, Data_Textures.player_b_atk, true, false, true);
		state[2].defineOffset(15,5,1,10, 0); 	// Hitbox vorne
		state[2].defineOffset(15,5,1,10, 1); 	// Hitbox links
		state[2].defineOffset(15,5,1,10, 2); 	// Hitbox rechts
		state[2].defineOffset(15,5,1,10, 3); 	// Hitbox hinten
		
		// 1. State aktivieren (lebendig)
		switchState(1);
		
		// Waffen initialisieren
		weapons[0]	=	new Weapon();	// Haupthand
		weapons[1]	=	new SimpleShield();	// Nebenhand
		weapons[2]	=	new SimpleBow();	// Fernkampfwaffe
		// drei Schwerter, Rorona Zorro, is that you? (Ist es nicht RoroNOA Zorro, jannik? :3)

		// Zauber enbenfalls initialisieren
		spell	=	new SpellObject(x,y);

		
		// Schadenswerte uebernehmen
    	minDmg	=	weapons[0].getMinDmg();
    	maxDmg	=	weapons[0].getMaxDmg();
		
    	// Resetwerte
    	resetValues	=	new int[12];

		
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
    	final int aw	=	(currEquipped<=0) ? 0 : 2;
    	
    	// Angriffstimer setzen!
    	new Thread(){
    		public void run(){
    			// Mit der Waffe angreifen (grafisch)
    			weapons[aw].attack();
    			
    			// Fernkampf? Schiessen!
    			if(aw==2)
    				shoot();
    			
    			// Sleep-Timer setzen
    			try {
					Thread.sleep(weapons[aw].getAtkTime());
				}catch (InterruptedException e) {e.printStackTrace();}
    			
    			// Angriff abbrechen
    			weapons[aw].stopAttack();
    			switchState(1);
    			attacking	=	false;
    		}
    	}.start();
    }
    
    // Methode zum Pfeile Schiessen
	public void shoot(){
		// genug Pfeile?
		if(statInventory[4]<=0)	return;

		// Position bestimmen
		int x	=	(int)this.x;
		int y	=	(int)this.y;
		// je nach Richtung
		switch(direction){
		case	1:	// nach links
    		y+=state[currState].getTexture().getTextureHeight()/2-1;	// vertikal zentrieren
    		x-=state[currState].getTexture().getTextureWidth(); 
    		break;
    	case	2:	// nach rechts
    		x+=state[currState].getTexture().getTextureWidth();	// auch wirklich rechts vom spieler
    		y+=state[currState].getTexture().getTextureHeight()/2;	// vertikal zentrieren
    		break;
    	case	3:	// nach oben
    		x+=state[currState].getTexture().getTextureWidth()/2-2;	// horizontal zentrieren
    		y-=2;
    		break;
    	case	0:	// nach unten
    	default:
    		x+=state[currState].getTexture().getTextureWidth()/2-2;	// vertikal zentrieren
    		y+=state[currState].getTexture().getTextureHeight();
    		break;
    	}
    	
		shoot(calcPlayerAngle());
    	
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
    
	// Methode zur Winkelberechnung des Spielerblickes
	private int calcPlayerAngle(){
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

		return angle;
	}

	// Methode zum Zaubern
	public void spellCast(){
		// was zaubern wir hier eigentlich?
		if(spell==null) return;

		// Zauber bereits mit Listener verbunden?
		if(!spell.isListened()){
			// GameEventListener hinzufuegen
			for(GameEventListener gel : evtList){
				spell.addGameListener(gel);
			}
		}

		// Manakosten berechnen
		int manaCost	=	spell.getManaCost();
		int healthCost	=	spell.getHealthCost();
	
		// wir koennen diesen Zauber nicht wirken!
		if(mana<manaCost || hp <=healthCost) return;

		// Zauberkosten berechnen
		mana-=manaCost;
		hp-=healthCost;

		// eventuelle Heilung wirken
		getHealed(spell.getHeal());

		// Zauber vorbereiten
		int angle	=	calcPlayerAngle();
		int hOffset	=	(int)Math.cos(Math.toRadians(angle));	// Damit wir nicht im Spieler spawnen
		int vOffset	=	(int)Math.sin(Math.toRadians(angle));	// wie oben -^

		// zauber nun wirken
		spell.cast(x+hOffset*32+vOffset*vOffset*state[currState].getTexture().getTextureWidth()/3,y+vOffset*32+hOffset*hOffset*state[currState].getTexture().getTextureHeight()/3,angle,atk);
	}

    
    // Methode um den Spieler an eine bestimmte Stelle zu teleportieren
    public void teleport(int x, int y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
	// Methode zum Setzen der Reset-Werte
	public void setResetValues(){
		super.setResetValues();		// Werte 1-6 abhandeln
		resetValues[6]	=	hpMax;	// 7.Wert: maximale HP
		resetValues[7]	=	manaMax;// 8.Wert: maximales Mana
		resetValues[8]	=	statInventory[1];	// 9.Wert: Gold
		resetValues[9]	=	statInventory[4];	// 10.Wert: Pfeile
		resetValues[10]	=	statInventory[2];	// 11.Wert: Heiltraenke
		resetValues[11]	=	statInventory[3];	// 12.Wert: Manatraenke
	}

	// Methode zum zuruecksetzen des Objektes
	public void reset(){
		super.reset();				// Werte 1-6 abhandeln
		// Max Mana und Max-HP
		hpMax	=	resetValues[6];
		manaMax	=	resetValues[7];
		// Inventar
		statInventory[1]	=	resetValues[8];
		statInventory[2]	=	resetValues[10];
		statInventory[3]	=	resetValues[11];
		statInventory[4]	=	resetValues[9];
		// Spezielles
		hp	=	hpMax;
		mana	=	manaMax;
	}

    
    // Methode zum Spieler-Zeichnen
    public void draw(){
    	// Sichtbar?
    	if(!state[currState].visible) return;
    	
    	// Den Spieler zeichnen
    	if(currState<=0){	// Tote brauchen keine Waffen
        	glBindTexture(GL_TEXTURE_2D, state[currState].getTexture().getTextureID());
    		return;
    	}
    	
    	// Richtungen der Waffen anpassen
    	if(weapons[0]!=null) weapons[0].changeDirection(direction);
    	if(weapons[1]!=null) weapons[1].changeDirection(direction);
    	if(weapons[2]!=null) weapons[2].changeDirection(direction);
    	// Nebenhand hinter Spieler zeichnen bei rechts und hinten
    	if(direction>=2 && weapons[currEquipped+1]!=null){	
    		weapons[currEquipped+1].draw((int)x+handOffsets[direction][currState-1][2],(int)y+handOffsets[direction][currState-1][3]);
    	}
    	// Haupthand hinter den Spieler zeichnen bei links und hinten
    	if((direction==3 || direction == 1) && currEquipped==0 && weapons[0]!=null){	
    		weapons[0].draw((int)x+handOffsets[direction][currState-1][0], (int)y+handOffsets[direction][currState-1][1]);
    	}
    	// Spieler zeichnen
    	super.draw();
    	// Haupthand vor den Spieler zeichnen bei rechts und vorne
    	if((direction ==0 || direction==2) && currEquipped==0 && weapons[0]!=null){
    		weapons[0].draw((int)x+handOffsets[direction][currState-1][0], (int)y+handOffsets[direction][currState-1][1]);
    	}
    	// Nebenhand vor den Spieler zeichnen bei links und vorne
    	if((direction==0 || direction==1) && weapons[currEquipped+1]!=null){
    		weapons[currEquipped+1].draw((int)x+handOffsets[direction][currState-1][2],(int)y+handOffsets[direction][currState-1][3]);
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
    
    // Spieler etwas in das Grundinventar hinzufuegen
    public void giveStatInventoryObject(int o, int a){
    	statInventory[o]+=a;
	}

    // Anzahl eines Gegenstandes aus dem Grundinventar abfragen
	public int getStatInventoryObjectCount(int o){
		return(statInventory[o]);
	}

    
    // Abfrage des aktuellen Waffensets
    public int getWeapSet(){
    	return currEquipped;
    }
    
    // Methoden fuer die X und Y-Koordinaten
    public int getTX(){
    	return (int)x-state[currState].getTexture().getTextureWidth()/2;
    }
    public int getTY(){
    	return (int)y-state[currState].getTexture().getTextureHeight()/2;
    }

// Methoden zur Steuerung des Spielers per Keyboard
	public void keyPressed(int k) {
		if (k == Keyboard.KEY_LEFT) {dx = -1;}
		if (k == Keyboard.KEY_RIGHT) {dx = +1;}
		if (k == Keyboard.KEY_DOWN) {dy = +1;}
		if (k == Keyboard.KEY_UP) {dy = -1;}
	}
	public void keyReleased(int k) {
		if (k == Keyboard.KEY_LEFT) {dx = 0;}
		if (k == Keyboard.KEY_RIGHT) {dx = 0;}
		if (k == Keyboard.KEY_DOWN) {dy = 0;}
		if (k == Keyboard.KEY_UP) {dy = 0;}
	}
}