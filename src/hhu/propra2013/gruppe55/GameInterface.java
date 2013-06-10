package hhu.propra2013.gruppe55;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameInterface {
	
	private Font fontDialog, fontShop;				//TextFont
	private FontMetrics fMetr;		//FontMetrics für Graphics2D
	private HUD hud;				//HUD
	private Level currLvl;		//Aktuelles Level
	private String[] currDialog;	//Anzuzeigender Dialog
	private int dialogCounter;		//Anzuzeigende Dialogzeile
	private int selectedObject;		// Ausgewähltes Shopobjekt


	//Interface-Konstruktor
	public GameInterface(Level t){
		fontDialog = new Font("Viner Hand ITC", Font.BOLD, 20);	//FontDialog setzen
		fontShop = new Font("Viner Hand ITC", Font.BOLD, 15);	//FontDialog setzen
		currLvl = t;										//Level uebergeben
		hud = new HUD();									//HUD konstruieren
	}
	
	//Interface zeichnen
	public void paint(Graphics2D g2d, Player p, boolean full){		
		hud.draw(g2d, full, p);					//Draw HUD
		
		//Wenn Dialog angezeigt werden soll, diesen zeichnen
		if(currLvl.getDialog()){
			// Dialoge zeichnen
			if(currLvl.getOpenedInterface() == 1 || currLvl.getOpenedInterface() == 3){
				g2d.setFont(fontDialog);					//Graphics2D Font setzen
				fMetr = g2d.getFontMetrics(fontDialog);	//FontMetrics erzeugen
				g2d.drawImage(Data_Img.dialogBox, (currLvl.getWidth()/2)-250, currLvl.getHeight()-80, null);	//DialogBox zeichnen
				g2d.drawString(currDialog[dialogCounter], (currLvl.getWidth()/2)-(fMetr.stringWidth(currDialog[dialogCounter])/2), (currLvl.getHeight()-40));	//Dialogzeile zeichnen
			}
			// Shop zeichnen
			if(currLvl.getOpenedInterface() == 2 || currLvl.getOpenedInterface() == 3){
				g2d.setFont(fontShop);					//Graphics2D Font setzen
				fMetr = g2d.getFontMetrics(fontShop);	//FontMetrics erzeugen
				g2d.drawImage(Data_Img.shop, (currLvl.getWidth()/2)-128, currLvl.getHeight()/2-64, null);	//DialogBox zeichnen
				g2d.drawImage(Data_Img.potion, (currLvl.getWidth()/2)-100, currLvl.getHeight()/2-30, null);	//DialogBox zeichnen
				g2d.drawImage(Data_Img.mpotion, (currLvl.getWidth()/2)-100, currLvl.getHeight()/2-3, null);	//DialogBox zeichnen
				g2d.drawImage(Data_Img.arrow_f, (currLvl.getWidth()/2)-87, currLvl.getHeight()/2+37, null);	//DialogBox zeichnen
				g2d.drawString("x1", (currLvl.getWidth()/2-60)-(fMetr.stringWidth("x1")/2), currLvl.getHeight()/2);	//Dialogzeile zeichnen
				g2d.drawString("x1", (currLvl.getWidth()/2-60)-(fMetr.stringWidth("x1")/2), currLvl.getHeight()/2+27);	//Dialogzeile zeichnen
				g2d.drawString("x10", (currLvl.getWidth()/2-57)-(fMetr.stringWidth("x10")/2), currLvl.getHeight()/2+50);	//Dialogzeile zeichnen
				g2d.drawString("10", (currLvl.getWidth()/2-20)-(fMetr.stringWidth("10")/2), currLvl.getHeight()/2);	//Dialogzeile zeichnen
				g2d.drawString("10", (currLvl.getWidth()/2-20)-(fMetr.stringWidth("10")/2), currLvl.getHeight()/2+27);	//Dialogzeile zeichnen
				g2d.drawString("10", (currLvl.getWidth()/2-20)-(fMetr.stringWidth("10")/2), currLvl.getHeight()/2+50);	//Dialogzeile zeichnen
				g2d.drawImage(Data_Img.currency, (currLvl.getWidth()/2-10), currLvl.getHeight()/2-10, null);	//DialogBox zeichnen
				g2d.drawImage(Data_Img.currency, (currLvl.getWidth()/2-10), currLvl.getHeight()/2+17, null);	//DialogBox zeichnen
				g2d.drawImage(Data_Img.currency, (currLvl.getWidth()/2-10), currLvl.getHeight()/2+40, null);	//DialogBox zeichnen
				switch(selectedObject){
					case 0:
						g2d.drawImage(Data_Img.shopArrow, (currLvl.getWidth()/2)-115, currLvl.getHeight()/2-15, null);	//DialogBox zeichnen
						break;
					case 1:
						g2d.drawImage(Data_Img.shopArrow, (currLvl.getWidth()/2)-115, currLvl.getHeight()/2+12, null);	//DialogBox zeichnen
						break;
					case 2:
						g2d.drawImage(Data_Img.shopArrow, (currLvl.getWidth()/2)-115, currLvl.getHeight()/2+35, null);	//DialogBox zeichnen
						break;
				}
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
		if(dialogCounter == currDialog.length-1){
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
		else{
			dialogCounter++;
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
	
	// Ausgewähltes Objekt ändern
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
	public void draw(Graphics g2d, boolean full, Player p){		
		// FullScreenOffset setzen
		if(full){
			fullScreenOffset = (Toolkit.getDefaultToolkit().getScreenSize().width/2)-480;
		}
		else{
			fullScreenOffset = 0;
		}
		
		// Draw HUD
		if(p.getWeapSet() == 0){
			g2d.drawImage(Data_Img.hud01, 0+fullScreenOffset, 0, null);
		}
		else if(p.getWeapSet() == 1){
			g2d.drawImage(Data_Img.hud02, 0+fullScreenOffset, 0, null);
		}
		else if(p.getWeapSet() == 2){
			g2d.drawImage(Data_Img.hud03, 0+fullScreenOffset, 0, null);
		}
		
		int posX = 10;
		int value = p.getHP();
		int i = p.getHPMax();
		
		// HP-Container zeichnen
		while(i > 0){
			if(value > 1){
				g2d.drawImage(Data_Img.hud_Tear_HP_Full, posX+fullScreenOffset, 15, null);
				value -= 2;
				i -= 2;
				posX += 30;
			}
			else if(value == 1){
				g2d.drawImage(Data_Img.hud_Tear_HP_Half, posX+fullScreenOffset, 15, null);
				value -= 1;
				i -= 2;
				posX += 30;
			}
			else if(i > 0){
				g2d.drawImage(Data_Img.hud_Tear_HP_Empty, posX+fullScreenOffset, 15, null);
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
				g2d.drawImage(Data_Img.hud_Crystal_Full, posX+fullScreenOffset, 60, null);
				value--;
				i--;
				posX += 30;
			}
			else{
				g2d.drawImage(Data_Img.hud_Crystal_Empty, posX+fullScreenOffset, 60, null);
				i--;
				posX += 30;
			}
		}

		// Weaponicons zeichnen
		g2d.drawImage(Data_Img.basicsword_icon, 430+fullScreenOffset, 20, null);
		g2d.drawImage(Data_Img.basicbow_icon, 530+fullScreenOffset, 20, null);
		
		// HP/Mana/GEld-Werte schreiben
		g2d.setColor(Color.WHITE);
		g2d.setFont(font);
		g2d.drawString("" + p.getHP() + "/" + p.getHPMax(), 315+fullScreenOffset, 44);
		g2d.drawString("" + p.getMana() + "/" + p.getManaMax(), 315+fullScreenOffset, 81);
		g2d.drawString("" + p.getStatInventoryObjectCount(1), 745+fullScreenOffset, 75);
		
		// Lives zeichnen
		g2d.drawImage(Data_Img.player_f, 820+fullScreenOffset, 14, null);
		g2d.drawString("x " + p.getStatInventoryObjectCount(0), 850+fullScreenOffset, 44);
		// Pfeile zeichnen
		g2d.drawImage(Data_Img.arrow_f, 900+fullScreenOffset, 24, null);
		g2d.drawString("x "+p.getStatInventoryObjectCount(4), 920+fullScreenOffset, 44);
		// Tränke zeichnen
		g2d.drawImage(Data_Img.potion, 820+fullScreenOffset, 54, null);
		g2d.drawString("x  " + p.getStatInventoryObjectCount(2), 850+fullScreenOffset, 84);
		g2d.drawImage(Data_Img.mpotion, 890+fullScreenOffset, 54, null);
		g2d.drawString("x  " + p.getStatInventoryObjectCount(3), 920+fullScreenOffset, 84);
	}
}

