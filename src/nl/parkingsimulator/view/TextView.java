package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jeroen Westers
 */
public class TextView extends AbstractView {

    private JLabel totalRevenueLabel;
    private JLabel notPayedRevenueLabel;
    private JLabel missedCars;
    private JLabel currentEvent;

    /**
     * Constructor for objects of class CarPark
     * @param model The model where to data comes from
     */
    public TextView(AbstractModel model) {
        super(model);

        setSize(getPreferredSize());
        setLayout(null);

        totalRevenueLabel = new JLabel();
        notPayedRevenueLabel = new JLabel();
        currentEvent = new JLabel();

        missedCars = new JLabel();
        missedCars.setBounds(0, 10, 200, 10);
        add(missedCars);


        totalRevenueLabel.setBounds(0, 20, 200, 10);
        notPayedRevenueLabel.setBounds(0, 30, 200, 10);

        add(totalRevenueLabel);
        add(notPayedRevenueLabel);


        currentEvent.setBounds(0, 40, 200, 10);
        add(currentEvent);


    }

    /**
     * Overidden. Tells to update the labels and updates the view
     */
    @Override
    public void updateView() {

        // Update the view (repaint)
        //g.drawString("abc", 25, 25);

        CarParkModel model = (CarParkModel) getModel();
        if(model != null){
            missedCars.setText("Missed cars: " + model.getMissedCarsMinute());

            String totalRevenue = formatMoney(model.getRevenue());
            totalRevenueLabel.setText("Opbrengst vandaag: " + totalRevenue);

            String revenueNotPayed = formatMoney(model.getRevenueNotPayed());
            notPayedRevenueLabel.setText("Nog te betalen: " + revenueNotPayed);

            currentEvent.setText("Huidige event: " + model.getEventTitle());
        }

        super.updateView();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(400, 220);
    }

    /**
     * Overridden. Draws all the elements on the screen
     * @param g The graphics to draw on
     */
    @Override
    public void paintComponent(Graphics g) {

    }

    private String formatMoney(double money){
        return String.format("%.2f", money);
    }
}

// je moet in deze view
// de opbrengst van de dag weergeven en de verwachtte opbrengst van de klanten die nog in de
// garage aanwezig zijn maar nog niet hebben betaald. Maak gebruik van de AbstractView structuur
// die je in het Life project dat in de MVC projecten zit kunt vinden.

// Opbrengtst van de dag
// Verwachte opbrengst van de klanten nog in garage niet betaald
