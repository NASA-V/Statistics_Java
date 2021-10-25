package Regression_Analysis.pkg;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Scatterplot extends JDialog {
    private static final long serialVersionUID = 6294689542092367723L;

    /**
     * @param frame creates frame from regression analysis class.
     * @param modal specifies modal of the dialog
     * @param info  info 0: title info 1: xaxis label info 2: yaxislabel info 3: series name
     */


    public Scatterplot(Regression_Analysis frame, boolean modal, String regression_analysis, String[] info) {
        super(frame, regression_analysis, modal);
        frame.setVisible(false);
        setResizable(true);
        XYDataset dataset = createDataset(frame, info);
        setDefaultLookAndFeelDecorated(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setVisible(true);
            }
        });

        // Create Panel sect 2
        ChartPanel panel = createChart(info, dataset);
        setContentPane(panel);

        addWindowStateListener(e -> frame.setVisible(true));

    }


    public ChartPanel createChart(String[] info, XYDataset dataset) {

        NumberAxis XAxis = new NumberAxis(info[1]);
        XAxis.setAutoRangeIncludesZero(false);
        NumberAxis YAxis = new NumberAxis(info[2]);
        YAxis.setAutoRangeIncludesZero(false);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
        XYPlot xyplot = new XYPlot(dataset, XAxis, YAxis, xylineandshaperenderer);
        double[] ad = Regression.getOLSRegression(dataset, 0);
        LineFunction2D linefunction2d = new LineFunction2D(ad[0], ad[1]);
        XYDataset xydataset = DatasetUtilities.sampleFunction2D(linefunction2d, 2D, 11D, 100, "Fitted Regression Line");
        xyplot.setDataset(1, xydataset);
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
        xylineandshaperenderer1.setSeriesPaint(0, Color.blue);
        xyplot.setRenderer(1, xylineandshaperenderer1);
        xyplot.setBackgroundPaint(new Color(255, 255, 255));
        JFreeChart jfreechart = new JFreeChart(info[0], JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        return new ChartPanel(jfreechart, false);
    }


    private XYDataset createDataset(Regression_Analysis frame, String[] info) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        //Boys (Age,weight) series


        XYSeries series1 = new XYSeries(info[3]);
        for (int i = 0; i < frame.tblDataset.getRowCount(); i++) {
            series1.add(Double.parseDouble(String.valueOf(frame.tblDataset.getValueAt(i, 0))), Double.parseDouble(String.valueOf(frame.tblDataset.getValueAt(i, 1))));
        }

        dataset.addSeries(series1);
        //Girls (Age,weight) series
//        XYSeries series2 = new XYSeries("Girls");
//        series2.add(1, 72.5);
//        series2.add(3, 87.2);
//        series2.add(4, 94.5);
//        series2.add(5, 101.4);
//        series2.add(6, 107.4);
//        series2.add(7, 112.8);
//        series2.add(8, 118.2);
//        series2.add(9, 122.9);
//        series2.add(10, 123.4);
//
//        dataset.addSeries(series2);

        return dataset;
    }

}
