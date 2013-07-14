package hhu.propra2013.gruppe55_opengl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Die Klasse Storyteller.
 * Diese Klasse erbt von der Klasse Npc und spezifiert diese weiter als Storyteller.
 * @see Npc
 */

public class Storyteller extends Npc {

	/**
	 * Der Konstruktor fuer den Storyteller.
	 * Beim Aufruf werden dem Storyteller die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird hier der State gesetzt, das Bild geladen und die Hitbox geaendert, sodass man mit dem Storyteller interagieren, aber nicht durch diesen hindurchlaufen kann.
	 * @param spawnX  Die x-Koordinate, an der der Storyteller gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Storyteller gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Storyteller generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Storyteller generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Storyteller generiert wird.
	 */
	
	// Konstruktor fuer den Shopkeeper
    public Storyteller(double spawnX, double spawnY, int h, int angr, int vert, int story) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].defineOffset(0, 0, 1, 5, 0);
		state[1].changeTexture(Data_Textures.storyteller); 	// Platzhalterbild fuer den Shopkeeper
		storyToTell	=	genDialog("story" + story);
		super.getBorder();
    }
    
    
	private static String[] genDialog(String file){
		String[] placeholder = {};
		String line;
		int i = 0;
		
		try{
			FileReader fReader = new FileReader("dialogs/"+file+".txt");
			BufferedReader bReader = new BufferedReader(fReader);
			
			for(i=0; (line = bReader.readLine()) != null; i++){
				if(i == 0){
					placeholder = new String[Integer.parseInt(line)];
				}
				else{
					placeholder[i-1] = line;
				}
			}
			bReader.close();
		}catch (IOException e){e.printStackTrace();}
		
		return(placeholder);
	}
    
}
