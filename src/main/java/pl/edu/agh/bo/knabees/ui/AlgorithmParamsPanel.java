package pl.edu.agh.bo.knabees.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;

@SuppressWarnings("serial")
public class AlgorithmParamsPanel extends JPanel implements Clearable {
	private static final org.apache.log4j.Logger logger = Logger
			.getLogger(ItemsAndKnapsacksPanel.class);
	private JSpinner nBeeSpinner;
	private JSpinner nSiteSpinner;
	private JSpinner nghSpinner;
	private JSpinner nepSpinner;
	private JSpinner maxIterationsSpinner;
	private JCheckBox flagCheckBox;

	AlgorithmParamsPanel() {
		initilize();
	}

	private void initilize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(createSpinner("nBee", nBeeSpinner = new JSpinner()));
		add(createSpinner("nSiteSpinner", nSiteSpinner = new JSpinner()));
		add(createSpinner("nghSpinner", nghSpinner = new JSpinner()));
		add(createSpinner("nepSpinner", nepSpinner = new JSpinner()));
		add(createSpinner("maxIterationsSpinner",
				maxIterationsSpinner = new JSpinner()));
		add(createCheckBox("falg (unused so far)",
				flagCheckBox = new JCheckBox()));

		loadDefaultValues();
	}

	private JPanel createSpinner(String labelTitle, JSpinner spinner) {
		add(Box.createRigidArea(new Dimension(20, 20)));

		JLabel label = new JLabel(labelTitle);
		// spinner.setMinimumSize(new Dimension(20, 0)); // FIXME - spinners
		// width is too small

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(20, 0)));
		panel.add(spinner);

		return panel;
	}

	private JPanel createCheckBox(String labelTitle, JCheckBox checkBox) {
		add(Box.createRigidArea(new Dimension(20, 20)));

		JLabel label = new JLabel(labelTitle);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(20, 0)));
		panel.add(checkBox);

		return panel;
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
		nBeeSpinner.setValue(30);
		nSiteSpinner.setValue(5);
		nghSpinner.setValue(2);
		nepSpinner.setValue(2);
		maxIterationsSpinner.setValue(50);
		flagCheckBox.setSelected(true);
	}
}
