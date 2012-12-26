package pl.edu.agh.bo.knabees.ui;

import java.awt.GridLayout;

import javax.naming.OperationNotSupportedException;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;
import pl.edu.agh.bo.knabees.alg.BeesAlgorithm.Builder;
import pl.edu.agh.bo.knabees.model.Item;

@SuppressWarnings("serial")
public class AlgorithmParamsPanel extends JPanel implements DataHolder {
	@SuppressWarnings("unused")
	private static final org.apache.log4j.Logger logger = Logger.getLogger(ItemsAndKnapsacksPanel.class);

	private JPanel mainPanel;
	private JSpinner nBeeSpinner;
	private JSpinner nSiteSpinner;
	private JSpinner nghSpinner;
	private JSpinner nepSpinner;
	private JSpinner maxIterationsSpinner;
	private JCheckBox flagCheckBox;

	AlgorithmParamsPanel() {
		initilize();
		loadDefaultValues();
	}

	private void initilize() {
		mainPanel = new JPanel();
		// TODO : align left - none of these 2 are working :
		// mainPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		// mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.setLayout(new GridLayout(0, 2, 50, 60));

		addSpinnerTo(mainPanel, "nBee", nBeeSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "nSite", nSiteSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "ngh", nghSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "nep", nepSpinner = new JSpinner());
		addSpinnerTo(mainPanel, "maxIterations", maxIterationsSpinner = new JSpinner());
		// FIXME : ↓ fix label in the release version
		addCheckBoxTo(mainPanel, "falg (unused so far)", flagCheckBox = new JCheckBox());

		add(mainPanel);
	}

	private void addSpinnerTo(JPanel target, String labelTitle, JSpinner spinner) {
		target.add(new JLabel(labelTitle));
		target.add(spinner);
	}

	private void addCheckBoxTo(JPanel target, String labelTitle, JCheckBox checkBox) {
		target.add(new JLabel(labelTitle));
		target.add(checkBox);
	}

	public void loadDataTo(BeesAlgorithm.Builder builder) {
		builder.nBee((int) nBeeSpinner.getValue());
		builder.nSite((int) nSiteSpinner.getValue());
		builder.ngh((int) nghSpinner.getValue());
		builder.nep((int) nepSpinner.getValue());
		builder.maxIterations((int) maxIterationsSpinner.getValue());
		// FIXME : flagCheckBox
	}

	@Override
	public void clearData() {
		loadDefaultValues();
	}

	private void loadDefaultValues() {
		BeesAlgorithm.Builder defaultsSource = new BeesAlgorithm.Builder(null, new Item[0]);
		nBeeSpinner.setValue(defaultsSource.getNBee());
		nSiteSpinner.setValue(defaultsSource.getNSite());
		nghSpinner.setValue(defaultsSource.getNgh());
		nepSpinner.setValue(defaultsSource.getNep());
		maxIterationsSpinner.setValue(defaultsSource.getMaxIterations());
		flagCheckBox.setSelected(true); // FIXME : provide proper default for
										// the flag
	}

	@Override
	public void loadData(Builder data) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
}
