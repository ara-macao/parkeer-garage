package nl.parkingsimulator.view;

import javax.swing.*;
import nl.parkingsimulator.logic.*;

public abstract class AbstractView extends JPanel {
    protected AbstractModel model;

    public AbstractView(AbstractModel model) {
            this.model=model;
            model.addView(this);
    }

    public AbstractModel getModel() {
            return model;
    }

    public void updateView() {
            repaint();
    }
}
