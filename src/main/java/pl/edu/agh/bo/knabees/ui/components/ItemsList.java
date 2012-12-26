package pl.edu.agh.bo.knabees.ui.components;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.model.Item;
import pl.edu.agh.bo.knabees.ui.icons.IconFactory;

@SuppressWarnings("serial")
public class ItemsList extends JList<String> {
	@SuppressWarnings("unused")
	private static final org.apache.log4j.Logger logger = Logger.getLogger(ItemsList.class);

	private final IconListRenderer renderer;
	private final DefaultListModel<String> model;

	public ItemsList(List<Item> allItems) {
		this.renderer = new IconListRenderer();
		setCellRenderer(renderer);
		this.model = new DefaultListModel<>();
		setModel(model);
		loadInitData(allItems);
	}

	private void loadInitData(List<Item> allItems) {
		for (Item item : allItems) {
			model.addElement(item.toString());
		}
	}

	public void setUsed(int iteration, Set<Integer> used) {
		renderer.setUsed(iteration, used);
	}

	public void showIteration(final int iteration) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				renderer.showIteration(iteration);
				ItemsList.this.repaint();
			}
		});
	}

	private static class IconListRenderer extends DefaultListCellRenderer {

		private final Map<Integer, Set<Integer>> usedInIteration = new HashMap<>();
		{
			usedInIteration.put(0, new HashSet<Integer>());
		}
		private int currentIteration = 0;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setIcon(usedInIteration.get(currentIteration).contains(index) ? IconFactory.TAKEN.getIcon() : IconFactory.FREE.getIcon());
			return label;
		}

		public synchronized void setUsed(int iteration, Set<Integer> used) {
			this.usedInIteration.put(iteration, used);
		}

		public synchronized void showIteration(int iteration) {
			currentIteration = iteration;
		}
	}
}