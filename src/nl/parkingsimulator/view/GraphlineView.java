/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.Car;
import nl.parkingsimulator.logic.CarParkModel;
import nl.parkingsimulator.logic.Location;

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

    /**
     * Overriden. The car parf view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    @Override
    public void paintComponent(Graphics g) {
        
        g.setColor(Color.red);
        g.fillRect(0, 0, 10, 10);
        
        g.drawString(" TEST ", 25, 25);

    }

    @Override
    public void updateView() {

         CarParkModel model = (CarParkModel)getModel();


        // Update the view (repaint)
        super.updateView();

    }
}
