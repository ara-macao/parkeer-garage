/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;
import java.awt.Color;
import java.awt.Graphics;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
/**
 * The histogram view gives an overview of statistics in rectangles
 * @author GraphX
 */
public class HistogramView extends AbstractView {
    
    private int adHocQueue;
    private int passQueue;
    private int exitQueue;
    
    public HistogramView (AbstractModel model) {
        super(model);
    }
    
    public void getValues (){
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            adHocQueue = model.getEntranceCarQueue();
            passQueue = model.getEntrancePassQueue();
            exitQueue = model.getExitCarQueue();
        }
        
    }

    public void paintComponent(Graphics g) {
        getValues();
        //set background colour
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        //Draw the first rectangle
        g.setColor(Color.RED);
        g.fillRect(25, 100, 25, adHocQueue);
        //Draw the second rectangle
        g.setColor(Color.BLUE);
        g.fillRect(50, 100, 25, passQueue);
        //Draw the third rectangle
        g.setColor(Color.YELLOW);
        g.fillRect(75, 100, 25, exitQueue);
    }

    @Override
    public void updateView() {
        CarParkModel model = (CarParkModel)getModel();
        //Update the view (repaint)
        super.updateView();
    }
    
}