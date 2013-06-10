package hhu.propra2013.leveleditor2;


import hhu.propra2013.gruppe55.Data_Img;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	private LevelEditor mainFrame;
	static int xPosition, yPosition;
	//static ArrayList<ArrayList<Integer>> objectList;
	private HashMap<Point, ArrayList<Integer>> roomData;
	public LevelData levelDataObj = new LevelData();
	//Momentan aktiver Raum
	public int currentRoomIndex = 0;
	private int[][] levelData;
	

	public Image imgWall = (new ImageIcon("img/wall.png")).getImage();
	public Image imgCreature = (new ImageIcon("img/creature.png")).getImage();
	public Image imgPlayer = (new ImageIcon("img/player.png")).getImage();

	public MapWindow(LevelEditor frame) {
		super();
		this.setPreferredSize(new Dimension(LevelEditor.mapLaenge
				* LevelEditor.imgSize, LevelEditor.mapBreite
				* LevelEditor.imgSize));
		this.mainFrame = frame;
		levelData = new int[LevelEditor.mapBreite][LevelEditor.mapLaenge];
		//objectList = new ArrayList<ArrayList<Integer>>();
		roomData = new HashMap<Point, ArrayList<Integer>>();
		for (int i = 0; i < levelData.length; i++) {
			for (int j = 0; j < levelData[0].length; j++) {
				levelData[i][j] = 0;
			}
		}
		//Mouselistener im Paintfeld
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				//x,y Rasterposition in Pixel
				xPosition = (me.getX() / LevelEditor.imgSize)
						* LevelEditor.imgSize;
				yPosition = (me.getY() / LevelEditor.imgSize)
						* LevelEditor.imgSize;
				
				levelDataObj.addRoomObject(currentRoomIndex, xPosition, yPosition, LevelEditor.toolType);
				repaint();
			}
		});

	}

	/*public void addRoomObject(int xPos, int yPos, int type) {
		int xPosGrid, yPosGrid;
		ArrayList<Integer> parameterList = new ArrayList<Integer>();
		xPosGrid = xPos / LevelEditor.imgSize;
		yPosGrid = yPos / LevelEditor.imgSize;
		Point xyPos = new Point(xPos,yPos);
		this.levelData[yPosGrid][xPosGrid] = type;
		parameterList.add(type);
		if(type == 1){
			parameterList.add(LevelEditor.wallTexture);
			System.out.println(LevelEditor.wallTexture);
		}
		roomData.put(xyPos, parameterList);
		//objectList.add(parameterList);
		//parameterListe.set(index, element)
	}*/

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		/*this.setSize(new Dimension(LevelEditor.mapLaenge * LevelEditor.imgSize,
				LevelEditor.mapBreite * LevelEditor.imgSize));
		g2.clearRect(0, 0, LevelEditor.mapLaenge * LevelEditor.imgSize,
				LevelEditor.mapBreite * LevelEditor.imgSize);*/
//		for (int i = 0; i <= levelData.length - 1; i++) {
//			for (int j = 0; j <= levelData[0].length - 1; j++) {
//				if (levelData[i][j] == 1)
//					g2.drawImage(imgWall, j * LevelEditor.imgSize, i
//							* LevelEditor.imgSize, this);
//				else if (levelData[i][j] == 2)
//					g2.drawImage(imgCreature, j * LevelEditor.imgSize, i
//							* LevelEditor.imgSize, this);
//				else if (levelData[i][j] == 3)
//					g2.drawImage(imgPlayer, j * LevelEditor.imgSize, i
//							* LevelEditor.imgSize, this);
//
//			}
//		}

		for(Map.Entry<String, ArrayList<Integer>> entry : levelDataObj.getlevelRoom(currentRoomIndex).entrySet()){
			ArrayList<Integer> tempParameterList = entry.getValue();
			int xPos,yPos;
			String[] tempStr = entry.getKey().split(",");
			xPos = Integer.parseInt(tempStr[0]);
			yPos = Integer.parseInt(tempStr[1]);
			//Wall
			if(tempParameterList.get(0) == 1){
				//Texturparameter
				if(tempParameterList.get(1) == 0)
					g2.drawImage(Data_Img.wall, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 2){
				//Creature painten
				g2.drawImage(Data_Img.creature, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 3){
				//Creature painten
				g2.drawImage(Data_Img.player_f, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 4){
				//Creature painten
				g2.setColor(Color.BLACK);
				g2.drawRect(xPos, yPos, 32, 32);
			}else if(tempParameterList.get(0) == 5){
				//Creature painten
				g2.drawImage(Data_Img.trap, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 6){
				//Creature painten
				g2.drawImage(Data_Img.goal, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 7){
				//Creature painten
				g2.drawImage(Data_Img.potion, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 8){
				//Creature painten
				g2.drawImage(Data_Img.mpotion, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 9){
				//Creature painten
				g2.drawImage(Data_Img.treasure, xPos, yPos, this);
			}else if(tempParameterList.get(0) == 10){
				//Creature painten
				g2.drawImage(Data_Img.shopkeeper, xPos, yPos, this);
			}
		}
	/*	for(int i=0; i<objectList.size(); i++){
			ArrayList<Integer> tempParameterList = objectList.get(i);
			int tempxPos = tempParameterList.get(1)*LevelEditor.imgSize;
			int tempyPos = tempParameterList.get(2)*LevelEditor.imgSize;
			if(tempParameterList.get(0) == 1){
				if(tempParameterList.get(3) == 0)
					g2.drawImage(imgWall, tempxPos, tempyPos, this);
			}
		}*/
	}

	public void setLevelData(int[][] levelData) {
		this.levelData = levelData;
	}

	public int[][] getLevelData() {
		return this.levelData;
	}

	public HashMap<Point, ArrayList<Integer>> getroomData() {
		return this.roomData;
	}

	public void setroomData(HashMap<Point, ArrayList<Integer>> roomData) {
		this.roomData = roomData;
	}

}
