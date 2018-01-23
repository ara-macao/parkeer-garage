/**
 * 
 */
package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTextField;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * PieChartView - This chart shows the amount of regular users and parking subscribers.
 * @author GraphX
 */

public class PieChartView extends AbstractView { 
    /**
     * Constructor for objects of class CarPark
     * @param model
     */
    public PieChartView (AbstractModel model) {
        super(model);
    }
    
    /**
     * Calculation of the Pie chart dimensions.
     */
    public void PieChartCalc(){
        //dummytext
    }
    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     * @return 
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        
        g.setColor(Color.BLUE);
        g.fillArc(10, 10, 180, 180, 0, 360);
        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(10, 10, 180, 180, 0, 5);
    }

    @Override
    public void updateView() {
        //CarParkModel model = (CarParkModel)getModel();
        // Update the view (repaint)
        //super.updateView();

    }
}

