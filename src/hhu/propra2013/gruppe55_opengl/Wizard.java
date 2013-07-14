package hhu.propra2013.gruppe55_opengl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Die Klasse Wizard.
 * Diese Klasse erbt von der Klasse Npc und spezifiert diese weiter als Wizard.
 * @see Npc
 */

public class Wizard extends Npc {
	
	/** Die Waffe, die der jeweilige Wizard uebergibt. */
		
	private int weapon;
	
	
	/**
	 * Der Konstruktor fuer den Wizard.
	 * Beim Aufruf werden dem Wizard die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird hier der State gesetzt, das Bild geladen und die Hitbox geaendert, sodass man mit dem Wizard interagieren, aber nicht durch diesen hindurchlaufen kann.
	 * @param spawnX  Die x-Koordinate, an der der Wizard gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Wizard gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Wizard generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Wizard generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Wizard generiert wird.
	 */
	
	// Konstruktor fuer den Wizard
    public Wizard(double spawnX, double spawnY, int h, int angr, int vert, int story) {
		super(spawnX, spawnY, h, angr, vert);
		// States setzen
		state[1].defineOffset(0, 0, 1, 5, 0);
		state[1].changeTexture(Data_Textures.wizard); 	// Platzhalterbild fuer den Wizard
		storyToTell	=		genDialog("wizard" + story);
		super.getBorder();
		weapon = story;
		
    }
    
	/**
	 * Die interaction-Methode von NPC-Objekten kontrolliert die Ausgabe eines Dialoges bzw das Abrufen des Shops.
	 * In diesem Fall wird noch das Event gefeuert, dass der Spieler das Schwert erhält.
	 * @param px X-Koordinate des Spielers
	 * @param py Y-Koordinate des Spielers
	 * @see LivingObject
	 */
    
    public void interaction(int px, int py){
    	super.interaction(px, py);
    	if(distanceBetween(px, py) <= interactionRange){
    		if(weapon==1){
    			for(GameEventListener gel : evtList){
    				gel.giveWeaponToPlayer();
    			}
    		}
    		else if(weapon==2){
    			for(GameEventListener gel : evtList){
    				gel.giveProjectileToPlayer();
    			}
    		}
    		
    	}
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