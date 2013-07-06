package hhu.propra2013.gruppe55_opengl;

/** 
 * Die Klasse FireElemental.
 * Diese Klasse erbt von der Klasse Creature und implementiert das FireElemental.
 * @see Creature
 */

public class FireElemental extends Creature {
	
	private Projectile projectile = new Fireball(0,0,0,0);		// Feuerelementare schiessen Feuerbaelle!
	private int shootorder = 0;		// Schusszaehler fuer den Extraschuss
	
	/**
	 * Der Konstruktor fuer das FireElemental.
	 * Beim Aufruf werden dem Konstruktor 
	 * Beim Aufruf werden dem Konstruktor die Werte spawnX, spawnY, h, angr und vert uebergeben.
	 * Des Weiteren werden die benoetigten States, sowie die Startkoordinaten, die Bilder, die Detectionrange, in der das FireElemental den Player angreift und die Bewegung initialisiert.
	 * @param spawnX - Die x-Koordinate, an der das FireElemental gezeichnet wird.
	 * @param spawnY - Die y-Koordinate, an der das FireElemental gezeichnet wird.
	 * @param h - Der HP-Wert, mit dem das FireElemental generiert wird.
	 * @param angr - Der Angriffswert, mit dem das FireElemental generiert wird.
	 * @param vert - Der Verteidigungswert, mit dem das FireElemental generiert wird.
	 */
	
	public FireElemental (double spawnX, double spawnY, int h, int angr, int vert){
		super (spawnX, spawnY, h, angr, vert);
		
		state[1].changeTexture(Data_Textures.fireelemental); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 3;								// Startgeschwindigkeit
		detectionRange = 500;					// Groessere Schussreichweite
		element=1;								// Feuerkreatur
		resistances[0][1]=1;					// Immun gegeueber Feuerschaden
	}

	/**
	 * Die Methode action.
	 * Diese Methode fuehrt die Schussfunktion auf die Mitte des Players aus, sofern dieser innerhalb der Reichweite (= Detectionrange). Zusaetzlich hat das FireElemental eine Shootorder, sodass nach X schuessen ein alternatives Schussmuster ausgeloest wird.
	 * @param pX  Die Methode erwartet die Uebergabe eines int Werts pX
     * @param pY  Die Methode erwartet die Uebergabe eines int Werts pY 
	 */
	
    public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Sollte das Elementar schon 3x in Richtung Spieler geschossen haben -> Schuss in alle 4 Himmelsrichtungen
			if(shootorder > 2){
				shoot((int)0, projectile);
				shoot((int)90, projectile);
				shoot((int)180, projectile);
				shoot((int)270, projectile);
				shootorder = 0;
			}
			// Schuss in Richtung des Spielers
			else{
				shoot((int)angle%360, projectile);
				shootorder +=1;
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
