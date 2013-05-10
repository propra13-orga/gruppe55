package hhu.propra2013.leveleditor;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapWindow extends JPanel {

	private static final long serialVersionUID = 1L;
	private LevelEditor mainFrame;
	static int xPosition, yPosition;
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
		for (int i = 0; i < levelData.length; i++) {
			for (int j = 0; j < levelData[0].length; j++) {
				levelData[i][j] = 0;
			}
		}
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				xPosition = (me.getX() / LevelEditor.imgSize)
						* LevelEditor.imgSize;
				yPosition = (me.getY() / LevelEditor.imgSize)
						* LevelEditor.imgSize;
				addMapObject(xPosition, yPosition, LevelEditor.toolType);
				repaint();
			}
		});

	}

	public void addMapObject(int xPos, int yPos, int type) {
		int xPosGrid, yPosGrid;
		xPosGrid = xPos / LevelEditor.imgSize;
		yPosGrid = yPos / LevelEditor.imgSize;
		this.levelData[yPosGrid][xPosGrid] = type;
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		this.setSize(new Dimension(LevelEditor.mapLaenge * LevelEditor.imgSize,
				LevelEditor.mapBreite * LevelEditor.imgSize));
		g2.clearRect(0, 0, LevelEditor.mapLaenge * LevelEditor.imgSize,
				LevelEditor.mapBreite * LevelEditor.imgSize);
		for (int i = 0; i <= levelData.length - 1; i++) {
			for (int j = 0; j <= levelData[0].length - 1; j++) {
				if (levelData[i][j] == 1)
					g2.drawImage(imgWall, j * LevelEditor.imgSize, i
							* LevelEditor.imgSize, this);
				else if (levelData[i][j] == 2)
					g2.drawImage(imgCreature, j * LevelEditor.imgSize, i
							* LevelEditor.imgSize, this);
				else if (levelData[i][j] == 3)
					g2.drawImage(imgPlayer, j * LevelEditor.imgSize, i
							* LevelEditor.imgSize, this);

			}
		}
	}

	public void setLevelData(int[][] levelData) {
		this.levelData = levelData;
	}

	public int[][] getLevelData() {
		return this.levelData;
	}

}
