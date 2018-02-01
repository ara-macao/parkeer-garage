package nl.parkingsimulator.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * @author Thom van Dijk
 */
public class GraphLineView extends AbstractView { 
    private int horizontalStep;
    private int graphZoom;
    private int lastGraphPosition;
    private int activeCharts;
    
    private boolean occupiedPlacesGraph;
    private boolean totalwaitingCarsGraph;
    private boolean leavingCarsGraph;
    private boolean passHoldersGraph;

    private ArrayList<ArrayList<Integer>> totalCarsGraph;
    private ArrayList<ArrayList<Integer>> totalCarsWaitingGraph;
    private ArrayList<ArrayList<Integer>> totalCarsLeavingGraph;
    private ArrayList<ArrayList<Integer>> totalPassHoldersGraph;
    
    private Color[] colors = { Color.RED, Color.ORANGE, Color.CYAN, Color.BLUE }; // Default colors.
    
    private XYChart graphLine;
    private SwingWrapper<XYChart> swingWrapper;
    
    private CarParkModel model;
    
    public enum GraphName {
    	OCCUPIED_PLACES, TOTAL_WAITING_CARS, TOTAL_LEAVING_CARS, PASS_HOLDERS
    }
    
    public enum ZoomLevel {
    	DAY, WEEK, MONTH
    }

    /**
     * Constructor for objects of class CarPark
     */
    public GraphLineView(AbstractModel model) {
        super(model);
        this.model = (CarParkModel)getModel();
        
        /**
         * Only use 1, 10, 15, 30, 60, 1440 for horizontalStep.
         * For example 15 == 15 minutes == quarter.
         */
        horizontalStep = 10;
        
        graphZoom = 100;
        lastGraphPosition = 0;
        
        occupiedPlacesGraph = true;
        totalwaitingCarsGraph = true;
        leavingCarsGraph = false;
        passHoldersGraph = false;
        
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());
  
        totalCarsGraph = new ArrayList<ArrayList<Integer>>();
        totalCarsWaitingGraph = new ArrayList<ArrayList<Integer>>();
        totalCarsLeavingGraph = new ArrayList<ArrayList<Integer>>();
        totalPassHoldersGraph = new ArrayList<ArrayList<Integer>>();
        
        totalCarsGraph.add(new ArrayList<Integer>());
        totalCarsGraph.add(new ArrayList<Integer>());
        totalCarsWaitingGraph.add(new ArrayList<Integer>());
        totalCarsWaitingGraph.add(new ArrayList<Integer>());
        totalCarsLeavingGraph.add(new ArrayList<Integer>());
        totalCarsLeavingGraph.add(new ArrayList<Integer>());
        totalPassHoldersGraph.add(new ArrayList<Integer>());
        totalPassHoldersGraph.add(new ArrayList<Integer>());
        
        totalCarsGraph.get(0).add(0);
        totalCarsGraph.get(1).add(0);
        totalCarsWaitingGraph.get(0).add(0);
        totalCarsWaitingGraph.get(1).add(0);
        totalCarsLeavingGraph.get(0).add(0);
        totalCarsLeavingGraph.get(1).add(0);
        totalPassHoldersGraph.get(0).add(0);
        totalPassHoldersGraph.get(1).add(0);
 
        // Create Chart.
        graphLine = new XYChartBuilder().title(this.model.getSettings().getGraphLineName()).xAxisTitle(getTitleXAxis()).yAxisTitle("Aantal auto's").build();
        
        // Default enabled.
        graphLine.addSeries("Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1));
        graphLine.addSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1));
        
        activeCharts = graphLine.getSeriesMap().size();

        Marker[] markers = { SeriesMarkers.NONE }; // Default markers.

        // Customize Chart.
        graphLine.getStyler().setYAxisMax((double)this.model.getNumberOfSpots());
        graphLine.getStyler().setPlotContentSize(1.0);
        graphLine.getStyler().setSeriesMarkers(markers);
        graphLine.getStyler().setSeriesColors(colors);
        graphLine.getStyler().setLegendPosition(LegendPosition.InsideNW);
        graphLine.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

        // Show it
        swingWrapper = new SwingWrapper<XYChart>(graphLine);

        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getGraphLinePosition().x, this.model.getSettings().getGraphLinePosition().y, this.model.getSettings().getGraphLineDimensions().width, this.model.getSettings().getGraphLineDimensions().height));        
    }
    
    /**
     * @Override updateView() in AbstractView class.
     */
    public void updateView() {
        if(model.getHasReset()){
            resetGraphs();
        }

        addGraphValues();
    }
    
    private void addGraphValues() {
        int totalNumberOfCars = model.getNumberOfSpots() - model.getNumberOfOpenSpots();
        int totalNumberOfCarswaiting = model.getEntranceCarQueue() + model.getEntrancePassQueue();
        int totalCarsLeaving = model.getExitCarQueue() + model.getPaymentCarQueue();

        if(model.getTotalTicks() >= lastGraphPosition * horizontalStep) {

        	/**
        	 * If the graph becomes to long we need to maintain a certain size.
        	 */
        	if(totalCarsGraph.get(0).size() > graphZoom) {
        		totalCarsGraph.get(0).remove(0);
        		totalCarsGraph.get(1).remove(0);
        		
        		totalCarsWaitingGraph.get(0).remove(0);
        		totalCarsWaitingGraph.get(1).remove(0);
        		                             
        		totalCarsLeavingGraph.get(0).remove(0);
        		totalCarsLeavingGraph.get(1).remove(0);
        		                             
        		totalPassHoldersGraph.get(0).remove(0);
        		totalPassHoldersGraph.get(1).remove(0);
        	}

    		totalCarsGraph.get(0).add(lastGraphPosition);
        	totalCarsGraph.get(1).add(totalNumberOfCars);
        	
        	totalCarsWaitingGraph.get(0).add(lastGraphPosition);
        	totalCarsWaitingGraph.get(1).add(totalNumberOfCarswaiting);
        	
        	totalCarsLeavingGraph.get(0).add(lastGraphPosition);
            totalCarsLeavingGraph.get(1).add(totalCarsLeaving);
            
            totalPassHoldersGraph.get(0).add(lastGraphPosition);
            totalPassHoldersGraph.get(1).add(model.getCurrentTotalCars(CarParkModel.PASS));


        	// Update the graph line.
        	if(occupiedPlacesGraph) graphLine.updateXYSeries("Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1), null);
        	if(totalwaitingCarsGraph) graphLine.updateXYSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1), null);
        	if(leavingCarsGraph) graphLine.updateXYSeries("Vertrekkende auto's", totalCarsLeavingGraph.get(0), totalCarsLeavingGraph.get(1), null);
        	if(passHoldersGraph) graphLine.updateXYSeries("Aantal gearriveerde pashouders", totalPassHoldersGraph.get(0), totalPassHoldersGraph.get(1), null);

            swingWrapper.repaintChart();
            
            lastGraphPosition++;
        }
    }
    
    public void toggleGraph(GraphName name) {
    	switch (name) {
			case OCCUPIED_PLACES:
				occupiedPlacesGraph = !occupiedPlacesGraph;
				if(occupiedPlacesGraph) { graphLine.addSeries("Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1)); colors[activeCharts] = Color.RED; activeCharts++; }
				else { graphLine.removeSeries("Bezette plekken"); activeCharts--; }
				break;
			
			case TOTAL_WAITING_CARS:
				totalwaitingCarsGraph = !totalwaitingCarsGraph;
				if(totalwaitingCarsGraph) { graphLine.addSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1)); colors[activeCharts] = Color.ORANGE; activeCharts++;}
				else { graphLine.removeSeries("Wachtende auto's"); activeCharts--; }
				break;
				
			case TOTAL_LEAVING_CARS:
				leavingCarsGraph = !leavingCarsGraph;
				if(leavingCarsGraph) { graphLine.addSeries("Vertrekkende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1)); colors[activeCharts] = Color.CYAN; activeCharts++;}
				else { graphLine.removeSeries("Vertrekkende auto's"); activeCharts--; }
				break;
				
			case PASS_HOLDERS:
				passHoldersGraph = !passHoldersGraph;
				if(passHoldersGraph) { graphLine.addSeries("Aantal gearriveerde pashouders", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1)); colors[activeCharts] = Color.BLUE; activeCharts++;}
				else { graphLine.removeSeries("Aantal gearriveerde pashouders"); activeCharts--; }
				break;
	
			default:
				throw new IllegalArgumentException("For some reason noting happend...");
		}
    	
    	graphLine.getStyler().setSeriesColors(colors);
    }
    
    /**
     * Only use 1, 10, 15, 30, 60, 1440 for step.
     * For example 15 == 15 minutes == quarter.
     * @param int step
     */
    public void setHorizontalStep(int step) {
    	horizontalStep = step;
    	graphLine.setXAxisTitle(getTitleXAxis());
    	resetGraphs();
    }
    
    public void setGraphHeight(int height) {
    	graphLine.getStyler().setYAxisMax((double)height);
    }
    
    public void setHorizontalZoom(ZoomLevel zoom) {
    	switch (horizontalStep) {
			case 1:
				if(zoom == ZoomLevel.DAY) graphZoom = 1440; // Minutes in a day.
				else if(zoom == ZoomLevel.WEEK) graphZoom = 10080; // Minutes in a week.
				else graphZoom = 40320; // Minutes in a month.
				break;
			
			case 10:
				if(zoom == ZoomLevel.DAY) graphZoom = 144;
				else if(zoom == ZoomLevel.WEEK) graphZoom = 1008;
				else graphZoom = 4032;
				break;
				
			case 15:
				if(zoom == ZoomLevel.DAY) graphZoom = 96;
				else if(zoom == ZoomLevel.WEEK) graphZoom = 672;
				else graphZoom = 2688;
				break;
				
			case 30:
				if(zoom == ZoomLevel.DAY) graphZoom = 48;
				else if(zoom == ZoomLevel.WEEK) graphZoom = 336;
				else graphZoom = 1344;
				break;
				
			case 60:
				if(zoom == ZoomLevel.DAY) graphZoom = 24;
				else if(zoom == ZoomLevel.WEEK) graphZoom = 168;
				else graphZoom = 627;
				break;
				
			case 1440:
				if(zoom == ZoomLevel.DAY) graphZoom = 0;
				else if(zoom == ZoomLevel.WEEK) graphZoom = 7;
				else graphZoom = 28;
				break;
	
			default:
				throw new IllegalArgumentException("horizontalStep " + horizontalStep + " is out of range");
		}
    }
    
    private void resetGraphs() {
    	totalCarsGraph.get(0).clear();       
    	totalCarsGraph.get(1).clear();       
    	totalCarsWaitingGraph.get(0).clear();
    	totalCarsWaitingGraph.get(1).clear();
    	totalCarsLeavingGraph.get(0).clear();
    	totalCarsLeavingGraph.get(1).clear();
    	totalPassHoldersGraph.get(0).clear();
    	totalPassHoldersGraph.get(1).clear();
    	
    	lastGraphPosition = 0;
    }
    
    private String getTitleXAxis() {
    	String xAxisTitle = "";
    	
    	switch (horizontalStep) {
			case 1:
				xAxisTitle = "Tijd in minuten";
				break;
			
			case 10:
				xAxisTitle = "Tijd x 10 minuten";
				break;
				
			case 15:
				xAxisTitle = "Tijd in kwartieren";
				break;
				
			case 30:
				xAxisTitle = "Tijd in halve uren";
				break;
				
			case 60:
				xAxisTitle = "Tijd in uren";
				break;
				
			case 1440:
				xAxisTitle = "Tijd in dagen";
				break;
	
			default:
				throw new IllegalArgumentException("horizontalStep " + horizontalStep + " is out of range");
		}
    	
    	return xAxisTitle;
    }
}
