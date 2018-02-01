package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import nl.parkingsimulator.logic.Settings;

/**
 * 
 * @author Thom van Dijk
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

    private CarParkModel model;
    
    /**
     * Constructor for objects of class CarPark
     * 
     * @param model 		The model where to data comes from.
     * @param dimensions 	The Dimension how big the JFrame should be.
     */
    public SettingsController(AbstractModel model, Dimension dimensions, Point position) {
        super(model);
        this.model = (CarParkModel) model;
        
        JFrame frame = new JFrame(this.model.getSettings().getGraphLineControllerName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(dimensions);
        frame.setLocation(position);
        frame.setResizable(true);

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
        
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int offset = 5;
        int groupOffset = 20;

        container.add(weekDayArrivalsLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekDayArrivalsField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        
        container.add(weekendArrivalsLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekendArrivalsField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        
        container.add(weekDayPassArrivalsLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekDayPassArrivalsField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        
        container.add(weekendPassArrivalsLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekendPassArrivalsField);
        container.add(Box.createRigidArea(new Dimension(0, groupOffset)));

        container.add(updateButton);
        
        weekDayArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekDayPassArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendPassArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
                                
        weekDayArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekDayPassArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendPassArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        int maxButtonWidth = Short.MAX_VALUE;
        int maxButtonHeight = Short.MAX_VALUE;

        weekDayArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekDayPassArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendPassArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        weekDayArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekDayPassArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendPassArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        updateButton.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        int scrollSpeed = 16;

        JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
        
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
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
    
    /**
     * @Override
     */
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
	}

    /**
     * Parses the value of an input field to an int
     * @param input The input field we need to reed our integers from
     */
    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }
}
