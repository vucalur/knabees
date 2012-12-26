package pl.edu.agh.bo.knabees.ui.components;

import javax.swing.JFrame;

import pl.edu.agh.bo.knabees.ui.icons.IconFactory;

@SuppressWarnings("serial")
public class IconisedJFrame extends JFrame {
	public IconisedJFrame() {
		super();
		setIconImage(IconFactory.BEE.getImage());
	}

	public IconisedJFrame(String title) {
		super(title);
		setIconImage(IconFactory.BEE.getImage());
	}
}
