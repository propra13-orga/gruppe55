package hhu.propra2013.gruppe55;

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
		state[0]	=	new State(Data_Img.arrow_f, false, false, false);
		// fliegender Pfeil
		state[1]	=	new State(Data_Img.arrow_f,Data_Img.arrow_l,Data_Img.arrow_r,Data_Img.arrow_b,true,false,true);
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
			// Etwas getroffen, also ausblenden
			switchState(0);
		}
		else if (d.isMassive() == true)
			// Etwas getroffen, also ausblenden
			switchState(0);
		
	}
	
	// Abschuss (wenn nur als Typenreferenz genutzt [siehe LivingObject])
	public Projectile launch(double x, double y, int angle, int dmg){
		return new Projectile(x,y,angle,dmg);
	}
	
	// Koodinatenanpassung
	protected void adjustPosition(){
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
		changeDirection(direction);
		checkDirection();
		// Koordinaten anpassen
		switch(direction){
		case	1:	// links
			x-=state[1].getImg().getWidth(null);
			y-=state[1].getImg().getHeight(null)/2;	// besser zentrieren
			break;
		case	2:	// rechts
			y-=state[1].getImg().getHeight(null)/2;	// besser zentrieren
			break;
		case	3:	// oben
			x-=state[1].getImg().getWidth(null)/2;	// besser zentrieren
			break;
		case	0:	// unten
		default:
			x-=state[1].getImg().getWidth(null)/2;	// besser zentrieren
			y+=state[1].getImg().getHeight(null);
			break;
		}
	}

}
