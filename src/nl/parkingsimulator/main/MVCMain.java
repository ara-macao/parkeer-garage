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
    private AbstractController controller;
    private AbstractView timeView;
    private AbstractView graphlineView;

    private TextView textView;

    public MVCMain() {
        
        model = new CarParkModel(3, 6, 30);
        controller=new Controller(model);

        carParkView=new CarParkView(model);
        textView=new TextView(model);
        timeView = new TimeView(model);
        graphlineView = new GraphlineView(model);

        screen=new JFrame("Parkeer garage");
        screen.setSize(1000, 500);
        screen.setResizable(false);
        screen.setLayout(null);

        addNewView(screen, carParkView, 0, 50, 1000, 400);
        addNewView(screen, textView, 100, 0, 300, 100);
        addNewView(screen, timeView, 400, 0, 100, 50);
        addNewView(screen, graphlineView, 600, 0, 100, 50);

        timeView.setOpaque(false);
        textView.setOpaque(false); // prevent drawing glitch, should be looked into
        graphlineView.setOpaque(false);

        screen.getContentPane().add(controller);

        controller.setBounds(0, 210, 450, 50);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame.DISPOSE_ON_CLOSE
        screen.setVisible(true);

        CarParkModel carModel = (CarParkModel)model;
        carModel.startSimulation();
    }


    public void addNewView(JFrame screen, AbstractView view, int x, int y, int width, int height){
        screen.getContentPane().add(view);
        view.setBounds(x,y,width, height);
    }
}
