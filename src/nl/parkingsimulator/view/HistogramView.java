/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.view;
import java.util.Arrays;
import javax.swing.JFrame;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.LegendPosition;
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
        
        getValues();
         
        JFrame.setDefaultLookAndFeelDecorated(this.model.getSettings().getDefaultLookAndFeel());

        // Create Chart
        histogram = new CategoryChartBuilder().title(this.model.getSettings().getHistogramName()).build();
        
        // Customize Chart
        histogram.getStyler().setLegendPosition(LegendPosition.InsideNW);
        histogram.getStyler().setHasAnnotations(true);

        // Series
        histogram.addSeries("queues", Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }), Arrays.asList(new Integer[] { adHocQueue, passQueue, exitQueue, 6, 5 }));
        
        swingWrapper = new SwingWrapper<CategoryChart>(histogram);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getHistogramPosition().x, this.model.getSettings().getHistogramPosition().y, this.model.getSettings().getHistogramDimensions().width, this.model.getSettings().getHistogramDimensions().height));
    }
    
    public void getValues (){
        CarParkModel model = (CarParkModel)getModel();
        if(model != null){
            adHocQueue = model.getEntranceCarQueue();
            passQueue = model.getEntrancePassQueue();
            exitQueue = model.getExitCarQueue();
        }
        
    }
    public void updateView() {
        getValues();
        //histogram.updateCategorySeries("queues", Arrays.asList(new Integer[] { 0, 1, 2, 3, 4 }), Arrays.asList(new Integer[] { adHocQueue, passQueue, exitQueue, 6, 5 }));
        swingWrapper.repaintChart();
    }
}