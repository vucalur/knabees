package pl.edu.agh.bo.knabees.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.alg.AlgorithmFacade;
import pl.edu.agh.bo.knabees.objects.Item;
import pl.edu.agh.bo.knabees.objects.Knapsack;

public class KnabeesGUI extends JFrame {
	private static final org.apache.log4j.Logger LOG = Logger.getLogger(KnabeesGUI.class);
	private KnapsacksPanel knapsacksPanel;
	private ItemsPanel itemsPanel;
	private final Action calculateAction = new SwingAction();

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	private KnabeesGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 886, 576);
		setTitle(Messages.getString("KnabeesGUI.this.title")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);

		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.setBounds(0, 0, 884, 547);
		getContentPane().add(tabs);

		knapsacksPanel = new KnapsacksPanel();
		tabs.addTab("Add knapsacks", null, knapsacksPanel, null);

		itemsPanel = new ItemsPanel();
		tabs.addTab("Add items", null, itemsPanel, null);
		itemsPanel.setLayout(null);

		JPanel calculatePanel = new JPanel();
		tabs.addTab("Calculate", null, calculatePanel, null);
		calculatePanel.setLayout(null);

		JButton calculateButton = new JButton(Messages.getString("KnabeesGUI.btnNewButton.text_2")); //$NON-NLS-1$
		calculateButton.setAction(calculateAction);
		calculateButton.setBounds(41, 40, 99, 27);
		calculatePanel.add(calculateButton);

		JLabel progressLabel = new JLabel("<html><b>Live progress info:</b></html>");
		progressLabel.setBounds(41, 146, 186, 19);
		calculatePanel.add(progressLabel);

		JButton knapsacksCapacityButton = new JButton("Show knapsacks capacities");
		knapsacksCapacityButton.setBounds(41, 177, 225, 27);
		calculatePanel.add(knapsacksCapacityButton);

		JButton itemsStatusButton = new JButton("Show items status (free/taken)");
		itemsStatusButton.setBounds(41, 215, 265, 27);
		calculatePanel.add(itemsStatusButton);

		JButton timelineButton = new JButton("Show progress chart");
		timelineButton.setBounds(41, 254, 225, 27);
		calculatePanel.add(timelineButton);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Calculate");
			putValue(SHORT_DESCRIPTION, "Confirms items & knapsacks and runs the main algorithm");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.setEnabled(false);
			final List<Knapsack> knapsacks = knapsacksPanel.getKnapsacks();
			final List<Item> items = itemsPanel.getItems();
			knapsacksPanel.clearData();
			itemsPanel.clearData();

			SwingWorker worker = new SwingWorker<Void, Void>() {
				@Override
				public Void doInBackground() {
					AlgorithmFacade alg = new AlgorithmFacade(knapsacks, items);

					alg.calculate(null);
					return null;
				}

				@Override
				protected void done() {
					SwingAction.this.setEnabled(true);
				}
			};

			worker.execute();
		}
	}
}
