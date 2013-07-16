package hhu.propra2013.gruppe55_opengl;

import static org.lwjgl.opengl.GL11.*;

/**
 * Die Klasse Boss2.
 * Diese Klasse erbt von der Klasse Creature und implementiert diese weiter als Boss3.
 * @see Creature
 */

public class Boss_BigEye extends Creature {
	// Attribute
	private Projectile projectile2, projectile3;
	private int actionCounter=0;
	private boolean attacking=false;
	
	/**
	 * Der Konstruktor fuer den dritten Boss. 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren wird an dieser Stelle der State fuer den Boss gesetzt und das Bild geladen, sowie die Bewegungsrichtung, die Geschwindigkeit und die Schussreichweite initialisiert.
	 * @param spawnX  Die x-Koordinate, an der der Boss gezeichnet wird.
	 * @param spawnY  Die y-Koordinate, an der der Boss gezeichnet wird.
	 * @param h  Der HP-Wert, mit dem der Boss generiert wird.
	 * @param angr  Der Angriffswert, mit dem der Boss generiert wird.
	 * @param vert  Der Verteidigungswert, mit dem der Boss generiert wird.
	 */
 
    public Boss_BigEye(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.boss_bigeye); 	// Bild der Lebendigen Kreatur laden
		state[1].moveable=false;
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =0;									// Bewegung wird initialisiert x - Richung
		dy =0;									// Bewegung wird initialisiert y - Richung								
		speed = 1;								// Startgeschwindigkeit
		detectionRange = 300;					// Groessere Schussreichweite
		element=3;								// Wasserkreatur
		resistances[0][1]=1;					// Immun gegen Feuer
		resistances[1][0]=7;					// Resistent gegen Eis
		resistances[2][0]=7;					// Resistent gegen Wasser
		
		projectile	=	new Fireball(0, 0, 0, 0);
		projectile2	=	new Spell_SnowFlake(0,0,0,0);
		projectile3	=	new Waterbubble(0,0,0,0);
		
		// Resetrelevante Werte
		//resetValues	=	new int[9];	// atk und detectionRange und speed kommen dazu, sonst wird nach dem Enrage nicht zurueck gesetzt
    }
    
    /**
     * Die Methode action.
     * Diese Methode definiert das Kampfverhalten des Bosses.  
     * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX
     * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY
     */
    
    public void action(int pX, int pY){
    	// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			final double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// angriff flaggen
			attacking=true;
			
			new Thread(){
				public void run(){
					int maxShots=21*2;
					Projectile p=projectile;
					for(int i=1;i<maxShots;i++){
						
						// Projektil auswählen
						if(i%3==0)
							p=projectile;
						else if(i%3==1)
							p=projectile2;
						else
							p=projectile3;
						
						shoot((int)angle, p);
						// Sleep-Timer setzen
		    			try {
							Thread.sleep(2000/maxShots);
						}catch (InterruptedException e) {e.printStackTrace();}
					}
					attacking=false;
				}
			}.start();
		}
		
		// Supermegahyperattacke
		if(hp<0.3*hpMax){ // phase 2
			actionCounter++;
			if(actionCounter==6){ // Feuerkreis
				for(int i=0;i<360; i+=20)
					shoot(i,projectile);
			}
			else if(actionCounter==8){ // Eiskreis
				for(int i=0;i<360; i+=20)
					shoot(i,projectile2);
			}
			else if(actionCounter==10){
				for(int i=0;i<360; i+=20)
					shoot(i,projectile3);
				actionCounter=0;
			}
		}
	}
      
    public void getHit(int dmg, int e){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg,e);
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.triggerFired("BigEyeDown");		// Der Boss feuert das Event seines Todes
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