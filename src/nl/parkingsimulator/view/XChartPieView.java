/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * Pie chart to show data to the user in a simple fashion.
 * @author GraphX
 */
public class XChartPieView extends AbstractView {
    
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
    
    public XChartPieView(AbstractModel model) {
        super(model);
        this.model = (CarParkModel)getModel();
    }
 
    /**
     * Calculation of the Pie chart slices.
     */
    public void PieChartCalc(){
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            //request all data needed
            openSpots = model.getNumberOfOpenSpots();
            totalSpots = model.getNumberOfSpots();
            queueEntrance = model.getEntranceCarQueue();
            queuePassEntrance = model.getEntrancePassQueue();
            //passCars = model.getPassCars();
            //regCars = model.getRegCars();
            //badPark = model.getBadParkAmount();
            //reserved = model.getSpotsReserved();
            
            //create percentages for pie chart slices
            percOpen = (openSpots * 100.0f) / totalSpots;
            percPass = (passCars * 100.0f) / totalSpots;
            percRegUser = (regCars * 100.0f ) / totalSpots;
            percBadPark = (badPark * 100.0f ) / totalSpots;
            percReserved = (reserved * 100.0f ) / totalSpots;
        }
    }
    
  @Override
  public PieChart getChart() {
 
    // Create Chart
    PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
 
    // Customize Chart
    Color[] sliceColors = new Color[] { new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182) };
    chart.getStyler().setSeriesColors(sliceColors);
 
    // Series
    chart.addSeries("Empty parking spot", percOpen);
    chart.addSeries("Subscriber", percPass);
    chart.addSeries("Regular user", percRegUser);
    chart.addSeries("Reserved", percReserved);
    chart.addSeries("Wrongly parked", percBadPark);
 
    return chart;
  }
 
}