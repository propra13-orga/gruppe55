package hhu.propra2013.gruppe55_opengl;

import java.net.*;
import java.io.*;

public class Client extends Thread{
	
	private Socket client;
	private ClientInput in;
	private PrintWriter out;
	private String inLine, outLine;
	private boolean running, send;
	
	public Client(){
		super();
	}
	
	@Override
	public void run(){
		running = true;
		
		while(running){
			if(client == null){
				try {
					client = new Socket("localhost", 2048);
					client.setSoTimeout(5000);
					in = new ClientInput();
					in.setInputStream(client.getInputStream());
					out = new PrintWriter(client.getOutputStream());
				} catch (IOException e) {e.printStackTrace();}
			}
			else if(!in.isOpened()){
				try {
					out.close();
					client.close();
					client = null;
				} catch (IOException e) {e.printStackTrace();}
			}
			else{
				if(send){
					out.println("blubb");
					send = false;
				}
			}
		}
		if(client != null){
			try {
				in.end();
				out.close();
				client.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public void send(String s){
		outLine = s;
		send = true;
	}
	
	public void end(){
		running = false;
	}
}

//----------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------

class ClientInput extends Thread{

	private BufferedReader in;
	private String inLine;
	private boolean running, open;
	
	public ClientInput(){
		super();
	}
	
	@Override
	public void run(){
		running = true;
		
		while(running){
			try {
				if(open && (inLine = in.readLine()) != null){
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
	
	public String getInput(){
		return(inLine);
	}
	
	public boolean isOpened(){
		return(open);
	}
	
	public void end(){
		running = false;
	}
}