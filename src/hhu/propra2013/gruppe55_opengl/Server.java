package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import java.util.Date;
import java.text.*;

public class Server{

	//Main-Funktion
	public static void main(String[] args) {
		Server sv = new Server();
	}

	private ServerFrame sf;				//Server GUI
	private ServerInput in1, in2;		//Input-Threads
	private ServerSocket server;		//ServerSocket
	private Socket client1, client2;	//Beiden verfuegbaren Clients
	private PrintWriter out1, out2;		//Outputs
	private String outLine1, outLine2;	// Output Lines fuer Clients
	private boolean running, started, send1, send2;			//Statusvariablen
	
	//Konstruktor
	public Server(){
		sf = new ServerFrame(this);
		
		try {
			server = new ServerSocket(2048);			//Serversocket für localhost an Port 2048 erstellen
			server.setSoTimeout(10000);					//Timeout fuer accepts=10s
			sf.addToLog("Server opened at Port 2048");	//Log aktualisieren
		} catch (IOException e) {e.printStackTrace();}
		
		run();	//Server starten
	}
	
	//Server-Schleife
	public void run(){
		running = true;
		
		while(running){
			//Wenn client1 nicht existiert ...
			if(client1 == null){
				//... Nach neuem Client suchen
				sf.addToLog("Waiting for Client 1 ...");
				try {
					client1 = server.accept();		//Auf Client warten
					client1.setSoTimeout(5000);		//Client-Read-Timeout=5s
					sf.addToLog("Client 1 connected at " + client1.getInetAddress() + ":" + client1.getLocalPort());
					in1 = new ServerInput(1);						//Input-Thread erstellen
					in1.setInputStream(client1.getInputStream());	//InputStream uebergeben und starten
					in1.start();
					out1 = new PrintWriter(client1.getOutputStream(), true);	//Output oeffnen
					sf.addToLog("Client 1's Input opened");
				} catch (IOException e) {}
			}
			//Wenn Verbindung unterbrochen ...
			else if(!in1.getOpened()){
				//Client1 schließen und loeschen
				try {
					System.out.println("end1");
					in1.end();
					out1.close();
					client1.close();
					client1 = null;
					sf.addToLog("Client 1 disconnected");
				} catch (IOException e) {e.printStackTrace();}
			}
			//Ansonsten Client-Action durchfuehren
			else{
				if(send1){
					out1.println(outLine1);
					send1 = false;
				}
			}
			//Wenn client2 nicht existiert ...
			if(client2 == null){
				//... Nach neuem Client suchen
				sf.addToLog("Waiting for Client 2 ...");
				try {
					client2 = server.accept();		//Auf Client warten
					client2.setSoTimeout(5000);		//Client-Read-Timeout=5s
					sf.addToLog("Client 2 connected at " + client2.getInetAddress() + ":" + client2.getLocalPort());
					in2 = new ServerInput(2);						//Input-Thread erstellen
					in2.setInputStream(client2.getInputStream());	//InputStream uebergeben und starten
					in2.start();
					out2 = new PrintWriter(client2.getOutputStream(), true);	//Output oeffnen
					sf.addToLog("Client 2's Input opened");
				} catch (IOException e) {}
			}
			//Wenn Verbindung unterbrochen ...
			else if(!in2.getOpened()){
				//Client1 schließen und loeschen
				try {
					System.out.println("end2");
					in2.end();
					out2.close();
					client2.close();
					client2 = null;
					sf.addToLog("Client 2 disconnected");
				} catch (IOException e) {e.printStackTrace();}
			}
			//Ansonsten Client-Action durchfuehren
			else{
				if(send2){
					out2.println(outLine2);
					send2 = false;
				}
			}
		}
	}

	//Game starten
	public void startGame(){
		send(1, "0");
		send(2, "0");
		started = true;
	}
	
	//Line senden
	public void send(int c, String line){
		if(c == 1){
			outLine1 = line;
			out1.println(outLine1);
			send1 = true;
		}
		else{
			outLine2 = line;
			out1.println(outLine2);
			send2 = true;
		}
	}
	
	//Abfragen, ob beide Clients da sind
	public boolean clientsReady(){
		if(client1 != null && client2 != null){
			return(true);
		}
		else{
			return(false);
		}
	}
	
	//Server stoppen
	public void stop(){
		running = false;
		//Alle Sockets und Streams schließen
		if(server != null){
			try {
				server.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		if(client1 != null){
			try {
				in1.end();
				out1.close();
				client1.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		if(client2 != null){
			try {
				in2.end();
				out2.close();
				client2.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		System.exit(0);
	}
}

//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------

//InputHandlerThread
class ServerInput extends Thread{
	
	private int clientID;			//ID des Parent-Clients
	private BufferedReader in;		//InputStreamReader
	private String inLine;			//Ausgelesene Line
	private boolean running, open;	//Statusvariablen
	
	//Konstruktor
	public ServerInput(int id){
		super();
		
		clientID = id;
	}
	
	//Thread-Schleife
	@Override
	public void run(){
		running = true;
		
		while(running){
			try {
				//Wenn Input offen und kein Abbruchsignal ausgelesen wird ...
				if(open && (inLine = in.readLine()) != null){
					//Eingehende Line ausgeben
					System.out.println("Client " + clientID + ": " + inLine);
				}
				//Wenn Abbruchsignal gesendet wurde, Thread beenden
				else{
					System.out.println("Argh");
					open = false;
					running = false;
					in.close();
				}
			} catch (IOException e) {}
		}
	}
	
	//InputStrea uebergeben
	public void setInputStream(InputStream is){
		in = new BufferedReader(new InputStreamReader(is));
		open = true;
	}
	
	//Return Status, ob Verbindung offen ist
	public boolean getOpened(){
		return(open);
	}
	
	//Thread beenden
	public void end(){
		running = false;
		open = false;
	}
}

//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------

//Server-GUI
class ServerFrame extends JFrame implements ActionListener, WindowListener{
	
	private Server srv;					//Parent-Thread
	private JButton start, end;			//Buttons
	private JScrollPane sp;				//ScrollPane fuer Log
	private JTextArea log;				//TextArea fuer Server-Log
	private JList clients;				//JList fuer verbundene Clients
	private JPanel toparea, buttons;	//Panels fuer Layout
	private SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");	//DateFormat fuer Log-Zeitstempel
	
	//Konstruktor
	public ServerFrame(Server s){
		srv = s;																		//Server-Thread uebergeben
		
		setTitle("GameServer");															//Fenstertitel
		setSize(600, 400);																//Fenstergroesse
		addWindowListener(this);														//WindowListener
		setLayout(new GridLayout(2,1));													//GridLayout four Fenster
		
		toparea = new JPanel();															//TopArea fuer List und Buttons
		buttons = new JPanel();															//Panel fuer Buttons
			buttons.setLayout(new GridLayout(2,1));										//GridLayout fuer Buttons
		
		clients = new JList();															//JList fuer Clientanzeige
			clients.setPreferredSize(new Dimension(400,200));							//Groesse
			clients.setBorder(BorderFactory.createTitledBorder("Connected Clients"));	//TitledBorder setzen
		
		start = new JButton("Start Game");												//Start-Button
			start.setPreferredSize(new Dimension(200, 50));								//Groesse
			start.setActionCommand("start");											//ActionCommand fuer ActionListener
			start.addActionListener(this);												//ActionListener setzen
		end = new JButton("Close Server");												//Ende-Button
			end.setPreferredSize(new Dimension(200, 50));								//Groesse
			end.setActionCommand("end");												//ActionCommand fuer ActionListener
			end.addActionListener(this);												//ActionListener setzen
			
		buttons.add(start);																//Buttons auf Panel setzen
		buttons.add(end);
			
		toparea.add(clients);															//Client-List und Button-Panel in TopArea setzen
		toparea.add(buttons);
		
		log = new JTextArea();															//Log-TextArea erzeugen
			log.setEditable(false);														//Nicht bearbeitbar
			log.setLineWrap(true);														//Automatische Zeilenumbrüche
			DefaultCaret caret = (DefaultCaret)log.getCaret();							//DefaulCaret abgfragen
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);							//AutoScroll: Caret immer and Area-Ende setzen

		sp = new JScrollPane(log);														//ScrollPane fuer TextArea
			sp.setPreferredSize(new Dimension(600, 200));								//Groesse
			sp.setBorder(BorderFactory.createTitledBorder("Serverlog"));				//TitledBorder erstellen
			
		getContentPane().add(toparea);													//TopArea und Log aufs Fenster setzen
		getContentPane().add(sp);
		log.append("-- " + sdf.format(new Date()) + ": ServerFrame successfully created! \n");
		
		pack();																			//FensterLayout anwenden
		setResizable(false);															//Fenster nicht Resizable
		setVisible(true);																//Fenster anzeigen
	}

	//Zeile zum Log hinzufuegen
	public void addToLog(String s){
		log.append("-- " + sdf.format(new Date()) + ": " + s + "\n");
	}

	//Client zur List hinzufuegen
	public void addClient(String name, String ip, int port){
	}
	
	// ActionListener --------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		//Start-Button
		if(e.getActionCommand() == "start"){
			if(srv.clientsReady()){
				addToLog("Game started");
				srv.startGame();
			}
		}
		//Ende-Button
		else if(e.getActionCommand() == "end"){
			srv.stop();		//Server beenden
		}
	}

	// WindowListener --------------------------------------------
	@Override
	public void windowOpened(WindowEvent e) {}

	//Server-Thread beenden, wenn Fenster geschlossen wird
	@Override
	public void windowClosing(WindowEvent e) {
		srv.stop();
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}