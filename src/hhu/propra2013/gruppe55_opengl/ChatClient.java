package hhu.propra2013.gruppe55_opengl;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

/**
 * Die Klasse ChatClient.
 * Dies ist das Frame, welches die Verbindungsthreads zum Game herstellt und das Chatfenster darstellt.
 */

public class ChatClient extends JFrame implements ActionListener, WindowListener{
	
	public static void main(String[] args) {
		ChatClient c = new ChatClient();
	}
	
	/** Der Client, der sich mit dem Spiel verbindet. */
	
	private ChatServer srv;
	
	/** Das JPanel, das alle Schaltflaechen beinhaltet. */
	
	private JPanel input;
	
	/** Der Button send, der die eingegebene Message an den Client zum Spiel sendet. */
	
	private JButton send;
	
	/** Die Textfelder, die den Alias und die zu uebermittelnde Nachricht einlesen. */
	
	private JTextField name, mess;
	
	/** Die Labels zur Beschriftung der Textfelder. */
	
	private JLabel lname, lmess;
	
	/** Das ScrollPane, dass die TextArea fuer das Log enthaelt. */
	
	private JScrollPane sp;
	
	/** Die TextArea, die den Chat-Log enthaelt. */
	
	private JTextArea log;
	
	/** Das DateFormat fuer den Zeitstempel im Log. */
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");
	
	
	/**
	 * Der Konstruktor des Frames.
	 */
	
	public ChatClient(){
		
		srv = new ChatServer(this);
		srv.start();
		
		setTitle("Chat");																//Fenstertitel
		setSize(600, 300);																//Fenstergroesse
		setLayout(new GridLayout(1,2));													//GridLayout four Fenster
		addWindowListener(this);
		
		log = new JTextArea();															//Log-TextArea erzeugen
			log.setEditable(false);														//Nicht bearbeitbar
			log.setLineWrap(true);														//Automatische Zeilenumbrueche
			DefaultCaret caret = (DefaultCaret)log.getCaret();							//DefaulCaret abgfragen
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);							//AutoScroll: Caret immer and Area-Ende setzen

		sp = new JScrollPane(log);														//ScrollPane fuer TextArea
			sp.setPreferredSize(new Dimension(500, 200));								//Groesse
			sp.setBorder(BorderFactory.createTitledBorder("Serverlog"));				//TitledBorder erstellen
			
		input = new JPanel();
			input.setLayout(new GridLayout(5,1));

		send = new JButton("Send Mesage");
			send.setPreferredSize(new Dimension(100, 50));
			send.setActionCommand("send");
			send.addActionListener(this);
			
		lname = new JLabel("You're Alias:");
			lname.setPreferredSize(new Dimension(100, 50));
			
		lmess = new JLabel("Type you're Message here:");
			lmess.setPreferredSize(new Dimension(100, 50));
			
		name = new JTextField();
			name.setPreferredSize(new Dimension(100, 50));
			
		mess = new JTextField();
			name.setPreferredSize(new Dimension(100, 50));
		
		input.add(lname);
		input.add(name);
		input.add(lmess);
		input.add(mess);
		input.add(send);
		getContentPane().add(sp);
		getContentPane().add(input);
																		//FensterLayout anwenden
		setResizable(false);
		pack();																		//Fenster nicht Resizable
		setVisible(true);																//Fenster anzeigen
	}

	/**
	 * Die Methode addToLog.
	 * Sie fuegt den uebergebenen String mit Zeitstempel an das Log an.
	 */
	
	//Zeile zum Log hinzufuegen
	public void addToLog(String s){
		log.append("-- " + sdf.format(new Date()) + ": " + s + "\n");
	}
	
	/**
	 * Die Methode ActionPerformed.
	 * Sie behandelt den send-Button und sendet die zu uebergebenen Inhalte an den ChatClient.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "send"){
			srv.send("3,"+name.getText()+","+mess.getText());
		}
	}
	
	// WindowListener --------------------------------------------
		@Override
		public void windowOpened(WindowEvent e) {}

		//Server-Thread beenden, wenn Fenster geschlossen wird
		@Override
		public void windowClosing(WindowEvent e) {
			srv.end();
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

//----------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------

/**
 * Die Klasse ChatInput.
 * Sie behandelt alle eingehenden Inputs vom Spiel an das Chatfenster.
 * @see ClientInput
 */

class ChatInput extends Thread{

	/** Reader fuer den InputStream. */
	
	private BufferedReader in;		//Reader fuer InputStream
	
	/** Die eingelesene Line. */
	
	private String inLine, inLine1 = "0";			//Eingelesene Line
	
	/** Der eingelesene, gesplittete Line-Array. */
	
	private String[] lineSplit;
	
	/** Statusvariablen. */
	
	private boolean running, open;	//Statusvariablen
	
	/** Chatfenster */
	
	private ChatClient cc;
	
	/**
	 * Der Konstruktor fuer die Klasse ChatInput. 
	 */
	
	//Konstruktor
	public ChatInput(ChatClient c){
		super();
		cc = c;
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
							cc.addToLog("["+lineSplit[1]+"] "+lineSplit[2]);
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

//----------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------

/**
 * Die Klasse ChatServer.
 * Dieser Thread baut einen ClientSocket zum Game auf, um Nachrichten zu versenden un zu empfangen.
 */

class ChatServer extends Thread{
	
	/** Socket des Clients. */
	
	private Socket client;				//Socket des Clients
	
	/** Input des Clients. */
	
	private ChatInput in;				//Input
	
	/** Output des Clients. */
	
	private PrintWriter out;			//Output
	
	/** Die ausgegebene Zeile. */
	
	private String outLine;				//Auszugebene Zeile
	
	/** Statusvariablen. */
	
	private boolean running, send;		//Statusvariablen
	
	/** Chatfenster */
	
	private ChatClient cc;
	
	
	/**
	 * Der Konstruktor des Clients, der fuer die Kommunikation zwischen Game und Chatfenster da ist.
	 */
	
	public ChatServer(ChatClient c){
		super();
		cc = c;
	}
	
	/**
	 * Die Methode run.
	 * Diese Methode implementiert alle Funktionen, die waehrend des laufenden Chats benoetigt werden. 
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
					client = new Socket("localhost", 2049);					//Neuen Socket an localhost mit Port 2048
					client.setSoTimeout(5000);								//Timeout für reads=5s
					in = new ChatInput(cc);									//Input-Thread konstruieren
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
		if(client != null){
			try {
				in.end();		//Input-Thread beenden
				out.close();	//Output schließen
				client.close();	//Socket schließen
			} catch (IOException e) {e.printStackTrace();}
			System.exit(0);
		}
	}
	
	/**
	 * Die Methode send.
	 * Diese Methode sendet die Chatdaten in Form von Strings an das Spiel. 
	 * @param s  Die Methode erwartet die Uebergabe eines Strings s 
	 */
	
	//String senden
	public void send(String s){
		outLine = s;
		send = true;
	}
	
	/**
	 * Die Methode end.
	 * Diese Methode beendet den Clienten.
	 */
	
	//Thread beenden
	public void end(){
		running = false;
	}
}