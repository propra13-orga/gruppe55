package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;

public class Network {
	
	private Thread server;	//Spielserver-Thread
	private Server s;		//Server-Runnable
	
	//Konstruktor
	public Network(){}
	
	//Server-Thread erstellen und starten
	public void start(Level l){
		s = new Server(2048, l);	//Server erstellen an Port 2048
		server = new Thread(s);
		server.start();
	}
	
	//Thread stoppen
	public void stop(){
		s.disconnect();
	}
}

//Server-Runnable Class
class Server implements Runnable{
	
	private int port;					//Zu verbindender Port
	private ServerSocket ss;			//ServerSocket selber
	private Socket client;				//Resultierender, verbundener client
	private PrintWriter out;			//Output
	private BufferedReader in;			//Input
	private Level lvl;					//SpieLevel
	private boolean running = false;	//Ob Server laufen soll
	
	//Konstruktor
	public Server(int po, Level l){
		//Port und Level setzen
		port = po;
		lvl = l;
		
		//ServerSocket an Port 2048 erstellen
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("--Listening to Port " + port + " failed!");
			e.printStackTrace();
		}
		System.out.println("--Listening to Port " + port + " successfully!");
	}

	//Run-Methode (Serverbetrieb)
	@Override
	public void run() {
		running = true;	//Server laeuft
		String line;	//Eintreffende Line
		
		while(running){
			//Wenn Client verbunden, Input lesen ...
			if(client != null){
				try {
					//Wenn Input existiert, diesen als Ingame-Dialog ausgeben
					if((line = in.readLine()) != null){
						if(!lvl.getDialog()){
							lvl.printDialog(line);
							out.println("Received!");
						}
						else{
							out.println("Wait until receiver is ready!");
						}
					}
				} catch (IOException e) {
					client = null;
					try {
						in.close();
					} catch (IOException e1) {e1.printStackTrace();}
					out.close();
					System.out.println("Client disconnected!");
				}
			}
			//... Ansonsten auf Verbindung warten
			else{
				//Auf Verbindung warten
				try {
					client = ss.accept();
				} catch (IOException e) {
					System.out.println("--No client to connect to!");
				}
				//Wenn Verbindung aufgebaut, Input und Output oeffnen
				if(client != null){
					try {
						in = new BufferedReader(new InputStreamReader(client.getInputStream()));
						out = new PrintWriter(client.getOutputStream(), true);
					} catch (IOException e) {
						System.out.println("--Getting Streams failed!");
						e.printStackTrace();
					}
					System.out.println("--Client connected and streams are opened!");
				}
			}
		}
		//Alles schlieﬂen bei beenden
		try {
			if(client != null){
				in.close();
				out.close();
				client.close();
			}
			ss.close();
		} catch (IOException e) {
			System.out.println("--Closing the server-stuff failed!");
			e.printStackTrace();
		}
		System.out.println("--Server is closed!");
	}
	
	//Server stoppen
	public void disconnect(){
		running = false;
		try {
			ss.close();
		} catch (IOException e) {e.printStackTrace();}
		System.out.println(running);
	}
}