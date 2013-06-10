package hhu.propra2013.gruppe55_opengl;

import java.awt.event.KeyEvent;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class GameInterface {
	
	private Font fontDialog, fontShop;				//TextFont
	private FontMetrics fMetr;		//FontMetrics für Graphics2D
	private HUD hud;				//HUD
	private Level currLvl;		//Aktuelles Level
	private String[] currDialog;	//Anzuzeigender Dialog
	private int dialogCounter;		//Anzuzeigende Dialogzeile
	private int selectedObject;		// Ausgewaehltes Shopobjekt


	//Interface-Konstruktor
	public GameInterface(Level t){
		fontDialog = new Font("Viner Hand ITC", Font.BOLD, 20);	//FontDialog setzen
		fontShop = new Font("Viner Hand ITC", Font.BOLD, 15);	//FontShop setzen

		currLvl = t;										//Level uebergeben
		hud = new HUD();									//HUD konstruieren
	}
	
	//Interface zeichnen
	public void paint(Player p, boolean full){		
		hud.draw(full, p);					//Draw HUD
		
		// Dialoge zeichnen
		if(currLvl.getOpenedInterface() == 1 || currLvl.getOpenedInterface() == 3){
//			g2d.setFont(fontDialog);					//Graphics2D Font setzen
//			fMetr = g2d.getFontMetrics(fontDialog);	//FontMetrics erzeugen
			hud.drawHudElement(Data_Textures.dialogBox, (Display.getWidth()/2)-250, Display.getHeight()-80);	//DialogBox zeichnen
//			g2d.drawString(currDialog[dialogCounter], (currLvl.getWidth()/2)-(fMetr.stringWidth(currDialog[dialogCounter])/2), (currLvl.getHeight()-40));	//Dialogzeile zeichnen
		}
		// Shop zeichnen
		if(currLvl.getOpenedInterface() == 2 || currLvl.getOpenedInterface() == 3){
//			g2d.setFont(fontShop);					//Graphics2D Font setzen
//			fMetr = g2d.getFontMetrics(fontShop);	//FontMetrics erzeugen
			hud.drawHudElement(Data_Textures.shop, (Display.getWidth()/2)-128, Display.getHeight()/2-64);	//DialogBox zeichnen
			hud.drawHudElement(Data_Textures.potion, (Display.getWidth()/2)-100, Display.getHeight()/2-30);	//DialogBox zeichnen
			hud.drawHudElement(Data_Textures.mpotion, (Display.getWidth()/2)-100, Display.getHeight()/2-3);	//DialogBox zeichnen
			hud.drawHudElement(Data_Textures.arrow_f, (Display.getWidth()/2)-87, Display.getHeight()/2+37);	//DialogBox zeichnen
//			g2d.drawString("x1", (currLvl.getWidth()/2-60)-(fMetr.stringWidth("x1")/2), currLvl.getHeight()/2);	//Dialogzeile zeichnen
//			g2d.drawString("x1", (currLvl.getWidth()/2-60)-(fMetr.stringWidth("x1")/2), currLvl.getHeight()/2+27);	//Dialogzeile zeichnen
//			g2d.drawString("x10", (currLvl.getWidth()/2-57)-(fMetr.stringWidth("x10")/2), currLvl.getHeight()/2+50);	//Dialogzeile zeichnen
//			g2d.drawString("10", (currLvl.getWidth()/2-20)-(fMetr.stringWidth("10")/2), currLvl.getHeight()/2);	//Dialogzeile zeichnen
//			g2d.drawString("10", (currLvl.getWidth()/2-20)-(fMetr.stringWidth("10")/2), currLvl.getHeight()/2+27);	//Dialogzeile zeichnen
//			g2d.drawString("10", (currLvl.getWidth()/2-20)-(fMetr.stringWidth("10")/2), currLvl.getHeight()/2+50);	//Dialogzeile zeichnen
			hud.drawHudElement(Data_Textures.currency, (Display.getWidth()/2-10), Display.getHeight()/2-10);	//DialogBox zeichnen
			hud.drawHudElement(Data_Textures.currency, (Display.getWidth()/2-10), Display.getHeight()/2+17);	//DialogBox zeichnen
			hud.drawHudElement(Data_Textures.currency, (Display.getWidth()/2-10), Display.getHeight()/2+40);	//DialogBox zeichnen
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
	}
	
	//Anzuzeigenden Dialog und Startzeile uebergeben (Mehrere Zeilen)
	public void setDialog(String[] str, int count){
		currDialog = str;
		dialogCounter = count;
	}
	
	//Anzuzeigenden Dialog und Startzeile uebergeben (Einzelne Zeilen)
	public void setDialog(String str){
		currDialog = new String[1];
		currDialog[0] = str;
		dialogCounter = 0;
	}
	
	//Nächste Dialogzeile anzeigen, wenn letzte Zeile ausgewählt, Dialog beenden
	public void next(){
		if(currLvl.getOpenedInterface() == 1){
			currLvl.toggleFreeze();
			currLvl.setDialog(false);
			currLvl.setOpenedInterface(0);
			currDialog = null;
			dialogCounter = 0;
		}
		else if(currLvl.getOpenedInterface() == 3){
			currLvl.setOpenedInterface(2);
			currDialog = null;
			dialogCounter = 0;
		}
	}
	
	// Keyboard-Events im Interface
	public void buttonAction(int k, Player p){
		if(currLvl.getOpenedInterface() == 1){
			if(k == KeyEvent.VK_ENTER){
				next();
			}
		}
		else if(currLvl.getOpenedInterface() == 2){
			if(k == KeyEvent.VK_DOWN){
				if(selectedObject <2){
					selectedObject++;
				}
				else if(selectedObject == 2){
					selectedObject = 0;
				}
			}
		else if(k == KeyEvent.VK_UP){
			if(selectedObject >0){
				selectedObject--;
			}
			else if(selectedObject == 0){
				selectedObject = 2;
			}
		}
		else if(k == KeyEvent.VK_ENTER){
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
			if(k == KeyEvent.VK_ENTER){
				next();
			}
		}
	}
	
	// Ausgewaehltes Objekt aendern
	public void setSelectedObject(int s){
		selectedObject = s;
	}

}


//HUD

class HUD {
	
	private int fullScreenOffset;
	private Font font;
	
	public HUD(){
		font = new Font("Viner Hand ITC", Font.PLAIN, 13);
	}
	
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
		
		int posX = 10;
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
		
		posX = 10;
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

		// Weaponicons zeichnen
		drawHudElement(Data_Textures.basicsword_icon, 460+fullScreenOffset, 30);
		drawHudElement(Data_Textures.basicbow_icon, 570+fullScreenOffset, 30);
		
		// HP/Mana/GEld-Werte schreiben
//		g2d.setColor(Color.WHITE);
//		g2d.setFont(font);
//		g2d.drawString("" + p.getHP() + "/" + p.getHPMax(), 315+fullScreenOffset, 44);
//		g2d.drawString("" + p.getMana() + "/" + p.getManaMax(), 315+fullScreenOffset, 81);
//		g2d.drawString("" + p.getStatInventoryObjectCount(1), 745+fullScreenOffset, 75);
		
		// Lives zeichnen
		drawHudElement(Data_Textures.player_f, 820+fullScreenOffset, 14);
//		g2d.drawString("x " + p.getLives(), 850+fullScreenOffset, 44);
		// Pfeile zeichnen
		drawHudElement(Data_Textures.arrow_f, 900+fullScreenOffset, 24);
//		g2d.drawString("x "+p.getStatInventoryObjectCount(4), 920+fullScreenOffset, 44);
		// Tränke zeichnen
		drawHudElement(Data_Textures.potion, 820+fullScreenOffset, 54);
//		g2d.drawString("x  " + p.getStatInventoryObjectCount(2), 850+fullScreenOffset, 84);
		drawHudElement(Data_Textures.mpotion, 890+fullScreenOffset, 54);
//		g2d.drawString("x  " + p.getStatInventoryObjectCount(3), 920+fullScreenOffset, 84);
	}
	
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

