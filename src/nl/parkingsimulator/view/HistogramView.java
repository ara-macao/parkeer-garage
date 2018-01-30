/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
/**
 * The histogram view gives an overview of statistics in rectangles
 * @author GraphX
 */
public class HistogramView extends AbstractView {
    
    private int adHocQueue;
    private int passQueue;
    private int exitQueue;
    
    private CategoryChart histogram;
    private SwingWrapper<CategoryChart> swingWrapper;
    
    private CarParkModel model;
    
    public HistogramView (AbstractModel model) {
        super(model);
        this.model = (CarParkModel)getModel();
         
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());
/**
        // Create Chart
        histogram = new CategoryChartBuilder().title(this.model.getSettings().getHistogramName()).build();
        
        swingWrapper = new SwingWrapper<CategoryChart>(histogram);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getHistogramPosition().x, this.model.getSettings().getHistogramPosition().y, this.model.getSettings().getHistogramDimensions().width, this.model.getSettings().getHistogramDimensions().height));
    */
    }
    
    public void getValues (){
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            adHocQueue = model.getEntranceCarQueue();
            passQueue = model.getEntrancePassQueue();
            exitQueue = model.getExitCarQueue();
        }
        
    }
/**
    @Override
    public void paintComponent(Graphics g) {
        getValues();
        //set background colour
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);
        //Draw the first rectangle
        g.setColor(Color.RED);
        g.fillRect(25, 50, 25, adHocQueue);
        //Draw the second rectangle
        g.setColor(Color.BLUE);
        g.fillRect(50, 50, 25, passQueue);
        //Draw the third rectangle
        g.setColor(Color.YELLOW);
        g.fillRect(75, 50, 25, exitQueue);
    }
*/
    @Override
    public void updateView() {
        CarParkModel model = (CarParkModel)getModel();
        //Update the view (repaint)
        super.updateView();
    }
    
}