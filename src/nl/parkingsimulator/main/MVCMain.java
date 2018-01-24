package nl.parkingsimulator.main;

import javax.swing.*;

import nl.parkingsimulator.controller.*;
import nl.parkingsimulator.logic.*;
import nl.parkingsimulator.view.*;

import java.awt.*;

public class MVCMain {
    private AbstractModel model;
    private JFrame screen;
    
    private AbstractView carParkView;
    private AbstractController tickController;
    private AbstractView timeView;
    private AbstractView graphLineView;
    ReservationView reservationView;
    private AbstractController pieChartController;
    private TextView textView;
    
    private Settings settings;

    public MVCMain() {

        new MVCScreen(); // Testing screen

        settings = new Settings();

        model = new CarParkModel(settings.getParkingFloors(), settings.getParkingRows(), settings.getParkingPlacesPerRow(), settings);
        tickController = new TickController(model);
        pieChartController = new PieChartController(model);

        carParkView = new CarParkView(model);
        textView = new TextView(model);
        timeView = new TimeView(model);
        graphLineView = new GraphlineView(model, settings.graphLineDimensions);
        reservationView = new ReservationView(model);

        screen = new JFrame(settings.screenName);
        screen.setSize(settings.screenDimension);
        screen.setResizable(settings.screenIsResizable);
        screen.setLayout(null);

        addNewElement(carParkView, settings.carParkViewPosition.x, settings.carParkViewPosition.y, settings.carParkViewDimensions.width, settings.carParkViewDimensions.height);
        addNewElement(textView, settings.textViewPosition.x, settings.textViewPosition.y, settings.textViewDimensions.width, settings.textViewDimensions.height);
        addNewElement(timeView, settings.timeViewPosition.x, settings.timeViewPosition.y, settings.timeViewDimensions.width, settings.timeViewDimensions.height);

        timeView.setOpaque(false);
        textView.setOpaque(false); // prevent drawing glitch, should be looked into
        graphLineView.setOpaque(false);

        screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // JFrame.DISPOSE_ON_CLOSE
        screen.setVisible(true);

        //CarParkModel carModel = (CarParkModel)model;
        //carModel.startSimulation();

        /*
          Window with the PieChartView
          Spawns window
         */
        
        // TODO implement PieChartView just like controller frame and graphlineFrame
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Taart Diagram");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(200, 350);

        PieChartView panel;
        panel = new PieChartView(model);
        frame.add(panel);
        frame.add(pieChartController);
        panel.setBounds(0, 0, 200, 200);
        pieChartController.setBounds(0, 200, 200, 100);
        frame.setVisible(true);
        
        //Frame pieChartFrame = windowBuilder(settings.pieChartName, Color.red, settings.pieChartDimensions, settings.pieChartPosition);
        //pieChartFrame.add(pieChartController);

        JFrame controllerFrame = windowBuilder(settings.tickControllerName, Color.red, settings.tickControllerDimensions, settings.tickControllerPosition);
        controllerFrame.add(tickController);
        
        JFrame graphLineFrame = windowBuilder(settings.graphLineName, Color.red, settings.graphLineDimensions , settings.graphLinePosition);
        graphLineFrame.add(graphLineView);

        JFrame reservationsFrame = windowBuilder(settings.reservationsName, Color.red, settings.reservationsDimensions, settings.reservationsPosition);
        reservationsFrame.add(reservationView);
    }


    private void addNewElement(JPanel view, int x, int y, int width, int height){
        screen.getContentPane().add(view);
        view.setBounds(x,y,width, height);
    }

    /**
     * Creates a frame to be used for the viewers and controllers
     * @param title the title for the frame
     * @param backgroundColor the backgroundcolor for the frame
     * @param dimension the dimension of the frame
     * @param location the location of the frame i the screen
     * @return the frame that has been made
     */
    private JFrame windowBuilder(String title, Color backgroundColor, Dimension dimension, Point location) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame(title);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(backgroundColor);
        frame.setSize(dimension);
        frame.setVisible(true);

        frame.setLocation(location);

        return frame;
    }
}
