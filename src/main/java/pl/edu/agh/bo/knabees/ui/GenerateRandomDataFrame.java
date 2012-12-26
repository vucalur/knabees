package pl.edu.agh.bo.knabees.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.OperationNotSupportedException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;
import pl.edu.agh.bo.knabees.model.Item;
import pl.edu.agh.bo.knabees.model.Knapsack;
import pl.edu.agh.bo.knabees.ui.components.IconisedJFrame;

@SuppressWarnings("serial")
public class GenerateRandomDataFrame extends IconisedJFrame {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(GenerateRandomDataFrame.class);

	private JPanel mainPanel;
	private JSpinner dimensionsSpinner;
	private JSpinner itemsCountSpinner;
	private JSpinner knapsackSizeSpinner;
	private JSpinner itemsMaxSizeSpinner;
	private JSpinner itemsMaxValueSpinner;
	private Action generateAction = this.new GenerateAndLoadAction();
	private DataHolder dataDestination;
	private Random rand = new Random();

	GenerateRandomDataFrame(DataHolder dataDestination) {
		super("Generate random data");
		this.dataDestination = dataDestination;
		initilize();
		loadDefaultSpinnersValues();
	}

	private void initilize() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(120, 130);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2, 40, 40));

		addSpinnerTo(mainPanel, "dimensions", dimensionsSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "items count", itemsCountSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "knapsack size in one dimension", knapsackSizeSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "items max size in one dimension", itemsMaxSizeSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "items max value", itemsMaxValueSpinner = new JSpinner());
		mainPanel.add(new JButton(generateAction));

		add(mainPanel);
		pack();
	}

	private void addSpinnerTo(JPanel target, String labelTitle, JSpinner spinner) {
		target.add(new JLabel(labelTitle));
		target.add(spinner);
	}

	private void loadDefaultSpinnersValues() {
		dimensionsSpinner.setValue(10);
		itemsCountSpinner.setValue(50);
		knapsackSizeSpinner.setValue(20);
		itemsMaxSizeSpinner.setValue(10);
		itemsMaxValueSpinner.setValue(100);
	}

	private class GenerateAndLoadAction extends AbstractAction {
		GenerateAndLoadAction() {
			putValue(NAME, "Load random data");
			putValue(SHORT_DESCRIPTION,
					"Generates random data based on limit set in this window and loads the to the items and knapsacks tab");
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			dataDestination.clearData();

			final int dimensions = (int) dimensionsSpinner.getValue();
			final int itemsCount = (int) itemsCountSpinner.getValue();
			final int knapsackSize = (int) knapsackSizeSpinner.getValue();
			final int itemsMaxSize = (int) itemsMaxSizeSpinner.getValue();
			final int itemsMaxValue = (int) itemsMaxValueSpinner.getValue();

			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				public Void doInBackground() {
					GenerateAndLoadAction.this.setEnabled(false);

					List<Double> knapsackDimensions = new ArrayList<>();
					for (int i = 0; i < dimensions; i++) {
						knapsackDimensions.add((double) knapsackSize);
					}
					Knapsack knapsack = new Knapsack(knapsackDimensions);
					List<Item> items = new ArrayList<>(itemsCount);
					List<Double> itemDimensions;
					for (int i = 0; i < itemsCount; i++) {
						itemDimensions = new ArrayList<>(dimensions);
						for (int j = 0; j < dimensions; j++) {
							itemDimensions.add((double) rand.nextInt(itemsMaxSize + 1));
						}
						items.add(new Item(rand.nextInt(itemsMaxValue + 1), itemDimensions));
					}

					try {
						dataDestination.loadData(new BeesAlgorithm.Builder(knapsack, items));
					} catch (OperationNotSupportedException e) {
						logger.error("Loading data not supported");
					}
					return null;
				}

				@Override
				protected void done() {
					GenerateRandomDataFrame.this.dispose();
				}
			};

			worker.execute();
		}
	};
}
