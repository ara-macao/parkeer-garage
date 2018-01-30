package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*; // temporaly
import java.util.Hashtable;

/**
 *
 * @author Jeroen Westers
 */
public class TickController extends AbstractController implements ActionListener, ChangeListener {

    JLabel amountLabel;
    private JTextField tickAmountField;
    private JButton runButton;

    private ButtonGroup buttonGroup;
    private JRadioButton minuteRadio;
    private JRadioButton hourRadio;
    private JRadioButton dayRadio;
    private JRadioButton weekRadio;

//    private JLabel minuteLabel;
//    private JRadioButton week;


    private JButton pauseButton;
    private JButton resumeButton;

    private JButton resetButton;

    JLabel tickLabel;
    JSlider tickRateSlider;
    private int miniumTick = 0;
    private int maxiumTick = 200;
    private int defaultTick = 100;

    /**
     * Constructor for objects of class CarPark
     * @param model The model where to data comes from
     */
    public TickController(AbstractModel model) {
        super(model);
        setSize(750, 200);
        //setBackground(Color.green);


        tickAmountField = new JTextField();
        tickAmountField.setText("1");  // 10080 = week,    1440 = dag
        runButton = new JButton("Start simulatie");
        runButton.addActionListener(this);
        runButton.setToolTipText("Click to start the simulation.");
        amountLabel = new JLabel("Simulatie lengte:");

        minuteRadio = new JRadioButton("Minuten", false);
        hourRadio = new JRadioButton("Uren", false);
        dayRadio = new JRadioButton("Dagen", false);
        weekRadio = new JRadioButton("Weken", true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(minuteRadio);
        buttonGroup.add(hourRadio);
        buttonGroup.add(dayRadio);
        buttonGroup.add(weekRadio);

        resumeButton = new JButton("Hervat");
        resumeButton.setToolTipText("Hervat de simulatie");
        pauseButton = new JButton("Pauzeer");
        pauseButton.setToolTipText("Pauzeert de simulatie");


        tickLabel = new JLabel("Simulatie snelheid:");
        tickRateSlider = new JSlider(JSlider.HORIZONTAL,-maxiumTick, -miniumTick, -Math.round(maxiumTick/2));
        tickRateSlider.addChangeListener(this);

        resumeButton.addActionListener(this);
        pauseButton.addActionListener(this);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        this.setLayout(null);

        add(amountLabel);
        add(tickAmountField);
        add(runButton);
        add(tickRateSlider);
        add(tickLabel);
        add(resumeButton);
        add(pauseButton);
        add(minuteRadio);
        add(hourRadio);
        add(dayRadio);
        add(weekRadio);
        add(resetButton);

        int xPos = 10;
        int yPos = 10;
        int offset = 5;

        amountLabel.setBounds(xPos, yPos, 120, 30);
        tickAmountField.setBounds(xPos + amountLabel.getWidth(), yPos, 140, 30);

        minuteRadio.setBounds(xPos + amountLabel.getWidth() + tickAmountField.getWidth() + (offset), yPos, 70, 30);
        hourRadio.setBounds(xPos + amountLabel.getWidth() + tickAmountField.getWidth() + minuteRadio.getWidth() + (offset * 2), yPos, 60, 30);
        dayRadio.setBounds(xPos + amountLabel.getWidth() + tickAmountField.getWidth() + minuteRadio.getWidth() + hourRadio.getWidth() + (offset * 3), yPos, 70, 30);
        weekRadio.setBounds( xPos + amountLabel.getWidth() + tickAmountField.getWidth() + minuteRadio.getWidth() + hourRadio.getWidth() + dayRadio.getWidth() + (offset * 4), yPos, 70, 30);
        runButton.setBounds( xPos + amountLabel.getWidth() + tickAmountField.getWidth() + minuteRadio.getWidth() + hourRadio.getWidth() + dayRadio.getWidth() + weekRadio.getWidth()  + (offset * 5), yPos, 120, 30);

        yPos += offset + runButton.getHeight();


        tickLabel.setBounds(xPos, yPos, 120, 60);
        tickRateSlider.setBounds(xPos + tickLabel.getWidth(), yPos, 250, 60);

        // Add positions label in the slider
        Hashtable position = new Hashtable();
        position.put(-maxiumTick, new JLabel(1 + "%"));
        position.put((-maxiumTick/4) * 3, new JLabel(maxiumTick/4 + "%"));
        position.put(-maxiumTick/2, new JLabel(maxiumTick/2 + "%"));
        position.put(-maxiumTick/4, new JLabel((maxiumTick/4) * 3 + "%"));
        position.put(-miniumTick, new JLabel(maxiumTick + "%"));
        // Set the label to be drawn
        tickRateSlider.setLabelTable(position);

        tickRateSlider.setPaintLabels(true);
        tickRateSlider.setMajorTickSpacing(50);
        tickRateSlider.setMinorTickSpacing(10);
        tickRateSlider.setPaintTicks(true);

        //yPos += offset += tickRateSlider.getHeight();
        pauseButton.setBounds(xPos  + (offset * 11) + tickLabel.getWidth() + tickRateSlider.getWidth(), yPos + 10, 120, 30);
        resumeButton.setBounds(xPos  + (offset * 13) + tickLabel.getWidth() + tickRateSlider.getWidth() + pauseButton.getWidth(), yPos + 10, 120, 30);

        yPos += offset + tickRateSlider.getHeight();

        resetButton.setBounds(xPos, yPos, 120, 30);
        setVisible(true);
    }

    private void SetElement(JPanel panel, int x, int y, int width, int height){
        add(panel);
        panel.setBounds(x, y, width, height);
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

            if(e.getSource() == resumeButton){
                parkModel.setPauseState(false);
            }

            if(e.getSource() == pauseButton){
                parkModel.setPauseState(true);
            }

            if(e.getSource() == resetButton){
                parkModel.resetSimulation();
            }
        }



    }

    /**
     * Handles the changes from the slider
     * @param e The event that has been fired
     */
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

            if(minuteRadio.isSelected()){
                // we can let the value as is
            }

            if(hourRadio.isSelected()){
               // 10080 = week,    1440 = dag
                // 1440 dag
                // 60 uur
                //
                tickAmount *= 60;
            }

            if(dayRadio.isSelected()){
                tickAmount *= 1440;
            }

            if(weekRadio.isSelected()){
                tickAmount *= 10080;
            }


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
        tickRate = -tickRate;

        if(tickRate > 0){
            if(tickRate >= maxiumTick)
                tickRate = maxiumTick;

            parkModel.setTickPause(tickRate);
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