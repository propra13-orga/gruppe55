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
	
	public ArrayList<HashMap<String, ArrayList<String>>> levelRooms = new ArrayList();
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
		HashMap<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();
		levelRooms.add(tempMap);
	}
	
	public void addlevelRoom(HashMap<String, ArrayList<String>> levelRoom,ArrayList<Integer> roomparameter){
		levelRooms.add(levelRoom);
		levelRoomsParameter.add(roomparameter);
	}
	public void addemptylevelRoom(){
		ArrayList<Integer> tempList = new ArrayList<>();
		tempList.add(80);
		tempList.add(70);
		levelRoomsParameter.add(tempList);
		HashMap<String, ArrayList<String>> tempMap = new HashMap<String, ArrayList<String>>();
		levelRooms.add(tempMap);
	}
	public void deletelevelRoom(int index){
		levelRooms.remove(index);
		levelRoomsParameter.remove(index);
	}
	public void setlevelRoom(int index,HashMap<String, ArrayList<String>> newlevelRoom,ArrayList<Integer> newroomparameter){
		levelRooms.set(index, newlevelRoom);
		levelRoomsParameter.set(index, newroomparameter);
	}
	public HashMap<String, ArrayList<String>> getlevelRoom(int index){
		return levelRooms.get(index);
	}
	public ArrayList<Integer> getlevelRoomParameter(int index){
		return levelRoomsParameter.get(index);
	}
	public void removeRoomObject(int index,int xPos,int yPos){
		String xyPos = String.valueOf(xPos) + "," + String.valueOf(yPos);
		levelRooms.get(index).remove(xyPos);
	}
	public void addRoomObject(int index, int xPos, int yPos, int type, ArrayList<String> paramStrList) {
		int xPosGrid, yPosGrid;
		ArrayList<String> parameterList = new ArrayList<String>();
		String xyPos = new String();
		xyPos = String.valueOf(xPos) + "," + String.valueOf(yPos);
		for(String tempStr : paramStrList){
			parameterList.add(tempStr);
		}
		levelRooms.get(index).put(xyPos,parameterList);
	}
/*	
 	public void addRoomObject(int index, int xPos, int yPos, int type, String paramStr) {
		int xPosGrid, yPosGrid;
		ArrayList<Integer> parameterList = new ArrayList<Integer>();
		//xPosGrid = xPos / LevelEditor.imgSize;
		//yPosGrid = yPos / LevelEditor.imgSize;
		String xyPos = new String();
		xyPos = String.valueOf(xPos) + "," + String.valueOf(yPos);
		parameterList.add(type);
		if(type == 1){
			parameterList.add(LevelEditor.wallTexture);
			levelRooms.get(index).put(xyPos, parameterList);
		}else if(type == 0){
			levelRooms.get(index).remove(xyPos);
		}else if(type == 2 || type == 10 || type == 11 || type == 14 || type == 15 || type == 17 || type == 18){
			parameterList.add(LevelEditor.health);
			parameterList.add(LevelEditor.angriff);
			parameterList.add(LevelEditor.verteidigung);
			parameterList.add(LevelEditor.ausdauer);
			parameterList.add(LevelEditor.mana);
			if(type == 2 || type == 14 ||  type == 15 || type == 17 || type == 18){
				parameterList.add(LevelEditor.moveAreaX);
				parameterList.add(LevelEditor.moveAreaY);
				parameterList.add(LevelEditor.schussAchse);
			}
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
		}else if(type == 13){
			parameterList.add(LevelEditor.otherObjectsTextur);
			parameterList.add(LevelEditor.otherObjectsMassive);
			levelRooms.get(index).put(xyPos, parameterList);
		}else{
			levelRooms.get(index).put(xyPos, parameterList);
		}
		
	}
*/	
	public int totalRooms(){
		return levelRooms.size();
	}

}
