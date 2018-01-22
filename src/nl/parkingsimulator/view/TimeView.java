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
            String tijd = "";

            String dag = "Dag:" + model.getDay();

            int hour = model.getHour();
            int minute = model.getMinute();

            if(hour < 10)
                tijd += "0" + hour;
            else
                tijd += hour;

            tijd += ":";

            if(minute < 10)
                tijd += "0" + minute;
            else
                tijd += minute;

            g.drawString(dag + " - " + tijd, 25, 25);
        }
    }
}
