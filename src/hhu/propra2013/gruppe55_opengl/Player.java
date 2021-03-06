package hhu.propra2013.gruppe55_opengl;

import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

/** 
 * Die Klasse Player.
 * Diese Klasse erbt von der Klasse LivinObject und spezifiziert die Art des Objektes weiter (als Spielerfigur).
 * @see LivingObject
 */

public class Player extends LivingObject {

// Attribute
	// Variablen fuer Handhabung von Unverwundbarkeit
	
	/** Dauer der Unverwundbarkeit in Millisekunden. */
	
	private int invulTime			=	500;		// Dauer der Unverwundbarkeit in Millisekunden
	
	// Variablen fuer den Angriff
	
	/** Die Waffen, die sich im Besitz des Spielers finden. */
	
	public Weapon[] weapons	=	new Weapon[3];	// Waffen in Besitz
	
	/** Der Zeiger auf das aktuell ausgeruestete Waffenset (0 = Nahkampf, 1 = Fernkampf). */
	
	private int currEquipped	=	0;			// Zeige auf aktuell ausgeruestetes Waffenset (0= Nahkampf; 1= Fernkampf)
	
	/** Abfrage, ob eine Attacke ausgefuehrt wird. */
	
	private boolean attacking	=	false;		// Waehrend einer Attacke true
	
	/** Die Offsets fuer die Postion der Spielerhaende. */
	
	private int[][][] handOffsets	=	new int[4][2][4];	// Offsets fuer die Positionen der Spielerhaende
	
	// Zaubervariablen
	
	/** Der ausgeruestete Zauber. */
	
	private SpellObject spell;
	
	// Besitztuemer des Spielers
	
	/** Das Inventar fuer statische Objekte (Gold Traenke Pfeile...). */
	
	private int[] statInventory;		//Inventar fuer statische Objekte (Gold, Traenke, Pfeile)
	
	/** Die durch die Ausruestung erhaltenen Attribute. */
	
	private int[] stats;				// durch Ausruestung erhaltene Attribute
	
	/** Die Basiswertde des Spielers. */
	
	private int[] baseStats;			// Basiswerte des Spielers
	
	/** Der Hut des Spielers. */
	
	public Hat hat;						// Der Hut des Spielers
	
	/** Die Abfrage, ob der Spieler mit einem Objekt interagieren moechte. */
	
	private boolean interaction=false;	// Angabe, ob der Spieler mit Objekten interagieren moechte
	
	/**
	 * Der Konstruktor fuer den Player.
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, atk, def, energy, mana und l uebergeben.
	 * Des Weiteren werden die benoetigten States, die Offsetarrays, das Inventory, die Waffen und die Zauber initialisiert.
	 * @param spawnX  Die x-Koordinate, an der der Player generiert wird
	 * @param spawnY  Die y-Koordinate, an der der Player generiert wird
	 * @param h  Der HP-Wert, mit dem der Player generiert wird
	 * @param atk  Der Angriffswert, mit dem der Player generiert wird
	 * @param def  Der Verteidigungswert, mit dem der Player generiert wird
	 * @param energy  Der Energiewert, mit dem der Player generiert wird
	 * @param mana  Der maximale Manawert, mit dem der Player generiert wird
	 * @param l  Die Anzahl Leben, mit denen der Player generiert wird
	 */
	
// Konstruktor
    public Player(double spawnX, double spawnY, int h, int atk, int def, int energy, int mana, int l) {
    	super(spawnX, spawnY, h, atk, def);

    	this.mana = this.manaMax = mana;
    	this.energy = this.energyMax = energy;
    	
    	// Basisattribute des Spielers speichern
    	baseStats	=	new int[]{atk,def,h,mana,critBonus,healBonus,manaBonus};

		// statInventory Slots
		// 0: Lives
		// 1: Gold
		// 2: HPPotion
		// 3: MPPotion
		// 4: Arrows
		statInventory = new int[5];
		statInventory[0] = l;
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
		weapons[0]	=	new SimpleSword(x,y);	// Haupthand
		weapons[1]	=	new SimpleShield(x,y);	// Nebenhand
		weapons[2]	=	new SimpleBow(x,y);	// Fernkampfwaffe

		// Zauber enbenfalls initialisieren
		spell	=	new SpellObject(x,y);

		
		// Attribute uebernehmen
		calcStatsByWeapons();
		
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
    
    /**
     * Die Methode attack.
     * Diese Methode fragt den Angriffszustand ab und realisiert den Spielerangriff (Nah- und Fernkampf je nach equipter Waffe)
     */
    
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

	/**
     * Die Methode dealDamage.
     * Wird vom LivingObject geerbt, berechnet den Schaden aber von dem Waffenelementen abhaengig
     * @param l Es wird ein zu schaedigendes LivingObject erwartet
     */
    public void dealDamage(LivingObject l){
    	// Wir muessen nur das Waffenelement an die Muttermethode weitergeben, dazu schummeln wir
    	element	=	weapons[0].getElement();
    	super.dealDamage(l);
    	element =	0;	// super.dealDamage nimmt nur das eigene Element, daher dieser kleine Kniff
    }
    
    /** 
     * Die Methode shoot.
     * Diese Methode ueberprueft ob Pfeile vorhanden sind und loest den Schuss mit dem Bogen aus (die Pfeile werden gezeichnet). Anschliessend werden Pfeile aus dem Inventar des Spielers entfernt.
     */
    
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
	
	/**
	 * Die Methode swapWeapons.
	 * Diese Methode realisiert den Waffenwechsel (Schwert -> Bogen und Bogen -> Schwert). Es wird abgefragt, ob die Waffen vorhanden sind und wenn ja, dann wird auf die jeweils andere Waffe gewechselt.
	 * Zudem werden die einzelnen Schadenswerte der neu ausgeruesteten Waffe uebernommen. 
	 */
	
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
    	calcStatsByWeapons();
    }
    
    /**
     * Die Methode calcStatsByWeapons.
     * Diese Methode errechnet den Min- und Maxschaden sowie die weiteren Boni der jeweils ausgeruesteten Waffe. Die neuen Werte werden direkt in den Player uebernommen.
     */
    
    // Wenn neue Waffen angelegt werden, sollten die Attribute eingelesen werden
    private void calcStatsByWeapons(){
    	// alte werte auf 0 setzen
    	stats	=	new int[]{0,0,0,0,0,0,0};
    	resistances	=	new int[][]{{0,0},{0,0},{0,0}};
    	minDmg	=	0;
    	maxDmg	=	0;
    	// neue Werte initialisieren zwischenspeichern
    	int[] newStats;
    	int[][] newResis;
    	
    	
    	// entweder Fernkampfwaffe oder beide Nahkampfwaffen
    	for(int i=0; i<2-currEquipped;i++){ // wird 2x ausgefuehrt bei Nah- und 1x bei Fernkampf
    		if(weapons[currEquipped+i]!=null){	// Waffe angelegt
    			newStats=weapons[2*currEquipped+i].getStats();
    			newResis=weapons[2*currEquipped+i].getResistances();
    			
    			// Schaden uebernehmen
    			minDmg+=weapons[2*currEquipped+i].getMinDmg();
    			maxDmg+=weapons[2*currEquipped+i].getMaxDmg();
    			
    			// Attribute zusammenrechnen
    			stats[0]+=newStats[0];
    			stats[1]+=newStats[1];
    			stats[2]+=newStats[2];
    			stats[3]+=newStats[3];
    			stats[4]+=newStats[4];
    			stats[5]+=newStats[5];
    			stats[6]+=newStats[6];
    			// Widerstaende zusammenrechnen
    			resistances[0][0]+=newResis[0][0];
    			resistances[0][1]+=newResis[0][1];
    			resistances[1][0]+=newResis[1][0];
    			resistances[1][1]+=newResis[1][1];
    			resistances[2][0]+=newResis[2][0];
    			resistances[2][1]+=newResis[2][1];
    		}
    	}
    	// Der Hut!
    	if(hat!=null){
    		newStats=hat.getStats();
			newResis=hat.getResistances();
			
			// Attribute zusammenrechnen
			stats[0]+=newStats[0];
			stats[1]+=newStats[1];
			stats[2]+=newStats[2];
			stats[3]+=newStats[3];
			stats[4]+=newStats[4];
			stats[5]+=newStats[5];
			stats[6]+=newStats[6];
			// Widerstaende zusammenrechnen
			resistances[0][0]+=newResis[0][0];
			resistances[0][1]+=newResis[0][1];
			resistances[1][0]+=newResis[1][0];
			resistances[1][1]+=newResis[1][1];
			resistances[2][0]+=newResis[2][0];
			resistances[2][1]+=newResis[2][1];
    	}
    	
    	// In den Spieler uebernehmen
    	atk	=	baseStats[0]+stats[0];
    	def	=	baseStats[1]+stats[1];
    //	hpMax	=	baseStats[2]+2*stats[2];
    //		if(hpMax>20) hpMax=20;
    	manaMax	=	baseStats[3]+stats[3];
    		if(manaMax>10) manaMax=10;
    	critBonus	=	baseStats[4]+stats[4];
    	healBonus	=	baseStats[5]+stats[5];
    	manaBonus	=	baseStats[6]+stats[6];
    }
    
    /**
     * Die Methode calcPlayerAngle.
     * Diese Methode berechnet den Winkel bzw. die Richtung in die der Player schaut (0, 90, 180, 270 Grad) und gibt diesen zurueck.
     * @return Der Winkel Wert als int.
     */
    
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
	
	/**
	 * Die Methode spellCast.
	 * Diese Methode fragt ab, was gezaubert werden soll, wie viel es kostet, setzt die Offsets neu, damit der Zauber nicht im Player generiert wird, fuegt das Event hinzu und wirkt schliesslich den Zauber.
	 */
	
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

	/**
	 * Die Methode teleport.
	 * Diese Methode teleportiert den Player an eine bestimmte Stelle (x,y).
	 * @param x  Die x-Koordinate, an die sich der Player teleportieren soll
	 * @param y  Die y-Koordinate, an die sich der Player teleportieren soll
	 */
    
    // Methode um den Spieler an eine bestimmte Stelle zu teleportieren
    public void teleport(double x, double y){
    	this.x	=	x;
    	this.y	=	y;
    }
    
    /**
     * Die Methode setResetValues.
     * Diese Methode ruft die Methode setResetValues aus der Mutterklasse LivingObject auf um die ResetValues zu setzen und erweitert diese dann noch um weitere Eintr�ge (hpMax, manaMax, ...).
     */
    
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
	
	/**
	 * Die Methode reset.
	 * Diese Methode ruft die Methode reset aus der Mutterklasse LivingObject auf um die Werte zu resetten und resettet anschliessend auch noch die Playerspezifischen, die zuvor in setResetValues im Player gesetzt wurden.
	 */

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
	
	/**
	 * Die Methode draw.
	 * Diese Methode zeichnet den Player, sofern dieser sichtbar ist und zeichnet auch alle Waffen und Kleidungsstuecke, die der Player derzeit ausgeruestet hat. 
	 * Zusaetzlich ueberschreibt diese Methode die Methode draw aus der Mutterklasse LivingObject, um einige Anpassungen vorzunehmen. 
	 */

    
    // Methode zum Spieler-Zeichnen
    public void draw(){
    	// Sichtbar?
    	if(!state[currState].visible) return;
    	
    	// Den Spieler zeichnen
    	if(currState<=0){	// Tote brauchen keine Waffen
        	glBindTexture(GL_TEXTURE_2D, state[currState].getTexture().getTextureID());
    		return;
    	}
    	// Wenn man den Hut hat soll er auch gezeichnet werden!
    	if(hat!=null){
    		hat.draw((int)x,(int)y);	
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
    
    /**
     * Die Methode revive.
     * Diese Methode belebt den Player wieder, wenn er gestorben ist. Dabei wird der State gewechselt und hp, mana und energy werden wieder zurueckgesetzt.
     */
    
    // Methode um den Spieler "wiederzubeleben"
    public void revive(){
    	hp	=	hpMax;
    	energy = energyMax;
    	mana = manaMax;
    	switchState(1);
    	// derzeit wird hier kein weiterer Code benoetigt (das kann sich im Laufe des Projekts aendern)
    }
    
    /**
     * Die Methode getAttackState.
     * Diese Methode gibt den attacking-Zustand zurueck. 
     * @return true, wenn der Player gerade angreift.
     */
    
    // Abfrage des Angriffsstatus
    public boolean getAttackState(){
    	return attacking;
    }
    
    /**
     * Die Methode getAtk.
     * Diese Methode gibt den aktuellen atk Wert zurueck.
     * @return Der aktuelle atk Wert als int.
     */
    
    // Abfrage des aktuellen ATK-Werts des Spielers
    public int getAtk(){
    	return atk;
    }
    
    /**
     * Die Methode giveStatInventoryObject.
     * Diese Methode erhoeht / verringert die Anzahl eines Objektes im Inventar des Players.
     * @param o  Die Stelle o im Inventararray, an der sich das Objekt befindet
     * @param a  Die Anzahl a um die das Objekt erhoeht oder verringert werden soll
     */
   
    // Spieler etwas in das Grundinventar hinzufuegen
    public void giveStatInventoryObject(int o, int a){
    	statInventory[o]+=a;
	}
    
    /** 
     * Die Methode getStatInventoryObject.
     * Diese Methode gibt die Anzahl der sich an der Stelle o befindlichen Objekte im Inventar des Players zurueck. 
     * @param o  Die Stelle o im Inventararray, an der sich das Objekt befindet
     * @return Die Menge an der Stelle o im Inventararray. 
     */

    // Anzahl eines Gegenstandes aus dem Grundinventar abfragen
	public int getStatInventoryObjectCount(int o){
		return(statInventory[o]);
	}

	/**
	 * Die Methode getWeapSet.
	 * Diese Methode gibt das aktuell equippte Waffenset zurueck.
	 * @return Das aktuell equippte Waffenset als int.
	 */
	
    
    // Abfrage des aktuellen Waffensets
    public int getWeapSet(){
    	return currEquipped;
    }
    
    /**
     * Die Methode collecthat.
     * Diese Methode setzt die Variable hashat auf true, sobald der Player den Hut einsammelt.
     */
    
    // Den Hut einsammeln!
    public void collecthat(){
    	hat=new Lavahat(x,y);
    	calcStatsByWeapons();
    }
    
    public void collectsword(){
    	weapons[0]	=	new IceSword(x,y);
    	calcStatsByWeapons();
    }
    
    public void collectbow(){
       	projectile = new Waterbubble(0,0,0,0);
       	weapons[2] = new WaterBow(x,y);
       	calcStatsByWeapons();
    }
    
    /**
     * Die Methode gethat.
     * Diese Methode gibt zurueck, ob der Player den Hut besitz.
     * @return true, wenn der Player den Hut besitzt.
     */
    
    // Abfrage ob man den Hut hat!
    public boolean gethat(){
    	return (hat!=null);
    }
    
    /**
     * Diese Methode gibt wieder, ob der Spieler gewillt ist mit naheliegenden Objekten zu interagieren
     * @return Wahrheitswert des Interaktionsbed&uuml;rfnisses
     */
    public boolean wantsToInteract(){
    	return interaction;
    }
    
    /**
     * Die Methode getTX.
     * Diese Methode gibt die x Koordinate der Mitte der Textur zurueck.
     * @return Die x-Koordinate der Mitte der Textur als int
     */
    
    // Methoden fuer die X und Y-Koordinaten
    public int getTX(){
    	return (int)x-state[currState].getTexture().getTextureWidth()/2;
    }
    
    /**
     * Die Methode getTY.
     * Diese Methode gibt die y-Koordinate der Mitte der Textur zurueck.
     * @return Die y-Koordinate der Mitte der Textur als int
     */
    
    public int getTY(){
    	return (int)y-state[currState].getTexture().getTextureHeight()/2;
    }
    
    /**
     * Die Methode setDX.
     * Diese Methode uebergibt dem Spieler die x-Direction.
     * @param x Diese Methode erwartet die Uebergabe eines Integers x fuer die Direction.
     */
    
    public void setDX(int x){
    	dx = x;
    }
    
    /**
     * Die Methode setDY.
     * Diese Methode uebergibt dem Spieler die y-Direction.
     * @param y Diese Methode erwartet die Uebergabe eines Integers y fuer die Direction.
     */
    
    public void setDY(int y){
    	dy = y;
    }
    
    /**
     * Die Methode keyPressed.
     * Diese Methode leitet die gedrueckte Taste auf der Tastatur an den Player weiter und bestimmt dadurch seine Bewegung.
     * @param k  Diese Methode erwartet die Uebergabe eines int Werts k
     */

// Methoden zur Steuerung des Spielers per Keyboard
	public void keyPressed(int k) {
		if (k == Keyboard.KEY_LEFT) {dx = -1;}
		if (k == Keyboard.KEY_RIGHT) {dx = +1;}
		if (k == Keyboard.KEY_DOWN) {dy = +1;}
		if (k == Keyboard.KEY_UP) {dy = -1;}
		if (k == Keyboard.KEY_E) {interaction=true;}
	}
	
	/**
     * Die Methode keyReleased.
     * Diese Methode leitet die losgelassene Taste (die zuvor in der KeyPressed Methode gedrueckt wurde) an den Player weiter und bestimmt dadurch seine Bewegung.
     * @param k  Diese Methode erwartet die Uebergabe eines int Werts k
     */
	
	public void keyReleased(int k) {
		if (k == Keyboard.KEY_LEFT) {dx = 0;}
		if (k == Keyboard.KEY_RIGHT) {dx = 0;}
		if (k == Keyboard.KEY_DOWN) {dy = 0;}
		if (k == Keyboard.KEY_UP) {dy = 0;}
		if (k == Keyboard.KEY_E) {interaction=false;}
	}
	
	public void controllerXAxisPressed(int k){
		if(k > 0)
			dx = +1;			
		if(k < 0)
			dx = -1;
	}
	public void controllerYAxisPressed(int k){
		if(k > 0)
			dy = +1;			
		if(k < 0)
			dy = -1;
	}
}