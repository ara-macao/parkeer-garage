package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jeroen Westers
 * @author Thom van Dijk (repositioning of labels and removed hardcoded values)
 */
public class TimeView extends AbstractView {

    JProgressBar progressBar;
    JLabel dayLabel;
    JLabel timeLabel;

    /**
     * Constructor for objects of class TimeView
     * @param model The model where to data comes from
     * @param dimensions The dimension of the model
     */
    public TimeView(AbstractModel model, Dimension dimensions) {
        super(model);
        setSize(dimensions);
        setLayout(null);
        
        int borderTop = 20;
        int borderLeft = 0;
        int offset = 22;
        int textHeight = 15;
        int textWidth = 300;
        int barHeight = 15;
        int barWidth = 300;

        // Create components
        progressBar = (JProgressBar)addComponent(new JProgressBar(), borderLeft, borderTop, barWidth, barHeight);
        progressBar.setMinimum(0);

        dayLabel = (JLabel)addComponent(new JLabel(), borderLeft, borderTop + offset, textWidth, textHeight);
        timeLabel = (JLabel)addComponent(new JLabel(), borderLeft, borderTop + (offset * 2), textWidth, textHeight);
    }

    /**
     * Creates an element, adds it the the panel and sets the bounds
     * @param component The component we made.
     * @param xPos The x position of the component.
     * @param yPos The y position of the component.
     * @param width The width of the component
     * @param height The height of the component
     * @return component The component we have made and set
     */
    private JComponent addComponent(JComponent component, int xPos, int yPos, int width, int height){
        component.setBounds(xPos, yPos, width, height); // set position and size
        add(component); // add the component
        return component;
    }

    /**
     * Overridden. Tells to update the labels and updates the view
     */
    @Override
    public void updateView() {
        // get the model and cast it to the model type we need
        CarParkModel model = (CarParkModel) getModel();

        // if it is not null
        if(model!= null){
            // set the maxium and current value of the slider
            progressBar.setMaximum(model.getAmountOfTicks());
            progressBar.setValue(model.getTickProgress());


            // get the day, hour minutes
            String day = model.getDayName();
            int hour = model.getHour();
            int minute = model.getMinute();
            int max = 10; // maximum value to convert

            // String builder to create the time string
            StringBuilder builder = new StringBuilder();
            builder.append("Tijd: ");
            builder.append(getDoubleTime(hour, max));
            builder.append(":");
            builder.append(getDoubleTime(minute, max));

            // Set the labels with the day and time
            dayLabel.setText("Dag: " + day);
            timeLabel.setText(builder.toString());

        }

        super.updateView();
    }

    /**
     * Overridden. Draws all the elements on the screen
     * @param g The graphics to draw on
     */
    @Override
    public void paintComponent(Graphics g) {

    }

    /**
     * Parses the value of an inputfield to an int
     * @param number The number to convert
     * @param max The maximum value of number
     */
    private String getDoubleTime(int number, int max){
        if(number < max){
            return "0" + number;
        }else{
            return Integer.toString(number);
        }
    }
}