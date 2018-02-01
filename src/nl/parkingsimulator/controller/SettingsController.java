package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
	private JLabel weekDayArrivalsLabel;
	private JTextField weekDayArrivalsField;
	
	private JLabel weekendArrivalsLabel;
    private JTextField weekendArrivalsField;
    
    private JLabel weekDayPassArrivalsLabel;
    private JTextField weekDayPassArrivalsField;
    
    private JLabel weekendPassArrivalsLabel;
    private JTextField weekendPassArrivalsField;

    private JLabel weekDayReservedLabel;
    private JTextField weekDayReservedField;
                         
    private JLabel weekendReservedLabel;
    private JTextField weekendReservedField;

    private JLabel weekDayBadParkersLabel;
    private JTextField weekDayBadParkersField;

    private JLabel weekendBadParkersLabel;
    private JTextField weekendBadParkersField;

    private JLabel setPricePerHourLabel;
    private JTextField setPricePerHourField;
    
    private JLabel setPricePassHolderLabel;
    private JTextField setPricePassHolderField;

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

        weekDayArrivalsLabel = new JLabel("Doordeweekse bezoekers");
        weekDayArrivalsField = new JTextField();
        weekDayArrivalsField.setText("100");
        
        weekendArrivalsLabel = new JLabel("Weekend bezoekers");
        weekendArrivalsField = new JTextField();
        weekendArrivalsField.setText("200");
        
        weekDayPassArrivalsLabel = new JLabel("Pashouders door de weeks");
        weekDayPassArrivalsField = new JTextField();
        weekDayPassArrivalsField.setText("50");
        
        weekendPassArrivalsLabel = new JLabel("Pashouders weekend");
        weekendPassArrivalsField = new JTextField();
        weekendPassArrivalsField.setText("5");
        
        weekDayReservedLabel = new JLabel("Reserveringen door de weeks");
        weekDayReservedField = new JTextField();
        weekDayReservedField.setText("25");
        
        weekendReservedLabel = new JLabel("Reserveringen weekend");
        weekendReservedField = new JTextField();
        weekendReservedField.setText("35");

        weekDayBadParkersLabel = new JLabel("Dubbel geparkeerden door de weeks");
        weekDayBadParkersField = new JTextField();
        weekDayBadParkersField.setText("10");

        weekendBadParkersLabel = new JLabel("Dubbel geparkeerden weekend");
        weekendBadParkersField = new JTextField();
        weekendBadParkersField.setText("10");

        setPricePerHourLabel = new JLabel("Prijs per uur");         
        setPricePerHourField = new JTextField();
        setPricePerHourField.setText("1,50");
        
        setPricePassHolderLabel = new JLabel("Prijs per abonnementhouder");        
		setPricePassHolderField = new JTextField();
		setPricePassHolderField.setText("60,00");
        
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
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        
        container.add(weekDayReservedLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekDayReservedField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        
        container.add(weekendReservedLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekendReservedField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));

        container.add(weekDayBadParkersLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekDayBadParkersField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));

        container.add(weekendBadParkersLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(weekendBadParkersField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));

        container.add(setPricePerHourLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setPricePerHourField);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        
        container.add(setPricePassHolderLabel);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setPricePassHolderField);
        container.add(Box.createRigidArea(new Dimension(0, groupOffset)));

        container.add(updateButton);
        
        weekDayArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekDayArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        weekendArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        weekDayPassArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekDayPassArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        weekendPassArrivalsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendPassArrivalsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        weekDayReservedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekDayReservedField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        weekendReservedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendReservedField.setAlignmentX(Component.CENTER_ALIGNMENT);

        weekDayBadParkersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekDayBadParkersField.setAlignmentX(Component.CENTER_ALIGNMENT);

        weekendBadParkersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weekendBadParkersField.setAlignmentX(Component.CENTER_ALIGNMENT);

        setPricePerHourLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		setPricePerHourField.setAlignmentX(Component.CENTER_ALIGNMENT);
		                       
		setPricePassHolderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		setPricePassHolderField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        int maxButtonWidth = Short.MAX_VALUE;
        int maxButtonHeight = Short.MAX_VALUE;

        weekDayArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekDayArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        weekendArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        weekDayPassArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekDayPassArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        weekendPassArrivalsLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendPassArrivalsField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        weekDayReservedLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekDayReservedField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
                            
        weekendReservedLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendReservedField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        weekDayBadParkersLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekDayBadParkersField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        weekendBadParkersLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        weekendBadParkersField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        setPricePerHourLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setPricePerHourField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
                                
        setPricePassHolderLabel.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setPricePassHolderField.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

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
            settings.setWeekDayReserved(parseIntValue(weekDayReservedField));
            settings.setWeekendReserved(parseIntValue(weekendReservedField));
            settings.setWeekDayBadParkers(parseIntValue(weekDayBadParkersField));
            settings.setWeekendBadParkers(parseIntValue(weekendBadParkersField));
            settings.setPricePerHour(parseFloatValue(setPricePerHourField));
            settings.setPricePerPassHolder(parseFloatValue(setPricePassHolderField));

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
    
    /**
     * Parses the value of an input field to an int
     * @param input The input field we need to reed our integers from
     */
    private float parseFloatValue(JTextField input) throws NumberFormatException {
    	String str = input.getText().replace(',', '.');
    	
        return Float.parseFloat(str);
    }
}
