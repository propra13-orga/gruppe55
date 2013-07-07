package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;

//Client-Thread
public class Client extends Thread{
	
	private Socket client;				//Socket des Clients
	private ClientInput in;				//Input
	private PrintWriter out;			//Output
	private String outLine;				//Auszugebene Zeile
	private boolean running, send;		//Statusvariablen
	
	//Konstruktor
	public Client(){
		super();
	}
	
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
	
	//String senden
	public void send(String s){
		outLine = s;
		send = true;
	}
	
	//Thread beenden
	public void end(){
		running = false;
	}
}

//----------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------

//Thread für die Inputverarbeitung
class ClientInput extends Thread{

	private BufferedReader in;		//Reader fue InputStream
	private String inLine;			//Eingelesene Line
	private boolean running, open;	//Statusvariablen
	
	//Konstruktor
	public ClientInput(){
		super();
	}
	
	//Thread-Schleife
	@Override
	public void run(){
		running = true;
		
		while(running){
			try {
				//Lese eine Line aus dem InputStream
				if(open && (inLine = in.readLine()) != null){
					System.out.println(inLine);
					if(inLine == "0"){
						System.out.println(0);
					}
				}
				//Schließe Input, wenn Gegenseite closed ist
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
	
	//Return des aktuellen inputs
	public String getInput(){
		return(inLine);
	}
	
	//Return, ob Input offen ist
	public boolean isOpened(){
		return(open);
	}
	
	//Thread beenden
	public void end(){
		running = false;
	}
}