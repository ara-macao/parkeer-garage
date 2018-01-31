package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import java.awt.*;

/**
 * Textview creates labels showing how many cars are missed, which event is happening, how many money there is earned and how many money you still get.
 * @author Jeroen Westers
 */
public class TextView extends AbstractView {

    private JLabel totalRevenueLabel;
    private JLabel notPayedRevenueLabel;
    private JLabel missedCars;
    private JLabel currentEvent;

    /**
     * Constructor for objects of class TextCiew
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

        CarParkModel model = (CarParkModel) getModel();
        if(model != null){
            missedCars.setText("Missed cars: " + model.getMissedCarsMinute()); // get missed cars

            String totalRevenue = formatMoney(model.getRevenue()); // get revenue
            totalRevenueLabel.setText("Opbrengst vandaag: " + totalRevenue); // show revenue

            String revenueNotPayed = formatMoney(model.getRevenueNotPayed()); // get not payed revenue
            notPayedRevenueLabel.setText("Nog te betalen: " + revenueNotPayed); // show not payed revenue

            currentEvent.setText("Huidige event: " + model.getEventTitle()); // Set current event
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

    /**
     * Converts the given amount to money format
     * @param money The number we need to convert.
     */
    private String formatMoney(double money){
        return String.format("%.2f", money); // format the giving amount to money format
    }
}