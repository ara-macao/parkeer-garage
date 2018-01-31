package nl.parkingsimulator.view;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.LegendPosition;
/**
 * The histogram view shows how many cars are in specific queues
 * @author GraphX
 */
public class HistogramView extends AbstractView {
    
    private int adHocQueue;
    private int passQueue;
    private int exitQueue;
    private int paymentQueue;
    private double maxHeight = 1000.0;
       
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
        
        histogram.getStyler().setLegendPosition(LegendPosition.InsideNE);
        histogram.getStyler().setHasAnnotations(true);
        histogram.getStyler().setLegendVisible(false);
        //max height reflects the amount of cars before people start to leave.
        histogram.getStyler().setYAxisMax((double)maxHeight);
        
        Color[] colors = { Color.ORANGE };
        histogram.getStyler().setSeriesColors(colors);

        // add the queue series to the histogram.
        histogram.addSeries("queues", new ArrayList<>(Arrays.asList(new String[] { "Ingang", "Pas ingang", "uitgang", "betalen"})), Arrays.asList(new Integer[] { adHocQueue, passQueue, exitQueue, paymentQueue}));
        
        swingWrapper = new SwingWrapper<CategoryChart>(histogram);
        JFrame frame = swingWrapper.displayChart();
        javax.swing.SwingUtilities.invokeLater(()->frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));
        javax.swing.SwingUtilities.invokeLater(()->frame.setBounds(this.model.getSettings().getHistogramPosition().x, this.model.getSettings().getHistogramPosition().y, this.model.getSettings().getHistogramDimensions().width, this.model.getSettings().getHistogramDimensions().height));
    }
    
    public void getValues () {
        CarParkModel model = (CarParkModel)getModel();
        // get all necessary data from the model
        if(model != null){
            adHocQueue = model.getEntranceCarQueue();
            passQueue = model.getEntrancePassQueue();
            exitQueue = model.getExitCarQueue();
            paymentQueue = model.getPaymentCarQueue();
        }
    }
    
    @Override
    public void updateView() {
        getValues();
        
        histogram.updateCategorySeries("queues", new ArrayList<>(Arrays.asList(new String[] { "Ingang", "Pas ingang", "uitgang", "betalen"})), Arrays.asList(new Integer[] { adHocQueue, passQueue, exitQueue, paymentQueue}), null);
        // update the view
        swingWrapper.repaintChart();
    }
}