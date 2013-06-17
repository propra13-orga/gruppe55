package hhu.propra2013.gruppe55;

import java.awt.Color;
import java.awt.Graphics2D;

public class Boss3 extends Creature {
	// Attribute
	private Projectile alternativeProjectile	=	new Fireball(0,0,0,0);
	// Kontrollvariablen fuer Phase 3
	private boolean warning1	=	false;	// Boolean, ob bereits ein Warnschuss abgegeben wurde
	private boolean warning2	=	false;	// Boolean, ob bereits ein zweiter Warnschuss abgegeben wurde
	private int warnedAngle = 0;		// Winkel, der gewarnt wurde
	
	protected boolean bounce = false;		// Abfrage ob der Boss von einer Wand abgebounced ist
 
    public Boss3(double spawnX, double spawnY, int h, int angr, int vert) {
		super(spawnX, spawnY, h, angr, vert);
		
		state[1].changeImg(Data_Img.boss3); 	// Bild der Lebendigen Kreatur laden
		sx	=	(int)spawnX;					// Erscheinungskoordinaten
		sy	=	(int)spawnY;					
		dx =1;									// Bewegung wird initialisiert x - Richung
		dy =1;									// Bewegung wird initialisiert y - Richung								
		speed = 3;								// Startgeschwindigkeit
		detectionRange = 350;					// Groessere Schussreichweite
		
		// Resetrelevante Werte
		resetValues	=	new int[9];	// atk und detectionRange und speed kommen dazu, sonst wird nach dem Enrage nicht zurueck gesetzt
    }

    public void move(){    	
    	// Berechnung des Winkels beim abprallen von einer Wand
    	if(bounce==true){
    		double angle=Math.random()*360; // [0,360[
    		dx=Math.cos(Math.toDegrees(angle)); // Beschleunigung in x Richtung
    		dy=Math.sin(Math.toDegrees(angle));	// Beschleunigung in y Richtung
    		bounce=false;	// Bounce wird wieder auf false gesetzt
    	}
		// bewegung ausfuehren
    	if(state[currState].moveable){
    		x+=speed*dx;
    		y+=speed*dy;
    	}
    }
    
    public void action(int pX, int pY){
		// Ist der Spieler in Reichweite?
		if(distanceBetween(pX,pY)<=detectionRange){
			// Winkel berechnen
			int[] center	=	getCenter();
			double angle	=	Math.toDegrees(Math.atan2((pY-center[1]),(pX-center[0])));
			// Schießen
			if(hp>10){
				shoot((int)angle%360);
			}
			// Auf Feuerbaelle umsteigen - so als Warnung
			else if(hp>5){
				shoot((int)angle%360, alternativeProjectile);
			}
			// Am Ende soll es schwieriger sein, uebertreiben wir also!
			else{
				// Warnschuss um dem Spieler ne Chance zu geben!
				if(!warning1 || (warning1 && !warning2) ){
					// Warnung flaggen
					warning2	=	warning1; // sichert, dass wir 2x zur Warnung schiessen
					warning1	=	true;
					warnedAngle	=	(!warning2)?(int)angle:warnedAngle;
					// Warnschuesse abgeben, die den 120° Bereich einleiten, der von der Feuerballsalve abgedeckt wird
					shoot(warnedAngle+60%360, alternativeProjectile);
					shoot(warnedAngle-60%360, alternativeProjectile);
				}
				else{
					// die naechsten Schuesse muessen wieder Warnungen sein
					warning1	=	false;
					warning2	=	false;
					// Nun decken wir den 120° Bereich (oben genannt) mit Feuer ein
					shoot(warnedAngle%360+60, alternativeProjectile);
					shoot(warnedAngle%360+50, alternativeProjectile);
					shoot(warnedAngle%360+40, alternativeProjectile);
					shoot(warnedAngle%360+30, alternativeProjectile);
					shoot(warnedAngle%360+20, alternativeProjectile);
					shoot(warnedAngle%360+10, alternativeProjectile);
					shoot(warnedAngle%360, alternativeProjectile);
					shoot(warnedAngle%360-10, alternativeProjectile);
					shoot(warnedAngle%360-20, alternativeProjectile);
					shoot(warnedAngle%360-30, alternativeProjectile);
					shoot(warnedAngle%360-40, alternativeProjectile);
					shoot(warnedAngle%360-50, alternativeProjectile);
					shoot(warnedAngle%360-60, alternativeProjectile);
				}
			}
		}
	}

    // Hier wird auch nur ein kleines Detail ergaenzt (genau wie bei getHit)
	public void setBack(){
		bounce = true;		// bounce wird auf true gesetzt damit das Monster weiss, dass es die Richtung wechseln soll
		super.setBack();	// Aufrufen der Muttermethode
	}
      
    public void getHit(int dmg){
    	// Muttermethode aufrufen - wir wollen nur ein Detail ergaenzen
    	super.getHit(dmg);		
    	
    	// bewegungsgeschwindigkeit berechnen
    	// Wenn der Boss weniger als 5 HP hat
    	if(hp<=5){
    		// stoppe Bewegung
    		speed = 7;
    		state[1].changeMoveable(false);
    		// ENRAGE WUAAAAARGH
    		atk	*=2;	// Staerker werden
    		// Groessere Range
    		detectionRange	=	600;
    	}
    	// Wenn der Boss weniger als 10 HP hat
    	else if(hp<=10){
    		speed = 5.5;
    	}
    	
    	// Unser Detail:
    	if(hp<=0){
    		for(GameEventListener gel : evtList){
    			gel.newGoal(x, y);		// Der Boss droppt an der Stelle seines Todes das Zielobjekt (bis wir nahtlosen Übergang zwischen den Leveln haben)
    		}
    	}
    }
    
    // Resetmethoden bearbeiten
    public void setResetValues(){
    	super.setResetValues();
    	resetValues[6]	=	atk;
    	resetValues[7]	=	detectionRange;
    	resetValues[8]	=	(int) speed;
    }
    public void reset(){
    	super.reset();
    	atk	=	resetValues[6];
    	detectionRange	=	resetValues[7];
    	speed	=	resetValues[8];
		state[1].changeMoveable(true);
    }
     
    @Override
    public void draw(Graphics2D g2d){
    	// Nichts zeichnen, wenn Kreatur unsichtbar
    	if(!state[currState].visible)
    		return;
    	// Zeichnen der HP-Leiste ueber den Koepfen der Kreaturen
    	g2d.setColor(Color.WHITE);
    	g2d.fillRect((int)x, (int)y-8, 58, 5);
    	g2d.setColor(Color.BLACK);
    	g2d.drawRect((int)x, (int)y-8, 58, 5);
    	g2d.setColor(Color.RED);
    	g2d.fillRect((int)x, (int)y-7, (int)(58*((double)hp/hpMax)), 4);
    	g2d.setColor(Color.BLACK);
    	// Zeichnen der Monster
    	g2d.drawImage(state[currState].getImg(), (int)x, (int)y, null);
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