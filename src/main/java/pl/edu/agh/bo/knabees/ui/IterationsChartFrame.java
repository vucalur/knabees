package pl.edu.agh.bo.knabees.ui;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import pl.edu.agh.bo.knabees.communication.IterationData;
import pl.edu.agh.bo.knabees.communication.Observer;

public class IterationsChartFrame extends JFrame implements
		Observer<IterationData> {
	IterationsChartFrame() {
		super("Solutions found in particular iteration");
		initilize();
	}

	private TimeSeries totalValue;
	private static final int MAXIMUM_AGE = 20000;
	private ChartPanel chartPanel;
	private JFreeChart chart;

	private void initilize() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(120, 70);
		// this.setLocationRelativeTo(this); // places frame in the centre of
		this.setResizable(false);
		this.setPreferredSize(new java.awt.Dimension(700, 500));

		this.totalValue = new TimeSeries("Total value taken");
		this.totalValue.setMaximumItemAge(MAXIMUM_AGE);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(totalValue);

		DateAxis domain = new DateAxis("Time");
		domain.setFixedAutoRange(MAXIMUM_AGE);

		NumberAxis range = new NumberAxis("Total items value");

		XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
		renderer.setSeriesPaint(1, Color.blue);
		renderer.setBaseStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL));
		XYPlot xyplot = new XYPlot(dataset, domain, range, renderer);
		// xyplot.setBackgroundPaint(Color.black);

		range.setAutoRange(true);
		domain.setAutoRange(true);
		domain.setLowerMargin(0.0);
		domain.setUpperMargin(0.0);
		domain.setTickLabelsVisible(true);
		range.setTickLabelsVisible(true);

		range.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		chart = new JFreeChart("Total value taken",
				JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
		chart.setNotify(false); // reduce performance overheads
								// (http://stackoverflow.com/a/3204086/1432478)
		chartPanel = new ChartPanel(chart);
		add(chartPanel);

		pack(); // must occur after adding all components
	}

	@Override
	public void notifyMe(IterationData data) {
		totalValue.addOrUpdate(new Millisecond(),
				data.calculateTotalTakenValue());
		chart.setNotify(true); // quick fix :)
		chart.setNotify(false);
	}

}