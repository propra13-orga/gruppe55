package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Rectangle;
import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;


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
	// Schnittstelle fuer GameEvents
	protected ArrayList<GameEventListener> evtList	=	new ArrayList<GameEventListener>();

	
// Konstruktor
		// x, y: Koordinaten zum Erscheinen
	public DungeonObject(int x, int y){
		// Koordinaten
		this.x	=	x;
		this.y	=	y;
		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.potionused, false, true, true); 
		// pointer setzen
		switchState(0);
	}
	
// Methoden
	
	// Hinzufuegen des GameEventListeners
	public void addGameListener(GameEventListener listener){
		evtList.add(listener);
	}

	
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
		int oWidth	=	state[currState].getTexture().getTextureWidth();	// alte Breite
		int oHeight	=	state[currState].getTexture().getTextureHeight();	// alte Hoehe
		
		// neuen pointer setzen
		currState	=	s;
		
		// Neuberechnung der Position in Abhängigkeit zur groesse des Bildes
		x	+=	(oWidth - state[currState].getTexture().getTextureWidth()) / 2;
		y	+=	oHeight - state[currState].getTexture().getTextureHeight();
		
	}
	
	// Methode zum Richtungswechsel
	public void changeDirection(int d){
		direction	=	d;
		// so "viele" states!
		for(int i=0; i<state.length; i++){
			state[i].changeDirection(d);
		}
	}
	
	public void draw(){
		if(state[currState].visible){
        	glBindTexture(GL_TEXTURE_2D, state[currState].getTexture().getTextureID());
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2f(x, y);
				glTexCoord2f(0, 1);
				glVertex2f(x, y+state[currState].getTexture().getTextureHeight());
				glTexCoord2f(1, 1);
				glVertex2f(x+state[currState].getTexture().getTextureWidth(), y+state[currState].getTexture().getTextureHeight());
				glTexCoord2f(1, 0);
				glVertex2f(x+state[currState].getTexture().getTextureWidth(), y);
			glEnd();
		}
	}

// Die folgenden Methoden werden fuer die Level-methoden "paint" und "collisionCheck" benoetigt:
	
	// x wert ausgeben
	public int getX(){return x;}
	// y wert ausgeben
	public int getY(){return y;}
	// bild ausgeben
	public Texture getTexture(){return state[currState].getTexture();}
	// Massive Status ausgeben
	public boolean isMassive(){
		return state[currState].massive;
	} 
	// rahmen ausgeben
	public Rectangle getBorder(){
		if(state[currState].visible){
			int[] offset= state[currState].getOffset();
			return new Rectangle(x + offset[1], y + offset[0], state[currState].getTexture().getTextureWidth() -offset[1]-offset[3], state[currState].getTexture().getTextureHeight()-offset[0]-offset[2]);
		}		
		// else
		return new Rectangle(0,0,0,0);
	}
	
}
