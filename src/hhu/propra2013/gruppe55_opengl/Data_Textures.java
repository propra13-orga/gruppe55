package hhu.propra2013.gruppe55_opengl;

import java.io.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Die Klasse Data_Textures.
 * Die Klasse Data_Textures dient dazu, alle Bilder einmal statisch zu laden, damit diese nicht bei jedem Objektaufruf erneut geladen werden muessen.
 */

public class Data_Textures {
	
	/** Das jeweilige statisch geladene Bild. */
	
	public static Texture player_f, player_l, player_r, player_b, dead, player_f_atk, player_l_atk, player_r_atk, player_b_atk, creature, creature_bow, fireelemental, boss1, boss2, boss3, boss_snail, shopkeeper, wizard, storyteller,
	win, youlose, gameover, bks, wall, goal, potion, potionused, healthcontainer, mpotion, treasure, lootarrow, lavahat,
	trap, trapact, basicsword, basicsword_f_atk, basicsword_l_atk, basicsword_r_atk, basicsword_u_atk, basicsword_icon, icesworddrop, icesword,  icesword_f_atk, icesword_l_atk, icesword_r_atk, icesword_u_atk, icesword_icon, cp_used, cp_unused, button_off, button_on, torch, torch_lit, door, lavapatch, 
	basicbow, basicbow_back, basicbow_f_atk, basicbow_l_atk, basicbow_r_atk, basicbow_u_atk, basicbow_icon, basicshield_f, basicshield_b, arrow_f, arrow_l, arrow_r, arrow_b, fireball,
	hud01, hud02, hud03, hud_Tear_HP_Full, hud_Tear_HP_Half, hud_Tear_HP_Empty,hud_Crystal_Full, hud_Crystal_Empty, dialogBox, shop, shopArrow, currency,
	t00, t01, t02, t03, t04, creature_ice, snowflake;
	
	
	/**
	 * Der Konstruktor fuer die Data_Textuers.
	 * Hier werden alle Bilder statisch geladen.
	 */
	
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
				creature = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/creature.png")));	// Standart-Kreatur
				creature_bow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/creature_bow.png")));	// Bogen-Monster
				creature_ice = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/creature_ice.png")));	// Eis-Monster
				fireelemental = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/fireelemental.png")));	// Feuerelementar
			// Bosse
				boss1 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/boss1.png")));
				boss2 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/boss2.png")));
				boss3 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/boss3.png")));
				boss_snail = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/bossfiresnail.png")));
			// Npc's
				// Shopkeeper
				shopkeeper = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Npc/shopkeeper.png")));
				// Storyteller
				storyteller = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Npc/storyteller.png")));
				// Wizard
				wizard = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/enemies/evilwizard.png")));
			// Levelrelevantes
				win = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/youwin.png")));		// Siegbild
				youlose = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/youlose.png")));	// Todesbild
				gameover = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/gameover.png")));	// Game Over
			// Umgebung des Levels
				wall = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/wall.png")));			// Wand
				goal = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/goal.png")));			// Zielobjekt
				cp_unused = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/CP_unused2.png")));			// Checkpoint unbenutzt
				cp_used = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/CP_used2.png")));			// Checkpoint benutzt
				torch = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/torch.png")));			// Fackel - aus
				torch_lit = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/torch_lit.png")));			// Fackel - aus
				door = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/door.png")));		// Tuere
				lavapatch = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/lavapatch2.png"))); // Lavafelder
				button_off = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/button_off.png")));
				button_on = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/button_on.png")));
				t00 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/textures/00.png")));
				t01 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/textures/01.png")));
				t02 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/textures/02.png")));
				t03 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/textures/03.png")));
				t04 = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/textures/04.png")));
			// Nutzbare Items
				potion = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/potion.png")));		// Trank
				potionused = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/potionused.png")));	// (Platzhalter)
				healthcontainer = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/Interface/HUD_Tear_HP_Full.png")));
				mpotion = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/mpotion.png")));		// Manatrank
				treasure = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/treasure.png")));		// Schaetze
				lootarrow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/lootarrow.png")));
				lavahat = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/armor/lavahat.png")));
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
				// eis Schwert
					icesword = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/front.png")));	// Frontansicht
					icesword_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/front_atk.png")));	// Schwertansicht beim Angriff nach vorn
					icesword_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/left_atk.png")));	// Schwertansicht beim Angriff nach links
					icesword_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/right_atk.png")));	// Schwertansicht beim Angriff nach rechts
					icesword_u_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/up_atk.png")));	// Schwertansicht beim Angriff nach oben
					icesword_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/icon.png")));	// Icon fuer das HUD
					icesworddrop = icesword_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/icesword/drop.png")));	// Icon fuer das HUD
				// simpler Bogen
					basicbow = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/front.png"))); // Frontansicht
					basicbow_back = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/back.png"))); // R�ckansicht
					basicbow_f_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/front_atk.png"))); // Bogenansicht beim Angriff nach vorn
					basicbow_l_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/left_atk.png"))); // Bogenansicht beim Angriff nach links
					basicbow_r_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/right_atk.png"))); // Bogenansicht beim Angriff nach rechts
					basicbow_u_atk = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/up_atk.png"))); // Bogenansicht beim Angriff nach unten
					basicbow_icon = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicbow/icon.png"))); // Icon fuer das HUD
				// simples Schild
					basicshield_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicshield/front.png"))); // Frontsansicht
					basicshield_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/basicshield/back.png"))); // Ruecksansicht
				// Projektile
					arrow_f = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/Projectiles/arrow_front.png")));	//Pfeil front
					arrow_l = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/Projectiles/arrow_left.png")));	//Pfeil links
					arrow_r = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/Projectiles/arrow_right.png")));	//Pfeil rechts
					arrow_b = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/weapons/Projectiles/arrow_back.png")));	//Pfeil hinten
					fireball = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/spells/fireball.png")));
					snowflake = TextureLoader.getTexture("PNG", new FileInputStream(new File("img/spells/snowflake.png")));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	static Texture getEnvTexture(int t){
		switch(t){
			case 0:
				return(t00);
			case 1:
				return(t01);
			case 2:
				return(t02);
			case 3:
				return(t03);
			case 4:
				return(t04);
			default:
				return(t00);
		}
	}
}