/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 *
 * @author Thom
 */
public class GraphlineView extends AbstractView { 
    private int numberOfOpenSpots;
    private int numberOfSpots;
    private int currentMinute;
    private int minuteSinceStart;
    private float horizontalGraphPosition;
    private int verticalGraphposition;
    private int lastGraphPosition;
    
    private ArrayList<Point> graphvalues; 
    
    Dimension dimensions;

    /**
     * Constructor for objects of class CarPark
     */
    public GraphlineView(AbstractModel model, Dimension dimensions) {
        super(model);
        setSize(dimensions);
        this.dimensions = dimensions;

        numberOfOpenSpots = 0;
        numberOfSpots = 0;
        currentMinute = 0;
        minuteSinceStart = 0;
        horizontalGraphPosition = 0;
        verticalGraphposition = 0;
        lastGraphPosition = 0;
        
        graphvalues = new ArrayList<Point>();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        //dimensions = this.getSize();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, dimensions.width, dimensions.height); // Background.
        
        g.setColor(Color.BLACK);   
        createGraphHolder(g);
        
        g.setColor(Color.RED);
        createGraphLine(g);
    }

    @Override
    public void updateView() {
        CarParkModel model = (CarParkModel)getModel();
         
        numberOfOpenSpots = model.getNumberOfOpenSpots();
        numberOfSpots = model.getNumberOfSpots();
        currentMinute = model.getMinute();
        minuteSinceStart = model.getTotalTicks();
        
        addGraphValues();
        
        // Update the view (repaint)
        super.updateView();
    }
    
    private void addGraphValues() {
        horizontalGraphPosition = ((float)dimensions.width / 10080) * minuteSinceStart; // 10080 mins in a week. 1440 mins in a day.
        verticalGraphposition = Math.round(((float)dimensions.height / numberOfSpots) * numberOfOpenSpots);
        
        if(horizontalGraphPosition > lastGraphPosition) {
            graphvalues.add(new Point(lastGraphPosition, verticalGraphposition));
            
            //System.out.println(graphvalues.get(lastGraphPosition));
            lastGraphPosition++;
        }
    }
    
    private void createGraphLine(Graphics g) {
        Point previousCoordinate = new Point(0, dimensions.height);
                
        for(Iterator<Point> i = graphvalues.iterator(); i.hasNext();) {
            Point coordinate = i.next();
            
            g.drawLine(previousCoordinate.x, previousCoordinate.y, coordinate.x, coordinate.y);
            //g.fillRect(coordinate.x, coordinate.y, 2, 2); 
            
            previousCoordinate = coordinate;
        }
    }
    
    private void createGraphHolder(Graphics g) {
        g.drawString("0", 0, dimensions.height); // 0 in the left under corner.
        g.drawString(String.valueOf(numberOfSpots), 0, 10); // Number of total spots in the left upper corner. 10 for font height.
        g.drawString("1 Week", dimensions.width - g.getFontMetrics().stringWidth("1 Week"), dimensions.height);
    }
}
