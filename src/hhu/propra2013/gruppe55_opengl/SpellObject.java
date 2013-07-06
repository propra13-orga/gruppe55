package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse SpellObject.
 * Diese Klasse erbt von der Klasse DungeonObject und implementiert ein Spellobject, welches zum Zaubern benutzt wird.
 * @see DungeonObject
 */

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

	/**
	 * Der Konstruktor fuer das SpellObject.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * @param x  Die Methode erwartet die Uebergabe eines double Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines double Werts y
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
	
	/**
	 * Die Methode cast.
	 * Diese Methode fuehrt den Zauber aus. Es wird ein neues Projectil (ein Fireball zB) gefeuert.
	 * @param x  Die Methode erwartet die Uebergabe eines double Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines double Werts x
	 * @param angle  Die Methode erwartet die Uebergabe eines int Werts angle
	 * @param spellDmg  Die Methode erwartet die Uebergabe eines int Werts spellDmg
	 */
	
	// Methode, die die eigentliche Magie ausführt
	public void cast(final double x,final double y,final int angle,final int spellDmg){
		// Zaubern!
		for(GameEventListener gel : evtList){
			gel.shootProjectile(new Fireball(x,y,angle,dmg+spellDmg));
		}
	}
	
	/**
	 * Die Methode getCastDurationData.
	 * Diese Methode gibt die Zauberdauer zurueck.
	 * @return Die Zauberdauer als int[] zurueck.
	 */
	
	// Zauberdauer?
	public int[] getCastDurationData(){
		return new int[]{castDuration,castDurationInterval};
	}
	
	/**
	 * Die Methode getManaCost.
	 * Diese Methode gibt die Manakosten des Zaubers zurueck.
	 * @return Die Manakosten als int.
	 */
	
	// Wiedergabe der Manakosten
	public int getManaCost(){
		return manaCost;
	}
	
	/**
	 * Die Methode getHealthCost.
	 * Diese Methode gibt die HP Kosten des Zaubers zurueck. 
	 * @return Die HP Kosten als int.
	 */
	
	// Wiedergabe der HP-Kosten
	public int getHealthCost(){
		return healthCost;
	}
	
	/**
	 * Die Methode getDmg.
	 * Diese Methode gibt den Schadenswert des Zaubers zurueck.
	 * @return Der Schadenswert als int.
	 */
	
	// Wiedergabe des Schadenswertes
	public int getDmg(){
		return dmg;
	}
	
	/**
	 * Die Methode getHeal.
	 * Diese Methode gibt den Heilwert des Zaubers zurueck.
	 * @return Der Heilwert als int.
	 */
	
	// Wiedergabe des Heilwertes
	public int getHeal(){
		return heal;
	}
	
	/**
	 * Die Methode isListened.
	 * Diese Methode gibt zurueck ob der Zauber mit dem Listener verbunden ist.
	 * @return true, wenn der Zauber durch den Player mit dem Listener verbunden ist
	 */
	
	public boolean isListened(){
		return (evtList.size()>0) ? true : false;
	}

}
