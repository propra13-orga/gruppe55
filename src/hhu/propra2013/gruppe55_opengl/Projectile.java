package hhu.propra2013.gruppe55_opengl;

public class Projectile extends MovingObject {
// Attribute
	protected int angle;	// Flugwinkel
	protected int dmg;		// Schadenswert

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

	// Kollision
	public void onCollision(DungeonObject d){
		// Gegner/Spieler getroffen?
		if(d instanceof LivingObject){
			((LivingObject)d).getHit(dmg);
			switchState(0);
		}
		else if (d.isMassive() == true)
			switchState(0);
			// Etwas getroffen, also ausblenden	
	}
	
	// Abschuss (wenn nur als Typenreferenz genutzt [siehe LivingObject])
	public Projectile launch(double x, double y, int angle, int dmg){
		return new Projectile(x,y,angle,dmg);
	}
	
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
	
	@Override
	// Muessen wir ueberschreiben, da sonst die move-Methode unsere Richtung neu setzt
	// Solange die checkDirection im MovingObject nicht besser gesetzt ist, ist das unser weg
	protected void checkDirection(){
		// Die Richtung eines Geschosses soll sich ja eigentlich nicht aendern, also weiss ich nicht, was hier rein sollte...
	}

}
