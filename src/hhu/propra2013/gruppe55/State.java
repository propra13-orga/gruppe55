package hhu.propra2013.gruppe55;

import java.awt.Image;

public class State {

	// Bilder
	protected Image img[]	=	new Image[4];	// Die Bilder aus der Data Klasse, dass bei entsprechender Richtung gezeigt wird
	// Eigenschaften des States
	protected boolean moveable;		// ist das Objekt beweglich?
	protected boolean massive;			// kann der Spieler hindurch gehen?
	protected boolean visible;			// ist das Objekt sichtbar?
	// Offset um individuelle Hitboxen zu generieren
	protected int[][] offset = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}}; 
	// Richtungseigenschaften
	protected boolean directionFix	=	false;	// soll das Objekt sich in versch. Richtungen drehen?
	protected int currDirection		=	0;		// 0 vorne, 1 links, 2 rechts, 3 hinten
	
// Konstruktor
	// Konstruktor mit einzelnem Bild => automatisch nur eine Richtung
	public State(Image resourceImg, boolean moveable, boolean massive, boolean visible) {
		img[0]	= resourceImg;
		this.moveable	=	moveable;
		this.massive	=	massive;
		this.visible	=	visible;
		// Richtung fixieren
		directionFix	=	true;
	}
	// Konstruktor mit mehreren Bildern
	public State(Image dImg1, Image dImg2, Image dImg3, Image dImg4, boolean moveable, boolean massive, boolean visible) {
		img[0]	= dImg1;
		img[1]	= dImg2;
		img[2]	= dImg3;
		img[3]	= dImg4;
		this.moveable	=	moveable;
		this.massive	=	massive;
		this.visible	=	visible;
		// Richtung fixieren
		directionFix	=	false;
	}
	
// Methoden
	
	// Aendern des Bildes des Objektes
	public void changeImg(Image image){
		changeImg(image, 0);
	}
	public void changeImg(Image image, int direction){
		img[direction]	= image;
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
	public Image getImg(){
		return img[currDirection];
	}

}
