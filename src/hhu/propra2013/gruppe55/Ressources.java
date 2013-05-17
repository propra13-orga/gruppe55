package hhu.propra2013.gruppe55;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Ressources {
	
//	static Image creature, dead, gameover, goal, hud, player, potion, potionused, trap, trapact, wall, win;
	private String creaturePath, deadPath, gameoverPath, goalPath, hudPath, playerPath, potionPath, potionusedPath, trapPath, trapactPath, wallPath, winPath;

	static Image creature = (new ImageIcon("img/creature.png")).getImage();
	static Image dead = (new ImageIcon("img/dead.png")).getImage();
	static Image gameover = (new ImageIcon("img/gameover.png")).getImage();
	static Image goal = (new ImageIcon("img/goal.png")).getImage();
	static Image hud = (new ImageIcon("img/HUD.png")).getImage();
	static Image player = (new ImageIcon("img/player.png")).getImage();
	static Image potion = (new ImageIcon("img/potion.png")).getImage();
	static Image potionused = (new ImageIcon("img/potionused.png")).getImage();
	static Image trap = (new ImageIcon("img/trap.png")).getImage();
	static Image trapact = (new ImageIcon("img/trapActivated.png")).getImage();
	static Image wall = (new ImageIcon("img/wall.png")).getImage();
	static Image win = (new ImageIcon("img/youwin.png")).getImage();
	
	public static Map<String, Image> lib;		// image library
	
	public Ressources(){
		//Bildpfade
		creaturePath = "img/creature.png";
		deadPath = "img/dead.png";
		gameoverPath = "img/gameover.png";
		goalPath = "img/goal.png";
		hudPath = "img/HUD.png";
		playerPath = "img/player.png";
		potionPath = "img/potion.png";
		potionusedPath = "img/potionused.png";
		trapPath = "img/trap.png";
		trapactPath = "img/trapActivated.png";
		wallPath = "img/wall.png";
		winPath = "img/youwin.png";
		// Bilder laden
		creature = (new ImageIcon(creaturePath)).getImage();
		dead = (new ImageIcon(deadPath)).getImage();
		gameover = (new ImageIcon(gameoverPath)).getImage();
		goal = (new ImageIcon(goalPath)).getImage();
		hud = (new ImageIcon(hudPath)).getImage();
		player = (new ImageIcon(playerPath)).getImage();
		potion = (new ImageIcon(potionPath)).getImage();
		potionused = (new ImageIcon(potionusedPath)).getImage();
		trap = (new ImageIcon(trapPath)).getImage();
		trapact = (new ImageIcon(trapactPath)).getImage();
		wall = (new ImageIcon(wallPath)).getImage();
		win = (new ImageIcon(winPath)).getImage();
		

	}
	
	public static void init(){
		// initialize map
		lib	=	new HashMap<String,Image>();
		// player images
		lib.put("player", player);
		lib.put("dead",  dead);
		// mobs
		lib.put("creature", creature);
		// level design objects
		lib.put("trap", trap);
		lib.put("trapact",trapact);
		lib.put("goal", goal);
		lib.put("wall",wall);
		lib.put("potion",potion);
		lib.put("potionused",potionused);
		// level win/lose
		lib.put("win",win);
		lib.put("gameover",gameover);
		// hud
		lib.put("hud",hud);
	}
}
