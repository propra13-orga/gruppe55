package hhu.propra2013.gruppe55_opengl;

import org.newdawn.slick.opengl.Texture;

public class State {

	// Bilder
	public Texture[] texture = new Texture[4];				// Das Bild aus der Ressources Klasse, dass beim jeweiligen State gezeigt wird
	// Eigenschaften des States
	public boolean moveable;		// ist das Objekt beweglich?
	public boolean massive;			// kann der Spieler hindurch gehen?
	public boolean visible;			// ist das Objekt sichtbar?
	protected int[][] offset = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
	// Richtungseigenschaften
	protected boolean directionFix	=	false;	// soll das Objekt sich in versch. Richtungen drehen?
	protected int currDirection		=	0;		// 0 vorne, 1 links, 2 rechts, 3 hinten
	
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
	
	// Aendern des Bildes des Objektes
	public void changeTexture(Texture tex){
		changeTexture(tex, 0);
	}
	public void changeTexture(Texture tex, int direction){
		texture[direction]	= tex;
	}
	// Aendern der beweglichkeit des Objektes
	public void changeMoveable(boolean b){
		moveable	=	b;
	}
	// Aendern der Begehbarkeit des Objekts
	public void changeMassive(boolean b){
		massive	=	b;
	}
	// Aendern der Sichtbarkeit des Objekts
	public void changeVisibility(boolean b){
		visible	=	b;
	}
	// Richtung Aendern
	public void changeDirection(int d){
		// keine Aenderung, wenn die Richtung fix ist
		if(directionFix) return;
		
		currDirection	=	d%4; //%4 weil wir nur Werte aus [0,3] erlauben moechten)
	}
	
	public void defineOffset(int t, int l, int b, int r, int direction){
	    offset[direction%4][0] = t; // Top - oberes Offset
	    offset[direction%4][1] = l; // left - linkes Offset
	    offset[direction%4][2] = b; // bottom - unteres Offset
	    offset[direction%4][3] = r; // right - rechtes Offset
	}
	
	// Offsetwerte uebergeben
	public int[] getOffset(){
		return  offset[currDirection];	
	}
	
	// Bild auslesen
	public Texture getTexture(){
		return texture[currDirection];
	}

}
