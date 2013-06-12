package hhu.propra2013.gruppe55;

import java.io.*;

public class Data_String {
	
// 	Klasse zum statischen Laden der Dialogdateien, damit diese nicht bei jedem Aufruf extra geladen werden müssen	
	
	// Example-Dialog
	// static String[] example = genDialog("example");		// der Spieler von vorne
	
	// Methode zum generieren der Dialog String-Arrays aus .txt
	private static String[] genDialog(String file){
		String[] placeholder = {};
		String line;
		int i = 0;
		
		try{
			FileReader fReader = new FileReader("dialogs/"+file+".txt");
			BufferedReader bReader = new BufferedReader(fReader);
			
			for(i=0; (line = bReader.readLine()) != null; i++){
				if(i == 0){
					placeholder = new String[Integer.parseInt(line)];
				}
				else{
					placeholder[i-1] = line;
				}
			}
			bReader.close();
		}catch (IOException e){e.printStackTrace();}
		
		return(placeholder);
	}
}