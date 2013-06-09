package hhu.propra2013.gruppe55;

public class SimpleShield extends Weapon{

	protected int def;
	
	public SimpleShield(){
	
	name = "Simples Schild";
	def = 1;
	
	state[0].changeImg(Data_Img.basicshield_f);
	state[1] = new State(Data_Img.basicshield_f, Data_Img.basicshield_f, Data_Img.basicshield_b, Data_Img.basicshield_b, false, false, true);
	state[2] = new State(Data_Img.basicshield_f, Data_Img.basicshield_f, Data_Img.basicshield_b, Data_Img.basicshield_b, false, false, true);
	
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
	
	public int getdef(){
		return def;
	}
}
