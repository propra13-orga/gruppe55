package hhu.propra2013.gruppe55;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Data {
	
// 	Klasse zum statischen Laden der Bilddateien, damit diese nicht bei jedem Aufruf extra geladen werden müssen	
	
// Spieler
	// Standartansichten
	static Image player = genImg("player/front.png");		// der Spieler von vorne
	static Image dead = genImg("dead.png");			// Toter Spieler ist tot
	// Ansichten beim Angriff
	static Image player_f_atk = genImg("player/front_atk.png");	// Angriff, Frontperspektive
// Monster
	static Image creature = genImg("creature.png");	// Standart-Kreatur
	// Levelrelevantes
	static Image win = genImg("youwin.png");		// Siegbild
	static Image gameover = genImg("gameover.png");	// Game Over
	static Image hud = genImg("HUD.png");			// HUD
	static Image bks = genImg("bks.png");			// Hintergrund
	// Umgebung des Levels
	static Image wall = genImg("wall.png");			// Wand
	static Image goal = genImg("goal.png");			// Zielobjekt
	// Nutzbare Items
	static Image potion = genImg("potion.png");		// Trank
	static Image potionused = genImg("potionused.png");	// (Platzhalter)
	// Fallen
	static Image trap = genImg("trap.png");			// Speerfalle
	static Image trapact = genImg("trapActivated.png");	// aktivierte Speerfalle
	
// Waffen
	// simples Schwert
	static Image basicsword = genImg("weapons/basicsword/front.png");	// Frontansicht
	static Image basicsword_f_atk = genImg("weapons/basicsword/front_atk.png");	// Frontansicht beim Angriff
	
	// Methode zum Bilder generieren (nicht nötig, spart aber Tipparbeit oben)
	private static Image genImg(String image){
		return (new ImageIcon("img/"+image)).getImage();
	}
}
