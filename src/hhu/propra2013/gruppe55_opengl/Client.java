package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;

/**
 * Die Klasse Client.
 * Diese Klasse implementiert alle Funktionen des Clients fuer die Coop Version des Spiels.
 */

//Client-Thread
public class Client extends Thread{
	
	private Socket client;				//Socket des Clients
	private ClientInput in;				//Input
	private PrintWriter out;			//Output
	private String outLine;				//Auszugebene Zeile
	private boolean running, send;		//Statusvariablen
	
	/**
	 * Der Konstruktor fuer den Client.
	 */
	
	//Konstruktor
	public Client(){
		super();
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
					client = new Socket("localhost", 2048);					//Neuen Socket an localhost mit Port 2048
					client.setSoTimeout(5000);								//Timeout für reads=5s
					in = new ClientInput();									//Input-Thread konstruieren
					in.setInputStream(client.getInputStream());				//Input-Thread mit Input des Sockets starten
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

//Thread für die Inputverarbeitung
class ClientInput extends Thread{

	private BufferedReader in;		//Reader fue InputStream
	private String inLine;			//Eingelesene Line
	private boolean running, open;	//Statusvariablen
	
	/**
	 * Der Konstruktor fuer die Klasse ClientInput. 
	 */
	
	//Konstruktor
	public ClientInput(){
		super();
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
				}
				//Schließe Input, wenn Gegenseite closed ist
				else{
					open = false;
					in.close();
				}
			} catch (IOException e) {e.printStackTrace();}
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