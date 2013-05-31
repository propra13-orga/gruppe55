To-Do-List
Geplante Aspekte
 - Boden als eine große Grafik, Objekte überlagern diese
 - Klasse zur Text-/Dialogdarstellung
 - Fernkampfwaffen/Projektile
 - Animation
 - Kampfsystem

Changelog:


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