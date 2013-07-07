package hhu.propra2013.gruppe55_opengl;

import org.newdawn.slick.opengl.Texture;

/**
 * Die Klasse State.
 * Diese Klasse implementiert die verschiedenen States, die die Objekte annehmen koennen.
 */

public class State {

	// Bilder
	
	/** Das Bild aus der Ressources Klasse, dass beim jeweiligen State gezeigt wird. */
	
	public Texture[] texture = new Texture[4];				// Das Bild aus der Ressources Klasse, dass beim jeweiligen State gezeigt wird
	
	// Eigenschaften des States
	
	/** Die Abfrage, ob das Objekt beweglich ist. */
	
	public boolean moveable;		// ist das Objekt beweglich?
	
	/** Die Abfrage, ob der Spieler durch das Objekt hindurch gehen kann. */
	
	public boolean massive;			// kann der Spieler hindurch gehen?
	
	/** Die Abfrage, ob das Objekt sichtbar ist. */
	
	public boolean visible;			// ist das Objekt sichtbar?
	
	/** Das Array fuer die Offsets. */
	
	protected int[][] offset = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
	
	// Richtungseigenschaften
	
	/** Die Abfrage, ob sich das Objekt in verschiedene Richtungen drehen soll. */
	
	protected boolean directionFix	=	false;	// soll das Objekt sich in versch. Richtungen drehen?
	
	/** Die aktuelle Richtung (0 = vorne; 1 = links; 2 = rechts, 3 = hinten). */
	
	protected int currDirection		=	0;		// 0 vorne, 1 links, 2 rechts, 3 hinten
	
	
	/**
	 * Der Konstruktor fuer die Klasse State bei einzelnen Bildern (in eine Richtung).
	 * Hier werden die Eigenschaften fuer die States mit einem einzelnen Bild gesetzt.
	 * @param resourceTexture  Die Methode erwartet die Uebergabe einer Textur resourceTexture
	 * @param moveable  Die Methode erwartet die Uebergabe eines boolean Werts moveable
	 * @param massive  Die Methode erwartet die Uebergabe eines boolean Werts massive
	 * @param visible  Die Methode erwartet die Uebergabe eines boolean Werts visible
	 */
	
// Konstruktor
	// Konstruktor mit einzelnem Bild => automatisch nur eine Richtung
	public State(Texture resourceTexture, boolean moveable, boolean massive, boolean visible) {
		texture[0]	= resourceTexture;
		this.moveable	=	moveable;
		this.massive	=	massive;
		this.visible	=	visible;
		// Richtung fixieren
		directionFix	=	true;
	}
	
	/**
	 * Der Konstruktor fuer die Klasse State bei einzelnen Bildern (in eine Richtung).
	 * Hier werden die Eigenschaften fuer die States mit einem einzelnen Bild gesetzt.
	 * @param dTex1  Die Methode erwartet die Uebergabe einer Textur dTex1
	 * @param dTex2  Die Methode erwartet die Uebergabe einer Textur dTex2
	 * @param dTex3  Die Methode erwartet die Uebergabe einer Textur dTex3
	 * @param dTex4  Die Methode erwartet die Uebergabe einer Textur dTex4
	 * @param moveable  Die Methode erwartet die Uebergabe eines boolean Werts moveable
	 * @param massive  Die Methode erwartet die Uebergabe eines boolean Werts massive
	 * @param visible  Die Methode erwartet die Uebergabe eines boolean Werts visible
	 */
	
	// Konstruktor mit mehreren Bildern
	public State(Texture dTex1, Texture dTex2, Texture dTex3, Texture dTex4, boolean moveable, boolean massive, boolean visible) {
		texture[0]	= dTex1;
		texture[1]	= dTex2;
		texture[2]	= dTex3;
		texture[3]	= dTex4;
		this.moveable	=	moveable;
		this.massive	=	massive;
		this.visible	=	visible;
		// Richtung fixieren
		directionFix	=	false;
	}
	
// Methoden
	
	/**
	 * Die Methode changeTexture.
	 * Diese Methode aendert die geladene Textur auf eine Andere.
	 * @param tex  Die Methode erwartet die Uebergabe einer Textur tex
	 */
	
	// Aendern des Bildes des Objektes
	public void changeTexture(Texture tex){
		changeTexture(tex, 0);
	}
	
	/**
	 * die Methode changeTexture.
	 * Diese Methode aendert die geladene Textur auf eine Andere und aender die Richtung in der Sie gezeichnet wird.
	 * @param tex  Die Methode erwartet die Uebergabe einer Textur tex
	 * @param direction  Die Methode erwartet die Uebergabe eines int Werts direction
	 */
	
	public void changeTexture(Texture tex, int direction){
		texture[direction]	= tex;
	}
	
	/**
	 * Die Methode changeMoveable.
	 * Diese Methode aendert die Beweglichkeit des Objekts.
	 * @param b  Die Methode erwartet die Uebergabe eines boolen Werts b
	 */
	
	
	// Aendern der beweglichkeit des Objektes
	public void changeMoveable(boolean b){
		moveable	=	b;
	}
	
	/**
	 * Die Methode changeMassive.
	 * Diese Methode aendert die Begehbarkeit des Objekts.
	 * @param b  Die Methode erwartet die Uebergabe eines boolen Werts b
	 */
	
	// Aendern der Begehbarkeit des Objekts
	public void changeMassive(boolean b){
		massive	=	b;
	}
	
	/**
	 * Die Methode changeVisibility.
	 * Diese Methode aendert die Sichtbarkeit des Objekts.
	 * @param b  Die Methode erwartet die Uebergabe eines boolen Werts b
	 */
	
	// Aendern der Sichtbarkeit des Objekts
	public void changeVisibility(boolean b){
		visible	=	b;
	}
	
	/**
	 * Die Methode changeDirection.
	 * Diese Methode aendert die Ausrichtung, mit der des Objekts.
	 * @param d  Die methode erwartet die Uebergabe eines int Werts d
	 */
	
	// Richtung Aendern
	public void changeDirection(int d){
		// keine Aenderung, wenn die Richtung fix ist
		if(directionFix) return;
		
		currDirection	=	d%4; //%4 weil wir nur Werte aus [0,3] erlauben moechten)
	}
	
	/**
	 * Die Methode defineOffset.
	 * Diese Methode definiert die einzelnen Offsets in einem 2-Dimensionalem Array.
	 * @param t  Die Methode erwartet die Uebergabe eines int Werts t
	 * @param l  Die Methode erwartet die Uebergabe eines int Werts l
	 * @param b  Die Methode erwartet die Uebergabe eines int Werts b
	 * @param r  Die Methode erwartet die Uebergabe eines int Werts r
	 * @param direction  Die Methode erwartet die Uebergabe eines int Werts direction
	 */
	
	public void defineOffset(int t, int l, int b, int r, int direction){
	    offset[direction%4][0] = t; // Top - oberes Offset
	    offset[direction%4][1] = l; // left - linkes Offset
	    offset[direction%4][2] = b; // bottom - unteres Offset
	    offset[direction%4][3] = r; // right - rechtes Offset
	}
	
	/**
	 * Die Methode getOffset.
	 * Diese Methode gibt das jeweilige Offset zurueck.
	 * @return Das Offset aus dem array an der Stelle offset[currDirection].
	 */
	
	// Offsetwerte uebergeben
	public int[] getOffset(){
		return  offset[currDirection];	
	}
	
	/**
	 * Die Methode getTexture.
	 * Diese Methode liest die benoetigte Textur aus.
	 * @return Die Textur in der gewuenschten Richtung.
	 */
	
	// Bild auslesen
	public Texture getTexture(){
		return texture[currDirection];
	}

}
