package pl.edu.agh.bo.knabees.ui.observers;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Stroke;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.edu.agh.bo.knabees.communication.FinishedData;
import pl.edu.agh.bo.knabees.communication.IterationData;
import pl.edu.agh.bo.knabees.communication.Observer;
import pl.edu.agh.bo.knabees.ui.components.IconisedJFrame;

@SuppressWarnings("serial")
public class IterationsChartFrame extends IconisedJFrame implements Observer<IterationData> {
	private JFreeChart chart;
	private XYSeries totalValueSeries;
	private XYSeries beesSeries;
	private XYPlot plot;

	private double maxTotalValue = -1;
	private int iterationWithMaxTotalValue;
	private int takenItemsCountOnBestSolution;

	private static int calculationsCounter = 0;

	public IterationsChartFrame() {
		super("Solutions chart (" + ++calculationsCounter + ")");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(120, 150);
		setPreferredSize(new Dimension(700, 500));

		this.setPreferredSize(new java.awt.Dimension(700, 500));
		totalValueSeries = new XYSeries("Total value");
		beesSeries = new XYSeries("bees");
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(totalValueSeries);
		dataset.addSeries(beesSeries);

		NumberAxis domain = new NumberAxis("Iteration");
		NumberAxis range = new NumberAxis("Total items value");

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesLinesVisible(1, false);
		renderer.setSeriesShapesVisible(1, true);
		Stroke boldLine = new BasicStroke(1.5f);
		renderer.setSeriesStroke(0, boldLine);

		plot = new XYPlot(dataset, domain, range, renderer);
		plot.setRenderer(0, renderer);
		plot.setNotify(true);
		chart = new JFreeChart("Total value taken", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
		pack(); // must occur after adding all components

		// turning on and off showing bees :
		// ((XYLineAndShapeRenderer)
		// plot.getRenderer()).setSeriesShapesVisible(1, true);
	}

	@Override
	public void notifyMe(IterationData data) {
		double totalTakenValue = data.getTotalTakenValue();

		totalValueSeries.add(data.getIterationNum(), totalTakenValue);
		// FIXME : consider putting this in a separate chart
		for (double solutionValue : data.getSolutionsValues()) {
			beesSeries.add(data.getIterationNum(), solutionValue);
		}

		if (totalTakenValue > maxTotalValue) {
			maxTotalValue = totalTakenValue;
			iterationWithMaxTotalValue = data.getIterationNum();
			takenItemsCountOnBestSolution = data.getTakenItemsCount();
		}
	}

	@Override
	public void notifyTaskFinished(final FinishedData finishedData) {
		try {
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					StringBuilder items = new StringBuilder();
					for (int i = 0; i < finishedData.getIsItemTaken().length; i++) {
						if (finishedData.getIsItemTaken()[i]) {
							items.append(i + "<br />");
						}
					}

					// @formatter:off
					String MESSAGE = "<html>Maximal total taken value in most optimal solution: " + maxTotalValue + "<br />"
							+ "Solution found in iteration: " + iterationWithMaxTotalValue + "<br />"
							+ "Solution picked " + takenItemsCountOnBestSolution + " items" + "<br />" + "<br />"
							+ "Chosen items numbers :" + "<br />"
							+ items.toString()
							+ "</html>";
					// @formatter:on

					JFrame parent = new JFrame();
					JOptionPane optionPane = new JOptionPane(MESSAGE, JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog(parent, "Summary - optimal solution (" + calculationsCounter + ")");
					dialog.setVisible(true);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}