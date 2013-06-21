package hhu.propra2013.gruppe55_opengl;

public class SpellFlameBreath extends SpellObject {

	public SpellFlameBreath(double x, double y) {
		super(x, y);

		// Werte setzen
		manaCost 	= 	2;
		dmg			=	2;
		castDuration=	1000;	// 2 Sekunden
		castDurationInterval	=	50;
	}

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
