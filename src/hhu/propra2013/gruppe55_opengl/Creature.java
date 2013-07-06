package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Creature.
 * Diese Klasse erbt von der Klasse LivingObject und spezifiert die Art des Objektes weiter (als Kreaturen = Gegner).
 * @see LivingObject
 */


public class Creature extends LivingObject {
// Attribute der Monster
    // Deklaration der Koordinaten 
    protected int sx, sy;			// Erscheinungskoordinaten
	protected int moveAreaX	=	5*32;		// maximale vertikale Bewegung nach rechts
	protected int moveAreaY	=	3*32;		// ... und nach oben
	
	/**
	 * Der Konstruktor fuer eine Creature.
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren werden die benoetigten States, sowie die Startkoordinaten und die Bewegung initialisiert.
	 * @param spawnX - Die x-Koordinate, an der die Creature gezeichnet wird.
	 * @param spawnY - Die y-Koordinate, an der die Creature gezeichnet wird.
	 * @param h - Der HP-Wert, mit dem die Creature generiert wird.
	 * @param angr - Der Angriffswert, mit dem die Creature generiert wird.
	 * @param vert - Der Verteidigungswert, mit dem die Creature generiert wird.
	 */
	
// Konstruktor
    public Creature(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		// States setzen
		state[0].visible	=	false; 		// totes Monster wird unsichtbar
		state[1].defineOffset(0, 0, 2, 5, 0);
		state[1].changeTexture(Data_Textures.creature); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;						// Erscheinungskoordinaten
		sy	=	(int)spawnY;
		
		// Bewegung initiieren
		dx=1;
	}
    
    /**
     * Die Methode move.
     * Diese Methode definiert das Bewegungsmuster nach dem sich die Creatures bewegen.
     * Die Methode ueberschreibt die Methode move aus der Mutterklasse MovingObject.
     * @see MovingObject
     */
    
    
    public void move(){
		// bewegung errechnen
		if(dx==1 && x>=sx+moveAreaX) // zu weit rechts
			if(y>sy-moveAreaY){ // nach oben, wenn gewuennscht
				dx=0;
				dy=-1;
			}
			else // ansonsten wieder nach links
				dx=-1;
		else if(dx==-1 && sx>=x) // zu weit links
			if(y<sy){ // nach unten
				dx=0;
				dy=1;
			}
			else // wieder nach rechts
				dx=1;
		else if(dy==-1 && sy-moveAreaY>=y) // zu weit oben
			if(x>sx){ // nach links
				dx=-1;
				dy=0;
			}
			else // wieder runter
				dy=1;
		else if(dy==1 && sy<=y) // zu weit unten
			if(x<sx+moveAreaX){ // nach rechts
				dx=1;
				dy=0;
			}
			else // wieder rauf
				dy=-1;
    	
		// bewegung ausfuehren
		super.move();
    }
    
    /**
     * Die Methode getHit.
     * Diese Methode ruft die Methode getHit aus der Mutterklasse LivingObject auf um den Schaden den die Creature machen zu errechnen.
     * Die Methode ueberschreibt die Methode der Mutterklasse LivingObject mit dem Event fuer den Loot den die Creature droppen soll.
     * @param dmg  Die Methode erwartet als Uebergabe eines int Werts dmg (aus der Berechnung in der Klasse LivinObject)
	 * @param e Es wird ein nummerischer Wert erwartet, der den Elementtyp des eintreffenden Schadens beschreibt
     * @see LivingObject
     */
    
	public void getHit(int dmg, int e){
		// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
		super.getHit(dmg,e);
		
		// Unser Detail:
		if(hp<=0){
			for(GameEventListener gel : evtList){
				gel.newTreasure(x, y);
			}
		}
	}
    
	/**
	 * Die Methode draw.
	 * Diese Methode zeichnet die Creature, sofern diese Lebt (tote Kreaturen sind nicht sichtbar). Zudem wird eine HP-Leist ueber den Koepfen der Monster gezeichnet.
	 * Die Methode ueberschreibt die Methode draw aus der Mutterklasse LivingObject um die Zeichnung der Creature zu realisieren.
	 */
	
    @Override
    public void draw(){
    	// Nichts zeichnen, wenn Kreatur unsichtbar
    	if(!state[currState].visible)
    		return;
    	// Zeichnen der HP-Leiste ueber den Koepfen der Kreaturen
    	glDisable(GL_TEXTURE_2D);
    	glColor3f(1f, 1f, 1f);
    	glBegin(GL_QUADS);
    		glVertex2d(x, y-8);
    		glVertex2d(x, y-3);
    		glVertex2d(x+state[currState].getTexture().getTextureWidth()-8, y-3);
    		glVertex2d(x+state[currState].getTexture().getTextureWidth()-8, y-8);
    	glEnd();
    	glColor3f(1f, 0f, 0f);
    	glBegin(GL_QUADS);
			glVertex2d(x+1, y-7);
			glVertex2d(x, y-4);
			glVertex2d(x+((state[currState].getTexture().getTextureWidth()-8)*((double)hp/hpMax)), y-4);
			glVertex2d(x+((state[currState].getTexture().getTextureWidth()-8)*((double)hp/hpMax)), y-7);
		glEnd();
    	glColor3f(1f, 1f, 1f);
    	glEnable(GL_TEXTURE_2D);
    	// Zeichnen der Monster
    	glBindTexture(GL_TEXTURE_2D, state[currState].getTexture().getTextureID());
    	super.draw();
    }
    
    public void onCollision(DungeonObject d){
		// Dem Spieler Schaden zufuegen
    	if(d instanceof	Player)
			dealDamage((Player)d);
    	
    	// Test auf Massive-Attribut in super.onCollision
		super.onCollision(d);
    }
}