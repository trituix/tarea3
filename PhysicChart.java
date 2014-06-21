import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class PhysicChart extends JPanel
{
	protected MyWorld world;
	private double time = 0;
	private XYSeriesCollection mechanicalDataCollection;
	private XYSeriesCollection potentialDataCollection;
	private XYSeriesCollection kineticDataCollection;
	private XYSeries mechanicalData;
	private XYSeries potentialData;
	private XYSeries kineticData;

	public PhysicChart(String chartTitle, MyWorld w) {
		setLayout(new GridLayout(1, 3));
		world = w;
		mechanicalDataCollection = new XYSeriesCollection();
		potentialDataCollection = new XYSeriesCollection();
		kineticDataCollection = new XYSeriesCollection();
		mechanicalData = new XYSeries("Energia Mecanica");
		potentialData = new XYSeries("Energia Potencial");
		kineticData = new XYSeries("Energia Cinetica");
		mechanicalData.setMaximumItemCount(1000);
		potentialData.setMaximumItemCount(1000);
		kineticData.setMaximumItemCount(1000);
		mechanicalDataCollection.addSeries(mechanicalData);
		potentialDataCollection.addSeries(potentialData);
		kineticDataCollection.addSeries(kineticData);
        final JFreeChart mechanicalChart = createChart(mechanicalDataCollection, "Energia Mecanica", "Tiempo[s]", "Energia[J]");
        final JFreeChart potentialChart = createChart(potentialDataCollection, "Energia Potencial", "Tiempo[s]", "Energia[J]");
        final JFreeChart kineticChart = createChart(kineticDataCollection, "Energia Cinetica", "Tiempo[s]", "Energia[J]");
        final ChartPanel mechanicalChartPanel = new ChartPanel(mechanicalChart);
        final ChartPanel potentialChartPanel = new ChartPanel(potentialChart);
        final ChartPanel kineticChartPanel = new ChartPanel(kineticChart);
        mechanicalChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        potentialChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        kineticChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        add(mechanicalChartPanel);
        add(potentialChartPanel);
        add(kineticChartPanel);
    }
    
    public void updateChart()
    {
    	mechanicalData.add(time, world.getMechanicalEnergy());
    	potentialData.add(time, world.getPotentialEnergy());
		kineticData.add(time, world.getKineticEnergy());
    	time += world.getDelta_t();
    }

    private JFreeChart createChart(final XYDataset dataset, String chartTitle, String xLabel, String yLabel) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            chartTitle,      // chart title
            xLabel,                      // x axis label
            yLabel,                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }

}