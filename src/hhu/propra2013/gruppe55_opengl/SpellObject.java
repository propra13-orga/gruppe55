package hhu.propra2013.gruppe55_opengl;

public class SpellObject extends DungeonObject {
// Attribute
	protected int manaCost;		// Manakosten des Zaubers
	protected int healthCost;		// Falls der Zauber mit Blut bezahlt wird
	protected int dmg;			// Falls der Zauber Schaden machen soll
	protected int heal;			// Falls der Zauber heilen soll
	protected int castDuration;	// Dauer, wie lange der Zauber wirkend gecastet wird in Millisekunden
	protected int castDurationInterval;	// Invertvallhafte Dauer, in der der Zauber waehrend seiner zugesprochenen Wirkzeit gewirkt wird
	// Listener
	protected boolean listener;	// true, wenn der Zauber durch den Spieler bereits mit Listenern verbunden wurde
	
/*
 * Zaubereigenschaften:
 * Schießt einen Feuerball
 * Heilt den Spieler
 */

// Konstruktor
	public SpellObject(double x, double y) {
		super(x, y);		
		
		// Wertezuweisungen
		manaCost	=	1;
		healthCost	=	0;
		dmg			=	4;
		heal		=	0;
		castDuration=	0;
		castDurationInterval	=	0;
	}
	
	// Methode, die die eigentliche Magie ausführt
	public void cast(final double x,final double y,final int angle,final int spellDmg){
		// Zaubern!
		for(GameEventListener gel : evtList){
			gel.shootProjectile(new Fireball(x,y,angle,dmg+spellDmg));
		}
	}
	
	// Zauberdauer?
	public int[] getCastDurationData(){
		return new int[]{castDuration,castDurationInterval};
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
