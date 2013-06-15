package hhu.propra2013.gruppe55;

public class Boss1 extends Creature {
	
	protected int sx, sy;					// Erscheinungskoordinaten
    protected int moveAreaX	=	3*32;		// maximale vertikale Bewegung nach rechts
    protected int moveAreaY	=	3*32;		// ... und nach oben

    public Boss1(int spawnX, int spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		
		state[0].visible	=	false; 		// totes Monster wird unsichtbar
		state[1].changeImg(Data_Img.boss1); 	// Bild der Lebendigen Kreatur laden
		sx	=	spawnX;						// Erscheinungskoordinaten
		sy	=	spawnY;
    }

    public void move(){    	
		// bewegung ausfuehren
    	// Hier soll der Boss eine Methode bekommen in der er den Spieler anstürmt / sich nach mustern bewegt
    }
    
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);
    		}
    	}
    }
   
       
    
public void onCollision(DungeonObject d){
	// Dem Spieler Schaden zufuegen
	if(d instanceof	Player){
		dealDamage((Player)d);
		}

	
	// Test auf Massive-Attribut in super.onCollision
	super.onCollision(d);
	}
}