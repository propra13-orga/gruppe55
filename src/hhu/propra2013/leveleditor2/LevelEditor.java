package hhu.propra2013.leveleditor2;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class LevelEditor extends JFrame implements ActionListener

{
	/**
	 * 
	 */
	public static int mapLaenge = 21;
	public static int mapBreite = 16;
	final static int imgSize = 32;

	//toolType
	//0: Background    
	//1: Wall
	//2: Creature
	//3: Player
	//4: Teleporter
	//5: Falle
	//6: Ziel
	//7: Potion
	//8: Manapotion
	//9: Schatz
	//10: Shopkeeper
	//11: Storyteller
	//12: Healthcontainer
	//13: andere Objekte
	
	
	//Parameter 
	public static int toolType = 0;
	public static int wallTexture = 0;
	public static int health = 3;
	public static int angriff = 1;
	public static int verteidigung = 0;
	public static int ausdauer = 100;
	public static int mana = 0;
	public static int leben = 3;
	public static int teleporterRoom = 1;
	public static int teleporterRoomxPos = 256;
	public static int teleporterRoomyPos = 128;
	public static int otherObjectsTextur = 1;
	public static int otherObjectsMassive = 0;
	public static int moveAreaX = 0;
	public static int moveAreaY = 0;
	public static int schussAchse = 0;

	private ArrayList<Integer> texturKeyList = new ArrayList<Integer>();

	private JFileChooser fileChooser = new JFileChooser();
	private JButton openLevel = new JButton("Open Level");
	private JButton saveLevel = new JButton("Save Level");
	private JButton wallButton = new JButton("Wall");
	private JButton creatureButton = new JButton("Creature");
	private JButton bowCreatureButton = new JButton("Bow Creature");
	private JButton deleteButton = new JButton("Delete");
	private JButton playerButton = new JButton("Player");
	private JButton teleporterButton = new JButton("Teleporter");
	private JButton falleButton = new JButton("Falle");
	private JButton zielButton = new JButton("Ziel");
	private JButton potionButton = new JButton("Potion");
	private JButton manapotionButton = new JButton("Manapotion");
	private JButton schatzButton = new JButton("Schatz");
	private JButton shopKeeperButton = new JButton("Shopkeeper");
	private JButton storytellerButton = new JButton("Storyteller");
	private JButton healthcountainerButton = new JButton("Healthcontainer");
	private JButton otherObjectsButton = new JButton("andere Objekte");
	private JButton checkpointButton = new JButton("Checkpoint");
	private JButton boss1Button = new JButton("Boss 1");
	private JButton boss2Button = new JButton("Boss 2");
	private JButton boss3Button = new JButton("Boss 3");
	private JButton arrowObjectButton = new JButton("Arrow Object");
	
	
	
	//Raum ausw�hlen ComboBox
	String[] roomSelectStr = {"Room : 1"};
	final DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(roomSelectStr);
	private JComboBox selectRoomBox = new JComboBox(comboBoxModel);
	private JButton loadRoomButton = new JButton("load selected Room");
	private JButton removeRoomButton = new JButton("remove selected Room");
	private JButton addRoomButton = new JButton("add Room");
	private JButton editRoomSizeButton = new JButton("edit Room size");
	public JLabel PosLabel = new JLabel("x: 0 y: 0");

	
	
	//Wall ParameterPanel Komponenten
	private String[] texturenStr = {"Textur1","Textur2"};
	private JComboBox wallTexturBox = new JComboBox(texturenStr);
	
	//otherObjects ParameterPanel Komponenten

	private JComboBox otherObjectsTexturBox = new JComboBox();
	private JCheckBox massiveCheckBox = new JCheckBox("massive: ");
	
	//Creature ParameterPanel Komponenten
	private JTextField healthTxtField = new JTextField("3");
	private JTextField angriffTxtField = new JTextField("1");
	private JTextField verteidigungTxtField = new JTextField("0");
	private JTextField ausdauerTxtField = new JTextField("100");
	private JTextField manaTxtField = new JTextField("0");
	private JTextField lebenTxtField = new JTextField("3");
	private JTextField moveAreaXTxtField = new JTextField("1");
	private JTextField moveAreaYTxtField = new JTextField("1");
	private JTextField schussAchseTxtField = new JTextField("0");
	private JButton setParametersButton = new JButton("set!");
	
	//Teleporter ParameterPanel Komponenten
	private JTextField zielRoomTxtField = new JTextField("1");
	private JTextField zielRoomxPosTxtField = new JTextField("256");
	private JTextField zielRoomyPosTxtField = new JTextField("128");
	


	public MapWindow mapWindow;
	private TitlePanel mapPanel;
	private TitlePanel wallParameterPanel;
	private TitlePanel livingObjectParameterPanel;
	private TitlePanel teleporterParameterPanel;
	private TitlePanel otherObjectsParameterPanel;
	private JPanel topPanel = new JPanel();

	private File levelFile;
	private File levelSaveFile;

	public LevelEditor() {
		super("Level Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		openLevel.addActionListener(this);
		saveLevel.addActionListener(this);
		wallButton.addActionListener(this);
		creatureButton.addActionListener(this);
		bowCreatureButton.addActionListener(this);
		deleteButton.addActionListener(this);
		playerButton.addActionListener(this);
		teleporterButton.addActionListener(this);
		falleButton.addActionListener(this);
		zielButton.addActionListener(this);
		potionButton.addActionListener(this);
		manapotionButton.addActionListener(this);
		schatzButton.addActionListener(this);
		shopKeeperButton.addActionListener(this);
		storytellerButton.addActionListener(this);
		healthcountainerButton.addActionListener(this);
		otherObjectsButton.addActionListener(this);
		checkpointButton.addActionListener(this);
		boss1Button.addActionListener(this);
		boss2Button.addActionListener(this);
		boss3Button.addActionListener(this);
		arrowObjectButton.addActionListener(this);

		// wallButton.setIcon(MapWindow.imgWall);
		JPanel toolsBarPanel = new JPanel();
		toolsBarPanel.setLayout(new GridLayout(22, 1));
		toolsBarPanel.add(this.openLevel);
		toolsBarPanel.add(this.saveLevel);
		toolsBarPanel.add(this.wallButton);
		toolsBarPanel.add(this.deleteButton);
		toolsBarPanel.add(this.creatureButton);
		toolsBarPanel.add(this.bowCreatureButton);
		toolsBarPanel.add(this.playerButton);
		toolsBarPanel.add(this.teleporterButton);
		toolsBarPanel.add(this.falleButton);
		toolsBarPanel.add(this.zielButton);
		toolsBarPanel.add(this.potionButton);
		toolsBarPanel.add(this.manapotionButton);
		toolsBarPanel.add(this.schatzButton);
		toolsBarPanel.add(this.shopKeeperButton);
		toolsBarPanel.add(this.storytellerButton);
		toolsBarPanel.add(this.healthcountainerButton);
		toolsBarPanel.add(this.otherObjectsButton);
		toolsBarPanel.add(this.checkpointButton);
		toolsBarPanel.add(this.boss1Button);
		toolsBarPanel.add(this.boss2Button);
		toolsBarPanel.add(this.boss3Button);
		toolsBarPanel.add(this.arrowObjectButton);
		
		//Wall Parameter: type(1),wallTexture
		//WallObject(int x, int y,int textur)
		wallParameterPanel = new TitlePanel("Parameter:");		
		wallTexturBox.addActionListener(this);
		wallParameterPanel.add(new JLabel("Textur:"));
		wallParameterPanel.add(wallTexturBox);
		wallParameterPanel.setVisible(false);
		
		//Creature Parameter: type(2),health,Angriff,Verteidigung,Ausgauer,Mana
		//Creature(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man)
		//Player Parameter: type(3),health,Angriff,Verteidigung,Energie,Mana,Leben
		//Player(int spawnX, int spawnY, int h, int atk, int def, int energy, int mana, int l)
		livingObjectParameterPanel = new TitlePanel(" Parameter: ");
		livingObjectParameterPanel.add(new JLabel(" Health: "));
		livingObjectParameterPanel.add(healthTxtField);
		livingObjectParameterPanel.add(new JLabel(" Angriff: "));
		livingObjectParameterPanel.add(angriffTxtField);
		livingObjectParameterPanel.add(new JLabel(" Verteidigung: "));
		livingObjectParameterPanel.add(verteidigungTxtField);
		livingObjectParameterPanel.add(new JLabel(" Ausdauer: "));
		livingObjectParameterPanel.add(ausdauerTxtField);
		livingObjectParameterPanel.add(new JLabel(" Mana: "));
		livingObjectParameterPanel.add(manaTxtField);
		livingObjectParameterPanel.add(new JLabel(" Leben(nur f�r Player): "));
		livingObjectParameterPanel.add(lebenTxtField);
		livingObjectParameterPanel.add(new JLabel(" move Area X: "));
		livingObjectParameterPanel.add(moveAreaXTxtField);
		livingObjectParameterPanel.add(new JLabel(" move Area Y: "));
		livingObjectParameterPanel.add(moveAreaYTxtField);
		livingObjectParameterPanel.add(new JLabel(" schuss Achse: "));
		livingObjectParameterPanel.add(schussAchseTxtField);
		
		setParametersButton.addActionListener(this);
		livingObjectParameterPanel.setVisible(false);
		
		
		//Teleporter Parameter: type(4),Zielraum,ZielX,ZielY
		//Teleporter(int x, int y, int room, int dx, int dy)
		teleporterParameterPanel = new TitlePanel(" Parameter: ");
		teleporterParameterPanel.add(new JLabel(" Ziel Raum: "));
		teleporterParameterPanel.add(zielRoomTxtField);
		teleporterParameterPanel.add(new JLabel(" Ziel Raum xPosition: "));
		teleporterParameterPanel.add(zielRoomxPosTxtField);
		teleporterParameterPanel.add(new JLabel(" Ziel Raum yPosition: "));
		teleporterParameterPanel.add(zielRoomyPosTxtField);
		
		//andere Objekte Parameter
		otherObjectsParameterPanel = new TitlePanel(" Parameter: ");
		otherObjectsParameterPanel.add(otherObjectsTexturBox);
		otherObjectsParameterPanel.add(massiveCheckBox);
		
		
		//TrapObject Parameter : type(5)
		//TrapObject(int x, int y)
		
		//GoalObject Parameter: type(6)
		//GoalObject(int x, int y)
		
		//PotionObject Parameter: type(7)
		//PotionObject(int x, int y)

		//MPotionObject Parameter: type(8)
		//MPotionObject(int x, int y)
		
		//TreasureObject Parameter: type(9)
		//TreasureObject(int x, int y)
		
		//Shopkeeper Parameter: type(10),health,Angriff,Verteidigung,Asudauer,Mana
		//Shopkeeper(int spawnX, int spawnY, int h, int angr, int vert, int ausd, int man)

		//der obere Teil vom Layout
		selectRoomBox.addActionListener(this);
		removeRoomButton.addActionListener(this);
		addRoomButton.addActionListener(this);
		loadRoomButton.addActionListener(this);
		editRoomSizeButton.addActionListener(this);
		topPanel.add(PosLabel);
		topPanel.add(selectRoomBox);
		topPanel.add(loadRoomButton);
		topPanel.add(removeRoomButton);
		topPanel.add(addRoomButton);
		topPanel.add(editRoomSizeButton);
		
		
		
		this.setPreferredSize(new Dimension(mapLaenge*imgSize+140,mapBreite*imgSize+100));
		
		wallParameterPanel.setPreferredSize(new Dimension(200,60));
		livingObjectParameterPanel.setPreferredSize(new Dimension(200,90));
		teleporterParameterPanel.setPreferredSize(new Dimension(200,60));
		otherObjectsParameterPanel.setPreferredSize(new Dimension(200,60));
		
		
		mapPanel = new TitlePanel("map:");		
		mapWindow = new MapWindow(this);
		JScrollPane scrollPane = new JScrollPane(mapWindow);
		scrollPane.setPreferredSize(new Dimension(mapLaenge*imgSize,mapBreite*imgSize));
		mapPanel.setLayout(new BorderLayout());
		mapPanel.add(scrollPane);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(mapPanel, BorderLayout.WEST);
		this.add(toolsBarPanel, BorderLayout.EAST);
		

		mapWindow.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				PosLabel.setText("x: " + mapWindow.xPosition + " y: " + mapWindow.yPosition);
				topPanel.updateUI();
				repaint();
			}
		});
		
		
		this.pack();
		this.setVisible(true);
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//seperater Thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LevelEditor levelEditor = new LevelEditor();
				
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == this.loadRoomButton){
			mapWindow.currentRoomIndex = selectRoomBox.getSelectedIndex();
			mapLaenge = mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).get(0);
			mapBreite = mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).get(1);
			mapWindow.setPreferredSize(new Dimension(mapLaenge * imgSize, mapBreite * imgSize));

		}
		else if (ae.getSource() == this.addRoomButton){
			String tempStr = "Room : " + (selectRoomBox.getItemCount()+1);
			comboBoxModel.addElement(tempStr);
			mapWindow.levelDataObj.addemptylevelRoom();
		}
		else if (ae.getSource() == this.removeRoomButton){
			comboBoxModel.removeElementAt(selectRoomBox.getSelectedIndex());
			mapWindow.levelDataObj.deletelevelRoom(selectRoomBox.getSelectedIndex());
		}
		else if (ae.getSource() == this.editRoomSizeButton){
			JTextField laenge = new JTextField();
            JTextField breite = new JTextField();
			laenge.setText(String.valueOf(mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).get(0)));
			breite.setText(String.valueOf(mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).get(1)));
            Object[] message = {"L�nge", laenge, 
                    "Breite", breite};

            JOptionPane pane = new JOptionPane( message, 
                                            JOptionPane.PLAIN_MESSAGE, 
                                            JOptionPane.OK_CANCEL_OPTION);
            pane.createDialog(null, "Raum Gr��e").setVisible(true);
            mapLaenge = Integer.parseInt(laenge.getText());
            mapBreite = Integer.parseInt(breite.getText());
            mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).set(0, mapLaenge);
            mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).set(1, mapBreite);
 			mapWindow.setPreferredSize(new Dimension(mapLaenge * imgSize, mapBreite * imgSize));
			
		}
		else if (ae.getSource() == this.wallTexturBox){
			JComboBox temp = (JComboBox)ae.getSource();
			wallTexture = temp.getSelectedIndex();
		}
		else if (ae.getSource() == this.otherObjectsButton){
			toolType = 13;
			this.remove(wallParameterPanel);
			this.remove(teleporterParameterPanel);
			this.remove(livingObjectParameterPanel);
			otherObjectsParameterPanel.add(setParametersButton);
			this.add(otherObjectsParameterPanel, BorderLayout.SOUTH);	
			String fileName;
			
			  File folder = new File("img/textures");
			  File[] listOfFiles = folder.listFiles(); 
			  //reset nach Button wechsel
			  otherObjectsTexturBox.removeAllItems();
			  texturKeyList.clear();
			  for (int i = 0; i < listOfFiles.length; i++) 
			  {			 
				  if (listOfFiles[i].isFile()) 
				  {
					  fileName = listOfFiles[i].getName();
					  // \\. und nicht . da . spezielles regex Zeichen ist
					  String[] tempString = fileName.split("\\.",-1);
					  texturKeyList.add(Integer.parseInt(tempString[0]));
					  otherObjectsTexturBox.addItem(tempString[0]);
				  }
			  }
				teleporterParameterPanel.setVisible(false);
				wallParameterPanel.setVisible(false);
				livingObjectParameterPanel.setVisible(false);
				otherObjectsParameterPanel.setVisible(true);
		}
		else if (ae.getSource() == this.wallButton){
			toolType = 1;
			this.remove(livingObjectParameterPanel);
			this.remove(teleporterParameterPanel);
			this.remove(otherObjectsParameterPanel);
			this.add(wallParameterPanel, BorderLayout.SOUTH);
			teleporterParameterPanel.setVisible(false);
			wallParameterPanel.setVisible(true);
			livingObjectParameterPanel.setVisible(false);
			otherObjectsParameterPanel.setVisible(false);
		}
		else if (ae.getSource() == this.deleteButton)
		{
			toolType = 0;
			wallParameterPanel.setVisible(false);
			livingObjectParameterPanel.setVisible(false);
			otherObjectsParameterPanel.setVisible(false);
		}
		else if (ae.getSource() == this.creatureButton || ae.getSource() == this.bowCreatureButton || ae.getSource() == this.shopKeeperButton || ae.getSource() == this.storytellerButton || ae.getSource() == this.boss1Button || ae.getSource() == this.boss2Button || ae.getSource() == this.boss3Button){
			if(ae.getSource() == this.creatureButton)
				toolType = 2;
			else if(ae.getSource() == this.shopKeeperButton)
				toolType = 10;
			else if(ae.getSource() == this.bowCreatureButton)
				toolType = 14;
			else if(ae.getSource() == this.boss1Button)
				toolType = 15;
			else if(ae.getSource() == this.boss2Button)
				toolType = 17;
			else if(ae.getSource() == this.boss3Button)
				toolType = 18;
			else 
				toolType = 11;
			teleporterParameterPanel.remove(setParametersButton);
			livingObjectParameterPanel.add(setParametersButton);
			this.remove(wallParameterPanel);
			this.remove(otherObjectsParameterPanel);
			this.remove(teleporterParameterPanel);
			this.add(livingObjectParameterPanel, BorderLayout.SOUTH);	
			teleporterParameterPanel.setVisible(false);
			livingObjectParameterPanel.setVisible(true);
			wallParameterPanel.setVisible(false);
			otherObjectsParameterPanel.setVisible(false);
		}
		else if (ae.getSource() == this.teleporterButton){
			toolType = 4;
			livingObjectParameterPanel.remove(setParametersButton);
			teleporterParameterPanel.add(setParametersButton);
			this.remove(wallParameterPanel);
			this.remove(livingObjectParameterPanel);
			this.remove(otherObjectsParameterPanel);
			this.add(teleporterParameterPanel, BorderLayout.SOUTH);	
			teleporterParameterPanel.setVisible(true);
			livingObjectParameterPanel.setVisible(false);
			wallParameterPanel.setVisible(false);
			otherObjectsParameterPanel.setVisible(false);
		}
		else if (ae.getSource() == this.setParametersButton){
			if(toolType == 2 || toolType == 3 || toolType == 10 || toolType == 14 || toolType == 15 || toolType == 17 || toolType == 18){
				health = Integer.parseInt(healthTxtField.getText());
				angriff = Integer.parseInt(angriffTxtField.getText());
				verteidigung = Integer.parseInt(verteidigungTxtField.getText());
				ausdauer = Integer.parseInt(ausdauerTxtField.getText());
				mana = Integer.parseInt(manaTxtField.getText());
				leben = Integer.parseInt(lebenTxtField.getText());
				moveAreaX = Integer.parseInt(moveAreaXTxtField.getText());
				moveAreaY = Integer.parseInt(moveAreaYTxtField.getText());
				schussAchse = Integer.parseInt(schussAchseTxtField.getText());
			}else if(toolType == 4){
				teleporterRoom = Integer.parseInt(zielRoomTxtField.getText());
				teleporterRoomxPos = Integer.parseInt(zielRoomxPosTxtField.getText());
				teleporterRoomyPos = Integer.parseInt(zielRoomyPosTxtField.getText());
				
			}else if(toolType == 13){
				int tempIndex = otherObjectsTexturBox.getSelectedIndex();	
				otherObjectsTextur = texturKeyList.get(tempIndex);
				if(massiveCheckBox.isSelected())
					otherObjectsMassive = 1;
				else 
					otherObjectsMassive = 0;
				
				System.out.println(otherObjectsMassive + " " + otherObjectsTextur);
			}
		}
		else if (ae.getSource() == this.playerButton){
			toolType = 3;
			teleporterParameterPanel.remove(setParametersButton);
			livingObjectParameterPanel.add(setParametersButton);
			this.remove(wallParameterPanel);
			this.remove(teleporterParameterPanel);
			this.remove(otherObjectsParameterPanel);
			this.add(livingObjectParameterPanel, BorderLayout.SOUTH);		
			teleporterParameterPanel.setVisible(false);
			livingObjectParameterPanel.setVisible(true);
			wallParameterPanel.setVisible(false);	
			otherObjectsParameterPanel.setVisible(false);
		}else if (ae.getSource() == this.falleButton){
			toolType = 5;
		}else if (ae.getSource() == this.zielButton){
			toolType = 6;
		}else if (ae.getSource() == this.potionButton){
			toolType = 7;
		}else if (ae.getSource() == this.manapotionButton){
			toolType = 8;
		}else if (ae.getSource() == this.schatzButton){
			toolType = 9;
		}else if (ae.getSource() == this.healthcountainerButton){
			toolType = 12;
		}else if (ae.getSource() == this.checkpointButton){
			toolType = 16;
		}else if (ae.getSource() == this.arrowObjectButton){
			toolType = 19;
		}
		else if (ae.getSource() == this.openLevel) {
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.levelFile = fileChooser.getSelectedFile();
				try {
					//LevelReader levelReader = new LevelReader(this.levelFile);
					LevelReader levelReader = new LevelReader(this.levelFile);
					// lade Leveldaten aus Datei und update levelDataObj in
					// mapWindow
					mapWindow.levelDataObj = levelReader.getLevelData();
					//Room Combobox neu f�llen
					comboBoxModel.removeAllElements();
					for(int i=0; i<mapWindow.levelDataObj.totalRooms(); i++){
						String tempStr = "Room : " + String.valueOf(i+1);
						comboBoxModel.addElement(tempStr);
					}
					mapWindow.currentRoomIndex = 0;	
					mapLaenge = mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).get(0);
					mapBreite = mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).get(1);
					mapWindow.setPreferredSize(new Dimension(mapLaenge * imgSize, mapBreite * imgSize));
					mapPanel.updateUI();
					mapWindow.updateUI();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Canceled");
			}
		} else if (ae.getSource() == this.saveLevel) {
			int returnVal = fileChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File saveFile = fileChooser.getSelectedFile();
				LevelWriter levelWriter ;
				try {
					levelWriter = new LevelWriter(saveFile);
					levelWriter.writeToFile(mapWindow.levelDataObj);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		mapPanel.updateUI();
		mapWindow.updateUI();

	}

}