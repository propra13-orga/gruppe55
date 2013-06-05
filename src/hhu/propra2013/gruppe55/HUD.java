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
		
		g2d.drawImage(Data_Img.hud, 0+fullScreenOffset, 0, null);
		
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
			else if(i > 1){
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
		g2d.drawString("" + p.getMoney(), 745+fullScreenOffset, 75);
		
		// Draw Weapon-Set-Selector
		if(p.getWeapSet() == 0){
			g2d.drawImage(Data_Img.hud_selector, 445+fullScreenOffset, 59, null);
		}
		else if(p.getWeapSet() == 1){
			g2d.drawImage(Data_Img.hud_selector, 551+fullScreenOffset, -51, null);
		}
		else if(p.getWeapSet() == 2){
			g2d.drawImage(Data_Img.hud_selector, 658+fullScreenOffset, -51, null);
		}
		
		// Lives zeichnen
		g2d.drawImage(Data_Img.player_f, 820+fullScreenOffset, 14, null);
		g2d.drawString("x " + p.getLives(), 850+fullScreenOffset, 44);
		// Pfeile zeichnen
		g2d.drawImage(Data_Img.arrow_f, 900+fullScreenOffset, 24, null);
		g2d.drawString("x 0", 920+fullScreenOffset, 44);
		// Tr�nke zeichnen
		g2d.drawImage(Data_Img.potion, 820+fullScreenOffset, 54, null);
		g2d.drawString("x 0", 850+fullScreenOffset, 84);
		g2d.drawImage(Data_Img.mpotion, 890+fullScreenOffset, 54, null);
		g2d.drawString("x 0", 920+fullScreenOffset, 84);
	}
}
