/*
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
        
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());
  
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
        graphLine = new XYChartBuilder().width(800).height(600).title(getClass().getSimpleName()).xAxisTitle("Tijd in minuten").yAxisTitle("Aantal auto's").build();
        
        graphLine.addSeries("Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1));
        graphLine.addSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1));
        
        // Customize Chart
        graphLine.getStyler().setYAxisMax((double)this.model.getNumberOfSpots());
        graphLine.getStyler().setXAxisMax((double)this.model.getSettings().getAmountOfTicks());
        graphLine.getStyler().setLegendPosition(LegendPosition.InsideNE);
        graphLine.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
        
        /**
         * Use this code to add the chart to this JPanel.
         */
        //XChartPanel<XYChart> chartPane = new XChartPanel<>(graphLine);
        //add(chartPane);

        // Show it
        swingWrapper = new SwingWrapper<XYChart>(graphLine);
        //swingWrapper.displayChart().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //swingWrapper.displayChart().setBounds(20, 600, 800, 600);//.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getGraphLinePosition().x, this.model.getSettings().getGraphLinePosition().y, this.model.getSettings().getGraphLineDimensions().width, this.model.getSettings().getGraphLineDimensions().height));        
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
