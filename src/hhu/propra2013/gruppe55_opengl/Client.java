package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;

/**
 * Die Klasse Client.
 * Diese Klasse implementiert alle Funktionen des Clients fuer die Coop Version des Spiels.
 */

//Client-Thread
public class Client extends Thread{
	
	/** Socket des Clients. */
	
	private Socket client;				//Socket des Clients
	
	/** Input des Clients. */
	
	private ClientInput in;				//Input
	
	/** Output des Clients. */
	
	private PrintWriter out;			//Output
	
	/** Die ausgegebene Zeile. */
	
	private String outLine;				//Auszugebene Zeile
	
	/** Statusvariablen. */
	
	private boolean running, send;		//Statusvariablen
	
	/** Adresse des Servers */
	
	private String adress;
	
	/** Das Level, zu dem der Client gehoert */
	
	private LevelMP lvl;
	
	/**
	 * Der Konstruktor fuer den Client.
	 */
	
	//Konstruktor
	public Client(LevelMP l, String a){
		super();
		adress = a;
		lvl = l;
	}
	
	/**
	 * Die Methode run.
	 * Diese Methode implementiert alle Funktionen, die waehrend des laufenden Spiels benoetigt werden. 
	 */
	
	//Thread-Schleife
	@Override
	public void run(){
		running = true;
		
		while(running){
			//Wenn kein Client existiert...
			if(client == null){
				//... erstelle einen neuen
				try {
					client = new Socket(adress, 2048);					//Neuen Socket an localhost mit Port 2048
					client.setSoTimeout(5000);								//Timeout für reads=5s
					in = new ClientInput(lvl);									//Input-Thread konstruieren
					in.setInputStream(client.getInputStream());				//Input-Thread mit Input des Sockets starten
					in.start();												
					out = new PrintWriter(client.getOutputStream(), true);	//Output öffnen
				} catch (IOException e) {e.printStackTrace();}
			}
			//Wenn Verbindung unterbrochen ist ...
			else if(!in.isOpened()){
				//... Schließe und lösche den Clienten
				try {
					out.close();		//Output schließen
					client.close();		//Socket schließen
					client = null;		//Socket löschen
				} catch (IOException e) {e.printStackTrace();}
			}
			//Wenn eine Verbindung besteht...
			else{
				//Sende gesetzte outLine, wenn gesendet werden soll (send)
				if(send){
					out.println(outLine);
					send = false;
				}
			}
		}
		//Clienten beenden
		if(client != null){
			try {
				in.end();		//Input-Thread beenden
				out.close();	//Output schließen
				client.close();	//Socket schließen
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	/**
	 * Die Methode send.
	 * Diese Methode sendet die Clientdaten in Form von Strings an den Server. 
	 * @param s  Die Methode erwartet die Uebergabe eines Strings s 
	 */
	
	//String senden
	public void send(String s){
		outLine = s;
		send = true;
	}
	
	/**
	 * Die Methode end.
	 * Diese Methode beendet das Coop Spiel.
	 */
	
	//Thread beenden
	public void end(){
		running = false;
	}
}

//----------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------


/**
 * Die Klasse ClientInput.
 * Diese Klasse implementiert die Inputfunktionen fuer den Client. 
 */

//Thread fuer die Inputverarbeitung
class ClientInput extends Thread{

	/** Reader fuer den InputStream. */
	
	private BufferedReader in;		//Reader fuer InputStream
	
	/** Die eingelesene Line. */
	
	private String inLine;			//Eingelesene Line
	
	/** Der eingelesene, gesplittete Line-Array. */
	
	private String[] lineSplit;
	
	/** Statusvariablen. */
	
	private boolean running, open;	//Statusvariablen
	
	/** Level des Clienten. */
	
	private LevelMP lvl;
	
	/**
	 * Der Konstruktor fuer die Klasse ClientInput. 
	 */
	
	//Konstruktor
	public ClientInput(LevelMP l){
		super();
		lvl = l;
	}
	
	/**
	 * Die Methode run.
	 * Diese Methode implementiert alle Funktionen fuer den Input (Clientseitig), die waehrend des Coop Spiels benoetigt werden. 
	 */
	
	//Thread-Schleife
	@Override
	public void run(){
		running = true;
		
		while(running){
			try {
				//Lese eine Line aus dem InputStream
				if(open && (inLine = in.readLine()) != null){
					System.out.println(inLine);
					lineSplit = inLine.split(",");
					System.out.println(lineSplit[0]);
					if(lineSplit[0].compareTo("0") == 0){
						if(lineSplit[1].compareTo("start") == 0){
								System.out.println("start");
								lvl.setOpenedInterface(0);
								lvl.setDialog(false);
								lvl.toggleFreeze();
								lvl.toggleWaiting();
						}
					}
					else if(lineSplit[0].compareTo("1") == 0){
						if(lineSplit[1].compareTo("0") == 0){
							
						}
					}
				}
				//Schliesse Input, wenn Gegenseite closed ist
				else{
					open = false;
					in.close();
				}
			} catch (IOException e) {}
		}
	}
	
	//InputStream des Clients übergeben
	public void setInputStream(InputStream is){
		in = new BufferedReader(new InputStreamReader(is));
		open = true;
	}
	
	/**
	 * Die Methode getInput.
	 * Diese Methode gibt den aktuellen Input zurueck.
	 * @return der aktuelle Input als String.
	 */
	
	//Return des aktuellen inputs
	public String getInput(){
		return(inLine);
	}
	
	/**
	 * Die Methode isOpened.
	 * Diese Methode gibt zurueck ob der Input offen ist.
	 * @return true, wenn der Input offen ist.
	 */
	
	//Return, ob Input offen ist
	public boolean isOpened(){
		return(open);
	}
	
	/**
	 * Die Methode end.
	 * Diese Methode beendet das Coop Spiel.
	 */
	
	//Thread beenden
	public void end(){
		running = false;
	}
}