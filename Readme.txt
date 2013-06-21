Geplante Aspekte
 - Boden als eine große Grafik, Objekte überlagern diese
 - Animation

IMPORTANT KNOWN ISSUE:

- Derzeit crasht das Spiel wenn der Boss BESIEGT wird da das Event nicht vernuenftig gefeuert wird :( 
- Bewegung fuer den ersten Boss muss noch implementiert werden

- Der Shopkeeper kann nicht an einer Wand stehen, da bei jetziger Implementieren dann die Wand die ganze zeit den Shopkeeper wegen der Actionhitbox wegschieben moechte und das nicht geht!

Known Issues:
- Bodentexturen muessen generiert werden ohne viel Speicheraufwand
- Pfeile und Projektile generell muessten schoener an der Position des Schiessenden orientiert abgefeuert werden (zB nach allen Seiten gleicher Abstand, schoen zentriert, etc)

WICHTIG: Ich habe die X/Y Koordinaten in allen Objekten von int auf double umgestellt, um geregeltere bewegung zu ermöglichen!

Changelog:

22.06.13 - jdnklau
Weapon
	- es werden nun mehr Attribute des Spielers durch die Waffen erhoehbar sein. Zum Abruf steht dann die Methode getStats zur verfuegung,
	  die die Werte als int[7] wiedergibt
Player
	- profitiert nun von allen Attributverbesserungen, die eine Waffe ihm geben kann


17.06.13 - van Meegen

Alle Bosse haben nun eigene Grafiken und der 3. Boss hat nun auch eine Mechanik. Der Spieler hat nun in allen Leveln 1 Def und die Bosse haben 2 Atk. Diverse andere fixes. 

/img:
	- Boss2 und 3 Grafiken geaddet (Normal und OpenGl)
/src:
	- Bosse überarbeitet und Data_Img / Textures angepasst
	- Boss3 macht nun auch wirklich was! :3
		
16.06.13 - jdnklau
MovingObject
	- besitzt nun eine Methode setDirectionByAngle
LivingObject
	- fixes fuer das Schießen
Creature_Bow
	- schießt nun auf die Mitte des Spielers statt auf seinen Kopf zu zielen (no headshots plx, there is no hitbox!)

16.06.13 - van Meegen
Bosskampf2:

Ich hatte mir fuer den Boss folgendes überlegt: Der Boss ist auf der anderen Seite einer Fallenreihe und man muss diesen mit Fernkampf besiegen (weil Fernkampfboss). Das Ziel spawnt dann auf der Seite des Spielers sobald der Boss tot ist. Das Level habe ich mitgepusht, es reicht das ins Testlvl umzubenennen damit es geladen wird (ich wollte euer Testlevel nicht überschreiben). 

/img:
	- Lootarrow und Creature_bow hinzugefuegt
/src:
ArrowObject
	- vom Spieler aufsammelbare Pfeile die den Pfeilvorrat erhoehen (:O)
Boss1
	- Der Boss bekommt seine MoveArea nun auch ueber den Konstruktor und nicht fest
Boss2
	- Den Boss fuer das 2. Level erstellt (Range Boss) - Der Kampf ist oben erklärt. Das Bild ist ein Platzhalter.
Boss3
	- Platzhalterklasse fuer den 3. Boss bis uns was einfaellt!
Data_Img
	- neue Bilder eingebunden
Level
	- die neuen Objekte werden geneniert (Bosse, Lootarrow etc)
	- Der Boss2 kann nun auch schiessen!
	
	
	
16.06-13 - jdnklau
Level
	- Raumfix fuer die CheckPoints, so dass man nicht im gleichen Raum abgelegt wird, in dem man gestorben ist, sondern in dem, in dem der Checkpoint lag
LivingObject
	- allgemeinere shoot-Methode hinzugefuegt
DungeonObject
	- x/y-Koordinaten sind nun double

15.06.13 - van Meegen
/img:
	- neuer youlose screen
	- neuer gameover screen (wenn man kein extraleben mehr hat)
/src:
Level:
	- Die Extraleben des Spielers werden nun beruecksichtigt. Stirbt man mit 0 extraleben kann man nur noch ins Hauptmenue zurueckkehren. Ansonsten kann nach dem Tod weiterhin neu begonnen werden. 
Data_Img:
	- Neue Bildpfade erstellt
	
15.06.13 - jdnklau
DungeonObject
	- die Methoden "reset" und "setResetValues" (fuer sich sprechende Namen) wurden implementiert
	- resetValues ist nun ein int[] attribut, dass die nummerischen Werte zum zuruecksetzen eines Objektes beinhaltet
	  die bei einem Reset angenommen werden sollen
	  Sprich dadrin befinden sich die zum zeitpunkt der Spieler/CheckPoint-Kollision aktuellen Werte des Objekts
	  Vergleiche die Methode in DungeonObject und LivingObject um die Erweiterbarkeit zu erkennen
Level
	- bei einem reload() werden nun die reset()-Methoden der Objekte abgerufen pro Liste pro Raum

15.06.13 - van Meegen
Boss1:
	- geruest fuer eine Bossklasse erstellt
/lvl:
	- level 2 Teleporter gefixt
	- level 3 erstellt
	*NOTE*: In Level 2 und 3 sind einige Geldsaecke Platzhalter fuer Bogenkreaturen, die noch nicht ueber den JsonParser erstellt werden koennen
Bugfixes:
	- diverse Bugfixes

15.06.13 - jdnklau
Player:
	- besitzt nun die Methode spellCast die seinen aktuellen Zauber wirkt
SpellObject
	- neue Klasse fuer Zauber
Fireball
	- neues verschiessbares Projektil

12.06.13 - van Meegen UPDATE:

Hab im Level den Jsonparseraufruf wieder auf true gesetzt. Ist im letzten push nur auf false gewesen da ich die Objekte im testlvl teste. :3

/src:
Storyteller
	- Storytellerklasse erstellt fuer Npcs die was zu sagen haben!
Level
	- Storyteller mit in die Tastaturabfrage eingebunden
Data_String
	- Der String fuer den Storyteller wird aus der Example.txt ausgelesen
Data_Img
	- Bild fuer den Storyteller mit eingebunden
/dialogs
	- Ordner wieder hinzugefuegt fuer Npc und generelle Spieldialoge

10.06.13 - van Meegen
/img:
	- bks.png entfernt (Wird nicht mehr gebraucht)
/src:
DungeonObject
	- Methode ergaenzt, die wiedergibt ob ein Objekt massiv ist oder begehbar
Projectile
	- Pfeile fliegen nun ueber Fallen, verschwinden aber wenn sie auf nicht begehbare Objekte treffen
Player
	- Der Spieler toetet sich nicht mehr selber wenn er nach links schiesst
SimpleBow
	- min- maxdmg angepasst (Man macht nun immer 2 Dmg mit einem Pfeil)
Level
	- Da wir nun kleinere Raeume haben kann man die Treffeabfrage fuer Projektile verwenden ohne dass es zu großen Slow Downs kommen sollte

09.06.13 - jdnklau
TestLevel:
	- JSON-Parser gefixed: nun werden alle Räume geladen und nicht nur einer -> Kein IndexOutOfBounds wenn man nen Teleporter tangiert

09.06.13 - van Meegen

/src:
Testlevel:
	- Der Aufruf der Level über den Jsonparser wird duch private boolean jsonParser = true; getogglet (false = altes Parsersystem). Per Default erstmal auf false gesetzt, bis alle Bugs beseitigt sind.
SimpleBow:
	- Klasse fuer den Bogen hinzugefuegt und Offsets eingerichtet
SimpleShield:
	- Klasse fuer das Schild hinzugefuegt und Offsets eingerichtet
GameInterface:
	- Hp fix fuer den Jsonparser
Data_Img:
	- Pfad fuer ein weiteres Bogenbild
Player:
	- Waffenaufruf geaendert (aber so nicht odeR?)
/img:
	- Bogen "back.png" erstellt


<<<<<<< HEAD
09.06.13 - Sami Salem
Neuer Leveleditor im package leveleditor2
 Features:
  - schreibt/liest das Level mit Unterräume im json-Format in/aus eine Datei.
  - Räume können hinzugefügt bzw. entfernt werden.
  - Größe von Räume kann immer geändert werden.  
  - Objekte(Wall,Player,Creature) kann man jetzt Parameter zuweisen

zum kompilieren müssen die jackson*.jar Dateien zum Buildpath hinzugefügt werden!
in TestLevel.java kann man durch die Variable jsonParser = true/false festlegen wie geparst wird
 
=======
09.06.13 - jdnklau
Player
	- shoot-Methode die dann auch Pfeile verschiesst
	- aus Testzwecken nun drei Schwerter, 2 fuer den Nah- eins fuer den Fernkampf zum Pfeile schiessen
	- nun auch ein Attribut dass die Anzahl der Pfeile speichert
	- getArrowsRemaining() liefert die Anzahl der verbleibenden Pfeile
GameEventListener
	- besitzt nun eine shootProjectile-Methode
LivingObject
	- erbt nun von MovingObject
*neu* MovingObject
	- *neu*
*neu* Projectile
	- Klasse fuer Projektile
	- braucht x, y, Flugwinkel, Schaden

>>>>>>> 52fec305f01390364bd2516d6b1f23ada0bdff6e

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