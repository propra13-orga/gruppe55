package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;


public abstract class DungeonObject {
// Attribute
	// Koordinaten
	protected double x;	// aktuelle X-Position des Objekts
	protected double y;	// aktuelle Y-Position des Objekts
	// Grafik
	protected  State[] state;
	protected int currState;
	protected int width;	// breite
	protected int height;	// hoehe
	protected int direction	=	0;	// aktuelle Blickrichtung des Objekts
	// Checkpoint-relevantes
	protected int[] resetValues;
	protected boolean hasResetValues = false;	// Wenn false kann dieses Objekt nicht resetet werden und sollte aus den Listen geloescht werden
	// Schnittstelle fuer GameEvents
	protected ArrayList<GameEventListener> evtList	=	new ArrayList<GameEventListener>();

	
// Konstruktor
		// x, y: Koordinaten zum Erscheinen
	public DungeonObject(double x, double y){
		// Koordinaten
		this.x	=	x;
		this.y	=	y;
		// Status-Array deklarieren
		state	=	new State[1];
		// Status definieren
		state[0]	=	new State(Data_Textures.potionused, false, true, true); 
		// pointer setzen
		switchState(0);
		// Reset-werte Setzen
		resetValues	=	new int[4];
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
	
	// Methode zum Setzen der Reset-Werte
	public void setResetValues(){
		// Objekt ist Resetbar!
		hasResetValues = true;
		// Werte setzen

		resetValues[0]	=	currState;	// 1. Wert: Aktueller State
			resetValues[1]	=	direction;	// 2. Wert Aktuelle Richtung
			resetValues[2]	=	(int)x;			// 3. Wert: X-Koordinate
			resetValues[3]	=	(int)y;			// 4. Wert: Y-Koordinate
	}

	// Methode zum zuruecksetzen des Objektes
	public void reset(){
		if(!hasResetValues) return;
		// Bild setzen
		switchState(resetValues[0]);
		// Richtung setzen
		changeDirection(resetValues[1]);
		// Koordinaten setzen
		x=resetValues[2];
		y=resetValues[3];
	}

	// Methode zur Abfrage, ob ein Objekt Resetbar ist
	public boolean isResetable(){
		return hasResetValues;
	}

	
	public void draw(){
		if(state[currState].visible){
        	glBindTexture(GL_TEXTURE_2D, state[currState].getTexture().getTextureID());
			glBegin(GL_QUADS);
				glTexCoord2f(0, 0);
				glVertex2d(x, y);
				glTexCoord2f(0, 1);
				glVertex2d(x, y+state[currState].getTexture().getTextureHeight());
				glTexCoord2f(1, 1);
				glVertex2d(x+state[currState].getTexture().getTextureWidth(), y+state[currState].getTexture().getTextureHeight());
				glTexCoord2f(1, 0);
				glVertex2d(x+state[currState].getTexture().getTextureWidth(), y);
			glEnd();
		}
	}

// Die folgenden Methoden werden fuer die Level-methoden "paint" und "collisionCheck" benoetigt:
	
	// x wert ausgeben
	public double getX(){return x;}
	// y wert ausgeben
	public double getY(){return y;}
	// Objekt Mitte ausgeben
	public int[] getCenter(){
		// Werte zwischenlagern vor der Ausgabe
		int[] ret	=	new int[2];
		int[] offsets	=	state[currState].getOffset();
		
		// X-Posi
		ret[0]	=	(int)x+state[currState].getTexture().getTextureWidth()/2;
		// Y-Posi
		ret[1]	=	(int)y+state[currState].getTexture().getTextureHeight()/2;
		
		return ret;
	}
	// bild ausgeben
	public Texture getTexture(){return state[currState].getTexture();}
	// Massive Status ausgeben
	public boolean isMassive(){
		return state[currState].massive;
	}

	// Methode zur Berechnung der Distanz zweier DungeonObjekte
	public int distanceBetween(DungeonObject d){
		// Werte zum vergleichen
		int[] dc	=	d.getCenter();
		return distanceBetween(dc[0],dc[1]);
	}
	public int distanceBetween(int x, int y){
		// Werte zum Vergleichen
		int[] tc	=	this.getCenter();
		
		// Pythagoras ist unser Freund und Helfer in der Not!
		return (int)Math.sqrt( Math.pow((x-tc[0]),2)+Math.pow((y-tc[1]),2) );
	}
	
	// aktuellen Status ausgeben
	public int getCurrState(){
		return currState;
	}

	
	// rahmen ausgeben
	public Rectangle getBorder(){
		if(state[currState].visible){
			int[] offset= state[currState].getOffset();
			return new Rectangle((int)x + (int)offset[1], (int)y + (int)offset[0], state[currState].getTexture().getTextureWidth() -offset[1]-offset[3], state[currState].getTexture().getTextureHeight()-offset[0]-offset[2]);
		}		
		// else
		return new Rectangle(0,0,0,0);
	}
	
}
