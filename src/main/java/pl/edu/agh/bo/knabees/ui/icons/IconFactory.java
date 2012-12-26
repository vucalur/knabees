package pl.edu.agh.bo.knabees.ui.icons;

import java.awt.Image;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

public enum IconFactory {
	TAKEN("/icons/full.png", "This item is taken"), //
	FREE("/icons/empty.png", "This item isn't chosen"), //
	BEE("/icons/bee4.jpg", "/icons/bee4.jpg");

	private static final org.apache.log4j.Logger logger = Logger.getLogger(IconFactory.class);

	private final Icon icon;
	private final Image image;

	public Icon getIcon() {
		return icon;
	}

	public Image getImage() {
		return image;
	}

	IconFactory(String iconUrl, String description) {
		icon = createIcon(iconUrl, description);
		image = createImage(iconUrl, description);
	}

	private Icon createIcon(String path, String description) {
		java.net.URL imgUrl = IconFactory.class.getResource(path);
		if (imgUrl != null) {
			return new ImageIcon(imgUrl, description);
		} else {
			logger.fatal("Couldn't find icon file: " + path);
			System.exit(1);
			return null;
		}
	}

	private Image createImage(String path, String description) {
		try {
			java.net.URL imgUrl = IconFactory.class.getResource(path);
			if (imgUrl == null) {
				throw new IOException();
			}
			return new ImageIcon(imgUrl, description).getImage();
		} catch (IOException e) {
			logger.fatal("Couldn't find icon file: " + path);
			System.exit(1);
			return null;
		}
	}
}
