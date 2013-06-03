package hhu.propra2013.gruppe55;

import java.awt.*;

public class HUD {
	
	private int fullScreenOffset;
	private Font font;
	
	public HUD(){
		font = new Font("Comic Sans MS", Font.PLAIN, 13);
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
		
		g2d.drawImage(Data.hud, 0+fullScreenOffset, 0, null);
		
		int posX = 10;
		int value = p.getHP();
		int i = p.getHPMax();
		
		// HP-Container zeichnen
		while(i > 0){
			if(value > 1){
				g2d.drawImage(Data.hud_Bottle_HP_Full, posX+fullScreenOffset, 15, null);
				value -= 2;
				i -= 2;
				posX += 30;
			}
			else if(value == 1){
				g2d.drawImage(Data.hud_Bottle_HP_Half, posX+fullScreenOffset, 15, null);
				value -= 1;
				i -= 2;
				posX += 30;
			}
			else if(i > 1){
				g2d.drawImage(Data.hud_Bottle_HP_Empty, posX+fullScreenOffset, 15, null);
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
				g2d.drawImage(Data.hud_Crystal_Full, posX+fullScreenOffset, 60, null);
				value--;
				i--;
				posX += 30;
			}
			else{
				g2d.drawImage(Data.hud_Crystal_Empty, posX+fullScreenOffset, 60, null);
				i--;
				posX += 30;
			}
		}

		g2d.setColor(Color.WHITE);
		g2d.setFont(font);
		g2d.drawString("" + p.getHP() + "/" + p.getHPMax(), 315+fullScreenOffset, -66+110);
		g2d.drawString("" + p.getMana() + "/" + p.getManaMax(), 315+fullScreenOffset, -29+110);
		g2d.drawString("" + p.getMoney(), 745+fullScreenOffset, -35+110);
		// Draw Weapon-Set-Selector
		if(p.getWeapSet() == 0){
			g2d.drawImage(Data.hud_selector, 445+fullScreenOffset, -51+110, null);
		}
		else if(p.getWeapSet() == 1){
			g2d.drawImage(Data.hud_selector, 551+fullScreenOffset, -51, null);
		}
		else if(p.getWeapSet() == 2){
			g2d.drawImage(Data.hud_selector, 658+fullScreenOffset, -51, null);
		}
	}
}
