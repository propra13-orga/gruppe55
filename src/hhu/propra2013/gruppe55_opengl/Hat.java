package hhu.propra2013.gruppe55_opengl;

public abstract class Hat extends DungeonObject {
// Attribute
	// Statuswerte die durch den Hut erhoeht werden
	protected int atk;			// Bonus auf die ATK
	protected int def;			// Bonus auf die DEF
	protected int hpMax;		// Maximal-HP wird um die angegebene Zahl Containerweise erhöht
	protected int manaMax;		// Wie maxHP nur fuer Mana
	protected int critBonus;	// erhoeht die Chance auf kritische Treffer
	protected int healBonus;	// erhoeht die durch Traenke erhaltene Heilung
	protected int manaBonus;	// wie healBonus nur fuer Mana
	protected int[][] resistances;	// Widerstaende

	public Hat(double x, double y) {
		super(x, y);
		
		// States
		state	=	new State[2];
		state[0]	=	new State(Data_Textures.lavahat,false, false, true);			// der Hut
		state[0].defineOffset(0,0,6,12,0);
		state[1]	=	new State(Data_Textures.lavahat, false, false, false);			// der aufgesammelte Hut
		
		
		// Wertzuweisungen
		atk=0;			// Extra ATK
		def=0;			// extra DEF
		hpMax=0;		// extra auf die maximale Gesundheit
		manaMax=0;		// extra auf das maximale Mana
		critBonus=0;	// Extraprozentpunkte fuer die kritische Trefferchance
		healBonus=0;	// gewaehrte extraheilung bei Traenken zB
		manaBonus=0;	// gewaehrtes extramana bei Traenken zB
		// Elementarresistenzen
		resistances	=	new int[3][2];
		resistances[0][0]	=	0;	// Widerstand gegen Element 1
		resistances[0][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 1 Immun
		resistances[1][0]	=	0;	// Widerstand gegen Element 2
		resistances[1][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 2 Immun
		resistances[2][0]	=	0;	// Widerstand gegen Element 3
		resistances[2][1]	=	0;	// Wenn >0 ist das Objekt gegen Element 3 Immun
	}
	
	
	// Werte uebergeben
	public int[] getStats(){
		return new int[] {atk,def,hpMax,manaMax,critBonus,healBonus,manaBonus};
	}
	
	// Widerstaende uebergeben
	public int[][]getResistances(){
		return resistances;
	}
	
	public void onCollision(DungeonObject d){	 // Aufnahme des Hutes bei Collision
    	if(d instanceof	Player){
			((Player)d).collecthat(); // TODO: Besser implementieren
			// Wechsel des Status auf "verschwundene" Potion
			switchState(1);
    	}
	} 
	
	public void draw(int x, int y){
		//x, y Werte setzen
		this.x	=	(x-2);
		this.y	=	(y-23);
		super.draw();
	}
}
