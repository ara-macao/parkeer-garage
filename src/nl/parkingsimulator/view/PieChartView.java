package nl.parkingsimulator.view;

import java.awt.Color;
import javax.swing.JFrame;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * PieChartView - This view shows a piechart
 * It's dynamically assigned to show either Car queue percentages or parking spot availability.
 * @author Robin de Man
 */

public class PieChartView extends AbstractView {
    
    private int totalSpots;
    private int totalCarsWaiting;
    private int queueExit;
    private int queuePayment;
    private int queueEntrance;
    private int queuePassEntrance;
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
                pieChart.addSeries("Vrije plekken", percOpen);
                pieChart.addSeries("Pashouders", percPass);
                pieChart.addSeries("Gewone bezoekers", percRegUser);
                pieChart.addSeries("Gereserveerd", percReserved);
                pieChart.addSeries("Verkeerd geparkeerd", percBadPark);
                break;
            }
            case "queues" : {
                pieChart.addSeries("Totaal wachtend", percQueueEntrance);
                pieChart.addSeries("Pashouders wachtend", percQueuePassEntrance);
                pieChart.addSeries("Wachtend uitgang", percQueueExit);
                pieChart.addSeries("Betalen uitgang", percQueuePayment);
            }
        }
        
        //Run the calculations
        PieChartCalc();
        
        // Generate the Jframe data
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
            queueExit = model.getExitCarQueue();
            queuePayment = model.getPaymentCarQueue();
            passCars = model.getCurrentTotalCars(CarParkModel.PASS);
            regCars = model.getCurrentTotalCars(CarParkModel.AD_HOC);
            badPark = model.getCurrentTotalCars(CarParkModel.BAD_PARKING);
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

            //Check whether overview or queues should be shown on screen
            /**if(totalCarsWaiting > 75 && chartType == "overview"){
                //System.out.println("Switching to queues");
                chartType = "queues";
                pieChart.removeSeries("Vrije plekken");
                pieChart.removeSeries("Pashouders");
                pieChart.removeSeries("Gewone bezoekers");
                pieChart.removeSeries("Gereserveerd");
                pieChart.removeSeries("Verkeerd geparkeerd");
                pieChart.addSeries("Totaal wachtend", percQueueEntrance);
                pieChart.addSeries("Pashouders wachtend", percQueuePassEntrance);
                pieChart.addSeries("Wachtend uitgang", percQueueExit);
                pieChart.addSeries("Betalen uitgang", percQueuePayment);
            }
            if(totalCarsWaiting (lessthan) 10 && chartType == "queues"){
                //System.out.println("Switching to overview");
                chartType = "overview";
                pieChart.removeSeries("Totaal wachtend");
                pieChart.removeSeries("Pashouders wachtend");
                pieChart.removeSeries("Wachtend uitgang");
                pieChart.removeSeries("Betalen uitgang");
                pieChart.addSeries("Vrije plekken", percOpen);
                pieChart.addSeries("Pashouders", percPass);
                pieChart.addSeries("Gewone bezoekers", percRegUser);
                pieChart.addSeries("Gereserveerd", percReserved);
                pieChart.addSeries("Verkeerd geparkeerd", percBadPark);
            }*/
        }
    }
    
    /**
     * @Override updateView() in AbstractView class.
     */
    @Override
    public void updateView() {
        PieChartCalc();
        if(model.getHasReset()){ 
            percOpen = 0;
            percPass = 0;
            percRegUser = 0;
            percReserved = 0;
            percBadPark = 0;
            percQueueEntrance = 0;
            percQueuePassEntrance = 0;
            percQueueExit = 0;
            percQueuePayment = 0;
        }

        switch (chartType){
            case "overview" : {
                pieChart.updatePieSeries("Vrije plekken", percOpen);          
                pieChart.updatePieSeries("Pashouders", percPass);             
                pieChart.updatePieSeries("Gewone bezoekers", percRegUser);    
                pieChart.updatePieSeries("Gereserveerd", percReserved);       
                pieChart.updatePieSeries("Verkeerd geparkeerd", percBadPark); 
                break;
            }
            case "queues" : {
                pieChart.updatePieSeries("Totaal wachtend", percQueueEntrance);        
                pieChart.updatePieSeries("Pashouders wachtend", percQueuePassEntrance);
                pieChart.updatePieSeries("Wachtend uitgang", percQueueExit);           
                pieChart.updatePieSeries("Betalen uitgang", percQueuePayment);         
                break;
            }
            
        }
    //repaint the PieChartView content
    swingWrapper.repaintChart();
    }
}