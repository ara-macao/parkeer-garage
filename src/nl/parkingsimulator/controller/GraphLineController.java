package nl.parkingsimulator.controller;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import nl.parkingsimulator.view.GraphLineView;
import nl.parkingsimulator.view.GraphLineView.GraphName;
import nl.parkingsimulator.view.GraphLineView.ZoomLevel;

/**
 * This class controls the graphs of the GraphLineView class.
 * Also in this class a separate JFrame is created where all buttons and text fields are hold.
 * 
 * @author Thom van Dijk
 */
public class GraphLineController extends AbstractController implements ActionListener {
	private JButton toggleOccupiedPlaces;
	private JButton toggleTotalWaitingCars;
	private JButton toggleTotalLeavingCars;
	private JButton toggleTotalRegularCars;
	private JButton toggleTotalReservedSpots;
	private JButton toggleParkedPassHolders;
	
	private JButton setTimeStepMin; // 1, 10, 15, 30, 60, 1440
	private JButton setTimeStep10Min;
	private JButton setTimeStepQuarter;
	private JButton setTimeStepHalfHour;
	private JButton setTimeStepHour;
	private JButton setTimeStepDay;
	
	private JTextField textFieldGraphHeight;
	private JButton setGraphHeight;
	
	private JButton setZoomDay;
	private JButton setZoomWeek;
	private JButton setZoomMonth;
	
    private GraphLineView graphLineView;
    private CarParkModel model;
    
    /**
     * Inside the constructor a JFrame is made witch contains all the buttons and text fields.
     * 
     * @param model			The CarParkModel used to get the settings and given to AbstractController.
     * @param dimensions	The dimensions to be assigned to the JFrame.
     * @param position		The position to be assigned to the JFrame.
     * @param graphLineView A reference to the GraphLineView class so we can access its functions.
     */
    public GraphLineController(AbstractModel model, Dimension dimensions, Point position, GraphLineView graphLineView) {    
        super(model);
        this.graphLineView = graphLineView;
        this.model = (CarParkModel) model;
        
        /**
         * Since we will probably never change this setting it is not implemented in the Settings class.
         */
        int scrollSpeed = 16;
 
        /**
         * Here we create the JFrame witch holds everything.
         */
        JFrame frame = new JFrame(this.model.getSettings().getGraphLineControllerName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(dimensions);
        frame.setLocation(position);
        frame.setResizable(true);
        
        /**
         * Create the buttons.
         */
        toggleOccupiedPlaces = new JButton("Bezette plekken");
        toggleOccupiedPlaces.setToolTipText("klik om de grafiek aan of uit te zetten.");

        toggleTotalWaitingCars = new JButton("Wachtende auto's ingang");
        toggleTotalWaitingCars.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleTotalLeavingCars = new JButton("Wachtende auto's uitgang");
        toggleTotalLeavingCars.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleTotalRegularCars = new JButton("Reguliere bezoekers");
        toggleTotalRegularCars.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleTotalReservedSpots = new JButton("Gereserveerde plekken");
        toggleTotalReservedSpots.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleParkedPassHolders = new JButton("Geparkeerde pashouders");
        toggleParkedPassHolders.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        setTimeStepMin		= new JButton("Tijd in minuten");
        setTimeStep10Min	= new JButton("Tijd in 10 minuten");
        setTimeStepQuarter	= new JButton("Tijd in kwartieren");
        setTimeStepHalfHour	= new JButton("Tijd in halve uren");
        setTimeStepHour		= new JButton("Tijd in uren");
        setTimeStepDay		= new JButton("Tijd in dagen");
        
        textFieldGraphHeight = new JTextField();
        textFieldGraphHeight.setText("550");
        
        setGraphHeight = new JButton("Zet grafiek hoogte");
        
        setZoomDay = new JButton("Dag weergave");
        setZoomWeek = new JButton("Week weergave"); 
        setZoomMonth = new JButton("Maand weergave");

        /**
         * Add action listeners so we know when they are pressed.
         */
        toggleOccupiedPlaces.addActionListener(this);
        toggleTotalWaitingCars.addActionListener(this);
        toggleTotalLeavingCars.addActionListener(this);
        toggleTotalRegularCars.addActionListener(this);
        toggleTotalReservedSpots.addActionListener(this);
        toggleParkedPassHolders.addActionListener(this);
        
        setTimeStepMin.addActionListener(this);
        setTimeStep10Min.addActionListener(this);
        setTimeStepQuarter.addActionListener(this);
        setTimeStepHalfHour.addActionListener(this);
        setTimeStepHour.addActionListener(this);
        setTimeStepDay.addActionListener(this);
        
        setGraphHeight.addActionListener(this);
        
        setZoomDay.addActionListener(this);  
        setZoomWeek.addActionListener(this);
        setZoomMonth.addActionListener(this);
        
        /**
         * Create a JPanel where we add all buttons and fields.
         */
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int offset = 5;
        int groupOffset = 20;
        
        /**
         * Add vertical spacing between the elements with: Box.createRigidArea(new Dimension(0, offset))
         */
        container.add(toggleOccupiedPlaces);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(toggleTotalWaitingCars);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(toggleTotalLeavingCars);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(toggleTotalRegularCars);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(toggleTotalReservedSpots);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(toggleParkedPassHolders);
        container.add(Box.createRigidArea(new Dimension(0, groupOffset)));
        
        container.add(setTimeStepMin);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setTimeStep10Min);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setTimeStepQuarter);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setTimeStepHalfHour);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setTimeStepHour);
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setTimeStepDay);
        container.add(Box.createRigidArea(new Dimension(0, groupOffset)));

        container.add(textFieldGraphHeight); // Text field.
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setGraphHeight);
        container.add(Box.createRigidArea(new Dimension(0, groupOffset)));

        container.add(setZoomDay);  
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setZoomWeek); 
        container.add(Box.createRigidArea(new Dimension(0, offset)));
        container.add(setZoomMonth);
        
        /**
         * This will center all elements nicely.
         */
        toggleOccupiedPlaces.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleTotalWaitingCars.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleTotalLeavingCars.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleTotalRegularCars.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleTotalReservedSpots.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleParkedPassHolders.setAlignmentX(Component.CENTER_ALIGNMENT);
                                
        setTimeStepMin.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTimeStep10Min.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTimeStepQuarter.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTimeStepHalfHour.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTimeStepHour.setAlignmentX(Component.CENTER_ALIGNMENT);
        setTimeStepDay.setAlignmentX(Component.CENTER_ALIGNMENT);
                                
        textFieldGraphHeight.setAlignmentX(Component.CENTER_ALIGNMENT);
        setGraphHeight.setAlignmentX(Component.CENTER_ALIGNMENT);
                                
        setZoomDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        setZoomWeek.setAlignmentX(Component.CENTER_ALIGNMENT);
        setZoomMonth.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        /**
         * Here we make all elements as big as possible so when the screen scales they scale with it.
         */
        int maxButtonWidth = Short.MAX_VALUE;
        int maxButtonHeight = Short.MAX_VALUE;

        toggleOccupiedPlaces.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        toggleTotalWaitingCars.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        toggleTotalLeavingCars.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        toggleTotalRegularCars.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        toggleTotalReservedSpots.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        toggleParkedPassHolders.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        setTimeStepMin.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setTimeStep10Min.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setTimeStepQuarter.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setTimeStepHalfHour.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setTimeStepHour.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setTimeStepDay.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        textFieldGraphHeight.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setGraphHeight.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        
        setZoomDay.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setZoomWeek.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        setZoomMonth.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));

        /**
         * Add the container to a scroll pane.
         */
        JScrollPane scrollPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
        
        /**
         * Add the scrollPane to the JFrame.
         */
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * An actionlistener function, triggered when a button is hit.
     * Here we change some graph related values inside the GraphLineView class.
     */
    public void actionPerformed(ActionEvent e) {
    	/**
    	 * Toggle different graphs.
    	 */
    	if(e.getSource() == toggleOccupiedPlaces) graphLineView.toggleGraph(GraphName.OCCUPIED_PLACES);
    	if(e.getSource() == toggleTotalWaitingCars) graphLineView.toggleGraph(GraphName.TOTAL_WAITING_CARS);
    	if(e.getSource() == toggleTotalLeavingCars) graphLineView.toggleGraph(GraphName.TOTAL_LEAVING_CARS);
    	if(e.getSource() == toggleTotalRegularCars) graphLineView.toggleGraph(GraphName.REGULAR_CARS);
    	if(e.getSource() == toggleTotalReservedSpots) graphLineView.toggleGraph(GraphName.RESERVED_SPOTS);
    	if(e.getSource() == toggleParkedPassHolders) graphLineView.toggleGraph(GraphName.PASS_HOLDERS);
    	
    	/**
    	 * Set the time stem for the horizontal line.
    	 */
    	if(e.getSource() == setTimeStepMin) graphLineView.setHorizontalStep(1);
    	if(e.getSource() == setTimeStep10Min) graphLineView.setHorizontalStep(10);
    	if(e.getSource() == setTimeStepQuarter) graphLineView.setHorizontalStep(15);
    	if(e.getSource() == setTimeStepHalfHour) graphLineView.setHorizontalStep(30);
    	if(e.getSource() == setTimeStepHour) graphLineView.setHorizontalStep(60);
    	if(e.getSource() == setTimeStepDay) graphLineView.setHorizontalStep(1440);
    	
    	if(e.getSource() == setGraphHeight) graphLineView.setGraphHeight(parseIntValue(textFieldGraphHeight));;
    	
    	if(e.getSource() == setZoomDay) graphLineView.setHorizontalZoom(ZoomLevel.DAY);;
    	if(e.getSource() == setZoomWeek) graphLineView.setHorizontalZoom(ZoomLevel.WEEK);
    	if(e.getSource() == setZoomMonth) graphLineView.setHorizontalZoom(ZoomLevel.MONTH);
    }
    
    /**
     * Parses the value of an input field to an integer.
     * 
     * @param input The input field we need to read our integers from.
     */
    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }
}
