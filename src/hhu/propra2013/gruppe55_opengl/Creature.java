package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

public class Creature extends LivingObject {
// Attribute der Monster
    // Deklaration der Koordinaten 
    protected int sx, sy;			// Erscheinungskoordinaten
	protected int moveAreaX	=	5*32;		// maximale vertikale Bewegung nach rechts
	protected int moveAreaY	=	3*32;		// ... und nach oben

    	
// Konstruktor
    public Creature(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man) {
		super(spawnX, spawnY, h, angr, vert, ausd, man);
		
		// States setzen
		state[0].visible	=	false; 		// totes Monster wird unsichtbar
		state[0].defineOffset(0, 0, 2, 5, 0);
		state[1].changeTexture(Data_Textures.creature); 	// Bild der Lebendigen Kreatur laden
		sx	=	spawnX;						// Erscheinungskoordinaten
		sy	=	spawnY;
		
		// Bewegung initiieren
		dx=1;
	}
    
    // TODO: Methode fuer Bewegung �berdenken
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
    
	public void getHit(int dmg){
		// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
		super.getHit(dmg);
		
		// Unser Detail:
		if(hp<=0){
			for(GameEventListener gel : evtList){
				gel.newTreasure(x, y);
			}
		}
	}
    
    @Override
    public void draw(){
    	// Nichts zeichnen, wenn Kreatur unsichtbar
    	if(!state[currState].visible)
    		return;
    	// Zeichnen der HP-Leiste ueber den Koepfen der Kreaturen
//    	g2d.setColor(Color.WHITE);
    	glDisable(GL_TEXTURE_2D);
    	glColor3f(1f, 1f, 1f);
    	glBegin(GL_QUADS);
    		glVertex2i(x, y-8);
    		glVertex2i(x, y-3);
    		glVertex2i(x+24, y-3);
    		glVertex2i(x+24, y-8);
    	glEnd();
//    	g2d.fillRect(x, y-8, 24, 5);
//    	g2d.setColor(Color.BLACK);
    	glColor3f(1f, 0f, 0f);
//    	g2d.drawRect(x, y-8, 24, 5);
//    	g2d.setColor(Color.RED);
//    	g2d.fillRect(x+1, y-7, (int)(24*((double)hp/hpMax)), 4);
    	glBegin(GL_QUADS);
			glVertex2i(x+1, y-7);
			glVertex2i(x, y-4);
			glVertex2i(x+(int)(24*((double)hp/hpMax)), y-4);
			glVertex2i(x+(int)(24*((double)hp/hpMax)), y-7);
		glEnd();
//    	g2d.setColor(Color.BLACK);
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