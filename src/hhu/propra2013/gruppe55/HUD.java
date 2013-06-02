package hhu.propra2013.gruppe55;

import java.awt.*;

public class HUD {
	
	private int fullScreenOffset;
	private Font font;
	private FontMetrics metr;
	
	public HUD(){
		font = new Font("Comic Sans MS", Font.PLAIN, 13);
	}
	
	// Zeichnen des HUD
	public void draw(Graphics g2d, boolean full, int hp, int hpMax, int ausd, int ausdMax, int mana, int manaMax, int money, int equip){
		metr = g2d.getFontMetrics(font);
		if(!full){
			// Zeichnen des Lebenspunktebalkens
			g2d.setColor(Color.BLACK);
			g2d.fillRect(20, -90, 355, 30);
			g2d.setColor(Color.RED);
			g2d.fillRect(20, -90, (int)(355*((double)hp/hpMax)), 30);
			// Zeichnen des Manabalkens
			g2d.setColor(Color.BLACK);
			g2d.fillRect(20, -54, 355, 30);
			g2d.setColor(Color.BLUE);
			g2d.fillRect(20, -54, (int)(355*((double)mana/manaMax)), 30);
			g2d.drawImage(Data.hud, 0, 0, null);
			// Draw HUD-Values
			g2d.setColor(Color.WHITE);
			g2d.setFont(font);
			g2d.drawString("" + hp + "/" + hpMax, 292, -66);
			g2d.drawString("" + mana + "/" + manaMax, 292, -29);
			g2d.drawString("" + money, 745, -35);
			// Draw Weapon-Set-Selector
			if(equip == 0){
				g2d.drawImage(Data.hud_selector, 445, -51, null);
			}
			else if(equip == 1){
				g2d.drawImage(Data.hud_selector, 551, -51, null);
			}
			else if(equip == 2){
				g2d.drawImage(Data.hud_selector, 658, -51, null);
			}
		}
		else{
			fullScreenOffset = (Toolkit.getDefaultToolkit().getScreenSize().width/2);
			g2d.setColor(Color.BLACK);
			// Zeichnen des Lebenspunktebalkens
			g2d.fillRect(20+fullScreenOffset, -90, 355, 30);
			g2d.setColor(Color.RED);
			g2d.fillRect(20+fullScreenOffset, -90, (int)(355*((double)hp/hpMax)), 30);
			// Zeichnen des Manabalkens
			g2d.setColor(Color.BLACK);
			g2d.fillRect(20+fullScreenOffset, -54, 355, 30);
			g2d.setColor(Color.BLUE);
			g2d.fillRect(20+fullScreenOffset, -54, (int)(355*((double)mana/manaMax)), 30);
			g2d.drawImage(Data.hud, 0+fullScreenOffset, -110, null);
			// Draw HUD-Values
			g2d.setColor(Color.WHITE);
			g2d.setFont(font);
			g2d.drawString("" + hp + "/" + hpMax, 292+fullScreenOffset, -66);
			g2d.drawString("" + mana + "/" + manaMax, 292+fullScreenOffset, -29);
			g2d.drawString("" + money, 745+fullScreenOffset, -35);
			// Draw Weapon-Set-Selector
			if(equip == 0){
				g2d.drawImage(Data.hud_selector, 445+fullScreenOffset, -51, null);
			}
			else if(equip == 1){
				g2d.drawImage(Data.hud_selector, 551+fullScreenOffset, -51, null);
			}
			else if(equip == 2){
				g2d.drawImage(Data.hud_selector, 658+fullScreenOffset, -51, null);
			}
		}
	}

}
