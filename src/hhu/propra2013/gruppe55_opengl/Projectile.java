package hhu.propra2013.gruppe55_opengl;

public class Projectile extends MovingObject {
// Attribute
	protected int angle;	// Flugwinkel
	protected int dmg;		// Schadenswert

// Konstruktor
	// int angle: Der Winkel, in dem sich bewegt werden soll (in Grad)
	public Projectile(int x, int y, int angle, int dmg) {
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
		// Richtung berechnen
		if(this.angle <=45 || this.angle > 360-45){ 			// nach rechts
			direction	=	2;
		}
		else if(this.angle <=45+90 || this.angle > 180-45){	// nach oben
			direction	=	3;
		}
		else if(this.angle <=45+180 || this.angle > 270-45){	// nach links
			direction	=	1;
		}
		else{ 													// nach unten
			direction	=	0;
		}
		// Richtung setzen
		changeDirection(direction);
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
			break;
		case	0:	// unten
		default:
			x-=state[1].getTexture().getTextureWidth()/2;	// besser zentrieren
			y+=state[1].getTexture().getTextureHeight();
			break;
		}
	}
	
	// Bewegung berechnen
	public void move(){
		// aktuell ziemlich schlecht geloest
		dx	=	(int)Math.cos(Math.toRadians(angle));
		dy	=	(int)Math.sin(Math.toRadians(angle));
		
		// bewegen
		super.move();
	}
	
	// Kollision
	public void onCollision(DungeonObject d){
		// Gegner/Spieler getroffen?
		if(d instanceof LivingObject)
			((LivingObject)d).getHit(dmg);
		
		// Etwas getroffen, also ausblenden
		switchState(0);
	}

}
