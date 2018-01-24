package nl.parkingsimulator.main;

import javax.swing.*;

import javafx.stage.Screen;
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
    private AbstractController pieChartController;
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
        graphLineView = new GraphlineView(model, settings.getGraphLineDimensions());

        screen = new JFrame(settings.getScreenName());
        screen.setSize(settings.getScreenDimension());
        screen.setResizable(settings.getScreenIsResizable());
        screen.setLayout(null);

        addNewElement(carParkView, settings.getCarParkViewPosition().x, settings.getCarParkViewPosition().y, settings.getCarParkViewDimensions().width, settings.getCarParkViewDimensions().height);
        addNewElement(textView, settings.getTextViewPosition().x, settings.getTextViewPosition().y, settings.getTextViewDimensions().width, settings.getTextViewDimensions().height);
        addNewElement(timeView, settings.getTimeViewPosition().x, settings.getTimeViewPosition().y, settings.getTimeViewDimensions().width, settings.getTimeViewDimensions().height);

        timeView.setOpaque(false);
        textView.setOpaque(false); // prevent drawing glitch, should be looked into
        graphLineView.setOpaque(false);

        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.DISPOSE_ON_CLOSE
        screen.setVisible(true);

        //CarParkModel carModel = (CarParkModel)model;
        //carModel.startSimulation();

        /**
         * Window with the PieChartView
         * Spawns window 
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

        JFrame controllerFrame = windowBuilder(settings.getTickControllerName(), Color.red, settings.getTickControllerDimensions(), settings.getTickControllerPosition());
        controllerFrame.add(tickController);
        
        JFrame graphLineFrame = windowBuilder(settings.getGraphLineName(), Color.red, settings.getGraphLineDimensions() , settings.getGraphLinePosition());
        graphLineFrame.add(graphLineView);
    }


    public void addNewElement(JPanel view, int x, int y, int width, int height){
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
    public JFrame windowBuilder(String title, Color backgroundColor, Dimension dimension, Point location) {
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
