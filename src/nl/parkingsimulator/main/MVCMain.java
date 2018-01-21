package nl.parkingsimulator.main;

import javax.swing.*;
import nl.parkingsimulator.controller.*;
import nl.parkingsimulator.logic.*;
import nl.parkingsimulator.view.*;

public class MVCMain {
    private AbstractModel model;
    private JFrame screen;
    private AbstractView carParkView;
    private AbstractController controller;

    public MVCMain() {
        
        model = new CarParkModel(3, 6, 30);
        controller=new Controller(model);
        carParkView=new CarParkView(model);

        screen=new JFrame("Model View Controller/Dynamic Model with thread");
        screen.setSize(1000, 500);
        screen.setResizable(false);
        screen.setLayout(null);
        screen.getContentPane().add(carParkView);
        screen.getContentPane().add(controller);
        carParkView.setBounds(50, 50, 900, 400);
        controller.setBounds(0, 210, 450, 50);
        screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        screen.setVisible(true);

        
        CarParkModel carModel = (CarParkModel)model;
        carModel.startSimulation();
    }
}
