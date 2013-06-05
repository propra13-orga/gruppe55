package hhu.propra2013.gruppe55;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Data_Img {
	
// 	Klasse zum statischen Laden der Bilddateien, damit diese nicht bei jedem Aufruf extra geladen werden müssen	
	
// Spieler
	// Standartansichten
	static Image player_f = genImg("player/front.png");		// der Spieler von vorne
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
	static Image creature = genImg("creature.png");	// Standart-Kreatur
	// Levelrelevantes
	static Image win = genImg("youwin.png");		// Siegbild
	static Image gameover = genImg("gameover.png");	// Game Over
	static Image bks = genImg("bks.png");			// Hintergrund
	// Umgebung des Levels
	static Image wall = genImg("wall.png");			// Wand
	static Image grass = genImg("grass.png");		//Grass
	static Image goal = genImg("goal.png");			// Zielobjekt
	// Nutzbare Items
	static Image potion = genImg("potion.png");		// Trank
	static Image potionused = genImg("potionused.png");	// (Platzhalter)
	static Image mpotion = genImg("mpotion.png");		// Manatrank
	static Image treasure = genImg("treasure.png");		// Schaetze 
	// Fallen
	static Image trap = genImg("trap.png");			// Speerfalle
	static Image trapact = genImg("trapActivated.png");	// aktivierte Speerfalle
	// Interface
	static Image hud = genImg("HUD.png");			// HUD
	static Image hud_selector = genImg("HUD_Selector.png");			//HUD_Selector
	static Image hud_Tear_HP_Full = genImg("HUD_Tear_HP_Full.png");			//HUD_Selector
	static Image hud_Tear_HP_Half = genImg("HUD_Tear_HP_Half.png");			//HUD_Selector
	static Image hud_Tear_HP_Empty = genImg("HUD_Tear_HP_Empty.png");			//HUD_Selector
	static Image hud_Crystal_Full = genImg("HUD_Crystal_MP_Full.png");			//HUD_Selector
	static Image hud_Crystal_Empty = genImg("HUD_Crystal_MP_Empty.png");			//HUD_Selector
	static Image dialogBox = genImg("DialogBox.png");	//DialogBox
	
	
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
	static Image basicbow_f_atk = genImg("weapons/basicbow/front_atk.png"); // Bogenansicht beim Angriff nach vorn
	static Image basicbow_l_atk = genImg("weapons/basicbow/left_atk.png"); // Bogenansicht beim Angriff nach links
	static Image basicbow_r_atk = genImg("weapons/basicbow/right_atk.png"); // Bogenansicht beim Angriff nach rechts
	static Image basicbow_u_atk = genImg("weapons/basicbow/up_atk.png"); // Bogenansicht beim Angriff nach unten
	static Image basicbow_icon = genImg("weapons/basicbow/icon.png"); // Icon fuer das HUD
	// simples Schild
	static Image basicshield_f = genImg("weapons/basicshield/front.png"); // Frontsansicht
	static Image basicshield_b = genImg("weapons/basicshield/front.png"); // Ruecksansicht
	// Projektile
	static Image arrow_f = genImg("weapons/basicbow/arrow_front.png");	//Pfeil front
	
	// Methode zum Bilder generieren (nicht nötig, spart aber Tipparbeit oben)
	private static Image genImg(String image){
		return (new ImageIcon("img/"+image)).getImage();
	}
}
