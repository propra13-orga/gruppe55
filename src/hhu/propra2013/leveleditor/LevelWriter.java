package hhu.propra2013.leveleditor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LevelWriter {

	private FileWriter fileWriter;

	LevelWriter(File file) throws IOException {
		fileWriter = new FileWriter(file);
	}

	public void writeToFile(int[][] data) throws IOException {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (j != data[0].length - 1)
					this.fileWriter.write(String.valueOf(data[i][j]) + ",");
				else
					this.fileWriter.write(String.valueOf(data[i][j]));
			}
			// this.bufferedWriter.newLine();
			this.fileWriter.append(System.getProperty("line.separator"));
		}
		this.fileWriter.close();
	}
}
