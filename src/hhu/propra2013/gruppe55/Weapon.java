package hhu.propra2013.gruppe55;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Weapon extends DungeonObject {
// Attribute
	// Spezifikation der Waffe
	protected int type	=	0;		// Waffentyp (0=Schwert)
	protected String name	=	"Simples Schwert";	// Waffenname
	// Angriffswerte
	protected int atkTime	=	400;	// Angriffszeit in Millisekunden
	protected int minDmg	=	1;		// Mindestschaden
	protected int maxDmg	=	3;		// Maximalschaden
	protected boolean attacking	=	false;	// Waehrend des Angriffs true
	// Offsetwerte zum zeichnen
	protected int[] weapOffsets	=	new int[6];	// Die Offsets wie die Waffe in der Spielerhand gehalten wird	
	// Statuswerte die durch die Waffe erhoeht werden
	// TODO: Statuswerte

	// Konstruktor
	public Weapon() {
		super(0, 0);	// Scheint merkwürdig, macht aber Sinn (wird dem Spieler angepasst)
		
		// States setzen
		state	=	new State[3];
		state[0]	=	new State(Data.basicsword, false, false, true);
		state[1]	=	new State(Data.basicsword, false, false, true);
		state[2]	=	new State(Data.basicsword_f_atk, false, false, true);
		switchState(1);
		
		// Offsets zeichnen
		weapOffsets[0]	=	0;	// X-Offset von lootbarer Waffe
		weapOffsets[1]	=	0;	// Y-Offset von lootbarer Waffe
		
		weapOffsets[2]	=	3;	// X-offset von gehaltener Waffe
		weapOffsets[3]	=	21;	// Y-Offset von gehaltener Waffe
		
		weapOffsets[4]	=	3;	// X-Offset beim Angriff
		weapOffsets[5]	=	0;	// Y-offset beim Angriff
	}
	
	// Rueckgabe der Angriffsdauer
	public int getAtkTime(){
		return atkTime;
	}
	
	// Zeichenmethode
	public void draw(Graphics2D g2d, int x, int y){
		//x, y Werte setzen
		this.x	=	(x-(weapOffsets[currState*2+0]));
		this.y	=	(y-(weapOffsets[currState*2+1]));
		super.draw(g2d);
	}
	
	// Angriffsmodus setzen
	public void attack(){
		// auf Angriff setzen
		this.attacking	=	true;
		switchState(2);
	}
	
	// Angriffsmodus abbrechen
	public void stopAttack(){
		// auf harmlos setzen
		this.attacking	=	false;
		switchState(1);
	}
	
	public int getMinDmg(){
		return minDmg;
	}
	
	public int getMaxDmg(){
		return maxDmg;
	}
	
	// Hitbox
	public Rectangle getBorder(){
		// bei Angriff macht die Hitbox Sinn!
		if(attacking)
			return super.getBorder();
		// ansonsten keine Hitbox
		return new Rectangle(0,0,0,0);
	}

}
