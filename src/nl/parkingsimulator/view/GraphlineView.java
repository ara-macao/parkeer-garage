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

    /**
     * Constructor for objects of class CarPark
     */
    public GraphlineView(AbstractModel model) {
        super(model);
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
         
        int numberOfOpenSpots = model.getNumberOfOpenSpots();
        int numberOfSpots = model.getNumberOfSpots();
        
        System.out.println(numberOfOpenSpots + " " + numberOfSpots);
        

        // Update the view (repaint)
        super.updateView();
    }
    
    private void createGraphHolder(Graphics g, Dimension dim) {
        g.drawString("0", 0, dim.height); // 0 in the left under corner.
    }
}
