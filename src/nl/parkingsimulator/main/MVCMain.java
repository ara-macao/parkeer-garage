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
    
    private Settings s;

    public MVCMain() {

        new MVCScreen(); // Testing screen

        s = new Settings();

        model = new CarParkModel(s.parkingFloors, s.parkingRows, s.parkingPlacesPerRow);
        tickController = new TickController(model);
        pieChartController = new PieChartController(model);

        carParkView = new CarParkView(model);
        textView = new TextView(model);
        timeView = new TimeView(model);
        graphLineView = new GraphlineView(model, s.graphLineDimensions);

        screen = new JFrame(s.screenName);
        screen.setSize(s.screenDimension);
        screen.setResizable(s.screenIsResizable);
        screen.setLayout(null);

        addNewElement(carParkView, s.carParkViewPosition.x, s.carParkViewPosition.y, s.carParkViewDimensions.width, s.carParkViewDimensions.height);
        addNewElement(textView, s.textViewPosition.x, s.textViewPosition.y, s.textViewDimensions.width, s.textViewDimensions.height);
        addNewElement(timeView, s.timeViewPosition.x, s.timeViewPosition.y, s.timeViewDimensions.width, s.timeViewDimensions.height);

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
        
        //Frame pieChartFrame = windowBuilder(s.pieChartName, Color.red, s.pieChartDimensions, s.pieChartPosition);
        //pieChartFrame.add(pieChartController);

        JFrame controllerFrame = windowBuilder(s.tickControllerName, Color.red, s.tickControllerDimensions, s.tickControllerPosition);
        controllerFrame.add(tickController);
        
        JFrame graphLineFrame = windowBuilder(s.graphLineName, Color.red, s.graphLineDimensions , s.graphLinePosition);
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
