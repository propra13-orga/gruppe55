package hhu.propra2013.gruppe55;

public class SpellObject extends DungeonObject {
// Attribute
	private int manaCost;		// Manakosten des Zaubers
	private int healthCost;		// Falls der Zauber mit Blut bezahlt wird
	private int dmg;			// Falls der Zauber Schaden machen soll
	private int heal;			// Falls der Zauber heilen soll
	// Listener
	private boolean listener;	// true, wenn der Zauber durch den Spieler bereits mit Listenern verbunden wurde
	
/*
 * Zaubereigenschaften:
 * Schießt einen Feuerball
 * Heilt den Spieler
 */

// Konstruktor
	public SpellObject(int x, int y) {
		super(x, y);		
		
		// Wertezuweisungen
		manaCost	=	1;
		healthCost	=	0;
		dmg			=	4;
		heal		=	0;
	}
	
	// Methode, die die eigentliche Magie ausführt
	public void cast(int x,int y,int angle,int spellDmg){
		// Feuerball schießen!
		for(GameEventListener gel : evtList){
			gel.shootProjectile(new Fireball(x,y,angle,dmg+spellDmg));
		}
	}
	
	// Wiedergabe der Manakosten
	public int getManaCost(){
		return manaCost;
	}
	// Wiedergabe der HP-Kosten
	public int getHealthCost(){
		return healthCost;
	}
	// Wiedergabe des Schadenswertes
	public int getDmg(){
		return dmg;
	}
	// Wiedergabe des Heilwertes
	public int getHeal(){
		return heal;
	}
	
	public boolean isListened(){
		return (evtList.size()>0) ? true : false;
	}

}
