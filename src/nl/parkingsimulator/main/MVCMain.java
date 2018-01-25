package nl.parkingsimulator.main;

import javax.swing.*;

import nl.parkingsimulator.controller.*;
import nl.parkingsimulator.logic.*;
import nl.parkingsimulator.view.*;

import java.awt.*;

public class MVCMain {
    private AbstractModel model;
    private JFrame screen;
    
    private AbstractController tickController;
    private SettingsController settingsController;
    private GraphLineController graphLineController;
    private AbstractController pieChartController;
    
    private AbstractView carParkView;
    private AbstractView timeView;
    private AbstractView graphLineView;
    ReservationView reservationView;  
    private TextView textView;
    
    private Settings settings;

    public MVCMain() {

        new MVCScreen(); // Testing screen

        settings = new Settings();

        model = new CarParkModel(settings);
        tickController = new TickController(model); 
        pieChartController = new PieChartController(model);

        carParkView = new CarParkView(model);
        textView = new TextView(model);
        timeView = new TimeView(model);
        reservationView = new ReservationView(model);
        graphLineView = new GraphLineView(model);

        /**
         * Creating the main screen...
         */
        JFrame.setDefaultLookAndFeelDecorated(settings.getDefaultLookAndFeel());
        screen = new JFrame(settings.getScreenName());
        screen.setSize(settings.getScreenDimension());
        screen.setResizable(settings.getScreenIsResizable());
        screen.setLayout(null);
        screen.setLocation(settings.getCarParkViewPosition());
        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // JFrame.DISPOSE_ON_CLOSE
        screen.setVisible(true);
        
        /**
         * Add elements to the main screen.
         */
        addNewElement(carParkView, settings.getCarParkViewPosition().x, settings.getCarParkViewPosition().y, settings.getCarParkViewDimensions().width, settings.getCarParkViewDimensions().height);
        addNewElement(textView, settings.getTextViewPosition().x, settings.getTextViewPosition().y, settings.getTextViewDimensions().width, settings.getTextViewDimensions().height);
        addNewElement(timeView, settings.getTimeViewPosition().x, settings.getTimeViewPosition().y, settings.getTimeViewDimensions().width, settings.getTimeViewDimensions().height);

        /**
         * Prevents drawing glitch, should be looked into!
         */
        timeView.setOpaque(false);
        textView.setOpaque(false); 

        CarParkModel carModel = (CarParkModel)model;
        carModel.notifyViews();
        
        /**
         * Creating separate windows here...
         */
        Frame pieChartFrame = windowBuilder(settings.getPieChartName(), settings.getPieChartDimensions(), settings.getPieChartPosition());
        PieChartView piechartpanel;
        piechartpanel = new PieChartView(model);
        piechartpanel.setBounds(0, 0, 200, 200);
        pieChartFrame.add(piechartpanel);
        pieChartFrame.add(pieChartController);
        pieChartController.setBounds(0, 200, 200, 40);

        JFrame controllerFrame = windowBuilder(settings.getTickControllerName(), settings.getTickControllerDimensions(), settings.getTickControllerPosition());
        controllerFrame.add(tickController);

        JFrame graphLineFrame = windowBuilder(settings.getGraphLineControllerName(), settings.getGraphLineControllerDimensions() , settings.getGraphLineControllerPosition());
        //When giving the content pane size directly to the constructor of the settings controller the content inside the JFrame will have the correct dimensions.
        graphLineController = new GraphLineController(model, graphLineFrame.getContentPane().getSize());
        graphLineFrame.add(graphLineController);
        graphLineFrame.add(graphLineView);

        JFrame reservationsFrame = windowBuilder(settings.getReservationsName(), settings.getReservationsDimensions(), settings.getReservationsPosition());
        reservationsFrame.add(reservationView);
        
        JFrame settingsFrame = windowBuilder(settings.getSettingsControllerName(), settings.getSettingsControllerDimensions(), settings.getSettingsControllerPosition());       
        //When giving the content pane size directly to the constructor of the settings controller the content inside the JFrame will have the correct dimensions.
        settingsController = new SettingsController(model, settingsFrame.getContentPane().getSize());
        settingsFrame.add(settingsController);
        
        Frame histogramFrame = windowBuilder(settings.getHistogramName(), settings.getHistogramDimensions(), settings.getHistogramPosition());
        HistogramView histogrampanel;
        histogrampanel = new HistogramView(model);
        histogrampanel.setBounds(0, 0, 200, 200);
        histogramFrame.add(histogrampanel);
    }

    private void addNewElement(JPanel view, int x, int y, int width, int height) {
        screen.getContentPane().add(view);
        view.setBounds(x,y,width, height);
    }

    /**
     * Creates a frame to be used for the viewers and controllers
     * 
     * @param title the title for the frame
     * @param backgroundColor the background color for the frame
     * @param dimension the dimension of the frame
     * @param location the location of the frame i the screen
     * @return the frame that has been made
     */
    private JFrame windowBuilder(String title, Dimension dimension, Point location) {
        JFrame.setDefaultLookAndFeelDecorated(settings.getDefaultLookAndFeel());
        JFrame frame = new JFrame(title);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Color.GRAY);
        frame.setSize(dimension);
        frame.setVisible(true);

        frame.setLocation(location);

        return frame;
    }
}
