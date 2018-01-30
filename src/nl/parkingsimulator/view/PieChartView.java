/**
 * 
 */
package nl.parkingsimulator.view;

import java.awt.Color;

import javax.swing.JFrame;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;

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
    private double percQueueExit;
    private double percQueuePayment;
    private double percQueueEntrance;
    private double percQueuePassEntrance;
    private String chartType =  "overview";  
    
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
        Color[] sliceColors = new Color[] { new Color(230, 230, 230), new Color(0, 0, 255), new Color(255, 0, 0), new Color(0, 255, 0), new Color(255, 255, 0) };
        pieChart.getStyler().setSeriesColors(sliceColors);
     
        // Series
        switch (chartType){
            case "overview" : {
                System.out.println("You have selected piechart type: OVERVIEW.");
                pieChart.removeSeries("IN");
                pieChart.removeSeries("IN PASS");
                pieChart.removeSeries("OUT Exit");
                pieChart.removeSeries("OUT Payment");
                pieChart.addSeries("Empty spots", percOpen);
                pieChart.addSeries("Pass Car", percPass);
                pieChart.addSeries("Regular Car", percRegUser);
                pieChart.addSeries("Reserved", percReserved);
                pieChart.addSeries("Wrongly parked", percBadPark);
                break;
            }
            case "queues" : {
                System.out.println("You have selected piechart type: queues.");
                pieChart.removeSeries("Empty Spots");
                pieChart.removeSeries("Pass Car");
                pieChart.removeSeries("Regular Car");
                pieChart.removeSeries("Reserved");
                pieChart.removeSeries("Wrongly Parked");
                pieChart.addSeries("IN", percQueueEntrance);
                pieChart.addSeries("IN PASS", percQueuePassEntrance);
                pieChart.addSeries("OUT Exit", percQueueExit);
                pieChart.addSeries("OUT Payment", percQueuePayment);
            }
            case "simple" : {
                break;
            }
        }
        
        //Run the calculations
        PieChartCalc();
        
        // Show it
        swingWrapper = new SwingWrapper<PieChart>(pieChart);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getPieChartPosition().x, this.model.getSettings().getPieChartPosition().y, this.model.getSettings().getPieChartDimensions().width, this.model.getSettings().getPieChartDimensions().height));  
    }
    
    public void PieChartCalc(){
        CarParkModel model = (CarParkModel)getModel();
        while(totalCarsWaiting > 50 && chartType.equals("overview")){
            System.out.println("Switching to queues");
            chartType = "queues";
            pieChart.removeSeries("Empty spots");
            pieChart.removeSeries("Pass Car");
            pieChart.removeSeries("Regular Car");
            pieChart.removeSeries("Reserved");
            pieChart.removeSeries("Wrongly parked");
            pieChart.addSeries("IN", percQueueEntrance);
            pieChart.addSeries("IN PASS", percQueuePassEntrance);
            pieChart.addSeries("OUT Exit", percQueueExit);
            pieChart.addSeries("OUT Payment", percQueuePayment);
            
        }
        while(totalCarsWaiting < 10 && chartType.equals("queues")){
            System.out.println("Switching to overview");
            chartType = "overview";
            pieChart.removeSeries("IN");
            pieChart.removeSeries("IN PASS");
            pieChart.removeSeries("OUT Exit");
            pieChart.removeSeries("OUT Payment");
            pieChart.addSeries("Empty spots", percOpen);
            pieChart.addSeries("Pass Car", percPass);
            pieChart.addSeries("Regular Car", percRegUser);
            pieChart.addSeries("Reserved", percReserved);
            pieChart.addSeries("Wrongly parked", percBadPark);
        }
        if(model != null){
            //request all data needed
            openSpots = model.getNumberOfOpenSpots();
            totalSpots = model.getNumberOfSpots();
            queueEntrance = model.getEntranceCarQueue();
            queuePassEntrance = model.getEntrancePassQueue();
            queueExit = model.getExitCarQueue();
            queuePayment = model.getPaymentCarQueue();
            passCars = model.getCurrentTotalCars(CarParkModel.PASS);
            regCars = model.getCurrentTotalCars(CarParkModel.AD_HOC);
            //badPark = model.getCurrentTotalCars(CarParkModel.BAD_PARK);
            reserved = model.getCurrentTotalCars(CarParkModel.RESERVED);
            totalCarsWaiting = queueExit + queuePayment + queueEntrance + queuePassEntrance;
            
            //create percentages for pie chart slices
            percOpen = (openSpots * 100.0f) / totalSpots;
            percPass = (passCars * 100.0f) / totalSpots;
            percRegUser = (regCars * 100.0f ) / totalSpots;
            percBadPark = (badPark * 100.0f ) / totalSpots;
            percReserved = (reserved * 100.0f ) / totalSpots;
            percQueueEntrance = (queueEntrance * 100.0f) / totalCarsWaiting;
            percQueuePassEntrance = (queuePassEntrance * 100.0f) / totalCarsWaiting;
            percQueueExit = (queueExit * 100.0f ) / totalCarsWaiting;
            percQueuePayment = (queuePayment * 100.0f ) / totalCarsWaiting;
            //percReserved = (reserved * 100.0f ) / totalCarsWaiting;
        }
    }
    
    /**
     * @Override updateView() in AbstractView class.
     */
    public void updateView() {
        PieChartCalc();
         switch (chartType){
            case "overview" : {
                pieChart.updatePieSeries("Empty spots", percOpen);
                pieChart.updatePieSeries("Pass Car", percPass);
                pieChart.updatePieSeries("Regular Car", percRegUser);
                pieChart.updatePieSeries("Reserved", percReserved);
                pieChart.updatePieSeries("Wrongly parked", percBadPark);
                break;
            }
            case "queues" : {
                pieChart.updatePieSeries("IN", percQueueEntrance);
                pieChart.updatePieSeries("IN PASS", percQueuePassEntrance);
                pieChart.updatePieSeries("OUT Exit", percQueueExit);
                pieChart.updatePieSeries("OUT Payment", percQueuePayment);
                break;
            }
            case "simple" : {
                break;
            }
        }
        swingWrapper.repaintChart();
    }
}