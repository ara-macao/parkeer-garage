package nl.parkingsimulator.view;

import java.awt.BasicStroke;
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

	private static final long serialVersionUID = 1L;
	
	private int numberOfOpenSpots;
    private int numberOfSpots;
    private int minuteSinceStart;
    private int horizontalStep;
    private int totalNumberOfCars;
    private int totalNumberOfCarswaiting;
    private int lastGraphPosition;
    
    private boolean occupiedPlacesGraph;
    private boolean totalwaitingCarsGraph;
    
    private int activeCharts;
    
    private ArrayList<ArrayList<Integer>> totalCarsGraph;
    private ArrayList<ArrayList<Integer>> totalCarsWaitingGraph;
    
    private Color[] colors = { Color.RED, Color.ORANGE, Color.BLUE }; // Default colors.
    
    private XYChart graphLine;
    private SwingWrapper<XYChart> swingWrapper;
    
    private CarParkModel model;
    
    public enum GraphName {
    	OCCUPIED_PLACES, TOTAL_WAITING_CARS
    }

    /**
     * Constructor for objects of class CarPark
     */
    public GraphLineView(AbstractModel model) {
        super(model);
        this.model = (CarParkModel)getModel();

        numberOfOpenSpots = 0;
        numberOfSpots = 0;
        minuteSinceStart = 0;
        
        /**
         * Only use 1, 10, 15, 30, 60, 1440 for horizontalStep.
         * For example 15 == 15 minutes == quarter.
         */
        horizontalStep = 10;
        
        totalNumberOfCars = 0;
        totalNumberOfCarswaiting = 0;
        lastGraphPosition = 0;
        
        occupiedPlacesGraph = true;
        totalwaitingCarsGraph = true;
        
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());
        
        String xAxisTitle = "Tijd in onbekend";
        
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
 
        // Create Chart.
        graphLine = new XYChartBuilder().title(this.model.getSettings().getGraphLineName()).xAxisTitle(xAxisTitle).yAxisTitle("Aantal auto's").build();
        
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
        graphLine.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);

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
        //model = (CarParkModel)getModel();
         
        numberOfOpenSpots = model.getNumberOfOpenSpots();
        numberOfSpots = model.getNumberOfSpots();
        minuteSinceStart = model.getTotalTicks();

        addGraphValues();
    }
    
    private void addGraphValues() {
        totalNumberOfCars = numberOfSpots - numberOfOpenSpots;
        totalNumberOfCarswaiting = model.getEntranceCarQueue() + model.getEntrancePassQueue();

        if(minuteSinceStart >= lastGraphPosition * horizontalStep) {
        	totalCarsGraph.get(0).add(lastGraphPosition);
        	totalCarsGraph.get(1).add(totalNumberOfCars);
        	
        	totalCarsWaitingGraph.get(0).add(lastGraphPosition);
        	totalCarsWaitingGraph.get(1).add(totalNumberOfCarswaiting);

        	// Update the graph line.
        	if(occupiedPlacesGraph) {
        		graphLine.updateXYSeries("Bezette plekken", totalCarsGraph.get(0), totalCarsGraph.get(1), null);
        	}
        	
        	if(totalwaitingCarsGraph) {
        		graphLine.updateXYSeries("Wachtende auto's", totalCarsWaitingGraph.get(0), totalCarsWaitingGraph.get(1), null);
        	}

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
	
			default:
				throw new IllegalArgumentException("For some reason noting happend...");
		}
    	
    	graphLine.getStyler().setSeriesColors(colors);
    }
}
