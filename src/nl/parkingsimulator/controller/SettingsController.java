package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import nl.parkingsimulator.logic.Settings;

/**
 * 
 * @author Thom van Dijk
 * 
 */
public class SettingsController extends AbstractController implements ActionListener, ChangeListener {

    private JTextField weekDayArrivalsField;
    private JTextField weekendArrivalsField;
    private JTextField weekDayPassArrivalsField;
    private JTextField weekendPassArrivalsField;
    
    private JLabel weekDayArrivalsLabel;
    private JLabel weekendArrivalsLabel;
    private JLabel weekDayPassArrivalsLabel;
    private JLabel weekendPassArrivalsLabel;
    
    private JButton updateButton;

    /**
     * Constructor for objects of class CarPark
     * 
     * @param model The model where to data comes from
     */
    public SettingsController(AbstractModel model, Dimension dimensions) {
        super(model);
        setSize(dimensions);
        setBackground(Color.yellow);

        weekDayArrivalsField = new JTextField();
        weekDayArrivalsField.setText("100");
        
        weekendArrivalsField = new JTextField();
        weekendArrivalsField.setText("200");
        
        weekDayPassArrivalsField = new JTextField();
        weekDayPassArrivalsField.setText("50");
        
        weekendPassArrivalsField = new JTextField();
        weekendPassArrivalsField.setText("5");
        
        weekDayArrivalsLabel = new JLabel("Doordeweekse bezoekers");
        weekendArrivalsLabel = new JLabel("Weekend bezoekers");
        weekDayPassArrivalsLabel = new JLabel("Pashouders door de weeks");
        weekendPassArrivalsLabel = new JLabel("Pashouders weekend");
        
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        updateButton.setToolTipText("klik om de waardes door te voeren.");

        this.setLayout(null);

        add(weekDayArrivalsField);
        add(weekendArrivalsField);
        add(weekDayPassArrivalsField);
        add(weekendPassArrivalsField);
        
        add(weekDayArrivalsLabel);
        add(weekendArrivalsLabel);
        add(weekDayPassArrivalsLabel);
        add(weekendPassArrivalsLabel);
        
        add(updateButton);

        int xPos = 10;
        int yPos = 10;
        int offset = 10;

        weekDayArrivalsField.setBounds(xPos, yPos, 120, 30);
        weekendArrivalsField.setBounds(xPos, yPos + (30 + offset), 120, 30);
        weekDayPassArrivalsField.setBounds(xPos, yPos + 2 * (30 + offset), 120, 30);
        weekendPassArrivalsField.setBounds(xPos, yPos + 3 * (30 + offset), 120, 30);
          
		weekDayArrivalsLabel.setBounds(xPos + offset + 120, yPos, 200, 30);
		weekendArrivalsLabel.setBounds(xPos + offset + 120, yPos + (30 + offset), 200, 30);
		weekDayPassArrivalsLabel.setBounds(xPos + offset + 120, yPos + 2 * (30 + offset), 200, 30);
		weekendPassArrivalsLabel.setBounds(xPos + offset + 120, yPos + 3 * (30 + offset), 200, 30);
        
        updateButton.setBounds(xPos, yPos + 4 * (30 + offset), 120, 30);

        setVisible(true);
    }

    /**
     * Handles the actions from the button
     * @param e The event that has been fired
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	CarParkModel parkModel = (CarParkModel)model;
    	
    	/**
    	 * Update the values in settings.
    	 */
    	if(e.getSource() == updateButton) {
    	    Settings settings = parkModel.getSettings();
            settings.setWeekDayArrivals(parseIntValue(weekDayArrivalsField));
            settings.setWeekendArrivals(parseIntValue(weekendArrivalsField));
            settings.setWeekDayPassArrivals(parseIntValue(weekDayPassArrivalsField));
            settings.setWeekendPassArrivals(parseIntValue(weekendPassArrivalsField));

            parkModel.setSettings(settings);
        }
    }
    
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
	}

    /**
     * Parses the value of an inputfield to an int
     * @param input The input field we need to reed our integes from
     */
    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }
}
