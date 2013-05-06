package hhu.propra2013.gruppe55;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public abstract class DungeonObject {
// Attribute
	// Koordinaten
	protected int x;	// aktuelle X-Position des Objekts
	protected int y;	// aktuelle Y-Position des Objekts
	// Grafik
	protected String imgPath	=	"img/wall.png";	// pfad zur bilddatei
	//protected Image img;	// bildobjekt
	protected State[] state;
	protected int actState;
	protected int width;	// breite
	protected int height;	// h�he
	
// Konstruktor
		// x, y: coordinates to spawn
	public DungeonObject(int x, int y){
		// Koordinaten
		this.x	=	x;
		this.y	=	y;
		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(imgPath, false, true, true);
		// pointer setzen
		switchState(0);
	}
	
// Methoden
	
	// spezielle Kollisionsbehandlung
	protected void onCollision(DungeonObject d){
		// standard for non dangerous objects
		if(this.state[actState].massive)
			if(d instanceof	LivingObject)
				((LivingObject)d).setBack();
	}
	
	// switch state
	protected void switchState(int s){
		// viable input?
		if(s>=state.length)
			return;
		actState	=	s;
		// breite und Hoehe bestimmen
		width	=	state[actState].img.getWidth(null);
		height	=	state[actState].img.getHeight(null);
	}

// following methods are relevant for the level methods paint and collisionCheck
	// x wert ausgeben
	public int getX(){return x;}
	// y wert ausgeben
	public int getY(){return y;}
	// bild ausgeben
	public Image getImg(){return state[actState].img;}
	// rahmen ausgeben
	public Rectangle getBorder(){return new Rectangle(x, y, width, height);}
	
}
