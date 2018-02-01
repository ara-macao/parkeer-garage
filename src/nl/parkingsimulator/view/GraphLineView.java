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
 * This class creates a JFrame with multiple graph lines witch can be turned on or off.
 * The graph chart uses an external library called XChart.
 * 
 * @author Thom van Dijk
 */
public class GraphLineView extends AbstractView { 
    private int horizontalStep;
    private int graphZoom;
    private int lastGraphPosition;
    private int activeCharts;
    
    /**
     * These booleans tell if the graph line is active.
     */
    private boolean occupiedPlacesGraph;
    private boolean totalwaitingCarsGraph;
    private boolean leavingCarsGraph;
    private boolean regularCarsGraph; 
    private boolean reservedCarsGraph;
    private boolean passHoldersGraph;

    /**
     * This are the array lists witch hold the graph line values.
     */
    private ArrayList<ArrayList<Integer>> totalCarsGraph;
    private ArrayList<ArrayList<Integer>> totalCarsWaitingGraph;
    private ArrayList<ArrayList<Integer>> totalCarsLeavingGraph;
    private ArrayList<ArrayList<Integer>> totalRegularCarsGraph;
    private ArrayList<ArrayList<Integer>> totalReservedSpotsGraph;
    private ArrayList<ArrayList<Integer>> totalPassHoldersGraph;
    
    /**
     * This are the colors of the different graph lines.
     */
    private Color[] colors = { Color.DARK_GRAY, Color.ORANGE, Color.MAGENTA, Color.RED, Color.GREEN, Color.BLUE, }; // Default colors.
    
    private XYChart graphLine;
    private SwingWrapper<XYChart> swingWrapper;
    
    private CarParkModel model;
    
    /**
     * This enum is used to in a switch statement to conveniently see witch graph needs to be toggled.
     */
    public enum GraphName {
    	OCCUPIED_PLACES, TOTAL_WAITING_CARS, TOTAL_LEAVING_CARS, REGULAR_CARS, RESERVED_SPOTS, PASS_HOLDERS
    }
    
    /**
     * The different zoom levels.
     */
    public enum ZoomLevel {
    	DAY, WEEK, MONTH
    }

    /**
     * Constructor for the graph line view.
     * 
     * @param model This is the CarParkModel class where we get all simulation related values and settings.
     */
    public GraphLineView(AbstractModel model) {
        super(model);
        this.model = (CarParkModel)getModel();
        
        /**
         * Horizontal step is the relative distance between points.
         * Only use 1, 10, 15, 30, 60, 1440 for horizontalStep.
         * For example 15 == 15 minutes == quarter.
         */
        horizontalStep = 10;
        
        graphZoom = 100;
        lastGraphPosition = 0;
        
        occupiedPlacesGraph = true;
        totalwaitingCarsGraph = true;
        leavingCarsGraph = true;
        regularCarsGraph = true;
        reservedCarsGraph = true;
        passHoldersGraph = true;
        
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());
        
        /**
         * Fill the ArrayLists with something.
         */
        totalCarsGraph = new ArrayList<ArrayList<Integer>>();
        totalCarsWaitingGraph = new ArrayList<ArrayList<Integer>>();
        totalCarsLeavingGraph = new ArrayList<ArrayList<Integer>>();
        totalRegularCarsGraph = new ArrayList<ArrayList<Integer>>();
        totalReservedSpotsGraph = new ArrayList<ArrayList<Integer>>();
        totalPassHoldersGraph = new ArrayList<ArrayList<Integer>>();
        
        totalCarsGraph.add(new ArrayList<Integer>());
        totalCarsGraph.add(new ArrayList<Integer>());
        totalCarsWaitingGraph.add(new ArrayList<Integer>());
        totalCarsWaitingGraph.add(new ArrayList<Integer>());
        totalCarsLeavingGraph.add(new ArrayList<Integer>());
        totalCarsLeavingGraph.add(new ArrayList<Integer>());
        totalRegularCarsGraph.add(new ArrayList<Integer>());
        totalRegularCarsGraph.add(new ArrayList<Integer>());
        totalReservedSpotsGraph.add(new ArrayList<Integer>());
        totalReservedSpotsGraph.add(new ArrayList<Integer>());
        totalPassHoldersGraph.add(new ArrayList<Integer>());
        totalPassHoldersGraph.add(new ArrayList<Integer>());
        
        totalCarsGraph.get(0).add(0);
        totalCarsGraph.get(1).add(0);
        totalCarsWaitingGraph.get(0).add(0);
        totalCarsWaitingGraph.get(1).add(0);
        totalCarsLeavingGraph.get(0).add(0);
        totalCarsLeavingGraph.get(1).add(0);
        totalRegularCarsGraph.get(0).add(0);
        totalRegularCarsGraph.get(1).add(0);
        totalReservedSpotsGraph.get(0).add(0);
        totalReservedSpotsGraph.get(1).add(0);
        totalPassHoldersGraph.get(0).add(0);
        totalPassHoldersGraph.get(1).add(0);
 
        /**
         * Create Chart.
         */
        graphLine = new XYChartBuilder().title(this.model.getSettings().getGraphLineName()).xAxisTitle(getTitleXAxis()).yAxisTitle("Aantal auto's").build();
        
        /**
         * Default enabled graph lines.
         */
        graphLine.addSeries("Totaal bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1));
        graphLine.addSeries("Wachtende bezoekers", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1));
        graphLine.addSeries("Vertrekkende bezoekers", totalCarsLeavingGraph.get(0), totalCarsLeavingGraph.get(1));
        graphLine.addSeries("Reguliere bezoekers", totalRegularCarsGraph.get(0), totalRegularCarsGraph.get(1));
        graphLine.addSeries("Gereserveerde plekken", totalReservedSpotsGraph.get(0), totalReservedSpotsGraph.get(1));
        graphLine.addSeries("Geparkeerde pashouders", totalPassHoldersGraph.get(0), totalPassHoldersGraph.get(1));
        
        activeCharts = graphLine.getSeriesMap().size();

        Marker[] markers = { SeriesMarkers.NONE }; // Default markers.

        /**
         * Customize the graph line chart.
         */
        graphLine.getStyler().setYAxisMax((double)this.model.getNumberOfSpots());
        graphLine.getStyler().setPlotContentSize(1.0);
        graphLine.getStyler().setSeriesMarkers(markers);
        graphLine.getStyler().setSeriesColors(colors);
        graphLine.getStyler().setLegendPosition(LegendPosition.InsideNW);
        graphLine.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);

        /**
         * Show the graph line chart.
         */
        swingWrapper = new SwingWrapper<XYChart>(graphLine);

        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE));
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
    
    /**
     * Add graph values and update the graph.
     */
    private void addGraphValues() {
        int totalNumberOfCars = model.getNumberOfSpots() - model.getNumberOfOpenSpots();
        int totalNumberOfCarswaiting = model.getEntranceCarQueue() + model.getEntrancePassQueue();
        int totalRegularCars = model.getCurrentTotalCars(CarParkModel.AD_HOC);
        int totalPlacesReserved = model.getCurrentTotalCars(CarParkModel.RESERVED);
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
        		
        		totalRegularCarsGraph.get(0).remove(0);
        		totalRegularCarsGraph.get(1).remove(0);
        		
        		totalReservedSpotsGraph.get(0).remove(0);
        		totalReservedSpotsGraph.get(1).remove(0);
        		                             
        		totalPassHoldersGraph.get(0).remove(0);
        		totalPassHoldersGraph.get(1).remove(0);
        	}

    		totalCarsGraph.get(0).add(lastGraphPosition);
        	totalCarsGraph.get(1).add(totalNumberOfCars);
        	
        	totalCarsWaitingGraph.get(0).add(lastGraphPosition);
        	totalCarsWaitingGraph.get(1).add(totalNumberOfCarswaiting);
        	
        	totalCarsLeavingGraph.get(0).add(lastGraphPosition);
            totalCarsLeavingGraph.get(1).add(totalCarsLeaving);
            
            totalRegularCarsGraph.get(0).add(lastGraphPosition);
            totalRegularCarsGraph.get(1).add(totalRegularCars);
                                   
            totalReservedSpotsGraph.get(0).add(lastGraphPosition);
            totalReservedSpotsGraph.get(1).add(totalPlacesReserved);
            
            totalPassHoldersGraph.get(0).add(lastGraphPosition);
            totalPassHoldersGraph.get(1).add(model.getCurrentTotalCars(CarParkModel.PASS));

        	// Update the graph line.
        	if(occupiedPlacesGraph) graphLine.updateXYSeries("Totaal bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1), null);
        	if(totalwaitingCarsGraph) graphLine.updateXYSeries("Wachtende bezoekers", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1), null);
        	if(leavingCarsGraph) graphLine.updateXYSeries("Vertrekkende bezoekers", totalCarsLeavingGraph.get(0), totalCarsLeavingGraph.get(1), null);
        	if(regularCarsGraph) graphLine.updateXYSeries("Reguliere bezoekers", totalRegularCarsGraph.get(0), totalRegularCarsGraph.get(1), null);
        	if(reservedCarsGraph) graphLine.updateXYSeries("Gereserveerde plekken", totalReservedSpotsGraph.get(0), totalReservedSpotsGraph.get(1), null);
        	if(passHoldersGraph) graphLine.updateXYSeries("Geparkeerde pashouders", totalPassHoldersGraph.get(0), totalPassHoldersGraph.get(1), null);

            swingWrapper.repaintChart();
            
            lastGraphPosition++;
        }
    }

    /**
     * Here you can toggle a specific graph. With the enum you can only toggle an existing graph.
     * 
     * @param name An enum value declared in this class.
     */
    public void toggleGraph(GraphName name) {
    	switch (name) {
			case OCCUPIED_PLACES:
				occupiedPlacesGraph = !occupiedPlacesGraph;
				if(occupiedPlacesGraph) { graphLine.addSeries("Totaal bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1)); colors[activeCharts] = Color.DARK_GRAY; activeCharts++; }
				else { graphLine.removeSeries("Totaal bezette plekken"); activeCharts--; }
				break;
			
			case TOTAL_WAITING_CARS:
				totalwaitingCarsGraph = !totalwaitingCarsGraph;
				if(totalwaitingCarsGraph) { graphLine.addSeries("Wachtende bezoekers", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1)); colors[activeCharts] = Color.ORANGE; activeCharts++;}
				else { graphLine.removeSeries("Wachtende bezoekers"); activeCharts--; }
				break;
				
			case TOTAL_LEAVING_CARS:
				leavingCarsGraph = !leavingCarsGraph;
				if(leavingCarsGraph) { graphLine.addSeries("Vertrekkende bezoekers", totalCarsLeavingGraph.get(0), totalCarsLeavingGraph.get(1)); colors[activeCharts] = Color.MAGENTA; activeCharts++;}
				else { graphLine.removeSeries("Vertrekkende bezoekers"); activeCharts--; }
				break;
				
			case REGULAR_CARS:
				regularCarsGraph = !regularCarsGraph;
				if(regularCarsGraph) { graphLine.addSeries("Reguliere bezoekers", totalRegularCarsGraph.get(0), totalRegularCarsGraph.get(1)); colors[activeCharts] = Color.RED; activeCharts++;}
				else { graphLine.removeSeries("Reguliere bezoekers"); activeCharts--; }
				break;
				
			case RESERVED_SPOTS:
				reservedCarsGraph = !reservedCarsGraph;
				if(reservedCarsGraph) { graphLine.addSeries("Gereserveerde plekken", totalReservedSpotsGraph.get(0), totalReservedSpotsGraph.get(1)); colors[activeCharts] = Color.GREEN; activeCharts++;}
				else { graphLine.removeSeries("Gereserveerde plekken"); activeCharts--; }
				break;
				
			case PASS_HOLDERS:
				passHoldersGraph = !passHoldersGraph;
				if(passHoldersGraph) { graphLine.addSeries("Geparkeerde pashouders", totalPassHoldersGraph.get(0), totalPassHoldersGraph.get(1)); colors[activeCharts] = Color.BLUE; activeCharts++;}
				else { graphLine.removeSeries("Geparkeerde pashouders"); activeCharts--; }
				break;
	
			default:
				throw new IllegalArgumentException("For some reason noting happend...");
		}
    	
    	graphLine.getStyler().setSeriesColors(colors);
    }
    
    /**
     * Horizontal step is the relative distance between points.
     * Only use 1, 10, 15, 30, 60, 1440 for horizontalStep.
     * For example 15 == 15 minutes == quarter.
     * 
     * @param int step
     */
    public void setHorizontalStep(int step) {
    	horizontalStep = step;
    	graphLine.setXAxisTitle(getTitleXAxis());
    	resetGraphs();
    }
    
    /**
     * Here you set the height of the graph. Don't use negative values.
     * 
     * @param height The height of the graph in number of cars.
     */
    public void setGraphHeight(int height) {
    	graphLine.getStyler().setYAxisMax((double)height);
    }
    
	/**
	 * Here you set the total width of the graph. 
	 * The amount of time from left to right, so a day a week or a month.
	 * 
	 * @param zoom The zoom is the total width to how much time is represented over the x axis.
	 */
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
    
    /**
     * All graphs are emptied.
     */
    private void resetGraphs() {
    	totalCarsGraph.get(0).clear();       
    	totalCarsGraph.get(1).clear();       
    	totalCarsWaitingGraph.get(0).clear();
    	totalCarsWaitingGraph.get(1).clear();
    	totalCarsLeavingGraph.get(0).clear();
    	totalCarsLeavingGraph.get(1).clear();
    	totalRegularCarsGraph.get(0).clear();
    	totalRegularCarsGraph.get(1).clear();            
    	totalReservedSpotsGraph.get(0).clear();
    	totalReservedSpotsGraph.get(1).clear();
    	totalPassHoldersGraph.get(0).clear();
    	totalPassHoldersGraph.get(1).clear();
    	
    	lastGraphPosition = 0;
    }
    
    /**
     * Depending on the horizontal step you choose there is a different
     * 
     * @return xAxisTitle The title for the x axis.
     */
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
