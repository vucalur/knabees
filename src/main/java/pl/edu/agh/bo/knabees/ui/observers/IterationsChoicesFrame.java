package pl.edu.agh.bo.knabees.ui.observers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.communication.IterationData;
import pl.edu.agh.bo.knabees.communication.Observer;
import pl.edu.agh.bo.knabees.model.Item;
import pl.edu.agh.bo.knabees.ui.components.IconisedJFrame;
import pl.edu.agh.bo.knabees.ui.components.ItemsList;

@SuppressWarnings("serial")
public class IterationsChoicesFrame extends IconisedJFrame implements Observer<IterationData> {
	@SuppressWarnings("unused")
	private static final org.apache.log4j.Logger logger = Logger.getLogger(IterationsChoicesFrame.class);

	private JComboBox<Integer> chooseIterationComboBox;
	private DefaultComboBoxModel<Integer> chooseIterationCBModel;
	private ItemsList list;

	private static int calculationsCounter = 0;

	public IterationsChoicesFrame(Item[] allItems) {
		this(Arrays.asList(allItems));
	}

	public IterationsChoicesFrame(List<Item> allItems) {
		super("Choices in particular iteration (" + ++calculationsCounter + ")");
		initilize(allItems);
	}

	private void initilize(List<Item> allItems) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(220, 70);
		// ↓↓↓ Suppresses list resizing
		// ! this.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 30));
		// this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(500, 600));
		JPanel chooseIterationPanel = new JPanel();
		chooseIterationPanel.add(new JLabel("Choose iteration numer:"));
		chooseIterationPanel.add(new JLabel("        ")); // Spacer
		chooseIterationComboBox = new JComboBox<>();
		chooseIterationPanel.add(chooseIterationComboBox);
		chooseIterationCBModel = new DefaultComboBoxModel<>();
		chooseIterationComboBox.setModel(chooseIterationCBModel);
		chooseIterationCBModel.addElement(0);
		mainPanel.add(chooseIterationPanel);

		JPanel usedListPanel = new JPanel();
		usedListPanel.setLayout(new BoxLayout(usedListPanel, BoxLayout.Y_AXIS));
		usedListPanel.add(new JLabel("Items status in chosen iteration:"));

		list = new ItemsList(allItems);
		list.setVisibleRowCount(20);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		usedListPanel.add(scrollPane);
		scrollPane.setViewportView(list);

		chooseIterationComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				list.showIteration((int) chooseIterationComboBox.getSelectedItem());
			}
		});

		mainPanel.add(usedListPanel);
		setContentPane(mainPanel);
		pack();
	}

	@Override
	public void notifyMe(IterationData data) {
		chooseIterationCBModel.addElement(data.getIterationNum());
		Set<Integer> usedSet = new HashSet<>();
		boolean[] itemsTaken = data.getItemsTaken();
		for (int i = 0; i < itemsTaken.length; i++) {
			if (itemsTaken[i]) {
				usedSet.add(i);
			}
		}
		list.setUsed(data.getIterationNum(), usedSet);
	}

	@Override
	public void notifyTaskFinished() {
		// not applicable - do nothing
	}
}
