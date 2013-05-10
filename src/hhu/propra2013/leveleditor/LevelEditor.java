package hhu.propra2013.leveleditor;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LevelEditor extends JFrame implements ActionListener

{
	/**
	 * 
	 */
	public static int mapLaenge = 20;
	public static int mapBreite = 10;
	final static int imgSize = 32;

	public static int toolType = 0;

	public JFileChooser fileChooser = new JFileChooser();
	public JButton openLevel = new JButton("Open Level");
	public JButton saveLevel = new JButton("Save Level");
	public JButton wallButton = new JButton("Wall");
	public JButton creatureButton = new JButton("Creature");
	public JButton deleteButton = new JButton("Delete");
	public JButton playerButton = new JButton("Player");

	public MapWindow mapWindow;
	public TitlePanel mapPanel;

	private File levelFile;
	private File levelSaveFile;

	public LevelEditor() {
		super("Level Editor");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		openLevel.addActionListener(this);
		saveLevel.addActionListener(this);
		wallButton.addActionListener(this);
		creatureButton.addActionListener(this);
		deleteButton.addActionListener(this);
		playerButton.addActionListener(this);
		// wallButton.setIcon(MapWindow.imgWall);
		JPanel toolsBarPanel = new JPanel();
		toolsBarPanel.setLayout(new GridLayout(8, 1));
		toolsBarPanel.add(this.openLevel);
		toolsBarPanel.add(this.saveLevel);
		toolsBarPanel.add(this.wallButton);
		toolsBarPanel.add(this.deleteButton);
		toolsBarPanel.add(this.creatureButton);
		toolsBarPanel.add(this.playerButton);

		mapPanel = new TitlePanel("map:");

		mapWindow = new MapWindow(this);
		mapPanel.setLayout(new BorderLayout());
		mapPanel.add(mapWindow);
		this.add(mapPanel, BorderLayout.WEST);
		this.add(toolsBarPanel, BorderLayout.EAST);

		this.pack();
		this.setVisible(true);
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LevelEditor levelEditor = new LevelEditor();

			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == this.wallButton)
			toolType = 1;
		else if (ae.getSource() == this.deleteButton)
			toolType = 0;
		else if (ae.getSource() == this.creatureButton)
			toolType = 2;
		else if (ae.getSource() == this.playerButton)
			toolType = 3;
		else if (ae.getSource() == this.openLevel) {
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.levelFile = fileChooser.getSelectedFile();
				try {
					LevelReader levelReader = new LevelReader(this.levelFile);
					// lade Leveldaten aus Datei und update levelData in
					// mapWindow
					mapWindow.setLevelData(levelReader.getLevelData());
					mapLaenge = levelReader.getLevelData()[0].length;
					mapBreite = levelReader.getLevelData().length;

					this.setSize(mapLaenge * imgSize + 120, mapBreite * imgSize
							+ 80);
					mapPanel.setPreferredSize(new Dimension(
							mapLaenge * imgSize, mapBreite * imgSize));
					mapWindow.repaint();
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
				this.levelSaveFile = fileChooser.getSelectedFile();
				LevelWriter levelWriter = null;
				try {
					levelWriter = new LevelWriter(this.levelSaveFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					levelWriter.writeToFile(mapWindow.getLevelData());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
