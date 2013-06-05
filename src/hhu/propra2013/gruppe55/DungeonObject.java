package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class DungeonObject {
// Attribute
	// Koordinaten
	protected int x;	// aktuelle X-Position des Objekts
	protected int y;	// aktuelle Y-Position des Objekts
	// Grafik
	protected  State[] state;
	protected int currState;
	protected int width;	// breite
	protected int height;	// hoehe
	protected int direction	=	0;	// aktuelle Blickrichtung des Objekts
	
// Konstruktor
		// x, y: Koordinaten zum Erscheinen
	public DungeonObject(int x, int y){
		// Koordinaten
		this.x	=	x;
		this.y	=	y;
		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data.potionused, false, true, true); 
		// pointer setzen
		switchState(0);
	}
	
// Methoden
	
	// spezielle Kollisionsbehandlung
	protected void onCollision(DungeonObject d){
		// standart fuer ungefaehrliche Objekte
		if(this.state[currState].massive)
			if(d instanceof	LivingObject){
				// solange Kollision wird das Objekt zurückgeschoben
				while(getBorder().intersects(d.getBorder()))
					((LivingObject)d).setBack();
			}
	}
	
	// Methode zum Wechseln der States
	protected void switchState(int s){
		// Abfrage ob die Eingabe valid ist
		if(s>=state.length)
			return;
		// Speichern des alten Bildes um es ggfs. an einen anderen Ort zu verschieben
		int oWidth	=	state[currState].getImg().getWidth(null);	// alte Breite
		int oHeight	=	state[currState].getImg().getHeight(null);	// alte Hoehe
		
		// neuen pointer setzen
		currState	=	s;
		
		// Neuberechnung der Position in Abhängigkeit zur groesse des Bildes
		x	+=	(oWidth - state[currState].getImg().getWidth(null)) / 2;
		y	+=	oHeight - state[currState].getImg().getHeight(null);
		
	}
	
	// Methode zum Richtungswechsel
	public void changeDirection(int d){
		direction	=	d;
		// so "viele" states!
		for(int i=0; i<state.length; i++){
			state[i].changeDirection(d);
		}
	}
	
	public void draw(Graphics2D g2d){
		if(state[currState].visible)
			g2d.drawImage(state[currState].getImg(), x, y, null);
	}

// Die folgenden Methoden werden fuer die Level-methoden "paint" und "collisionCheck" benoetigt:
	
	// x wert ausgeben
	public int getX(){return x;}
	// y wert ausgeben
	public int getY(){return y;}
	// bild ausgeben
	public Image getImg(){return state[currState].getImg();}
	// rahmen ausgeben
	public Rectangle getBorder(){
		if(state[currState].visible){
			int[] offset= state[currState].getOffset();
			return new Rectangle(x + offset[1], y + offset[0], state[currState].getImg().getWidth(null) -offset[1]-offset[3], state[currState].getImg().getHeight(null)-offset[0]-offset[2]);
		}		
		// else
		return new Rectangle(0,0,0,0);
	}
	
}
