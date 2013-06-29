package hhu.propra2013.gruppe55_opengl;

import java.io.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Data_Textures {
	
	static Texture player_f, player_l, player_r, player_b, dead, player_f_atk, player_l_atk, player_r_atk, player_b_atk, creature, creature_bow, fireelemental, boss1, boss2, boss3, shopkeeper, storyteller,
	win, youlose, gameover, bks, wall, grass, goal, potion, potionused, healthcontainer, mpotion, treasure, lootarrow, lavahat,
	trap, trapact, basicsword, basicsword_f_atk, basicsword_l_atk, basicsword_r_atk, basicsword_u_atk, basicsword_icon, cp_used, cp_unused, torch, torch_lit, door, 
	basicbow, basicbow_back, basicbow_f_atk, basicbow_l_atk, basicbow_r_atk, basicbow_u_atk, basicbow_icon, basicshield_f, basicshield_b, arrow_f, arrow_l, arrow_r, arrow_b, fireball,
	hud01, hud02, hud03, hud_Tear_HP_Full, hud_Tear_HP_Half, hud_Tear_HP_Empty,hud_Crystal_Full, hud_Crystal_Empty, dialogBox, shop, shopArrow, currency;
	
	public Data_Textures(){
		try {
			// Spieler
				// Standartansichten
				player_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/front.png")));		// der Spieler von vorne
				player_l = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/left.png")));		// der Spieler von links
				player_r = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/right.png")));		// der Spieler von rechts
				player_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/back.png")));		// der Spieler von hinten
				dead = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/dead.png")));			// Toter Spieler ist tot
				// Ansichten beim Angriff
				player_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/front_atk.png")));	// Angriff, Frontperspektive
				player_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/left_atk.png")));		// Angriff, links
				player_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/right_atk.png")));		// Angriff, rechts
				player_b_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/player/back_atk.png")));		// Angriff, hinten
			// Monster
				creature = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/enemies/creature.png")));	// Standart-Kreatur
				creature_bow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/enemies/creature_bow.png")));	// Bogen-Monster
				fireelemental = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/enemies/fireelemental.png")));	// Feuerelementar
			// Bosse
				boss1 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/enemies/boss1.png")));
				boss2 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/enemies/boss2.png")));
				boss3 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/enemies/boss3.png")));
			// Npc's
				// Shopkeeper
				shopkeeper = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Npc/shopkeeper.png")));
				// Storyteller
				storyteller = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/NPC/storyteller.png")));
			// Levelrelevantes
				win = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/youwin.png")));		// Siegbild
				youlose = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/youlose.png")));	// Todesbild
				gameover = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/gameover.png")));	// Game Over
			// Umgebung des Levels
				wall = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/wall.png")));			// Wand
				grass = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/textures/1.png")));		//Grass
				goal = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/goal.png")));			// Zielobjekt
				cp_unused = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/CP_unused.png")));			// Checkpoint unbenutzt
				cp_used = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/CP_used.png")));			// Checkpoint benutzt
				torch = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/torch.png")));			// Fackel - aus
				torch_lit = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/torch_lit.png")));			// Fackel - aus
				door = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/door.png")));		// Tuere
				
			// Nutzbare Items
				potion = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/potion.png")));		// Trank
				potionused = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/potionused.png")));	// (Platzhalter)
				healthcontainer = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD_Tear_HP_Full.png")));
				mpotion = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/mpotion.png")));		// Manatrank
				treasure = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/treasure.png")));		// Schaetze
				lootarrow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/lootarrow.png")));
				lavahat = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/armor/lavahat.png")));
			// Fallen
				trap = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/trap.png")));			// Speerfalle
				trapact = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/trapActivated.png")));	// aktivierte Speerfalle
			// Interface
				hud01 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD01.png")));			// HUD (1st Slot selected)
				hud02 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD02.png")));			// HUD (2nd slot selected)
				hud03 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD03.png")));			// HUD (3rd Slot selected)
				hud_Tear_HP_Full = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD_Tear_HP_Full.png")));			//HUD_Selector
				hud_Tear_HP_Half = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD_Tear_HP_Half.png")));			//HUD_Selector
				hud_Tear_HP_Empty = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD_Tear_HP_Empty.png")));			//HUD_Selector
				hud_Crystal_Full = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD_Crystal_MP_Full.png")));			//HUD_Selector
				hud_Crystal_Empty = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/HUD_Crystal_MP_Empty.png")));			//HUD_Selector
				dialogBox = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/DialogBox.png")));	//DialogBox
				shop = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/Shop.png")));	//Shop
				shopArrow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/ShopArrow.png")));	//Shop-Pfeil
				currency = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/Interface/Currency.png")));	// Waehrungssymbol
			// Waffen
				// simples Schwert
					basicsword = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicsword/front.png")));	// Frontansicht
					basicsword_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicsword/front_atk.png")));	// Schwertansicht beim Angriff nach vorn
					basicsword_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicsword/left_atk.png")));	// Schwertansicht beim Angriff nach links
					basicsword_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicsword/right_atk.png")));	// Schwertansicht beim Angriff nach rechts
					basicsword_u_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicsword/up_atk.png")));	// Schwertansicht beim Angriff nach oben
					basicsword_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicsword/icon.png")));	// Icon fuer das HUD
				// simpler Bogen
					basicbow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/front.png"))); // Frontansicht
					basicbow_back = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/back.png"))); // R�ckansicht
					basicbow_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/front_atk.png"))); // Bogenansicht beim Angriff nach vorn
					basicbow_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/left_atk.png"))); // Bogenansicht beim Angriff nach links
					basicbow_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/right_atk.png"))); // Bogenansicht beim Angriff nach rechts
					basicbow_u_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/up_atk.png"))); // Bogenansicht beim Angriff nach unten
					basicbow_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicbow/icon.png"))); // Icon fuer das HUD
				// simples Schild
					basicshield_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicshield/front.png"))); // Frontsansicht
					basicshield_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/basicshield/back.png"))); // Ruecksansicht
				// Projektile
					arrow_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/projectiles/arrow_front.png")));	//Pfeil front
					arrow_l = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/projectiles/arrow_left.png")));	//Pfeil links
					arrow_r = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/projectiles/arrow_right.png")));	//Pfeil rechts
					arrow_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/weapons/projectiles/arrow_back.png")));	//Pfeil hinten
					fireball = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/openGL/spells/fireball.png")));
		} catch (IOException e) {e.printStackTrace();}
	}
}
