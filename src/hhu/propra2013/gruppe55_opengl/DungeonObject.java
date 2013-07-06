package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Rectangle;
import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse DungeonObject.
 * Diese Klasse bildet das Grundgeruest, von der die meisten anderen Klassen erben. Viele der Funktionen in dieser Klasse werden in den einzelnen Unterklassen ueberschrieben und leicht abgeaendert.
 */

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
	protected Map<String, Boolean> trigger	=	new HashMap<String, Boolean>();		// Werte der Trigger
	protected ArrayList<String> triggerNames	=	new ArrayList<String>();		// TriggerNamen, auf die geachtet werden soll
	// Element
	protected int element;		// Element des Objektes (siehe Konstruktor fuer Beschreibung)

	/**
	 * Der Konstruktor fuer ein DungeonObject.
	 * Beim Aufruf werden die x und y Werte an den Konstruktor uebergeben. 
	 * Des Weiteren werden hier alle Standardwerte fuer ein Objekt auf einen Standard-/Platzhalterzustand gesetzt, sowie das Array fuer die Resetfunktion initialisiert
	 * @param x  die x Koordinate fuer ein Dungeonobject
	 * @param y  die y Koordinate fuer das Dungeonobject
	 */
	
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
		
		// Element setzen
			/*
			 * 0: normal
			 * 1: Feuer
			 * 2: ???
			 * 3: ???
			 * 
			 * Reihenfolge:
			 * 1>2>3>1, wobei > angibt, welches Element was uebertrumpht
			 * 0 ist das neutrale Element und gewaehrt keinen Bonus aber auch keinen Malus
			 */
		element	=	0;	// Standartelement ist neutral
		
	}
	
// Methoden
	
	/**
	 * Die Methode addGameListener.
	 * Diese Methode fuegt den GameEventListener hinzu.
	 * @param listener  Die Methode erwartet die Uebergabe eines Objektes vom Typ GameEventListener.
	 */
	
	// Hinzufuegen des GameEventListeners
	public void addGameListener(GameEventListener listener){
		evtList.add(listener);
	}
	
	/**
	 * Die Methode onCollision.
	 * Diese Methode uberprueft ob 2 Objekte miteinander Kollidieren, das heisst ob Ihre Hitboxen sich ueberschneiden. Diese Methode wird in den einzelnen spezifizierten Objekten individuell angepasst.
	 * @param d  Die Methode erwartet die Uebergabe eines Objektes vom Typ DungeonObject. 
	 */
	
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
	
	/**
	 * Die Methode switchState.
	 * Diese Methode wechselt den Status (state) in dem sich die verschiedenen Objekte befinden, z.B. wenn der Spieler stirbt und den status von lebendig auf tot aendert. 
	 * @param s  Die Methode erwartet die Uebergabe eines Int Werts s (= der gewuenschte State). 
	 */
	
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
	
	/**
	 * Die Methode changeDirection.
	 * Diese Methode wechselt die Richtung in der die einzelnen Objekte angezeigt werden.
	 * @param d  Die Methode erwartet die Uebergabe eines Int Werts d (=die gewuenschte Richtung).
	 */
	
	// Methode zum Richtungswechsel
	public void changeDirection(int d){
		direction	=	d;
		// so "viele" states!
		for(int i=0; i<state.length; i++){
			state[i].changeDirection(d);
		}
	}
	
	/**
	 * Die Methode setResetValues.
	 * Diese Methode ueberprueft ob es Werte gibt auf die man zuruecksetzen moechte (also die Werte bei erreichen eines Checkpoints) und speichert diese in ein dafuer vorgesehenes Array. 
	 */
	
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
	
	/**
	 * Die Methode reset.
	 * Diese Methode fuehrt den Reset, sofern ResetValues vorhanden sind, mit den in setResetValues gesetzten Werten durch.
	 */

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
	
	/**
	 * Die Methode isResetable.
	 * Diese Methode gibt einen boolean Wert der Variable hasResetValues zurueck, die ueber die SetResetValuesFunktion gesetzt wird.
	 * @return boolean hasResetValues
	 */

	// Methode zur Abfrage, ob ein Objekt Resetbar ist
	public boolean isResetable(){
		return hasResetValues;
	}

	/**
	 * Die Methode draw.
	 * Diese Methode zeichnet das jeweilige Objekt mit openGl. Diese Methode wird in den jeweiligen Unterklassen ueberschrieben und angepasst.
	 */
	
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
	
	/**
	 * Die Methode isTriggerSet.
	 * Diese Methode fragt ab, ob ein entsprechender Trigger gesetzt ist.
	 * @param key  Die Methode erwartet die Uebergabe eines Strings Key
	 * @return true, wenn ein Trigger gesetzt ist.
	 */
	
	// Nachpruefen, ob ein entsprechender Trigger gesetzt ist
	public boolean isTriggerSet(String key){
		// Liste abfragen
		return (trigger.containsKey(key) && trigger.get(key));
	}
	
	/**
	 * Die Methode isTriggerListened.
	 * Diese Methode ueberprueft ob ein bestimmter Trigger abgehoert wird (das heisst ob das Schalten des Triggers etwas ausloest).
	 * @param key  Die Methode erwartet die Uebergabe eines Strings key 
	 * @return true, wenn der TriggerKey abgehorcht wird.
	 */
	
	// Nachpruefen ob auf einen bestimmten TriggerKey gehorcht wird
	public boolean isTriggerListened(String key){
		boolean isListenedTo	=	false;
		for(String listenedKey: triggerNames)
			if(listenedKey.equals(key)){
				isListenedTo	=	true;
				break;
			}
		return isListenedTo;
	}
	
	/**
	 * Die Methode toggleTrigger.
	 * Diese Methode ueberprueft ob ein Trigger existiert und toggled (=umschalten zwischen den Werten true und false) den gesuchten Trigger.
	 * @param key  Die Methode erwartet die Uebergabe eines Strings key
	 */
	
	// Methode um einen Trigger zu setzen
	public void toggleTrigger(String key){
		// Nur Trigger setzen, die wir auch beachten wollen
		for(String listenedKey:triggerNames){
			if(listenedKey.equals(key)){
				// Existiert dieser Trigge bereits?
				if(!trigger.containsKey(key)){	// nein, also erstmal setzen
					trigger.put(key, true);		// da beim togglen gesetzt natuerlich auf true
				}
				else{
					trigger.put(key, !trigger.get(key));	// Wert umkehren
				}
				// Objekt gefunden, also ende
				break;
			}
		}
	}
	
	/**
	 * Die Methode triggerAction.
	 * Diese Methode dient zur Ausfuehrung Trigger-bedingter Zustaende.
	 * @param key  Die Methode erwartet die Uebergabe eines Strings key
	 */
	
	// Methode zum ausfuehren Trigger-bedingter Zustaende
	public void triggerAction(String key){
		// Testimplemention zur veranschaulichung
		if(trigger.containsKey(key)){
			System.out.println("you adorable lucker");
		}
	}
	
	/**
	 * Die Methode fireTrigger.
	 * Diese Methode feuert den gewuenschten Trigger.
	 * @param key  Die Methode erwartet die Uebergabe eines Strings key
	 */
	
	// Methode zum leichten Trigger schiessen
	protected void fireTrigger(String key){
		for(GameEventListener gel : evtList){
			gel.triggerFired(key);
		}
	}
	
	/**
	 * Die Methode allTriggersTrue.
	 * Diese Methode fragt ab ob alle Trigger auf true gesetzt sind.
	 * @return true, wenn alle Trigger auf true gesetzt sind.
	 */
	
	// Methode zur Abfrage ob ALLE Trigger auf true stehen
	protected boolean allTriggersTrue(){
		for(String key:triggerNames){
			if(!isTriggerSet(key))
				return false;
		}
		return true;
	}

// Die folgenden Methoden werden fuer die Level-methoden "paint" und "collisionCheck" benoetigt:
	
	/**
	 * Die Methode getX.
	 * Diese Methode gibt den x-Wert des Objektes aus.
	 * @return Der x Wert des Objektes als double.
	 */
	
	// x wert ausgeben
	public double getX(){return x;}
	
	/**
	 * Die Methode getY.
	 * Diese Methode gibt den y-Wert des Objektes aus.
	 * @return Der y Wert des Objektes als double.
	 */
	
	// y wert ausgeben
	public double getY(){return y;}

	/**
	 * Die Methode getCenter.
	 * Diese Methode gibt die genaue Mitte des Objektes aus.
	 * @return Die x und y Werte der Mitte des Objektes als int.
	 */
	
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
	
	/**
	 * Die Methode getTexture.
	 * Die Methode gibt die gewuenschte Textur aus. 
	 * @return Die aktuelle Textur.
	 */
	
	// bild ausgeben
	public Texture getTexture(){return state[currState].getTexture();}
	
	/**
	 * Die Methode isMassiv.
	 * Diese Methode ueberprueft, ob das Objekt massiv ist oder nicht (das heisst, ob das Objekt begehbar ist).
	 * @return true, wenn das Objekt massiv ist. 
	 */
	
	// Massive Status ausgeben
	public boolean isMassive(){
		return state[currState].massive;
	}

	/**
	 * Die Methode distanceBetween.
	 * Diese Methode berechnet die Distanz zwischen 2 Dungeonobjekten.
	 * @param d  Die Methode erwartet die Uebergabe eines DungeonObjektes
	 * @return Der x und y Wert der Mitte des Objektes
	 */
	
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
	
	/**
	 * Die Methode getCurrState.
	 * Diese Methode gibt den aktuellen Status (=state) des Objektes wieder.
	 * @return Der aktuelle Status des Objekts als int.
	 */
	
	// aktuellen Status ausgeben
	public int getCurrState(){
		return currState;
	}
	
	/**
	 * Die Methode getElement
	 * Diese Methode gibt den numerischen Index des Elements wieder, von dem dieses Objekt sein sollte
	 * @return Das Element des Objekts
	 */
	public int getElement(){
		return element;
	}
	
	/**
	 * Die Methode getBorder.
	 * Diese Methode aendert die Hitbox des Objektes.
	 * @return Die neue Hitbox als Rectangle(int, int, int, int).
	 */

	
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
