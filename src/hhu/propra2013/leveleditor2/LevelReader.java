package hhu.propra2013.leveleditor2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LevelReader {

	private ObjectMapper mapper;
	private LevelData levelobj;
	JsonFactory factory = new JsonFactory(); 

	
	public LevelReader(File levelFile) throws FileNotFoundException {
		try {
			mapper = new ObjectMapper();
			TypeReference<LevelData> typeRef  = new TypeReference<LevelData>() {}; 
			this.levelobj = mapper.readValue(levelFile, typeRef);
		} catch (JsonParseException e) {
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

	public LevelData getLevelData() {
		return levelobj;
	}
}
