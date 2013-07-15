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
	
	/** Server zur Kommunikation mit dem lokalen Chatprogramm. */
	
	private ChatClientServer ccs;
	
	/**
	 * Der Konstruktor fuer den Client.
	 */
	
	//Konstruktor
	public Client(LevelMP l, String a){
		super();
		adress = a;
		lvl = l;
		ccs = new ChatClientServer(lvl, this);
		ccs.start();
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
					client.setSoTimeout(5000);								//Timeout fuer reads=5s
					in = new ClientInput(lvl, ccs, this);									//Input-Thread konstruieren
					in.setInputStream(client.getInputStream());				//Input-Thread mit Input des Sockets starten
					in.start();												
					out = new PrintWriter(client.getOutputStream(), true);	//Output oeffnen
				} catch (IOException e) {e.printStackTrace();}
			}
			//Wenn Verbindung unterbrochen ist ...
			else if(!in.isOpened()){
				//... Schliesse und loesche den Clienten
				try {
					out.close();		//Output schliessen
					client.close();		//Socket schliessen
					client = null;		//Socket loeschen
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
		ccs.end();
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
	
	private String inLine, inLine1 = "0";			//Eingelesene Line
	
	/** Der eingelesene, gesplittete Line-Array. */
	
	private String[] lineSplit;
	
	/** Statusvariablen. */
	
	private boolean running, open;	//Statusvariablen
	
	/** Level des Clienten. */
	
	private LevelMP lvl;
	
	/** Uebergebener Server zur Kommunikation mit dem lokalen Chatprogramm.  */
	
	private ChatClientServer ccs;
	
	/** Client, der den Input geoeffnet hat. */
	
	private Client c;
	
	/**
	 * Der Konstruktor fuer die Klasse ClientInput. 
	 */
	
	//Konstruktor
	public ClientInput(LevelMP l, ChatClientServer cc, Client c){
		super();
		lvl = l;
		ccs = cc;
		this.c = c;
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
					if(inLine.compareTo(inLine1) != 0){
						inLine1 = inLine;
						lineSplit = inLine.split(",");
						if(lineSplit[0].compareTo("0") == 0){
							if(lineSplit[1].compareTo("start") == 0){
									lvl.setOpenedInterface(0);
									lvl.setDialog(false);
									lvl.freeze = false;
									lvl.setWaiting(false);
							}
						}
						else if(lineSplit[0].compareTo("1") == 0){
							if(lineSplit[1].compareTo("0") == 0){
								lvl.setPlayerDirection(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]));
							}
							else if(lineSplit[1].compareTo("1") == 0){
								lvl.player2.teleport(Double.parseDouble(lineSplit[2]), Double.parseDouble(lineSplit[3]));
							}
							else if(lineSplit[1].compareTo("2") == 0){
								lvl.input2(Integer.parseInt(lineSplit[2]));
							}
						}
						else if(lineSplit[0].compareTo("3") == 0){
							c.send("4,"+lineSplit[1]+","+lineSplit[2]);
						}
						else if(lineSplit[0].compareTo("4") == 0){
							ccs.send("0,"+lineSplit[1]+","+lineSplit[2]);
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

//----------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------

/**
 * Die Klasse ChatClientServer.
 * Dies istd er ServerSocket, der fuer die Kommunikation mit dem lokalen Chatprogramm verantwortlich ist.
 */

class ChatClientServer extends Thread{
	
	/** Der Input des Servers. */
	
	private ClientInput in;							//Input-Thread
	
	/** Der ServerSocket des Servers. */
	
	private ServerSocket server;					//ServerSocket
	
	/** Der Client/Chatprogramm des Servers. */
	
	private Socket client;							//Chat-Client
	
	/** Der Output des Servers. */
	
	private PrintWriter out;						//Output
	
	/** Die auszugebene Line des Servers. */
	
	private String outLine;							// Output Line fuer Client
	
	/** Die Statusvariablen des Servers. */
	
	private boolean running, started, send;			//Statusvariablen
	
	/** Das Level des Servers. */
	
	private LevelMP lvl;
	
	/** Der uebergeordnete Client des Servers. */
	
	private Client c;
	
	/**
	 * Der Konstruktor des ChatClientServers.
	 * Er baut den Serversocket auf.
	 */
	
	public ChatClientServer(LevelMP l, Client c){
		lvl = l;
		this.c = c;
		try {
			server = new ServerSocket(2049);			//Serversocket für localhost an Port 2048 erstellen
			server.setSoTimeout(10000);					//Timeout fuer accepts=10s
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Die Thread-Schleife des Threads.
	 * Sie haendelt das verbundene Chatprogramm und die Weiterleitung an den Client des Games.
	 */
	
	public void run(){
		running = true;
		
		while(running){
			//Wenn client1 nicht existiert ...
			if(client == null){
				//... Nach neuem Client suchen
				try {
					client = server.accept();		//Auf Client warten
					client.setSoTimeout(5000);		//Client-Read-Timeout=5s
					in = new ClientInput(lvl, this, c);						//Input-Thread erstellen
					in.setInputStream(client.getInputStream());	//InputStream uebergeben und starten
					in.start();
					out = new PrintWriter(client.getOutputStream(), true);	//Output oeffnen
				} catch (IOException e) {}
			}
			//Wenn Verbindung unterbrochen ...
			else if(!in.isOpened()){
				//Client1 schliessen und loeschen
				try {
					in.end();
					out.close();
					client.close();
					client = null;
				} catch (IOException e) {e.printStackTrace();}
			}
			//Ansonsten Client-Action durchfuehren
			else{
				if(send){
					out.println(outLine);
					send = false;
				}
			}
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
	 * Die Methode end().
	 * Sie beendet den ChatServer.
	 */
	
	public void end(){
		running = false;
		//Alle Sockets und Streams schließen
		if(server != null){
			try {
				server.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		System.exit(0);
	}
}