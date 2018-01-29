/**
 * 
 */
package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * PieChartView - This chart shows a pie chart of the current spots in use.
 * @author Robin de Man
 */

public class PieChartView extends AbstractView {
    
    private int openSpots;
    private int totalSpots;
    private int takenSpots;
    private double percOpenSpots;
    private double percTakenSpots;
    
    private PieChart pieChart;
    private SwingWrapper<PieChart> swingWrapper;
    
    private CarParkModel model;
    
    /**
     * Constructor for objects of class CarPark
     * @param model
     */
    public PieChartView (AbstractModel model) {
        super(model);
        this.model = (CarParkModel)getModel();
         
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());

        // Create Chart
        pieChart = new PieChartBuilder().title(this.model.getSettings().getPieChartName()).build();
        
        // Customize Chart
        Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
        pieChart.getStyler().setSeriesColors(sliceColors);
     
        // Series
        pieChart.addSeries("Gold", 24);
        pieChart.addSeries("Silver", 21);
        pieChart.addSeries("Platinum", 39);
        pieChart.addSeries("Copper", 17);
        pieChart.addSeries("Zinc", 40);
        
        // Show it
        swingWrapper = new SwingWrapper<PieChart>(pieChart);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getPieChartPosition().x, this.model.getSettings().getPieChartPosition().y, this.model.getSettings().getPieChartDimensions().width, this.model.getSettings().getPieChartDimensions().height));  
    }
    
    /**
     * @Override updateView() in AbstractView class.
     */
    public void updateView() {
        model = (CarParkModel)getModel();
         
        // Update the pie chart.
    	pieChart.updatePieSeries("Gold", Math.random() * 100);

        swingWrapper.repaintChart();
    }
    
    /**
     * Calculation of the Pie chart slices.
     */
    public void PieChartCalc() {
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            //request all data needed
            openSpots = model.getNumberOfOpenSpots();
            totalSpots = model.getNumberOfSpots();
            takenSpots = totalSpots - openSpots;
            //create percentages for pie chart slices
            percOpenSpots = (openSpots * 100.0f) / totalSpots;
            percTakenSpots = (takenSpots * 100.0f) / totalSpots;
        }
    }

    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     * @param g the graphic object
     
    @Override
    public void paintComponent(Graphics g) {
        PieChartCalc();
        //set background colour
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        //Draw the first slice
        startPosition = -1;
        g.setColor(Color.LIGHT_GRAY);
        degrees =(int)(percOpenSpots * 360/100);
        g.fillArc(10, 10, 180, 180, startPosition, degrees);
        startPosition = degrees;
        //Draw the second slice
        g.setColor(Color.RED);
        degrees = (int)(percTakenSpots * 360/100);
        g.fillArc(10, 10, 180, 180, startPosition, degrees);
    }*/
}


