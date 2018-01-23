package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import java.awt.*;

public class TimeView extends AbstractView {

    /**
     * Constructor for objects of class CarPark
     */
    public TimeView(AbstractModel model) {
        super(model);
    }

    @Override
    public void updateView() {

        // Update the view (repaint)
        //g.drawString("abc", 25, 25);

        super.updateView();
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(400, 100);
    }

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