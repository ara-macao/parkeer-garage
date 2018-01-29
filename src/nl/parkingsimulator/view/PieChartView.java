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
    
    private int totalSpots;
    private int totalCarsWaiting;
    private int carsLeft;
    private int queueExit;
    private int queuePayment;
    private int queueEntrance;
    private int queuePassEntrance;
    private int totalCars;
    private int openSpots;
    private int passCars;
    private int regCars;
    private int badPark;
    private int reserved;
    private double percOpen;
    private double percPass;
    private double percRegUser;
    private double percBadPark;
    private double percReserved;
    
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
        pieChart.addSeries("Empty spots", percOpen);
        pieChart.addSeries("Pass Car", percPass);
        pieChart.addSeries("Regular Car", percRegUser);
        pieChart.addSeries("Reserved", percReserved);
        pieChart.addSeries("Wrongly parked", percBadPark);
        
        // Show it
        swingWrapper = new SwingWrapper<PieChart>(pieChart);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getPieChartPosition().x, this.model.getSettings().getPieChartPosition().y, this.model.getSettings().getPieChartDimensions().width, this.model.getSettings().getPieChartDimensions().height));  
    }
    
    public void PieChartCalc(){
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            //request all data needed
            openSpots = model.getNumberOfOpenSpots();
            totalSpots = model.getNumberOfSpots();
            queueEntrance = model.getEntranceCarQueue();
            queuePassEntrance = model.getEntrancePassQueue();
            passCars = model.getCurrentTotalCars(CarParkModel.PASS);
            regCars = model.getCurrentTotalCars(CarParkModel.AD_HOC);
            //badPark = model.getCurrentTotalCars(CarParkModel.BAD_PARK);
            //reserved = model.getCurrentTotalCars(CarParkModel.RESERVED);
            
            //create percentages for pie chart slices
            percOpen = (openSpots * 100.0f) / totalSpots;
            percPass = (passCars * 100.0f) / totalSpots;
            percRegUser = (regCars * 100.0f ) / totalSpots;
            percBadPark = (badPark * 100.0f ) / totalSpots;
            percReserved = (reserved * 100.0f ) / totalSpots;
        }
    }
    
    /**
     * @Override updateView() in AbstractView class.
     */
    public void updateView() {
        model = (CarParkModel)getModel();
         
        // Update the pie chart.
        pieChart.updatePieSeries("Empty spots", percOpen);
        pieChart.updatePieSeries("Pass Car", percPass);
        pieChart.updatePieSeries("Regular Car", percRegUser);
        pieChart.updatePieSeries("Reserved", percReserved);
        pieChart.updatePieSeries("Wrongly parked", percBadPark);

        swingWrapper.repaintChart();
    }
   
}