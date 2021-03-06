package hhu.propra2013.leveleditor;

import java.awt.*;

import javax.swing.*;

public class Data_Img {
	
// 	Klasse zum statischen Laden der Bilddateien, damit diese nicht bei jedem Aufruf extra geladen werden m�ssen	
	
// Spieler
	// Standartansichten
	public static Image player_f = genImg("player/front.png");		// der Spieler von vorne
	static Image player_l = genImg("player/left.png");		// der Spieler von links
	static Image player_r = genImg("player/right.png");		// der Spieler von rechts
	static Image player_b = genImg("player/back.png");		// der Spieler von hinten
	static Image dead = genImg("dead.png");			// Toter Spieler ist tot
	// Ansichten beim Angriff
	static Image player_f_atk = genImg("player/front_atk.png");	// Angriff, Frontperspektive
	static Image player_l_atk = genImg("player/left_atk.png");		// Angriff, links
	static Image player_r_atk = genImg("player/right_atk.png");		// Angriff, rechts
	static Image player_b_atk = genImg("player/back_atk.png");		// Angriff, hinten
// Monster
	public static Image creature = genImg("enemies/creature.png");	// Standart-Kreatur
	public static Image creature_bow = genImg("enemies/creature_bow.png");					// Bogen-Monster
	public static Image fireelemental = genImg("enemies/fireelemental.png");					// FireElemental
	public static Image creature_ice = genImg("enemies/creature_ice.png");					// Eismonster
	public static Image creature_water = genImg("enemies/creature_water.png");

// Bosse
	public static Image boss1 = genImg("enemies/boss1.png");
	public static Image boss2 = genImg("enemies/boss2.png");
	public static Image boss3 = genImg("enemies/boss3.png");
	public static Image boss_firesnail = genImg("enemies/bossfiresnail.png");
	public static Image boss_icesnail = genImg("enemies/bossicesnail.png");
	public static Image boss_water = genImg("enemies/bosswater.png");
	public static Image boss_bigeye = genImg("enemies/bossbigeye.png");
// Npc's
	// Shopkeeper
	public static Image shopkeeper = genImg("Npc/shopkeeper.png");
	// Storyteller
	public static Image storyteller = genImg("Npc/storyteller.png");
	// Wizard
	public static Image wizard = genImg("enemies/evilwizard.png");
	// Levelrelevantes
	static Image win = genImg("youwin.png");		// Siegbild
	static Image youlose = genImg("youlose.png");	// Todesbild
	static Image gameover = genImg("gameover.png"); // Gameover!
	// Umgebung des Levels
	public static Image wall = genImg("wall.png");			// Wand
	static Image grass = genImg("grass.png");		//Gras
	public static Image goal = genImg("goal.png");			// Zielobjekt
	public static Image cp_unused = genImg("CP_unused.png");			// Checkpoint unbenutzt
	public static Image cp_used = genImg("CP_used.png");			// Checkpoint benutzt
	public static Image door = genImg("door.png");		// Door
	public static Image torch = genImg("torch.png");		// Torch
	public static Image button_off = genImg("button_off.png");		// Switch
	public static Image waterpatch = genImg("waterpatch.png"); // Waterpatch
	public static Image lavapatch = genImg("lavapatch.png");		// Lavapatch
	public static Image floorplate_active = genImg("floorplateactive.png");		// Bodenplatte
	public static Image t00 = genImg("textures/00.png");		// Lavapatch
	public static Image t01 = genImg("textures/01.png");		// Lavapatch
	public static Image t02 = genImg("textures/02.png");		// Lavapatch
	public static Image t03 = genImg("textures/03.png");		// Lavapatch
	public static Image t04 = genImg("textures/04.png");		// Lavapatch
	

	// Nutzbare Items
	public static Image potion = genImg("potion.png");		// Trank
	static Image potionused = genImg("potionused.png");	// (Platzhalter)
	public static Image healthcontainer = genImg("Interface/HUD_Tear_HP_Full.png");
	public static Image mpotion = genImg("mpotion.png");		// Manatrank
	public static Image treasure = genImg("treasure.png");		// Schaetze 
	public static Image lootarrow = genImg("weapons/basicbow/lootarrow.png");
	//armor
	public static Image lavahat = genImg("armor/lavahat.png");

	
	// Fallen
	public static Image trap = genImg("trap.png");			// Speerfalle
	static Image trapact = genImg("trapActivated.png");	// aktivierte Speerfalle
	// Interface
	static Image hud01 = new ImageIcon("img/Interface/HUD01.png").getImage();			// HUD (1st Slot selected)
	static Image hud02 = new ImageIcon("img/Interface/HUD02.png").getImage();			// HUD (2nd Slot selected)
	static Image hud03 = new ImageIcon("img/Interface/HUD03.png").getImage();			// HUD (3rd Slot selected)
	static Image hud_Tear_HP_Full = new ImageIcon("img/Interface/HUD_Tear_HP_Full.png").getImage();			// HUD Full HP-Container)
	static Image hud_Tear_HP_Half = new ImageIcon("img/Interface/HUD_Tear_HP_Half.png").getImage();			// HUD Half HP-Container)
	static Image hud_Tear_HP_Empty = new ImageIcon("img/Interface/HUD_Tear_HP_Empty.png").getImage();		// HUD Empty HP-Container)
	static Image hud_Crystal_Full = new ImageIcon("img/Interface/HUD_Crystal_MP_Full.png").getImage();			// HUD Full MP-Container)
	static Image hud_Crystal_Empty = new ImageIcon("img/Interface/HUD_Crystal_MP_Empty.png").getImage();	// HUD Empty MP-Container)
	static Image dialogBox = new ImageIcon("img/Interface/DialogBox.png").getImage();	//DialogBox
	static Image shop = new ImageIcon("img/Interface/Shop.png").getImage();	//Shop
	static Image shopArrow = new ImageIcon("img/Interface/ShopArrow.png").getImage();	//Shop-Pfeil
	static Image currency = new ImageIcon("img/Interface/Currency.png").getImage();	// W�hrungssymbol
	
	
// Waffen
	// simples Schwert
	static Image basicsword = genImg("weapons/basicsword/front.png");	// Frontansicht
	static Image basicsword_f_atk = genImg("weapons/basicsword/front_atk.png");	// Schwertansicht beim Angriff nach vorn
	static Image basicsword_l_atk = genImg("weapons/basicsword/left_atk.png");	// Schwertansicht beim Angriff nach links
	static Image basicsword_r_atk = genImg("weapons/basicsword/right_atk.png");	// Schwertansicht beim Angriff nach rechts
	static Image basicsword_u_atk = genImg("weapons/basicsword/up_atk.png");	// Schwertansicht beim Angriff nach oben
	static Image basicsword_icon = genImg("weapons/basicsword/icon.png");	// Icon fuer das HUD
	// simpler Bogen
	static Image basicbow = genImg("weapons/basicbow/front.png"); // Frontansicht
	static Image basicbow_back = genImg("weapons/basicbow/back.png"); // R�ckansicht
	static Image basicbow_f_atk = genImg("weapons/basicbow/front_atk.png"); // Bogenansicht beim Angriff nach vorn
	static Image basicbow_l_atk = genImg("weapons/basicbow/left_atk.png"); // Bogenansicht beim Angriff nach links
	static Image basicbow_r_atk = genImg("weapons/basicbow/right_atk.png"); // Bogenansicht beim Angriff nach rechts
	static Image basicbow_u_atk = genImg("weapons/basicbow/up_atk.png"); // Bogenansicht beim Angriff nach unten
	static Image basicbow_icon = genImg("weapons/basicbow/icon.png"); // Icon fuer das HUD
	// simples Schild
	static Image basicshield_f = genImg("weapons/basicshield/front.png"); // Frontsansicht
	static Image basicshield_b = genImg("weapons/basicshield/back.png"); // Ruecksansicht
	// Projektile
	static Image arrow_f = genImg("weapons/projectiles/arrow_front.png");	//Pfeil front
	static Image arrow_l = genImg("weapons/projectiles/arrow_left.png");	//Pfeil links
	static Image arrow_r = genImg("weapons/projectiles/arrow_right.png");	//Pfeil rechts
	static Image arrow_b = genImg("weapons/projectiles/arrow_back.png");	//Pfeil hinten
	
// zauber
	public static Image fireball = genImg("spells/fireball.png");
	public static Image snowflake = genImg("spells/snowflake.png");
	
	// Methode zum Bilder generieren (nicht n�tig, spart aber Tipparbeit oben)
	private static Image genImg(String image){
		return (new ImageIcon("img/"+image)).getImage();
	}
}
