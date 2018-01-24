package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jeroen Westers
 */
public class TimeView extends AbstractView {

    JProgressBar progressBar;

    /**
     * Constructor for objects of class CarPark
     * @param model The model where to data comes from
     */
    public TimeView(AbstractModel model) {
        super(model);
        setSize(getPreferredSize());
        setLayout(null);
        progressBar = new JProgressBar();
        progressBar.setBounds(0,30, 300, 20);
        progressBar.setMinimum(0);
        add(progressBar);
    }

    /**
     * Overidden. Tells to update the labels and updates the view
     */
    @Override
    public void updateView() {

        // Update the view (repaint)
        //g.drawString("abc", 25, 25);
        CarParkModel model = (CarParkModel) getModel();

        if(model!= null){
            progressBar.setMaximum(model.getAmountOfTicks());
            progressBar.setValue(model.getTickProgress());
        }

        super.updateView();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(400, 100);
    }

    /**
     * Overridden. Draws all the elements on the screen
     * @param g The graphics to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        CarParkModel model = (CarParkModel) getModel();

        if(model != null){
            String time = "";

            String dag = model.getDayName();

            int hour = model.getHour();
            int minute = model.getMinute();
            int max = 10;

            time += getDoubleTime(hour, max);
            time += ":";
            time += getDoubleTime(minute, max);

            g.drawString(dag + " - " + time, 25, 25);
        }
    }

    private String getDoubleTime(int number, int max){
        if(number < max){
            return "0" + number;
        }else{
            return Integer.toString(number);
        }
    }
}