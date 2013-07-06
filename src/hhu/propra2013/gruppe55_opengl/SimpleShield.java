package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse SimpleShield.
 * Diese Klasse erbt von der Klasse Weapon und spezifiziert diese weiter als SimpleShield (einfaches Schild).
 */

public class SimpleShield extends Weapon{
// ATTRIBUTE
	
	/**
	 * Der Konstruktor fuer das SimpleShield.
	 * Hier werden Name, Schaden, States und Bilder sowie Offsetwerte fuer das Zeichnen des Schildes gesetzt. 
	 */
	
	// KONSTRUKTOR
	public SimpleShield(){
		super();
		
		// Spezifizieren
		type=1;	// Nebenhand
		name = "Simples Schild";
		// Attribute
		def = 1;
		minDmg	=	0;		// Mindestschaden
		maxDmg	=	0;		// Maximalschaden
		
		state[0].changeTexture(Data_Textures.basicshield_f);
		state[1] = new State(Data_Textures.basicshield_f, Data_Textures.basicshield_f, Data_Textures.basicshield_b, Data_Textures.basicshield_b, false, false, true);
		state[2] = new State(Data_Textures.basicshield_f, Data_Textures.basicshield_f, Data_Textures.basicshield_b, Data_Textures.basicshield_b, false, false, true);
		
		weapOffsets[0][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[0][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[0][2]	=	5;	// X-offset von gehaltener Waffe
		weapOffsets[0][3]	=	5;	// Y-Offset von gehaltener Waffe
		weapOffsets[0][4]	=	7;	// X-Offset beim Angriff
		weapOffsets[0][5]	=	7;	// Y-offset beim Angriff
		// Offsets zeichnen - von links
		weapOffsets[1][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[1][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[1][2]	=	8;	// X-offset von gehaltener Waffe
		weapOffsets[1][3]	=	6;	// Y-Offset von gehaltener Waffe
		weapOffsets[1][4]	=	12;	// X-Offset beim Angriff
		weapOffsets[1][5]	=	12;	// Y-offset beim Angriff
		// Offsets zeichnen - von rechts
		weapOffsets[2][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[2][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[2][2]	=	3;	// X-offset von gehaltener Waffe
		weapOffsets[2][3]	=	6;	// Y-Offset von gehaltener Waffe
		weapOffsets[2][4]	=	4;	// X-Offset beim Angriff
		weapOffsets[2][5]	=	12;	// Y-offset beim Angriff
		// Offsets zeichnen - von hinten
		weapOffsets[3][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[3][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[3][2]	=	5;	// X-offset von gehaltener Waffe
		weapOffsets[3][3]	=	7;	// Y-Offset von gehaltener Waffe
		weapOffsets[3][4]	=	-3;	// X-Offset beim Angriff
		weapOffsets[3][5]	=	16;	// Y-offset beim Angriff
	
	}
}
