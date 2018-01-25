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

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * @author Thom
 */
public class GraphLineView extends AbstractView { 
    private int numberOfOpenSpots;
    private int numberOfSpots;
    private int minuteSinceStart;
    private int updateFrequency;
    private int lastUpdate;
    private float horizontalGraphPosition;
    private int verticalGraphposition;
    private int lastGraphPosition;
    
    private ArrayList<ArrayList<Integer>> graphValues;
    
    private XYChart graphLine;
    private SwingWrapper<XYChart> swingWrapper;
    
    CarParkModel model;
    
    Dimension dimensions;

    /**
     * Constructor for objects of class CarPark
     */
    public GraphLineView(AbstractModel model, Dimension dimensions) {
        super(model);
        setSize(dimensions);
        
        this.model = (CarParkModel)getModel();
        this.dimensions = dimensions;

        numberOfOpenSpots = 0;
        numberOfSpots = 0;
        minuteSinceStart = 0;
        updateFrequency = 10;
        lastUpdate = 0;
        horizontalGraphPosition = 0;
        verticalGraphposition = 0;
        lastGraphPosition = 0;
  
        graphValues = new ArrayList<ArrayList<Integer>>();
        graphValues.add(new ArrayList<Integer>());
        graphValues.add(new ArrayList<Integer>());
        
        graphValues.get(0).add(0);
        graphValues.get(1).add(0);
        
        // Create Chart
        graphLine = QuickChart.getChart("Overzicht van bezette plekken", "Tijd", "Bezette PLekken", "bezette plekken", graphValues.get(0), graphValues.get(1));
        graphLine.getStyler().setYAxisMax((double)this.model.getNumberOfSpots());
        graphLine.getStyler().setXAxisMax((double)this.model.getSettings().getAmountOfTicks());

        // Show it
        swingWrapper = new SwingWrapper<XYChart>(graphLine);
        swingWrapper.displayChart();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        /*//dimensions = this.getSize();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, dimensions.width, dimensions.height); // Background.
        
        g.setColor(Color.RED);
        createGraphLine(g);
        
        g.setColor(Color.BLACK);   
        createGraphHolder(g);*/  
    }

    @Override
    public void updateView() {
        model = (CarParkModel)getModel();
         
        numberOfOpenSpots = model.getNumberOfOpenSpots();
        numberOfSpots = model.getNumberOfSpots();
        minuteSinceStart = model.getTotalTicks();
        
        addGraphValues();
        
        // Update every hour.
        if(minuteSinceStart <= lastUpdate) {
        	graphLine.updateXYSeries("bezette plekken", graphValues.get(0), graphValues.get(1), null);
            swingWrapper.repaintChart();
            
            lastUpdate = minuteSinceStart + updateFrequency;
        }
        
        // Update the view (repaint)
        super.updateView();
    }
    
    private void addGraphValues() {
        horizontalGraphPosition = ((float)dimensions.width / 10080) * minuteSinceStart; // 10080 mins in a week. 1440 mins in a day.
        verticalGraphposition =  numberOfSpots - numberOfOpenSpots;
        
        if(horizontalGraphPosition > lastGraphPosition) {
        	graphValues.get(0).add(lastGraphPosition);
        	graphValues.get(1).add(verticalGraphposition);
            
            //System.out.println(graphvalues.get(lastGraphPosition));
            lastGraphPosition++;
        }
    }
    
    /*private void createGraphLine(Graphics g) {
        Point previousCoordinate = new Point(horizontalGraphOffset + graphLineThickness, dimensions.height - verticalGraphOffset - outlineThickness);
                
        for(Iterator<Point> i = graphvalues.iterator(); i.hasNext();) {
            Point coordinate = i.next();
            
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setStroke(new BasicStroke(graphLineThickness));
            g2.drawLine(previousCoordinate.x, previousCoordinate.y, coordinate.x, coordinate.y);
            //g.fillRect(coordinate.x, coordinate.y, 2, 2); 
            
            previousCoordinate = coordinate;
        }
    }*/
    
    /*private void createGraphHolder(Graphics g) {
        g.drawString("0", 0, dimensions.height); // 0 in the left under corner.
        g.drawString(String.valueOf(numberOfSpots), 0, 10); // Number of total spots in the left upper corner. 10 for font height.
        g.drawString("1 Week", dimensions.width - g.getFontMetrics().stringWidth("1 Week"), dimensions.height);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(outlineThickness));
        g2.drawLine(0, dimensions.height - verticalGraphOffset, dimensions.width, dimensions.height - verticalGraphOffset);
        g2.drawLine(horizontalGraphOffset, 0, horizontalGraphOffset, dimensions.height);
    }*/
}
