package hhu.propra2013.gruppe55;

import java.awt.Rectangle;

public class Npc extends LivingObject {	

	// Diese Funktion sieht ziemlich leer aus, koennte sich aber fuellen je mehr Npc-Typen wir benoetigen
	
	public Npc(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		state[1].defineOffset(15, 5, 0, 5, 0);
		state[1].massive = true;
		
    }
	
	// To DO - Funktionen fuer den NPC und 2. "Hitbox" fuer die Interaktionsflaeche!
	

}
