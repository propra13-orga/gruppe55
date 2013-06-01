To-Do-List
 - realisierte Stat-Uebergabe von ATK/DEF von den Waffen an den Spieler
Geplante Aspekte
 - Boden als eine große Grafik, Objekte überlagern diese
 - Klasse zur Text-/Dialogdarstellung
 - Fernkampfwaffen/Projektile
 - Animation
 - Kampfsystem

Known Issues:
- Monster sind im Scrollinglvl nicht angreifbar
- Nach Beruehren des Ziels im Scrollinglvl kann man sich dennoch weiterhin bewegen

Changelog:

01.06.13 - jdnklau
LivingObject
	- getHit und getHealed erwarten nun Schadens bzw. Heilwerte
	- getHit beachtet nun die DEF des Objekts
	- es gibt nun eine methode "public void dealDamage", die als Parameter das zu schaedigende LivingObject erwartet
TrapObject
	- besitzt nun ein Attribut trapDmg, das (wie koennte es anders sein?) den von der Falle verursachten Schaden repraesentiert
Player
	- Schadensberechnungen von Markus' Push wieder herausgenommen. Sie sind jetzt schlanker im LivingObject implementiert
	- Methode swapWeapons um zwischen den Waffensets wechseln zu koennen


01.06.13 - van Meegen

/img:
	- Manapotionbild hinzugefuegt
	- vorlaeufiges Schatzbild hinzugefuegt 

/src:
	- Klasse Ressources geloescht (wurde von Data ersetzt aber war immer noch im Git)

Player:
	- Uebergabe der Waffenschadenswerte an den Spieler
	- Berechnung von kritischem und nicht kritischem Treffer anhand der Stärke des Spielers (atk) und dem min. bzw max. Waffenschaden
      Problem: Derzeit waere es nur moeglich die Werte dann ans LivingObject zu uebergeben, wenn diese static sind, was entgegen dem Sinn der Rechnung steht.
      		   Die Berechnung an sich funktioniert aber der Schaden ist immer noch einfach 1 solange wir die Uebergabe nicht realisiert haben.
    - Goldattribut hinzugefuegt und eine Platzhalterfunktion die den Goldstand erhoeht

Weapon:
	- Funktionen ergaenzt um den Min- bzw Maxschaden der Waffen zu uebergeben

LivingObject:
	- fillmana() als Funktion fuer die Manatränke hinzugefuegt

MPotionObject:
	- Klasse fuer Manatraenke erstellt 

TreasureObject:
	- Klasse fuer Schatze erstellt.

Data:
	- Bilder fuer Schatz und Manatrank eingebunden
	
/lvl:
	- Testlvl leicht angepasst. Test der beiden neuen Objekte.




31.05.13 - jdnklau

LivingObject
	- kurz unverwundbar nach Treffer
Player
	- besitzt nun ein Attribut Weapon[] weapons mit 3 Einträgen (Haupthand, Nebenhand, Bogen)
	- Attribut attacking hinzugefuegt, falls der Spieler gerade im Angriff steckt
	- Methode draw auf Waffen hingehend erweitert
	- Methode attack() hinzugefuegt
	- Attribut zur "Adressierung" der Haende
Ressources
	- heißt jetzt Data
	- Die HashMap "lib" hab ich wieder heraus genommen. Alex' erste Implementierung hat bereits das 
	  gewuenschte Ergebnis erreicht; ein Denkfehler meinerseits dass ich sie als implementierungsnoetig
	  empfand
Weapon
	- neu hinzugefügte Klasse
	- so gut es geht kommentiert, schaut sie euch an!
Level
	- Der KeyListener leitet nun Angriffsbefehle an den Spieler weiter
	


20.05.13 - MvMeegen

/img:

 - Hintergrund entworfen

/src:
 Level:
  - Hintergrundmalfunktion eingefuegt. Hintergrund fuer das Scrollinglvl muss noch implementiert werden
 Ressources:
  - Pfade fuer das Bild erstellt und eingebunden


MvMeegen - 18.05.13

/img:
 
 - Neues Bild fuer die Potion
 - "gameover" und "youwin" ueberarbeitet um die Moeglichkeit ins Hauptmenu zurueck zu kehren zu reflektieren

/lvl:
 
 - kleiner Wand fix (2 neue Raeume kommen demnaechst!)

/src:
 Level.java und Testlevel.java:
  
  - Reloadfunktion ueberarbeitet. Auch in den getrennten Raeumen werden nun alle statischen Objekte korrekt neu geladen
  - Keypressedevent ueberarbeitet. (schlankerer Aufruf des Hauptmenues/Neustarts)
 Global:
  - Sämtliche Kommentare sind nun in einer Sprache (Denglisch!). Code ein wenig aufgeräumt.