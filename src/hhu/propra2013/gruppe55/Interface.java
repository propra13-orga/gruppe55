package hhu.propra2013.gruppe55;

import java.awt.*;
import javax.swing.*;

public class Interface {
	
	private HUD hud;
	
	public Interface(){
		hud = new HUD();
	}
	
	public void paint(Graphics2D g2d, Player p, boolean full){
		hud.draw(g2d, full, p);
	}
}
