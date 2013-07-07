package hhu.propra2013.gruppe55_opengl;

/**
 * Die Klasse SimpleBow.
 * Diese Klasse erbt von der Klasse Weapon und spezifiziert diese weiter als SimpleBow (einfacher Bogen).
 */

public class SimpleBow extends Weapon{

// Attribute

	/** Der Typ des Angriffs mit dieser Waffe (2 = Fernkampf). */
	
	protected int type	=	2;		// Fernkampf 
	
	/**
	 * Der Konstruktor fuer den SimpleBow.
	 * Hier werden Name, Schaden, States und Bilder sowie Offsetwerte fuer das Zeichnen des Bogens gesetzt. 
	 */
	
	public SimpleBow() {
		super();
		
		// Werteanpassungen
		minDmg = 1;
		maxDmg = 1;
		name = "Simpler Bogen";
		// Grafik
		state[0].changeTexture(Data_Textures.basicbow);
		state[1] = new State(Data_Textures.basicbow_back, Data_Textures.basicbow, Data_Textures.basicbow_back, Data_Textures.basicbow, false, false, true);
		state[2] = new State(Data_Textures.basicbow_f_atk, Data_Textures.basicbow_l_atk, Data_Textures.basicbow_r_atk, Data_Textures.basicbow_u_atk, false, false, true);

		// Offsets
		weapOffsets[0][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[0][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[0][2]	=	5;	// X-offset von gehaltener Waffe
		weapOffsets[0][3]	=	12;	// Y-Offset von gehaltener Waffe
		weapOffsets[0][4]	=	21;	// X-Offset beim Angriff
		weapOffsets[0][5]	=	-3;	// Y-offset beim Angriff
		// Offsets zeichnen - von links
		weapOffsets[1][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[1][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[1][2]	=	0;	// X-offset von gehaltener Waffe
		weapOffsets[1][3]	=	12;	// Y-Offset von gehaltener Waffe
		weapOffsets[1][4]	=	12;	// X-Offset beim Angriff
		weapOffsets[1][5]	=	13;	// Y-offset beim Angriff
		// Offsets zeichnen - von rechts
		weapOffsets[2][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[2][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[2][2]	=	5;	// X-offset von gehaltener Waffe
		weapOffsets[2][3]	=	12;	// Y-Offset von gehaltener Waffe
		weapOffsets[2][4]	=	4;	// X-Offset beim Angriff
		weapOffsets[2][5]	=	12;	// Y-offset beim Angriff
		// Offsets zeichnen - von hinten
		weapOffsets[3][0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[3][1]	=	0;	// Y-Offset von lootbarer Waffe
		weapOffsets[3][2]	=	0;	// X-offset von gehaltener Waffe
		weapOffsets[3][3]	=	13;	// Y-Offset von gehaltener Waffe
		weapOffsets[3][4]	=	3;	// X-Offset beim Angriff
		weapOffsets[3][5]	=	16;	// Y-offset beim Angriff
	}
}
