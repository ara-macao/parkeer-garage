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
    private AbstractView graphlineView;

    private TextView textView;

    public MVCMain() {
        
        model = new CarParkModel(3, 6, 30);
        tickController=new TickController(model);

        carParkView=new CarParkView(model);
        textView=new TextView(model);
        timeView = new TimeView(model);
        graphlineView = new GraphlineView(model);

        screen=new JFrame("Parkeer garage");
        screen.setSize(1200, 800);
        screen.setResizable(false);
        screen.setLayout(null);

        addNewElement(carParkView, 0, 50, 1000, 400);
        addNewElement(textView, 50, 0, 300, 100);
        addNewElement(timeView, 400, 0, 200, 50);
        addNewElement(graphlineView, 10, 460, 600, 300);
        addNewElement(tickController, 650, 460, 140, 300);

        timeView.setOpaque(false);
        textView.setOpaque(false); // prevent drawing glitch, should be looked into
        graphlineView.setOpaque(false);


        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.DISPOSE_ON_CLOSE
        screen.setVisible(true);

        //CarParkModel carModel = (CarParkModel)model;
        //carModel.startSimulation();

        /**
         * Window with the PieChartView
         * Spawns window 
         */
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Taart Diagram");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(300, 200);

        PieChartView panel;
        panel = new PieChartView(model);
        frame.add(panel);
        frame.setVisible(true);
    }


    public void addNewElement(JPanel view, int x, int y, int width, int height){
        screen.getContentPane().add(view);
        view.setBounds(x,y,width, height);
    }
}
