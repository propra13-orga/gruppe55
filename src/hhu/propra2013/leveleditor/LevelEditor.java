package hhu.propra2013.leveleditor;


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
	
	private ArrayList<String> guiObjectList = new ArrayList<>();

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
	public static int currentCat = 0;

	private ArrayList<Integer> texturKeyList = new ArrayList<Integer>();

	private JFileChooser fileChooser = new JFileChooser();
	private JButton openLevel = new JButton("Open Level");
	private JButton saveLevel = new JButton("Save Level");
	private JButton deleteButton = new JButton("Delete");

	
	
	
	//Raum auswählen ComboBox
	String[] roomSelectStr = {"Room : 1"};
	final DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(roomSelectStr);
	private JComboBox selectRoomBox = new JComboBox(comboBoxModel);
	private JButton loadRoomButton = new JButton("load selected Room");
	private JButton removeRoomButton = new JButton("remove selected Room");
	private JButton addRoomButton = new JButton("add Room");
	private JButton editRoomSizeButton = new JButton("edit Room size");
	public JLabel PosLabel = new JLabel("x: 0 y: 0");

	
	


	public MapWindow mapWindow;
	private TitlePanel mapPanel;
	private TitlePanel otherObjectsParameterPanel;
	private TitlePanel toolsPanel = new TitlePanel("Tools:");
	private JPanel topPanel = new JPanel();

	private File levelFile;
	private File levelSaveFile;
	
	
	ArrayList<JButton> guiObjectButtons = new ArrayList<>();
	ArrayList<Integer> guiObjectKategorie = new ArrayList<>();
	ArrayList<TitlePanel> guiParameterPanels = new ArrayList<>();
	ArrayList<ArrayList<JLabel>> guiObjectParameterLabels = new ArrayList<>();
	ArrayList<ArrayList<JTextField>> guiObjectParameterTextfields = new ArrayList<ArrayList<JTextField>>();
	ArrayList<ArrayList<JComboBox>> guiObjectParameterComboboxes = new ArrayList<ArrayList<JComboBox>>();
	ArrayList<String> currentParameterList = new ArrayList<>();
	String currentParameterString = "";
	int toolTypeMapper[];
	
	private String[] strKategorien = {"Level-Umgebung","Creatures","Nutzbare Items"};
	private JComboBox kategorienBox = new JComboBox(strKategorien);
	private JButton setParamsButton = new JButton("Set!");
	//placeholder dafür da um die Arraylisten synchron zu halten
	private JLabel placeHolderLabel = new JLabel();
	private JTextField placeHolderTextfield = new JTextField();
	private TitlePanel placeHolderPanel = new TitlePanel("");
	private JComboBox placeHolderComboBox = new JComboBox();
	private JButton PlaceHolderButton = new JButton("");
	

	public LevelEditor() {
		super("Level Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//String der Form:
		//<toolType>:<LabelText>:<KategorieID>:<Parameter1Label>,<Typ(String oder ComboBox)>,<initValue>:<Parameter2Label>,...
		//ComboBox parameter werden immer nach den Textfeldparametern in die Json-Datei geschrieben
		//<KategorieID> zB. 1=Level-Umgebung,2=LivingCreatures
		guiObjectList.add("1:Wall:1:Textur,ComboBox,texture1 texture2");
		guiObjectList.add("2:Creature:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0");
		guiObjectList.add("3:Player:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0:Ausdauer ,String,100:Mana ,String,0:Leben ,String,3");
		guiObjectList.add("4:Teleporter:1:Zielraum ,String,1:Zielraum xPos ,String,128:Zielraum yPos ,String,256");
		guiObjectList.add("5:Falle:1");
		guiObjectList.add("6:Ziel:1");
		guiObjectList.add("7:Potion:3");
		guiObjectList.add("8:Mana Potion:3");
		guiObjectList.add("9:Schatz:3");
		guiObjectList.add("10:Shopkeeper:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0");
		guiObjectList.add("11:Storyteller:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0");
		guiObjectList.add("12:Healthcontainer:3");
		guiObjectList.add("13:SimpleStaticObject:1:Massiv,ComboBox,0 1:Textur,ComboBox,0 1 2 3 4");
		guiObjectList.add("14:Bow Creature:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0:Ausdauer ,String,100:Mana ,String,0:moveAreaX ,String,0:moveAreaY ,String,0:schussAchse ,String,0");
		guiObjectList.add("15:Boss1:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0");
		guiObjectList.add("16:Checkpoint:1");
		guiObjectList.add("17:Boss2:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0:Ausdauer ,String,100:Mana ,String,0:moveAreaX ,String,0:moveAreaY ,String,0");
		guiObjectList.add("18:Boss3:2:Health ,String,3:Angriff ,String,1:Verteidigung ,String,0");
		guiObjectList.add("19:Arrow Object:3");
		guiObjectList.add("20:Gate:1:Trigger ,String,Bezeichner (auch mehrererere)");
		guiObjectList.add("21:Torch:1:Trigger ,String,Bezeichner");
		guiObjectList.add("22:Fire Elemental:2:Health ,String,15:Angriff ,String,1:Verteidigung ,String,0");
		guiObjectList.add("23:Switch:1:Trigger ,String,Bezeichner");
		guiObjectList.add("24:LavaHat:3");
		guiObjectList.add("25:LavaPatch:1");


		
		
		setParamsButton.addActionListener(this);
		placeHolderLabel.setVisible(false);
		placeHolderTextfield.setVisible(false);
		placeHolderComboBox.setVisible(false);
		PlaceHolderButton.setVisible(false);
		
		//init
		currentParameterList.add("0");
		//toolTypeMapper ordnet den Buttons aus der Arraylist den richtigen tooltype zu
		toolTypeMapper = new int[guiObjectList.size()];
		int i = 0;
		for(String guiString : guiObjectList){
			guiObjectParameterLabels.add(new ArrayList<JLabel>());
			guiObjectParameterTextfields.add(new ArrayList<JTextField>());
			guiObjectParameterComboboxes.add(new ArrayList<JComboBox>());
			String[] objectDataString = guiString.split(":");
			guiObjectButtons.add(new JButton(objectDataString[1]));
			guiObjectKategorie.add(Integer.parseInt(objectDataString[2]));
			guiParameterPanels.add(new TitlePanel(objectDataString[1]+" Parameter:"));
			guiParameterPanels.get(i).setVisible(false);
			guiObjectButtons.get(i).addActionListener(this);
			toolTypeMapper[i] = Integer.parseInt(objectDataString[0]);
			int j=0,k=0;
			for(String paramString : objectDataString){
				String[] paramData = paramString.split(",");
				System.out.println(i);
				//wenn Parameter vorhanden
				if(paramData.length > 1){	
					System.out.println(i);
					if(paramData[1].equals("String")){
						guiObjectParameterLabels.get(i).add(new JLabel(paramData[0]));
						guiObjectParameterTextfields.get(i).add(new JTextField(paramData[2]));		
						guiObjectParameterComboboxes.get(i).add(placeHolderComboBox);
						guiParameterPanels.get(i).add(guiObjectParameterLabels.get(i).get(j));
						guiParameterPanels.get(i).add(guiObjectParameterTextfields.get(i).get(j));
						j++;
					}else if(paramData[1].equals("ComboBox")){
						guiObjectParameterLabels.get(i).add(new JLabel(paramData[0]));
						guiObjectParameterTextfields.get(i).add(placeHolderTextfield);	
						guiObjectParameterComboboxes.get(i).add(new JComboBox());
						String[] tempComboBoxStr = paramData[2].split(" ");
						for(int l=0;l<tempComboBoxStr.length;l++){
							guiObjectParameterComboboxes.get(i).get(j).addItem(tempComboBoxStr[l]);
						}
						guiParameterPanels.get(i).add(guiObjectParameterLabels.get(i).get(j));
						guiParameterPanels.get(i).add(guiObjectParameterComboboxes.get(i).get(j));
						j++;
					}
					
					
				}	
				
				
				
			}
			guiParameterPanels.get(i).add(PlaceHolderButton);
			i++;
		}

		openLevel.addActionListener(this);
		saveLevel.addActionListener(this);
		deleteButton.addActionListener(this);


		toolsPanel.setLayout(new GridLayout(18, 1));
		kategorienBox.addActionListener(this);
		toolsPanel.add(kategorienBox);
		
		JPanel toolsBarPanel = new JPanel();
		toolsBarPanel.setLayout(new GridLayout(12, 1));
		toolsBarPanel.add(this.openLevel);
		toolsBarPanel.add(this.saveLevel);
		toolsBarPanel.add(this.deleteButton);
		

		//andere Objekte Parameter
		/*
		otherObjectsParameterPanel = new TitlePanel(" Parameter: ");
		otherObjectsParameterPanel.add(otherObjectsTexturBox);
		otherObjectsParameterPanel.add(massiveCheckBox);
		*/
		
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
		

		//otherObjectsParameterPanel.setPreferredSize(new Dimension(200,60));
		
		
		mapPanel = new TitlePanel("map:");		
		mapWindow = new MapWindow(this);
		JScrollPane scrollPane = new JScrollPane(mapWindow);
		scrollPane.setPreferredSize(new Dimension(mapLaenge*imgSize,mapBreite*imgSize));
		mapPanel.setLayout(new BorderLayout());
		mapPanel.add(scrollPane);
		this.add(toolsPanel, BorderLayout.WEST);
		this.add(topPanel, BorderLayout.NORTH);
		this.add(mapPanel, BorderLayout.CENTER);
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
		if (ae.getSource() == this.kategorienBox){
			JComboBox temp = (JComboBox)ae.getSource();
			currentCat = temp.getSelectedIndex()+1;
			for(int i=0;i<guiObjectKategorie.size();i++){
				if(guiObjectKategorie.get(i) == currentCat){
					toolsPanel.add(guiObjectButtons.get(i));
				}else if(ae.getSource() != this.setParamsButton){
					toolsPanel.remove(guiObjectButtons.get(i));
				}
			}
		}else if(ae.getSource() == this.setParamsButton){
			System.out.println("clicked");
			for(int i=0;i<toolTypeMapper.length;i++){
				if(toolTypeMapper[i] == toolType){
					System.out.println("tooltype ok");
					currentParameterList.clear();
					currentParameterList.add(String.valueOf(toolType));					
					for(JTextField tempField : guiObjectParameterTextfields.get(i)){
						if(!tempField.getText().isEmpty())
							currentParameterList.add(tempField.getText());
					}
					if(!guiObjectParameterComboboxes.get(i).isEmpty()){
						for(JComboBox tempBox : guiObjectParameterComboboxes.get(i)){
							if(tempBox.getItemCount() >= 1){
								currentParameterList.add(tempBox.getSelectedItem().toString());
								System.out.println(tempBox.getSelectedItem().toString());
							}
						}
					}
				}
			}
			//System.out.println(currentParameterList);
		}
		int iterator=0;
		for(JButton tempButton : guiObjectButtons){
			if (ae.getSource() == tempButton){
				toolType = toolTypeMapper[iterator];
				System.out.println(tempButton.getText());
				if(guiParameterPanels.get(iterator).getComponentCount() >= 1)
					guiParameterPanels.get(iterator).add(setParamsButton);
				guiParameterPanels.get(iterator).setVisible(true);
				this.add(guiParameterPanels.get(iterator), BorderLayout.SOUTH);
				currentParameterList.clear();
				currentParameterList.add(String.valueOf(toolType));	
				for(JTextField tempField : guiObjectParameterTextfields.get(iterator)){
					currentParameterList.add(tempField.getText());
				}
			}else{
				guiParameterPanels.get(iterator).setVisible(false);
				this.remove(guiParameterPanels.get(iterator));
			}
			iterator++;
		}
		
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
            Object[] message = {"Länge", laenge, 
                    "Breite", breite};

            JOptionPane pane = new JOptionPane( message, 
                                            JOptionPane.PLAIN_MESSAGE, 
                                            JOptionPane.OK_CANCEL_OPTION);
            pane.createDialog(null, "Raum Größe").setVisible(true);
            mapLaenge = Integer.parseInt(laenge.getText());
            mapBreite = Integer.parseInt(breite.getText());
            mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).set(0, mapLaenge);
            mapWindow.levelDataObj.getlevelRoomParameter(mapWindow.currentRoomIndex).set(1, mapBreite);
 			mapWindow.setPreferredSize(new Dimension(mapLaenge * imgSize, mapBreite * imgSize));
			
		}/*
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
		}*/
		else if (ae.getSource() == this.deleteButton)
		{
			toolType = 0;
			//otherObjectsParameterPanel.setVisible(false);
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
					//Room Combobox neu füllen
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
