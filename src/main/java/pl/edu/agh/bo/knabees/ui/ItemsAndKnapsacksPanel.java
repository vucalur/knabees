package pl.edu.agh.bo.knabees.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;
import pl.edu.agh.bo.knabees.communication.FileParsingException;
import pl.edu.agh.bo.knabees.objects.Item;
import pl.edu.agh.bo.knabees.objects.Knapsack;
import pl.edu.agh.bo.knabees.utils.Utils;

@SuppressWarnings("serial")
public class ItemsAndKnapsacksPanel extends JPanel implements DataHolder {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(ItemsAndKnapsacksPanel.class);

	private JTextField fileStatusField;
	private JFileChooser fileChooser;
	private JButton openFileButton;
	private JTextField knapsackTextField;
	private JList<String> itemsList;
	private DefaultListModel<String> itemsListModel;

	ItemsAndKnapsacksPanel(Action calculateAction) {
		initilize(calculateAction);
	}

	private void initilize(Action calculateAction) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(initOpenFilePanel());
		add(initGenerateRandomPanel());
		add(initLoadedDataPanel(calculateAction));
	}

	private JPanel initOpenFilePanel() {
		JPanel openFilePanel = new JPanel();
		openFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		fileStatusField = new JTextField(20);
		fileStatusField.setEditable(false);
		fileStatusField.setToolTipText("File load status");

		openFileButton = new JButton("Load items & knapsacks");
		openFileButton.setToolTipText("<html>File should be a plain text file, containing lines describing items &amp; knapsacks:<br/>"
				+ "please refer to documentation for additional info about input format</html>");
		fileChooser = new JFileChooser();
		openFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == openFileButton) {
					int returnVal = fileChooser.showOpenDialog(ItemsAndKnapsacksPanel.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						BeesAlgorithm.Builder dataRead;
						try {
							dataRead = Utils.readItemsAndKnapsackData(fileChooser.getSelectedFile());
							fileStatusField.setText("Data successfully read");
						} catch (FileParsingException e) {
							fileStatusField.setText(e.getMessage());
							return;
						}
						loadData(dataRead);
					} else {
						logger.log(Priority.INFO, "Open command cancelled by user.");
					}
				}
			}
		});
		openFilePanel.add(openFileButton);
		// FIXME : align fileStatusField right
		openFilePanel.add(fileStatusField);

		return openFilePanel;
	}

	private Component initGenerateRandomPanel() {
		JPanel generateRandomPanel = new JPanel();
		generateRandomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton showGenerateWindowButton = new JButton();
		showGenerateWindowButton.setAction(new AbstractAction() {
			{
				putValue(NAME, "Generate random data");
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				new GenerateRandomDataFrame(ItemsAndKnapsacksPanel.this).setVisible(true);
			}
		});
		generateRandomPanel.add(showGenerateWindowButton);

		return generateRandomPanel;
	}

	private JPanel initLoadedDataPanel(Action calculateAction) {
		JPanel loadedDataPanel = new JPanel();
		loadedDataPanel.setLayout(new BoxLayout(loadedDataPanel, BoxLayout.Y_AXIS));

		JPanel knapsackPanel = new JPanel();
		knapsackPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		knapsackPanel.add(new JLabel("Knapsack limits:"));
		knapsackTextField = new JTextField(48);
		knapsackTextField.setEditable(false);
		knapsackPanel.add(knapsackTextField);
		loadedDataPanel.add(knapsackPanel);

		loadedDataPanel.add(new JLabel("Items:"));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		loadedDataPanel.add(scrollPane);

		itemsList = new JList<String>();
		itemsList.setVisibleRowCount(20);
		itemsListModel = new javax.swing.DefaultListModel<String>();
		itemsList.setModel(itemsListModel);
		scrollPane.setViewportView(itemsList);

		JPanel bottomButtonsPanel = new JPanel();
		bottomButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton removeSelectedButton = new JButton("Remove selected");
		removeSelectedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.removeSelection(itemsListModel, itemsList.getSelectedIndices());
			}
		});

		JButton calculateButton = new JButton("Calculate !");
		calculateButton.setAction(calculateAction);

		bottomButtonsPanel.add(removeSelectedButton);
		bottomButtonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		bottomButtonsPanel.add(calculateButton);
		loadedDataPanel.add(bottomButtonsPanel);

		return loadedDataPanel;
	}

	@Override
	public void loadData(BeesAlgorithm.Builder data) {
		knapsackTextField.setText(data.getKnapsack().toString());
		for (Item item : data.getItems()) {
			itemsListModel.addElement(item.toString());
		}
	}

	@Override
	public void clearData() {
		fileStatusField.setText("");
		knapsackTextField.setText("");
		itemsListModel.clear();
	}

	public List<Item> getItems() {
		List<Item> result = new ArrayList<>();
		for (int i = 0; i < itemsListModel.getSize(); ++i) {
			result.add(Item.parseItem(itemsListModel.get(i)));
		}
		return result;
	}

	public Knapsack getKnapsack() {
		return Knapsack.parseKnapsack(knapsackTextField.getText());
	}
}
