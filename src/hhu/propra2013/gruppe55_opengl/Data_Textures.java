package hhu.propra2013.gruppe55_opengl;

import java.io.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Data_Textures {
	
	static Texture player_f, player_l, player_r, player_b, dead, player_f_atk, player_l_atk, player_r_atk, player_b_atk, creature, shopkeeper, win, gameover, bks, wall, grass, goal, potion, potionused, mpotion, treasure,
	trap, trapact, basicsword, basicsword_f_atk, basicsword_l_atk, basicsword_r_atk, basicsword_u_atk, basicsword_icon,
	basicbow, basicbow_back, basicbow_f_atk, basicbow_l_atk, basicbow_r_atk, basicbow_u_atk, basicbow_icon, basicshield_f, basicshield_b, arrow_f, arrow_l, arrow_r, arrow_b,
	hud01, hud02, hud03, hud_Tear_HP_Full, hud_Tear_HP_Half, hud_Tear_HP_Empty,hud_Crystal_Full, hud_Crystal_Empty, dialogBox, shop, shopArrow, currency;
	
	public Data_Textures(){
		try {
			// Spieler
				// Standartansichten
				player_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/front.png")));		// der Spieler von vorne
				player_l = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/left.png")));		// der Spieler von links
				player_r = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/right.png")));		// der Spieler von rechts
				player_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/back.png")));		// der Spieler von hinten
				dead = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/dead.png")));			// Toter Spieler ist tot
				// Ansichten beim Angriff
				player_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/front_atk.png")));	// Angriff, Frontperspektive
				player_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/left_atk.png")));		// Angriff, links
				player_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/right_atk.png")));		// Angriff, rechts
				player_b_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/player/back_atk.png")));		// Angriff, hinten
			// Monster
			creature = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/creature.png")));	// Standart-Kreatur
			// Npc's
				// Shopkeeper
				shopkeeper = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Npc/shopkeeper.png")));

			// Levelrelevantes
			win = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/youwin.png")));		// Siegbild
			gameover = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/gameover.png")));	// Game Over
			bks = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/bks.png")));			// Hintergrund
			// Umgebung des Levels
			wall = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/wall.png")));			// Wand
			grass = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/grass.png")));		//Grass
			goal = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/goal.png")));			// Zielobjekt
			// Nutzbare Items
			potion = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/potion.png")));		// Trank
			potionused = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/potionused.png")));	// (Platzhalter)
			mpotion = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/mpotion.png")));		// Manatrank
			treasure = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/treasure.png")));		// Schaetze 
			// Fallen
			trap = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/trap.png")));			// Speerfalle
			trapact = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/trapActivated.png")));	// aktivierte Speerfalle
			// Interface
			hud01 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD01.png")));			// HUD (1st Slot selected)
			hud02 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD02.png")));			// HUD (2nd slot selected)
			hud03 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD03.png")));			// HUD (3rd Slot selected)
			hud_Tear_HP_Full = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD_Tear_HP_Full.png")));			//HUD_Selector
			hud_Tear_HP_Half = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD_Tear_HP_Half.png")));			//HUD_Selector
			hud_Tear_HP_Empty = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD_Tear_HP_Empty.png")));			//HUD_Selector
			hud_Crystal_Full = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD_Crystal_MP_Full.png")));			//HUD_Selector
			hud_Crystal_Empty = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD_Crystal_MP_Empty.png")));			//HUD_Selector
			dialogBox = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/DialogBox.png")));	//DialogBox
			shop = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/Shop.png")));	//Shop
			shopArrow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/ShopArrow.png")));	//Shop-Pfeil
			currency = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/Currency.png")));	// Waehrungssymbol

			// Waffen
				// simples Schwert
				basicsword = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicsword/front.png")));	// Frontansicht
				basicsword_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicsword/front_atk.png")));	// Schwertansicht beim Angriff nach vorn
				basicsword_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicsword/left_atk.png")));	// Schwertansicht beim Angriff nach links
				basicsword_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicsword/right_atk.png")));	// Schwertansicht beim Angriff nach rechts
				basicsword_u_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicsword/up_atk.png")));	// Schwertansicht beim Angriff nach oben
				basicsword_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicsword/icon.png")));	// Icon fuer das HUD
				// simpler Bogen
				basicbow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/front.png"))); // Frontansicht
				basicbow_back = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/back.png"))); // Rückansicht
				basicbow_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/front_atk.png"))); // Bogenansicht beim Angriff nach vorn
				basicbow_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/left_atk.png"))); // Bogenansicht beim Angriff nach links
				basicbow_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/right_atk.png"))); // Bogenansicht beim Angriff nach rechts
				basicbow_u_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/up_atk.png"))); // Bogenansicht beim Angriff nach unten
				basicbow_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/icon.png"))); // Icon fuer das HUD
				// simples Schild
				basicshield_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicshield/front.png"))); // Frontsansicht
				basicshield_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicshield/back.png"))); // Ruecksansicht
				// Projektile
				arrow_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/projectiles/arrow_front.png")));	//Pfeil front
				arrow_l = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/projectiles/arrow_left.png")));	//Pfeil links
				arrow_r = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/projectiles/arrow_right.png")));	//Pfeil rechts
				arrow_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/projectiles/arrow_back.png")));	//Pfeil hinten

		} catch (IOException e) {e.printStackTrace();}
	}
}
