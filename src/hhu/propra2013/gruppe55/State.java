package hhu.propra2013.gruppe55;

import java.awt.Image;

public class State {

	// Bilder
	public String img;				// Das Bild aus der Ressources Klasse, dass beim jeweiligen State gezeigt wird
	// Eigenschaften des States
	public boolean moveable;		// ist das Objekt beweglich?
	public boolean massive;			// kann der Spieler hindurch gehen?
	public boolean visible;			// ist das Objekt sichtbar?
	public int[] offset = {0,0,0,0}; // Offset um individuelle Hitboxen zu generieren
	
// Konstruktor
	public State(String resourceImg, boolean moveable, boolean massive, boolean visible) {
		img	= resourceImg;
		this.moveable	=	moveable;
		this.massive	=	massive;
		this.visible	=	visible;
	}
	
// Methoden
	
	// Aendern des Bildes des Objektes
	public void changeImg(String image){
		img	= image;
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
	
	public void defineOffset(int t, int l, int b, int r){
	    offset[0] = t; // Top - oberes Offset
	    offset[1] = l; // left - linkes Offset
	    offset[2] = b; // bottom - unteres Offset
	    offset[3] = r; // right - rechtes Offset
	}
	
	// Offsetwerte uebergeben
	public int[] getOffset(){
		return  offset;	
	}

}
