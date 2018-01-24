package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;

import java.awt.*;

public class ReservationView extends AbstractView {

    public ReservationView(AbstractModel model) {
        super(model);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);

        g.setColor(Color.BLUE);
        g.fillArc(10, 10, 180, 180, 0, 360);
        g.setColor(Color.LIGHT_GRAY);
        g.fillArc(10, 10, 180, 180, 0, 5);
    }
}
