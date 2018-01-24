package nl.parkingsimulator.view;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import java.awt.*;

/**
 *
 * @author Jeroen Westers
 */
public class TextView extends AbstractView {

    /**
     * Constructor for objects of class CarPark
     */
    public TextView(AbstractModel model) {
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

    /**
     * Overridden. Paints all the elements on the screen
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        CarParkModel model = (CarParkModel) getModel();
        if(model != null){
            g.drawString("Opbrengst vandaag:", 25, 25);
            g.drawString("Verwachte Opbrengst niet betaald:", 25, 40);
        }
    }
}

// je moet in deze view
// de opbrengst van de dag weergeven en de verwachtte opbrengst van de klanten die nog in de
// garage aanwezig zijn maar nog niet hebben betaald. Maak gebruik van de AbstractView structuur
// die je in het Life project dat in de MVC projecten zit kunt vinden.

// Opbrengtst van de dag
// Verwachte opbrengst van de klanten nog in garage niet betaald
