/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

    /**
     * Constructor for objects of class CarPark
     */
    public GraphlineView(AbstractModel model) {
        super(model);

        numberOfOpenSpots = 0;
        numberOfSpots = 0;
        currentMinute = 0;
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        Dimension dim = this.getSize();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, dim.width, dim.height); // Background.
        
        g.setColor(Color.BLACK);
        g.drawString(" TEST " + dim.width, 25, 25);
        
        createGraphHolder(g, dim);
    }

    @Override
    public void updateView() {
        CarParkModel model = (CarParkModel)getModel();
         
        numberOfOpenSpots = model.getNumberOfOpenSpots();
        numberOfSpots = model.getNumberOfSpots();
        currentMinute = model.getMinute();
        
        //System.out.println(numberOfOpenSpots + " " + numberOfSpots);
        
        // Update the view (repaint)
        super.updateView();
    }
    
    private void createGraphHolder(Graphics g, Dimension dim) {
        g.drawString("0", 0, dim.height); // 0 in the left under corner.
        g.drawString(String.valueOf(numberOfSpots), 0, 10); // Number of total spots in the left upper corner. 10 for font height.
        //g.drawString(String.valueOf(currentMinute), dim.width - g.getFontMetrics().stringWidth(String.valueOf(currentMinute)), dim.height);
    }
}
