package hhu.propra2013.leveleditor2;


import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Beinhaltet alle Daten eines Levels
 * */



public class LevelData {
	//
	// Beispiel:levelRoom index:0->HashMap (Daten für alle Raumobjekte)
	// ...
	
	public ArrayList<HashMap<String, ArrayList<Integer>>> levelRooms = new ArrayList();
	//
	// Beispiel:levelRoom index:0->laenge:40,Breite:40
	//					  index:1->laenge:50,Breite:60
	public ArrayList<ArrayList<Integer>> levelRoomsParameter = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * @param args
	 */
	public LevelData() {
		// TODO Auto-generated constructor stub
		ArrayList<Integer> tempList = new ArrayList<>();
		tempList.add(30);
		tempList.add(30);
		levelRoomsParameter.add(tempList);
		HashMap<String, ArrayList<Integer>> tempMap = new HashMap<String, ArrayList<Integer>>();
		levelRooms.add(tempMap);
	}
	
	public void addlevelRoom(HashMap<String, ArrayList<Integer>> levelRoom,ArrayList<Integer> roomparameter){
		levelRooms.add(levelRoom);
		levelRoomsParameter.add(roomparameter);
	}
	public void addemptylevelRoom(){
		ArrayList<Integer> tempList = new ArrayList<>();
		tempList.add(80);
		tempList.add(70);
		levelRoomsParameter.add(tempList);
		HashMap<String, ArrayList<Integer>> tempMap = new HashMap<String, ArrayList<Integer>>();
		levelRooms.add(tempMap);
	}
	public void deletelevelRoom(int index){
		levelRooms.remove(index);
		levelRoomsParameter.remove(index);
	}
	public void setlevelRoom(int index,HashMap<String, ArrayList<Integer>> newlevelRoom,ArrayList<Integer> newroomparameter){
		levelRooms.set(index, newlevelRoom);
		levelRoomsParameter.set(index, newroomparameter);
	}
	public HashMap<String, ArrayList<Integer>> getlevelRoom(int index){
		return levelRooms.get(index);
	}
	public ArrayList<Integer> getlevelRoomParameter(int index){
		return levelRoomsParameter.get(index);
	}
	public void addRoomObject(int index, int xPos, int yPos, int type) {
		int xPosGrid, yPosGrid;
		ArrayList<Integer> parameterList = new ArrayList<Integer>();
		//xPosGrid = xPos / LevelEditor.imgSize;
		//yPosGrid = yPos / LevelEditor.imgSize;
		String xyPos = new String();
		xyPos = String.valueOf(xPos) + "," + String.valueOf(yPos);
		parameterList.add(type);
		if(type == 1){
			parameterList.add(LevelEditor.wallTexture);
			System.out.println(LevelEditor.wallTexture);
			levelRooms.get(index).put(xyPos, parameterList);
		}else if(type == 0){
			levelRooms.get(index).remove(xyPos);
		}else if(type == 2){
			parameterList.add(LevelEditor.health);
			parameterList.add(LevelEditor.angriff);
			parameterList.add(LevelEditor.verteidigung);
			parameterList.add(LevelEditor.ausdauer);
			parameterList.add(LevelEditor.mana);
			levelRooms.get(index).put(xyPos, parameterList);
		}else if(type == 3){
			parameterList.add(LevelEditor.health);
			parameterList.add(LevelEditor.angriff);
			parameterList.add(LevelEditor.verteidigung);
			parameterList.add(LevelEditor.ausdauer);
			parameterList.add(LevelEditor.mana);
			parameterList.add(LevelEditor.leben);
			levelRooms.get(index).put(xyPos, parameterList);
		}else if(type == 4){
			parameterList.add(LevelEditor.teleporterRoom);
			parameterList.add(LevelEditor.teleporterRoomxPos);
			parameterList.add(LevelEditor.teleporterRoomyPos);
			levelRooms.get(index).put(xyPos, parameterList);
		}else{
			levelRooms.get(index).put(xyPos, parameterList);
		}
		
	}

}
