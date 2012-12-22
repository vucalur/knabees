package pl.edu.agh.bo.knabees.ui.observers;

import java.awt.Dimension;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import pl.edu.agh.bo.knabees.communication.IterationData;
import pl.edu.agh.bo.knabees.communication.Observer;

@SuppressWarnings("serial")
public class IterationsChartFrame extends ApplicationFrame implements Observer<IterationData> {
	private JFreeChart chart;
	private XYSeries totalValueSeries;
	private XYSeries beesSeries;

	public IterationsChartFrame() {
		super("Solutions chart");
		this.setLocation(120, 70);
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

		XYPlot plot = new XYPlot(dataset, domain, range, renderer);
		plot.setRenderer(0, renderer);
		plot.setNotify(true);
		chart = new JFreeChart("Total value taken", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		ChartPanel chartPanel = new ChartPanel(chart);
		setContentPane(chartPanel);
		pack(); // must occur after adding all components
	}

	@Override
	public void notifyMe(IterationData data) {
		totalValueSeries.add(data.getIterationNum(), data.calculateTotalTakenValue());
		// TODO : add to beesSeries
		beesSeries.add(data.getIterationNum(), data.calculateTotalTakenValue() / 2); // mockup
	}
}