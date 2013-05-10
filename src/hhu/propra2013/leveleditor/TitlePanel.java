package hhu.propra2013.leveleditor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TitlePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TitlePanel(String title) {
		super();
		TitledBorder titleBorder;
		titleBorder = BorderFactory.createTitledBorder(title);
		// this.resize(new
		// Dimension(LevelEditor.mapBreite*LevelEditor.imgSize,LevelEditor.mapLaenge*LevelEditor.imgSize));
		// this.setBackground(Color.black);
		// this.setPreferredSize(new Dimension(50,50));
		// this.setBounds(0, 0, 22, 55);
		this.setBorder(titleBorder);
	}
}