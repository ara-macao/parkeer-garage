package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*; // temporaly

/**
 *
 * @author Jeroen Westers
 */
public class TickController extends AbstractController implements ActionListener, ChangeListener {

    private JTextField tickAmountField;
    private JButton runButton;

    private JTextField tickPauseField;
    private JButton tickPauseButton;

    private JButton pauseButton;
    private JButton resumeButton;

    JSlider tickRateSlider;
    private int miniumTick = 1;
    private int maxiumTick = 200;
    private int defaultTick = 100;

    /**
     * Constructor for objects of class CarPark
     * @param model The model where to data comes from
     */
    public TickController(AbstractModel model) {
        super(model);
        setSize(350, 200);
        setBackground(Color.green);

        tickAmountField = new JTextField();
        tickAmountField.setText("10080");  // 10080 = week,    1440 = dag
        runButton = new JButton("Run");
        runButton.addActionListener(this);

        resumeButton = new JButton("Resume");
        pauseButton = new JButton("Pause");

        tickPauseField = new JTextField();
        tickPauseField.setText("100");
        tickPauseButton = new JButton("Set tick pause");
        tickPauseButton.addActionListener(this);

        tickRateSlider = new JSlider(JSlider.HORIZONTAL,miniumTick, maxiumTick, defaultTick);
        tickRateSlider.addChangeListener(this);

        resumeButton.addActionListener(this);
        pauseButton.addActionListener(this);

        this.setLayout(null);

        add(tickAmountField);
        add(runButton);
        add(tickPauseField);
        add(tickPauseButton);
        add(tickRateSlider);
        add(resumeButton);
        add(pauseButton);

        int xPos = 10;
        int yPos = 10;
        int offset = 5;

        tickAmountField.setBounds(xPos, yPos, 120, 30);

        runButton.setBounds(xPos + tickAmountField.getWidth() + offset, yPos, 120, 30);
        yPos += offset + runButton.getHeight();

        tickPauseField.setBounds(xPos, yPos, 120, 30);
        tickPauseButton.setBounds(xPos + tickPauseField.getWidth() + offset, yPos, 120, 30);
        yPos += offset + tickPauseButton.getHeight();
        tickRateSlider.setBounds(xPos, yPos, 120, 30);

        yPos += offset + tickPauseButton.getHeight();
        resumeButton.setBounds(xPos, yPos, 120, 30);
        pauseButton.setBounds(xPos + resumeButton.getWidth() + offset, yPos, 120, 30);

        setVisible(true);
    }

    /**
     * Handles the actions from the button
     * @param e The event that has been fired
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CarParkModel parkModel = (CarParkModel)model;

        if(parkModel != null){
            if(e.getSource() == runButton){
                runSimulation(parkModel);
            }

            if(e.getSource() == tickPauseButton){
                try {
                    int tickAmount = parseIntValue(tickPauseField);
                    setTickPause(parkModel,tickAmount);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(e.getSource() == resumeButton){
                parkModel.setPauseState(false);
            }

            if(e.getSource() == pauseButton){
                parkModel.setPauseState(true);
            }
        }



    }

    @Override
    public void stateChanged(ChangeEvent e){
        CarParkModel parkModel = (CarParkModel)model;

        if(e.getSource() == tickRateSlider){
            setTickPause(parkModel, tickRateSlider.getValue());
        }
    }

    /**
     * Starts the simulator of the given model
     * @param parkModel The model we use for the simulation
     */
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

    /**
     * pause the simulator
     * @param parkModel The model we want to pause
     */
    private void setTickPause(CarParkModel parkModel, int tickRate){
        if(tickRate > 0){
            if(tickRate >= maxiumTick)
                tickRate = maxiumTick;

            System.out.println("Huh" + tickRate);
            parkModel.setTickPause(tickRate);

            tickRateSlider.setValue(tickRate);
            tickPauseField.setText(Integer.toString(tickRate));
        }
    }

    /**
     * Parses the value of an inputfield to an int
     * @param input The input field we need to reed our integes from
     */
    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }

}