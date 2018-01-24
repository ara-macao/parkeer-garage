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
 * PieChartView - This chart shows a pie chart of the current spots in use.
 * @author Robin de Man
 */

public class PieChartView extends AbstractView { ;
    
    private int openSpots;
    private int totalSpots;
    private int takenSpots;
    private double percOpenSpots;
    private double percTakenSpots;
    private int degrees;
    private int startPosition;
    /**
     * Constructor for objects of class CarPark
     * @param model
     */
    public PieChartView (AbstractModel model) {
        super(model);
    }
    
    /**
     * Calculation of the Pie chart slices.
     */
    public void PieChartCalc(){
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            //request all data needed
            openSpots = model.getNumberOfOpenSpots();
            totalSpots = model.getNumberOfSpots();
            takenSpots = totalSpots - openSpots;
            //create percentages for pie chart slices
            percOpenSpots = (openSpots * 100.0f) / totalSpots;
            percTakenSpots = (takenSpots * 100.0f) / totalSpots;
        }
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
     * @param g the graphic object
     */
    @Override
    public void paintComponent(Graphics g) {
        PieChartCalc();
        //set background colour
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        //Draw the first slice
        startPosition = -1;
        g.setColor(Color.LIGHT_GRAY);
        degrees =(int)(percOpenSpots * 360/100);
        g.fillArc(10, 10, 180, 180, startPosition, degrees);
        startPosition = degrees;
        //Draw the second slice
        g.setColor(Color.RED);
        degrees = (int)(percTakenSpots * 360/100);
        g.fillArc(10, 10, 180, 180, startPosition, degrees);
    }

    @Override
    public void updateView() {
        CarParkModel model = (CarParkModel)getModel();
        //Update the view (repaint)
        super.updateView();
    }
}

