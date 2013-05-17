To-Do-List
-Bestehendes Konzept optimieren
 - Paintmethoden in entspr. Klassen auslagern, Ãœbergabe des Graphics2D Objektes des Panels
Geplante Aspekte
 - Boden als eine groÃŸe Grafik, Objekte Ã¼berlagern diese
 - Stats (HP, RÃ¼stung, Angriff, etc)
 - Klasse zur Text-/Dialogdarstellung
 - Fernkampfwaffen/Projektile
 - Animation

 
 ## Change-Log ##
 
 17.05.2013 - jdnklau
 	-	ReadMe als Change-Log missbraucht
 	-	Ressources
 		* besitzt nun eine Map (Ressources.lib) zur leichteren referenzierung von Bildobjekten
 		* besitzt nun eine Methode init(), die die Map Ressources.lib mit Bildern beläd 
 	- State
 		* anstatt das Image-Objekt aus Ressources zu laden wird statt dessen per String auf das entsprechende Bild referenziert
 		* das Attribut "Image img" ist nun "String img" und bildet den Referenzstring auf das Bildobjekt
 		* das Attribut "boolean visible" hat nun einen Nutzen
 	- DungeonObject and all sub classes
 		* die Methode "draw(g2d, offsetX, offsetY)" ist nun OOP-feundlicher und nicht mehr in jeder Unterklasse neu definiert
 		  Die Offset-Werte sind nur dem flüssigen Bildlauf dienlich
 		* die Methode "getBorder()" verbessert: Der rechte Offset muss nun den linken nicht mehr ausgleichen (genau so unten und oben)
 	- Level
 		* versehentliche Vertauschung vom Sieges- und GameOver-Bildschirm behoben
 	- LivingObject
 		* Die Attribute für Angriff, Verteidigung, Ausdauer heißen nun atk, def, energy um besser zu den generell englischsprachigen
 		  Variablennamen zu passen - sie fühlten sich so anders und keiner wollte mit ihnen spielen :(