package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Date;
import java.text.*;

public class Server{

	public static void main(String[] args) {
		Server sv = new Server();
	}

	private ServerFrame sf;
	private ServerInput in1, in2;
	private ServerSocket server;
	private Socket client1, client2;
	private PrintWriter out1, out2;
	private boolean running, started;
	
	//Konstruktor
	public Server(){
		sf = new ServerFrame(this);
		
		try {
			server = new ServerSocket(2048);
			server.setSoTimeout(10000);
			sf.addToLog("Server opened at Port 2048");
		} catch (IOException e) {e.printStackTrace();}
		
		run();
	}
	
	public void run(){
		running = true;
		
		while(running){
			if(client1 == null){
				sf.addToLog("Waiting for Client 1 ...");
				try {
					client1 = server.accept();
					client1.setSoTimeout(3000);
					sf.addToLog("Client 1 connected");
					in1 = new ServerInput(1);
					in1.setInputStream(client1.getInputStream());
					in1.start();
					out1 = new PrintWriter(client1.getOutputStream());
					sf.addToLog("Client 1's Input opened");
				} catch (IOException e) {e.printStackTrace();}
			}
			else if(!in1.getOpened()){
				try {
					out1.close();
					client1.close();
					client1 = null;
				} catch (IOException e) {
					e.printStackTrace();
					sf.addToLog("Client 1 disconnected");
				}
			}
			if(client2 == null){
				sf.addToLog("Waiting for Client 2 ...");
				try {
					client2 = server.accept();
					client2.setSoTimeout(3000);
					sf.addToLog("Client 2 connected");
					in2 = new ServerInput(2);
					in2.setInputStream(client2.getInputStream());
					in2.start();
					out2 = new PrintWriter(client2.getOutputStream());
					sf.addToLog("Client 2's Input opened");
				} catch (IOException e) {e.printStackTrace();}
			}
			else if(!in2.getOpened()){
				try {
					out2.close();
					client2.close();
					client2 = null;
				} catch (IOException e) {
					e.printStackTrace();
					sf.addToLog("Client 2 disconnected");
				}
			}
		}
	}
	
	public void start(){
		running = true;
		run();
	}
	
	public void stop(){
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

class ServerInput extends Thread{
	private int clientID;
	private BufferedReader in;
	private String inLine;
	private boolean running, open;
	
	public ServerInput(int id){
		super();
		
		clientID = id;
	}
	
	@Override
	public void run(){
		running = true;
		
		while(running){
			try {
				if(open && (inLine = in.readLine()) != null){
					System.out.println("Client " + clientID + ": " + inLine);
				}
				else{
					open = false;
					in.close();
				}
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void setInputStream(InputStream is){
		in = new BufferedReader(new InputStreamReader(is));
		open = true;
	}
	
	public boolean getOpened(){
		return(open);
	}
	
	public void end(){
		running = false;
	}
}

//-------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------

class ServerFrame extends JFrame implements ActionListener, WindowListener{
	
	private Server srv;
	private JButton start, end;
	private JTextArea log;
	private JList clients;
	private JPanel toparea, buttons;
	private String logText;
	private SimpleDateFormat sdf = new SimpleDateFormat("HH.mm.ss");
	
	public ServerFrame(Server s){
		srv = s;
		
		setTitle("GameServer");
		setSize(600, 400);
		addWindowListener(this);
		setLayout(new GridLayout(2,1));
		
		toparea = new JPanel();
		buttons = new JPanel();
			buttons.setLayout(new GridLayout(2,1));
		
		clients = new JList();
			clients.setPreferredSize(new Dimension(400,200));
			clients.setBorder(BorderFactory.createTitledBorder("Connected Clients"));
		
		start = new JButton("Start Game");
			start.setPreferredSize(new Dimension(200, 50));
			start.setActionCommand("start");
			start.addActionListener(this);
		end = new JButton("Close Server");
			end.setPreferredSize(new Dimension(200, 50));
			end.setActionCommand("end");
			end.addActionListener(this);
			
		buttons.add(start);
		buttons.add(end);
			
		toparea.add(clients);
		toparea.add(buttons);
			
		log = new JTextArea();
			log.setEditable(false);
			log.setLineWrap(true);
			log.setPreferredSize(new Dimension(600, 200));
			log.setBorder(BorderFactory.createTitledBorder("Serverlog"));
		
		getContentPane().add(toparea);
		getContentPane().add(log);
		logText = "-- " + sdf.format(new Date()) + ": ServerFrame successfully created! \n";
		log.setText(logText);
		
		pack();
		setResizable(false);
		setVisible(true);
	}

	public void addToLog(String s){
		logText += "-- " + sdf.format(new Date()) + ": " + s + "\n";
		log.setText(logText);
	}

	public void addClient(String name, String ip, int port){
		
	}
	
	// ActionListener --------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "start"){
			
		}
		else if(e.getActionCommand() == "end"){
			srv.stop();
		}
	}

	// WindowListener --------------------------------------------
	@Override
	public void windowOpened(WindowEvent e) {}

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