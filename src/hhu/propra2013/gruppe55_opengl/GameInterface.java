package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * Die Klasse GameInterface.
 * Diese Klasse implementiert die Dialogboxen und den Shop.
 */

public class GameInterface {
	
	/** Die AWT-Font-Vorlagen. */
	
	private Font font1, font2;					//AWT-Font-Vorlagen
	
	/** Der TrueTypeFont der Bibliothek Slick. */
	
	private UnicodeFont fontDialog, fontShop;	//Slick's TrueTypeFont
	
	/** Das HUD. */
	
	private HUD hud;				//HUD
	
	/** Das aktuelle Level. */
	
	private Level currLvl;		//Aktuelles Level
	
	/** Der aktuelle (anzuzeigende) Dialog. */
	
	private String[] currDialog;	//Anzuzeigender Dialog
	
	/** Die aktuelle (anzuzeigende) Dialogzeile. */
	
	private int dialogCounter;		//Anzuzeigende Dialogzeile
	
	/** Das ausgewaehlte Shopobjekt. */
	
	private int selectedObject;		// Ausgewaehltes Shopobjekt

	/**
	 * Der Konstruktor fuer das GameInterface.
	 * Beim Aufruf wird dem Konstruktor das Objekt t vom Typ Level uebergeben. Des Weiteren werden Fonts gesetzt und das HUD konstruiert (siehe HUD).
	 * @param t  Die Methode erwartet die Uebergabe eines Objekts t vom Typ Level
	 * @see HUD
	 */

	//Interface-Konstruktor
	public GameInterface(Level t){
		//Fonts fue das Interface konstruieren
		font1 = new Font("Viner Hand ITC", Font.BOLD, 20);
		font2 = new Font("Viner Hand ITC", Font.BOLD, 15);
		fontDialog = new UnicodeFont(font1);
		fontShop = new UnicodeFont(font2);
		
		fontDialog.addAsciiGlyphs();
		fontDialog.addGlyphs(400, 600);
		fontDialog.getEffects().add(new ColorEffect(Color.WHITE));
		fontShop.addAsciiGlyphs();
		fontShop.addGlyphs(400, 600);
		fontShop.getEffects().add(new ColorEffect(Color.WHITE));
		try {
			fontDialog.loadGlyphs();
			fontShop.loadGlyphs();
		}catch(SlickException e){}
		
		currLvl = t;										//Level uebergeben
		hud = new HUD();									//HUD konstruieren
	}
	
	/**
	 * Die Methode paint.
	 * Diese Methode zeichnet das komplette HUD, fragt ab ob ein Dialog oder ein Shop gezeichnet werden soll und zeichnet diesen dann.
	 * @param p  Die Methode erwartet die Uebergabe eines Objektes p vom Typ Player
	 * @param full  Die Methode erwartet die Uebergabe eines boolean Werts full
	 * @see HUD
	 */
	
	//Interface zeichnen
	public void paint(Player p, boolean full){		
		hud.draw(full, p);					//Draw HUD
		
		// Dialoge zeichnen
		if(currLvl.getOpenedInterface() == 1 || currLvl.getOpenedInterface() == 3){
			paintDialog();
		}
		// Shop zeichnen
		if(currLvl.getOpenedInterface() == 2 || currLvl.getOpenedInterface() == 3){
			paintShop();
		}
	}
	
	/**
	 * Die Methode paintDialog.
	 * Diese Methode zeichnet einen Dialog (z.B. wenn der Player mit einem NPC redet). 
	 */
	
	//Dialog zeichnen
	public void paintDialog(){
		hud.drawHudElement(Data_Textures.dialogBox, (Display.getWidth()/2)-250, Display.getHeight()-80);	//DialogBox zeichnen
		fontDialog.drawString((Display.getWidth()/2)-220f, (Display.getHeight()-55f), currDialog[dialogCounter]);	//Dialogzeile zeichnen
	}
	
	/**
	 * Die Methode paintShop
	 * Diese Methode zeichnet den Shop, wenn dieser aufgerufen wird und implementiert die Auswahl von kaufbaren Objekten.
	 */
	
	//Shopfenster zeichnen
	public void paintShop(){
		hud.drawHudElement(Data_Textures.shop, (Display.getWidth()/2)-128, Display.getHeight()/2-64);	//DialogBox zeichnen
		hud.drawHudElement(Data_Textures.potion, (Display.getWidth()/2)-100, Display.getHeight()/2-30);	//DialogBox zeichnen
		hud.drawHudElement(Data_Textures.mpotion, (Display.getWidth()/2)-100, Display.getHeight()/2-3);	//DialogBox zeichnen
		hud.drawHudElement(Data_Textures.arrow_f, (Display.getWidth()/2)-87, Display.getHeight()/2+37);	//DialogBox zeichnen
		fontShop.drawString((Display.getWidth()/2-67), Display.getHeight()/2-15, "x1");	//Dialogzeile zeichnen
		fontShop.drawString((Display.getWidth()/2-67), Display.getHeight()/2+12, "x1");	//Dialogzeile zeichnen
		fontShop.drawString((Display.getWidth()/2-67), Display.getHeight()/2+35, "x10");	//Dialogzeile zeichnen
		fontShop.drawString((Display.getWidth()/2-25), Display.getHeight()/2-15, "10");	//Dialogzeile zeichnen
		fontShop.drawString((Display.getWidth()/2-25), Display.getHeight()/2+12, "10");	//Dialogzeile zeichnen
		fontShop.drawString((Display.getWidth()/2-25), Display.getHeight()/2+35, "10");	//Dialogzeile zeichnen
		hud.drawHudElement(Data_Textures.currency, (Display.getWidth()/2), Display.getHeight()/2-11);	//DialogBox zeichnen
		hud.drawHudElement(Data_Textures.currency, (Display.getWidth()/2), Display.getHeight()/2+15);	//DialogBox zeichnen
		hud.drawHudElement(Data_Textures.currency, (Display.getWidth()/2), Display.getHeight()/2+38);	//DialogBox zeichnen
		switch(selectedObject){
			case 0:
				hud.drawHudElement(Data_Textures.shopArrow, (Display.getWidth()/2)-115, Display.getHeight()/2-15);	//DialogBox zeichnen
				break;
			case 1:
				hud.drawHudElement(Data_Textures.shopArrow, (Display.getWidth()/2)-115, Display.getHeight()/2+12);	//DialogBox zeichnen
				break;
			case 2:
				hud.drawHudElement(Data_Textures.shopArrow, (Display.getWidth()/2)-115, Display.getHeight()/2+35);	//DialogBox zeichnen
				break;
		}
	}
	
	/**
	 * Die Methode setDialog.
	 * Diese Methode uebergibt den anzuzeigenden Dialog (str) und die Startzeile (count), wenn dieser Mehrere Zeilen umfasst.
	 * @param str  Die Methode erwartet die Uebergabe eines String[] str
	 * @param count  Die Methode erwartet die Uebergabe eines int Werts count
	 */
	
	//Anzuzeigenden Dialog und Startzeile uebergeben (Mehrere Zeilen)
	public void setDialog(String[] str, int count){
		currDialog = str;
		dialogCounter = count;
	}
	
	/**
	 * Die Methode setDialog.
	 * Diese Methode uebergibt den anzuzeigenden Dialog (str) und die Startzeile (count), wenn dieser eine einzelne Zeile umfasst. 
	 * @param str  Die Methode erwartet die Uebergabe eines String[] str
	 */
	
	//Anzuzeigenden Dialog und Startzeile uebergeben (Einzelne Zeilen)
	public void setDialog(String str){
		currDialog = new String[1];
		currDialog[0] = str;
		dialogCounter = 0;
	}
	
	/** 
	 * Die Methode next.
	 * Diese Methode schaltet die Dialogzeilen durch und wenn es sich um die letzte Zeile handelt wird der Dialog beendet.
	 */
	
	//N�chste Dialogzeile anzeigen, wenn letzte Zeile ausgew�hlt, Dialog beenden
	public void next(){
		if(currLvl.getOpenedInterface() == 1){
			if(dialogCounter == currDialog.length-1){
				currLvl.toggleFreeze();
				currLvl.setDialog(false);
				currLvl.setOpenedInterface(0);
				currDialog = null;
				dialogCounter = 0;
			}
			else{
				dialogCounter++;
			}
		}
		else if(currLvl.getOpenedInterface() == 3){
			currLvl.setOpenedInterface(2);
			currDialog = null;
			dialogCounter = 0;
		}
	}
	
	/**
	 * Die Methode buttonAction.
	 * Diese Methode implementiert die Bedienbarkeit des Shops. 
	 * @param k  Die Methode erwartet die Uebergabe eines int Werts k
	 * @param p  Die Methode erwartet die Uebergabe eines Obejekts p vom Typ player
	 */
	
	// Keyboard-Events im Interface
	public void buttonAction(int k, Player p){
		if(currLvl.getOpenedInterface() == 1){
			if(k == 28){
				next();
			}
		}
		else if(currLvl.getOpenedInterface() == 2){
			if(k == Keyboard.KEY_DOWN){
				if(selectedObject <2){
					selectedObject++;
				}
				else if(selectedObject == 2){
					selectedObject = 0;
				}
			}
		else if(k == Keyboard.KEY_UP){
			if(selectedObject >0){
				selectedObject--;
			}
			else if(selectedObject == 0){
				selectedObject = 2;
			}
		}
		else if(k == 28){
			switch(selectedObject){
				case 0:
					if(p.getStatInventoryObjectCount(1) >= 10){
						p.giveStatInventoryObject(2, 1);
						p.giveStatInventoryObject(1, -10);
					}
					else{
						setDialog("Du hast nicht genug Gold");
						currLvl.setOpenedInterface(3);
					}
					break;
				case 1:
					if(p.getStatInventoryObjectCount(1) >= 10){
						p.giveStatInventoryObject(3, 1);
						p.giveStatInventoryObject(1, -10);
					}
					else{
						setDialog("Du hast nicht genug Gold");
						currLvl.setOpenedInterface(3);
					}
					break;
				case 2:
					if(p.getStatInventoryObjectCount(1) >= 10){
						p.giveStatInventoryObject(4, 10);
						p.giveStatInventoryObject(1, -10);
					}
					else{
						setDialog("Du hast nicht genug Gold");
						currLvl.setOpenedInterface(3);
					}
					break;
				}
			}
		}
		else if(currLvl.getOpenedInterface() == 3){
			if(k == 28){
				next();
			}
		}
	}
	
	/**
	 * Die Methode setSelectedObject.
	 * Diese Methode aendert das im Shop ausgewaehlte Objekt.
	 * @param s  Die Methode erwartet die Uebergabe eines int Werts s
	 */
	
	
	// Ausgewaehltes Objekt aendern
	public void setSelectedObject(int s){
		selectedObject = s;
	}

}

/**
 * Die Klasse HUD.
 * Diese Klasse implementiert das HUD.
 *
 */

//HUD
class HUD {
	
	/** Das FullscreenOffset. */
	
	private int fullScreenOffset;
	
	/** Die AWT-Font-Vorlagen. */
	
	private Font fontAwt;
	
	/** Der TrueTypeFont der Bibliothek Slick. */
	
	private UnicodeFont font;
	
	/**
	 * Der Konstruktor fuer das HUD.
	 * Hier werden Eigenschaften wie die Fonts fuer das HUD gesetzt.
	 */
	
	public HUD(){
		//Fonts fuer das HUD konstruieren
		fontAwt = new Font("Viner Hand ITC", Font.BOLD, 13);
		font = new UnicodeFont(fontAwt);
		font.addAsciiGlyphs();
		font.addGlyphs(400, 600);
		font.getEffects().add(new ColorEffect(Color.WHITE));
		try {
			font.loadGlyphs();
		}catch(SlickException e){e.printStackTrace();}
	}
	
	/**
	 * Die Methode draw.
	 * Diese Methode zeichnet die einzelnen Elemente des HUD's
	 * @param full  Die Methode erwartet die Uebergabe eines booleanwerts full
	 * @param p  Die Methode erwartet die Uebergabe eines Objektes p vom Typ Player
	 */
	
	// Zeichnen des HUD
	public void draw(boolean full, Player p){		
		// FullScreenOffset setzen
		if(full){
			fullScreenOffset = (Toolkit.getDefaultToolkit().getScreenSize().width/2)-480;
		}
		else{
			fullScreenOffset = 0;
		}
		
		// Draw HUD
		if(p.getWeapSet() == 0){
			drawHudElement(Data_Textures.hud01, 0+fullScreenOffset, 0);
		}
		else if(p.getWeapSet() == 1){
			drawHudElement(Data_Textures.hud02, 0+fullScreenOffset, 0);
		}
		else if(p.getWeapSet() == 2){
			drawHudElement(Data_Textures.hud03, 0+fullScreenOffset, 0);
		}
		
		int posX = 15;
		int value = p.getHP();
		int i = p.getHPMax();
		
		// HP-Container zeichnen
		while(i > 0){
			if(value > 1){
				drawHudElement(Data_Textures.hud_Tear_HP_Full, posX+fullScreenOffset, 20);
				value -= 2;
				i -= 2;
				posX += 30;
			}
			else if(value == 1){
				drawHudElement(Data_Textures.hud_Tear_HP_Half, posX+fullScreenOffset, 20);
				value -= 1;
				i -= 2;
				posX += 30;
			}
			else if(i > 0){
				drawHudElement(Data_Textures.hud_Tear_HP_Empty, posX+fullScreenOffset, 20);
				i -= 2;
				posX += 30;
			}
		}
		font.drawString(posX+fullScreenOffset, 34, "" + p.getHP() + "/" + p.getHPMax());
		
		posX = 15;
		value = p.getMana();
		i = p.getManaMax();
		
		// MP-Container zeichnen
		while(i > 0){
			if(value >= 1){
				drawHudElement(Data_Textures.hud_Crystal_Full, posX+fullScreenOffset, 70);
				value--;
				i--;
				posX += 30;
			}
			else{
				drawHudElement(Data_Textures.hud_Crystal_Empty, posX+fullScreenOffset, 70);
				i--;
				posX += 30;
			}
		}
		font.drawString(posX+fullScreenOffset, 71, "" + p.getMana() + "/" + p.getManaMax());

		// Weaponicons zeichnen
		drawHudElement(Data_Textures.basicsword_icon, 430+fullScreenOffset, 20);
		drawHudElement(Data_Textures.basicbow_icon, 530+fullScreenOffset, 20);
		
		// Geld-Wert schreiben
		font.drawString(745+fullScreenOffset, 60, "" + p.getStatInventoryObjectCount(1));
		drawHudElement(Data_Textures.currency, 775+fullScreenOffset, 63);	//Currency
		
		// Lives zeichnen
		drawHudElement(Data_Textures.player_f, 820+fullScreenOffset, 14);
		font.drawString(855+fullScreenOffset, 24, "x " + p.getStatInventoryObjectCount(0));
		// Pfeile zeichnen
		drawHudElement(Data_Textures.arrow_f, 900+fullScreenOffset, 24);
		font.drawString(920+fullScreenOffset, 24, "x "+p.getStatInventoryObjectCount(4));
		// Tr�nke zeichnen
		drawHudElement(Data_Textures.potion, 820+fullScreenOffset, 54);
		font.drawString(850+fullScreenOffset, 68, "x  " + p.getStatInventoryObjectCount(2));
		drawHudElement(Data_Textures.mpotion, 890+fullScreenOffset, 54);
		font.drawString(920+fullScreenOffset, 68, "x  " + p.getStatInventoryObjectCount(3));
	}
	
	/**
	 * Die Methode drawHudElement.
	 * Diese Methode zeichnet die Objekte mit openGL.
	 * @param tex  Die Methode erwartet die Uebergabe einer Textur
	 * @param x  Die Methode erwartet die Uebergabe eines int Werts x
	 * @param y  Die Methode erwartet die Uebergabe eines int Werts y
	 */
	
	public void drawHudElement(Texture tex, int x, int y){
    	glBindTexture(GL_TEXTURE_2D, tex.getTextureID());
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(0, 1);
			glVertex2f(x, y+tex.getTextureHeight());
			glTexCoord2f(1, 1);
			glVertex2f(x+tex.getTextureWidth(), y+tex.getTextureHeight());
			glTexCoord2f(1, 0);
			glVertex2f(x+tex.getTextureWidth(), y);
		glEnd();		
	}
}

