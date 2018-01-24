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

public class PieChartView extends AbstractView { ;
    
    private int openSpots;
    private int totalSpots;
    private int takenSpots;
    private double percOpenSpots;
    private double percTakenSpots;
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
        model.getNumberOfSpots();
        if(model != null){
            openSpots = model.getNumberOfSpots();
            totalSpots = model.getNumberOfPlaces();
            takenSpots = totalSpots - openSpots;
            percOpenSpots = ((openSpots * 100.0f) / totalSpots) * 3.6;
            percTakenSpots = ((takenSpots * 100.0f) / totalSpots) * 3.6;
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
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        
        g.setColor(Color.BLUE);
        g.fillArc(10, 10, 180, 180, 0, (int) percOpenSpots);
        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(10, 10, 180, 180, 0, (int) percTakenSpots);
    }

    @Override
    public void updateView() {
        CarParkModel model = (CarParkModel)getModel();
        //Update the view (repaint)
        super.updateView();
        System.out.println("updateview wordt aangeroepen.");
    }
}

