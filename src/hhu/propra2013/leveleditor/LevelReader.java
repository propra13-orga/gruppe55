package hhu.propra2013.leveleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Liest eine Datei vom Format:
 * 1,1,1,1,1,1
 * 1,0,0,2,0,1
 * 0,0,0,0,0,1
 * 1,1,1,1,1,1
 * und speichert alles in 2D int Array levelData[Zeilen][Spalten]
 */

public class LevelReader {

	private int[][] levelData;

	LevelReader(File levelFile) throws FileNotFoundException {
		ArrayList<String> linelist = new ArrayList<String>();
		Scanner scan = new Scanner(levelFile);
		// Datei Zeilenweise in linelist speichern
		while (scan.hasNext()) {
			linelist.add(scan.next());
		}
		// ArrayList linelist in String Array umwandeln
		String[] levelDataStr = linelist.toArray(new String[0]);
		String seperator = ",";

		levelData = new int[levelDataStr.length][levelDataStr[0]
				.split(seperator).length];

		for (int i = 0; i < levelDataStr.length; i++) {
			String[] temp = levelDataStr[i].split(seperator);
			for (int j = 0; j < temp.length; j++) {
				// System.out.println(Integer.parseInt(temp[j]));
				levelData[i][j] = Integer.parseInt(temp[j]);
			}
		}

	}

	public int[][] getLevelData() {
		return levelData;
	}
}
