package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse Projectile.
 * Diese Klasse erbt von der Klasse MovingObject und implementiert die Geschosse, die vom Player und von verschiedenen Creatures verschossen werden.
 * @see MovingObject
 */

public class Projectile extends MovingObject {

// Attribute

	/** Der Flugwinkel des Projektils. */
	
	protected int angle;	// Flugwinkel

	/** Der Schadenswert des Projektils. */
	
	protected int dmg;		// Schadenswert
	
	/**
	 * Der Konstruktor fuer das Projectile.
	 * Beim Aufruf werden dem Konstruktor die x und y Koordinaten, der Winkel und der Schaden uebergeben.
	 * Des Weiteren werden hier die verschiedenen States fuer das Projektil gesetzt und die Bilder geladen, sowie die Bewegung initialisiert.
	 * @param x  Die x-Koordinate an der das Projektil gezeichnet wird
	 * @param y  Die y-Koordinate an der das Projektil gezeichnet wird
	 * @param angle  Der Winkel in dem das Projektil gefeuert wird
	 * @param dmg  Der Schadenswert fuer das Projektil
	 */

// Konstruktor
	// int angle: Der Winkel, in dem sich bewegt werden soll (in Grad)
	public Projectile(double x, double y, int angle, int dmg) {
		super(x,y);
		
		// Winkel und Schaden uebernehmen
		this.angle	=	angle%360;
		this.dmg	=	dmg;
		// Geschwindigkeit anpassen
		speed	=	5;	// Wuhuuu
		
		// States neu definieren
		state	=	new State[2];
		
		// Nicht zeichnen, wenn was getroffen wurde
		state[0]	=	new State(Data_Textures.arrow_f, false, false, false);
		// fliegender Pfeil
		state[1]	=	new State(Data_Textures.arrow_f,Data_Textures.arrow_l,Data_Textures.arrow_r,Data_Textures.arrow_b,true,false,true);
			
		// State setzen
		switchState(1);

		// Bewegungsrichtung initialisieren
		dx	=	Math.cos(Math.toRadians(angle));
		dy	=	Math.sin(Math.toRadians(angle));
		
		// Besser positionieren
		adjustPosition();
	}
	
	/**
	 * Die Methode onCollision. 
	 * Diese Methode sorgt dafuer, dass das Projektil, wenn es auf ein Objekt trifft, entweder Schaden an diesem anrichtet und verschwindet, oder einfach nur verschwindet, wenn das Objekt massiv war.
	 * Die Methode onCollision ueberschreibt die aus der Klasse DungeonObject stammende Methode onCollision.
	 * @param d  Die Methode erwartet ein Dungeonobjekt und ueberprueft ob es sich dabei um ein LivingObject handelt
	 * @see DungeonObject
	 * 
	 */

	// Kollision
	public void onCollision(DungeonObject d){
		// Gegner/Spieler getroffen?
		if(d instanceof LivingObject){
			((LivingObject)d).getHit(dmg,element);
			switchState(0);
		}
		else if (d.isMassive() == true)
			switchState(0);
			// Etwas getroffen, also ausblenden	
	}
	
	/**
	 * Die Methode launch.
	 * Due Methode fuer den Abschuss.
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 * @param angle  Die Methode erwartet die Uebergabe eines int Werts angle
	 * @param dmg  Die Methode erwartet die Uebergabe eines int Werts dmg
	 * @return Die Methode gibt ein Objekt vom Typ Projectile, mit den angegebenen Parametern zurueck.
	 */
	
	// Abschuss (wenn nur als Typenreferenz genutzt [siehe LivingObject])
	public Projectile launch(double x, double y, int angle, int dmg){
		return new Projectile(x,y,angle,dmg);
	}
	
	/**
	 * Die Methode adjustPosition.
	 * Diese Methode passt die Position der Projektile an, abhaengig davon in welche Richung gefeuert wird.
	 */
	
	// Koodinatenanpassung
	protected void adjustPosition(){
		setDirectionByAngle(angle%360);
		// Koordinaten anpassen
		switch(direction){
		case	1:	// links
			x-=state[1].getTexture().getTextureWidth();
			y-=state[1].getTexture().getTextureHeight()/2;	// besser zentrieren
			break;
		case	2:	// rechts
			y-=state[1].getTexture().getTextureHeight()/2;	// besser zentrieren
			break;
		case	3:	// oben
			x-=state[1].getTexture().getTextureWidth()/2;	// besser zentrieren
			y-=state[1].getTexture().getTextureHeight();
			break;
		case	0:	// unten
		default:
			x-=state[1].getTexture().getTextureWidth()/2;	// besser zentrieren
			break;
		}
	}
	
	/**
	 * Die Methode checkDirection.
	 * Diese Methode ueberprueft die Richtung.
	 */
	
	@Override
	// Muessen wir ueberschreiben, da sonst die move-Methode unsere Richtung neu setzt
	// Solange die checkDirection im MovingObject nicht besser gesetzt ist, ist das unser weg
	protected void checkDirection(){
		// Die Richtung eines Geschosses soll sich ja eigentlich nicht aendern, also weiss ich nicht, was hier rein sollte...
	}
}
