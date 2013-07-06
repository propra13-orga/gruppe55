package hhu.propra2013.gruppe55_opengl;

import java.io.*;

/**
 * Die Klasse Data_String.
 * Die Klasse Data_String dient dazu, die Texte fuer saemtliche Dialoge dynamisch einzulesen.
 */

public class Data_String {
	
// 	Klasse zum statischen Laden der Dialogdateien, damit diese nicht bei jedem Aufruf extra geladen werden müssen	
	
	// Story-Dialog
	static String[] story1 = genDialog("story1");    // 1st one
	
	/**
	 * Die Methode genDialog.
	 * Diese Methode liest die einzelnen Zeilen aus der Textdatei, damit diese dann im Spiel in den Dialogen angezeigt werden koennen.
	 * @param file  Die Methode erwartet die Uebergabe eines Strings File
	 * @return Die auszugebenden Lines werden zurueckgegeben.
	 */
	
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
