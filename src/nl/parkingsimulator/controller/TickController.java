package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // temporaly

public class TickController extends AbstractController implements ActionListener {

    private JTextField tickAmountField;
    private JButton runButton;

    private JTextField tickPauseField;
    private JButton tickPauseButton;



    public TickController(AbstractModel model) {
        super(model);
        setSize(140, 300);
        setBackground(Color.green);

        tickAmountField = new JTextField();
        tickAmountField.setText("10080");  // 10080 = week,    1440 = dag
        runButton = new JButton("Run");
        runButton.addActionListener(this);

        tickPauseField = new JTextField();
        tickPauseField.setText("100");
        tickPauseButton = new JButton("Set tick pause");
        tickPauseButton.addActionListener(this);

        this.setLayout(null);

        add(tickAmountField);
        add(runButton);
        add(tickPauseField);
        add(tickPauseButton);

        tickAmountField.setBounds(10, 10, 120, 30);
        runButton.setBounds(10, 50, 120, 30);
        tickPauseField.setBounds(10, 90, 120, 30);
        tickPauseButton.setBounds(10, 130, 120, 30);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CarParkModel parkModel = (CarParkModel)model;

        if(parkModel != null){
            if(e.getSource() == runButton){
                runSimulation(parkModel);
            }

            if(e.getSource() == tickPauseButton){
                setTickPause(parkModel);
            }
        }



    }

    private void runSimulation(CarParkModel parkModel){
        try {
            int tickAmount = parseIntValue(tickAmountField);

            if(tickAmount > 0){
                parkModel.setAmountOfTicks(tickAmount);
                parkModel.startSimulation();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setTickPause(CarParkModel parkModel){
        try {
            int tickPause = parseIntValue(tickPauseField);

            if(tickPause > 0){
                parkModel.setTickPause(tickPause);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }

}