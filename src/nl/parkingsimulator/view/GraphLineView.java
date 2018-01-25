/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * @author Thom van Dijk
 */
public class GraphLineView extends AbstractView { 
    private int numberOfOpenSpots;
    private int numberOfSpots;
    private int minuteSinceStart;
    private int horizontalStep;
    private int lastUpdate;
    private float horizontalGraphPosition;
    private int totalNumberOfCars;
    private int totalNumberOfCarswaiting;
    private int lastGraphPosition;
    
    private ArrayList<ArrayList<Integer>> totalCarsGraph;
    private ArrayList<ArrayList<Integer>> totalCarsWaitingGraph;
    
    private XYChart graphLine;
    private SwingWrapper<XYChart> swingWrapper;
    
    private CarParkModel model;

    /**
     * Constructor for objects of class CarPark
     */
    public GraphLineView(AbstractModel model) {
        super(model);
        
        this.model = (CarParkModel)getModel();

        numberOfOpenSpots = 0;
        numberOfSpots = 0;
        minuteSinceStart = 0;
        horizontalStep = 10; // Every 10 minutes.
        horizontalGraphPosition = 0;
        totalNumberOfCars = 0;
        totalNumberOfCarswaiting = 0;
        lastGraphPosition = 0;
  
        totalCarsGraph = new ArrayList<ArrayList<Integer>>();
        totalCarsWaitingGraph = new ArrayList<ArrayList<Integer>>();
        
        totalCarsGraph.add(new ArrayList<Integer>());
        totalCarsGraph.add(new ArrayList<Integer>());
        totalCarsWaitingGraph.add(new ArrayList<Integer>());
        totalCarsWaitingGraph.add(new ArrayList<Integer>());
        
        totalCarsGraph.get(0).add(0);
        totalCarsGraph.get(1).add(0);
        totalCarsWaitingGraph.get(0).add(0);
        totalCarsWaitingGraph.get(1).add(0);
        
        // Create Chart
        graphLine = QuickChart.getChart("Overzicht van bezette plekken", "Tijd in minuten", "Aantal auto's", "Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1));
        graphLine.addSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1));
        
        // Customize Chart
        graphLine.getStyler().setYAxisMax((double)this.model.getNumberOfSpots());
        graphLine.getStyler().setXAxisMax((double)this.model.getSettings().getAmountOfTicks());
        graphLine.getStyler().setLegendPosition(LegendPosition.InsideNE);
        graphLine.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);

        // Show it
        swingWrapper = new SwingWrapper<XYChart>(graphLine);
        swingWrapper.displayChart();
    }
    
    /**
     * @Override updateView() in AbstractView class.
     */
    public void updateView() {
        model = (CarParkModel)getModel();
         
        numberOfOpenSpots = model.getNumberOfOpenSpots();
        numberOfSpots = model.getNumberOfSpots();
        minuteSinceStart = model.getTotalTicks();

        addGraphValues();
    }
    
    private void addGraphValues() {
        totalNumberOfCars = numberOfSpots - numberOfOpenSpots;
        totalNumberOfCarswaiting = model.getEntranceCarQueue() + model.getEntrancePassQueue();

        if(minuteSinceStart >= lastGraphPosition * horizontalStep) {
        	totalCarsGraph.get(0).add(lastGraphPosition * horizontalStep);
        	totalCarsGraph.get(1).add(totalNumberOfCars);
        	
        	totalCarsWaitingGraph.get(0).add(lastGraphPosition * horizontalStep);
        	totalCarsWaitingGraph.get(1).add(totalNumberOfCarswaiting);
        	
        	// Update the graph line.
        	graphLine.updateXYSeries("Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1), null);
        	graphLine.updateXYSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1), null);
            swingWrapper.repaintChart();
            
            lastGraphPosition++;
        }
    }
}
