package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse SpellFlameBreath
 * Diese Klasse erbt von der Klasse SpellObject und spezifiziert dieses als Feueratem (Beta).
 * @see SpellObject
 */

public class SpellFlameBreath extends SpellObject {
	
	/**
	 * Der Konstruktor fuer den SpellFlameBreath.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten uebergeben.
	 * Des Weiteren werden die manaCost, dmg, Castduration und castDurationInterval uberschrieben.
	 */

	public SpellFlameBreath(double x, double y) {
		super(x, y);

		// Werte setzen
		manaCost 	= 	2;
		dmg			=	2;
		castDuration=	1000;	// 2 Sekunden
		castDurationInterval	=	50;
	}

	/**
	 * Die Methode cast.
	 * Diese Methode fuehrt den Zauber aus. Es wird ein neues Projectil (ein Fireball zB) gefeuert.
	 * @param x  Die Methode erwartet die Uebergabe eines double Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines double Werts y
	 * @param angle  Die Methode erwartet die Uebergabe eines double Werts angle
	 * @param spellDmg  Die Methode erwartet die Uebergabe eines double Werts spellDmg
	 */
	
	// Methode, die die eigentliche Magie ausführt
	public void cast(double x,double y,int angle,int spellDmg){
		// Feuerball schießen!
		for(GameEventListener gel : evtList){
			gel.shootProjectile(new Fireball(x,y,angle+10,dmg+spellDmg));
			gel.shootProjectile(new Fireball(x,y,angle,dmg+spellDmg));
			gel.shootProjectile(new Fireball(x,y,angle-10,dmg+spellDmg));
		}
	}
}
