package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // temporaly

public class TickController extends AbstractController implements ActionListener {

    private JTextField tickAmountField;
    private JButton runButton;

    public TickController(AbstractModel model) {
        super(model);
        setSize(100, 200);
        setBackground(Color.green);

        tickAmountField = new JTextField();
        runButton = new JButton("Run");


        runButton.addActionListener(this);
        this.setLayout(null);
        tickAmountField.setBounds(10, 10, 70, 30);
        runButton.setBounds(10, 50, 70, 30);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            CarParkModel parkModel = (CarParkModel)model;

            if(parkModel != null){
                int tickAmount = parseIntValue(tickAmountField);
                parkModel.setAmountOfTicks(tickAmount);
                parkModel.startSimulation();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }

}