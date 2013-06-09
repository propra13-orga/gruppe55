package hhu.propra2013.leveleditor2;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.cfg.MapperConfig;


public class LevelWriter {
	
	private File saveFile;
	private FileWriter fileWriter;
	private ObjectMapper mapper;
	private ObjectWriter writer;

	public LevelWriter(File file) throws IOException {
		this.saveFile = file;
		mapper = new ObjectMapper();
		//für bessere Lesbarkeit
		writer = mapper.writer().withDefaultPrettyPrinter();
	}
	public void writeToFile(LevelData leveldata){
		
		try {
			//System.out.print(mapper.writeValueAsString(objectmap.toString()));
			writer.writeValue(saveFile,leveldata);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public void writeToFile(int[][] data) throws IOException {
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
	}*/
}
