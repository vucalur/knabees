package pl.edu.agh.bo.knabees.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;

@SuppressWarnings("serial")
public class KnabeesGUI extends JFrame {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(KnabeesGUI.class);
	private AlgorithmParamsPanel algorithmParamsPanel;
	private ItemsAndKnapsacksPanel itemsAndKnapsacksPanel;
	private final Action calculateAction = this.new CalculateAction();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				KnabeesGUI window = new KnabeesGUI();
				window.setVisible(true);
			}
		});
	}

	private KnabeesGUI() {
		initialize();
		pack();
	}

	private void initialize() {
		setPreferredSize(new Dimension(886, 576));
		setTitle("Knapsack problem solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabs);

		algorithmParamsPanel = new AlgorithmParamsPanel();
		tabs.addTab("Set algorithm parameters", null, algorithmParamsPanel, null);

		itemsAndKnapsacksPanel = new ItemsAndKnapsacksPanel(calculateAction);
		tabs.addTab("Run algorithm ", null, itemsAndKnapsacksPanel, null);
	}

	@SuppressWarnings("serial")
	private class CalculateAction extends AbstractAction {
		CalculateAction() {
			putValue(NAME, "Calculate");
			putValue(SHORT_DESCRIPTION, "Confirms items & knapsacks choice and runs the main algorithm");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.setEnabled(false);
			// algorithmParamsPanel.setEnabled(false);
			// itemsAndKnapsacksPanel.setEnabled(false);
			final BeesAlgorithm.Builder builder = new BeesAlgorithm.Builder(itemsAndKnapsacksPanel.getKnapsack(), itemsAndKnapsacksPanel.getItems());
			algorithmParamsPanel.loadDataTo(builder);

			itemsAndKnapsacksPanel.clearData();
			algorithmParamsPanel.clearData();

			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				@Override
				public Void doInBackground() {
					logger.info("Calculation started");
					BeesAlgorithm ba = builder.build();
					IterationsChartFrame iterationsChartFrame = new IterationsChartFrame();
					ba.addItemsObserver(iterationsChartFrame);
					iterationsChartFrame.setVisible(true);
					ba.run();
					return null;
				}

				@Override
				protected void done() {
					CalculateAction.this.setEnabled(true);
					// algorithmParamsPanel.setEnabled(true);
					// itemsAndKnapsacksPanel.setEnabled(true);
				}
			};

			worker.execute();
		}
	}
}
