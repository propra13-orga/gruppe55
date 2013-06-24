package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class NetworkClient implements ActionListener{
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	private JFrame dialog;
	private JTextField text;
	private JButton send, connect, end;
	
	
	//Main-Methode
	public static void main(String[] args) {
		NetworkClient nwc = new NetworkClient();	// Network-Client starten
	}
	
	//Konstruktor
	public NetworkClient(){
		//1. Verbindungsversuch
		connect();
		
		//Fenster bauen
		dialog = new JFrame("Chat");
			dialog.setSize(300, 200);
			dialog.setResizable(false);
			dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dialog.setLayout(new GridLayout(4,1));
		text = new JTextField();
		send = new JButton("Send");
			send.addActionListener(this);
		connect = new JButton("Conenct to Game");
			connect.addActionListener(this);
		end = new JButton("Beenden");
			end.addActionListener(this);
		dialog.getContentPane().add(text);
		dialog.getContentPane().add(send);
		dialog.getContentPane().add(connect);
		dialog.getContentPane().add(end);
		dialog.setVisible(true);
	}

	//Zu einem Spiel verbinden
	public void connect(){
		try {
			client = new Socket("localhost", 2048);										//Socket erstellen mit lokaler Verbindung zu Port 2048
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));	//BufferedReader, um Verbindung zu lesen
			out = new PrintWriter(client.getOutputStream(), true);						//Printwriter, um Output zu schreiben
		} catch (IOException e) {
			System.out.println("Connecting to 'localhost' at Port 2048 failed!");
		}
		System.out.println("Connecting to'localhost' Port 2048 successfully!");
	}
	
	//String an Spiel senden
	public void send(String line) {
		if(client.isConnected() && !client.isClosed() && in != null){
			out.println(line);
		}
	}	
	
	//Verbindung trennen
	public void disconnect(){
		try {
			if(client != null){
				in.close();
				out.close();
				client.close();
				dialog.dispose();
			}
		} catch (IOException e) {
			System.out.println("Closing the client-stuff failed!");
			e.printStackTrace();
		}
	}
	
	//ButtonListener
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Send"){
			if(text.getText() != null){
				send(text.getText());
			}
		}
		else if(e.getActionCommand() == "Beenden"){
			disconnect();
		}
		else if(e.getActionCommand() == "Conenct to Game"){
			connect();
		}
	}
}