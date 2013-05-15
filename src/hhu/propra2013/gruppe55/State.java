package hhu.propra2013.gruppe55;

import java.awt.Image;

import javax.swing.ImageIcon;

public class State {

	// image
	public Image img;				// the image displayed while this state is active
									// properties controlled by this state
	public boolean moveable;		// allows movement?
	public boolean massive;			// does it stop the player on moving through?
	public boolean visible;			// img visible?
	public int[] offset = {0,0,0,0}; // Offset to create individual hitboxes
	
// constructor
	public State(String imgPath, boolean moveable, boolean massive, boolean visible) {
		img	=	(new ImageIcon(imgPath)).getImage();
		this.moveable	=	moveable;
		this.massive	=	massive;
		this.visible	=	visible;
	}
	
// methods
	
	// change the grafic
	public void changeImg(String imgPath){
		img	=	(new ImageIcon(imgPath)).getImage();
	}
	// change movement
	public void changeMoveable(boolean b){
		moveable	=	b;
	}
	// change massive
	public void changeMassive(boolean b){
		massive	=	b;
	}
	// change movement
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
