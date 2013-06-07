To-Do-List
 - realisierte Stat-Uebergabe von ATK/DEF von den Waffen an den Spieler
Geplante Aspekte
 - Boden als eine große Grafik, Objekte überlagern diese
 - Klasse zur Text-/Dialogdarstellung
 - Fernkampfwaffen/Projektile
 - Animation
 - Kampfsystem

Known Issues:
- FIXED? wenn man waehrend man gegen die Wand ueber dem Gem laueft stirbt wird der Grabstein nach unten geportet
- Bodentexturen muessen generiert werden ohne viel Speicheraufwand
- Level.java broken - Wozu auf Stand halten, wnen wir die sowieso rausschmeißen? (van Meegen: Kaputte Zeilen auskommentiert um Fehlergeschmeissen zu entfernen)


Changelog:

08.06.13 - van Meegen - Update

Die Npcs haben nun eine zweite größere Hitbox fuer die Interaktionen und eine kleine "wirkliche" Hitbox die ihrem Image entspricht.
/src:
Npc:
	- Neues Offsetarray mit Getborder()-funktion fuer die Interaktionshitbox
Shopkeeper:
	- Bekommt die Hitbox per superfunktion von livingobject abgeleitet um eine kleinere Hitbox als die aus dem Npc zu generieren

08.06.13 - van Meegen

2 Neue Klassen hinzugefuegt. Npc und Shopkeeper, der sich aus Npc ableitet. Die Klassen sind derzeit noch leer, werden aber demnaechst befuellt)
=> Hitbox und Interaktionshitbox
=> diverse Funktionen die der Shopkeeper und weitere Npc's brauchen
=> ????????
=> Profit

/src:
Npc:
	- Konstruktor hinzugefuegt
	- Vorlaeufige Hitbox
	- Klasse ist bisher noch leer wird aber demnaechst voller

Shopkeeper:
	- Konstruktor (ja ich weiss, maechtig!)
	- Vorlaeufiges Bild

07.06.13 - jdnklau
TestLevel:
	- fuegt sich selbst nun jedem DungeonObject als Listener hinzu
	- implementiert nun GameEventListener
DungeonObject
	- besitzt nun eine ArrayList aller Listener fuer eventuelle GameEvents
Creature
	- laesst nun einen Schatz liegen bei Tot
Player
	- besitzt nun keine Methoden und Attribute mehr um aus dem Spieler herauszulesen ob das Level beendet wurde
GoalObject
	- beendet das Level nun ueber ein GameEvent
GameEventListener
	- tolles neues Interface ueber das GameEvents geregelt werden
	- Die GameEvents selbst werden in den DungeonObject Objekten gefeuert und vom Level gehoert

05.06.13 - jdnklau
Weapon:
	- einen Pixel beim Offset fuer den Angriff nach Links nach unten verschoben
TestLevel:
	- freeze-State

05.06.13 - van Meegen
/src:
Player:
	- Offsets fuer die Haende (nach oben, links und rechts) ausgerechnet und hinzugefuegt
Weapon:
	- Offsets fuers Schwert (nach oben, links und rechts) ausgerechnet und hinzugefuegt
	- je nachdem in Welche Richtung man schlaegt wird nun auch das richtige Bild dazu angezeigt

05.06.13 - jdnklau

State
	- besitzt nun Attribute und Methoden fuer richtungsspezifische Grafiken!
	- defineOffset, changeImg erwarten nun zusaetzlich einen int-Parameter dafuer, fuer welche
	  Richtung die Aenderung vorgenommen werden soll
	- Ein zweiter Konstruktor wurde hinzugefuegt, der 4 statt einem Bild erwartet fuer die entsprechenden Richtungen
	- die Richtungen sind 0:vorne, 1:links, 2:rechts, 3:hinten
LivingObject
	- aendert nun in move die Richtung aller States auf die neuste Richtung


03.06.13 - van Meegen
/src:
Testlevel:
	- alte Bodenfarbe wieder hergestellt und Grasobjektgenerierung auskommentiert (Performanceprobleme)


02.06.13 - van Meegen
/src:
Data:
	- Bildpfade fuer Schwert, Schild und Bogen erstellt

/img:
	- Angriffsrichtungen fuer das Schwert erstellt
	- Schild Front- und Rueckseite
	- Bogen + Angriffsrichtungen
	- HUD Icons fuer Schwert und Bogen

02.06.13 - van Meegen
/src:
Testlevel:
	- Diesmal WIRKLICH Manapotion und Schatz ins Level eingefuegt (das hat letztes mal irgendwie nicht geklappt x.x)
	- Spieler ist nach clearen des Levels wieder unbeweglich

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