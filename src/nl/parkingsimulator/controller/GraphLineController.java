package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.view.GraphLineView;
import nl.parkingsimulator.view.GraphLineView.GraphName;
import nl.parkingsimulator.view.GraphLineView.ZoomLevel;

/**
 * This class controls the graphs of the GraphLineView class.
 * 
 * @author Thom van Dijk
 */
public class GraphLineController extends AbstractController implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton toggleOccupiedPlaces;
	private JButton toggleTotalWaitingCars;
	private JButton toggleTotalLeavingCars;
	private JButton toggleParkedPassHolders;
	
	private JButton setTimeStepMin; // 1, 10, 15, 30, 60, 1440
	private JButton setTimeStep10Min;
	private JButton setTimeStepQuarter;
	private JButton setTimeStepHalfHour;
	private JButton setTimeStepHour;
	private JButton setTimeStepDay;
	
	private JTextField graphHeight;
	private JButton setGraphHeight;
	
	private JButton setZoomDay;
	private JButton setZoomWeek;
	private JButton setZoomMonth;
	
    private GraphLineView graphLineView;
    
    public GraphLineController(AbstractModel model, Dimension dimensions, GraphLineView graphLineView) {    
        super(model);
        setSize(dimensions);
        
        this.graphLineView = graphLineView;

        setBackground(Color.ORANGE);

        toggleOccupiedPlaces = new JButton("Bezette plekken");
        toggleOccupiedPlaces.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleTotalWaitingCars = new JButton("Wachtende auto's ingang");
        toggleTotalWaitingCars.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleTotalLeavingCars = new JButton("Wachtende auto's uitgang");
        toggleTotalLeavingCars.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleParkedPassHolders = new JButton("Geparkeerde pashouders");
        toggleParkedPassHolders.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        setTimeStepMin		= new JButton("Tijd in minuten");
        setTimeStep10Min	= new JButton("Tijd in 10 minuten");
        setTimeStepQuarter	= new JButton("Tijd in kwartieren");
        setTimeStepHalfHour	= new JButton("Tijd in halve uren");
        setTimeStepHour		= new JButton("Tijd in uren");
        setTimeStepDay		= new JButton("Tijd in dagen");
        
        graphHeight = new JTextField();
        graphHeight.setText("550");
        
        setGraphHeight = new JButton("Zet grafiek hoogte");
        
        setZoomDay = new JButton("Dag weergave");
        setZoomWeek = new JButton("Week weergave"); 
        setZoomMonth = new JButton("Maand weergave");
        
        toggleOccupiedPlaces.addActionListener(this);
        toggleTotalWaitingCars.addActionListener(this);
        toggleTotalLeavingCars.addActionListener(this);
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

        setLayout(null);
        
        add(toggleOccupiedPlaces);
        add(toggleTotalWaitingCars);
        add(toggleTotalLeavingCars);
        add(toggleParkedPassHolders);
        
        add(setTimeStepMin);
        add(setTimeStep10Min);
        add(setTimeStepQuarter);
        add(setTimeStepHalfHour);
        add(setTimeStepHour);
        add(setTimeStepDay);
        
        add(graphHeight);
        add(setGraphHeight);
        
        add(setZoomDay);  
        add(setZoomWeek); 
        add(setZoomMonth);
        
        int offset = 10;

        toggleOccupiedPlaces.setBounds		(offset, offset, 170, 30);
        toggleTotalWaitingCars.setBounds	(offset, ((offset * 2) + 30) * 1, 170, 30);
        toggleTotalLeavingCars.setBounds	(offset, ((offset * 3) + (30 * 2)) * 1, 170, 30);
        toggleParkedPassHolders.setBounds	(offset, ((offset * 4) + (30 * 3)) * 1, 170, 30);
        
        setTimeStepMin.setBounds		((offset * 2) + 170, offset, 170, 30);
        setTimeStep10Min.setBounds		((offset * 2) + 170, ((offset * 2) + 30) * 1, 170, 30);      
        setTimeStepQuarter.setBounds	((offset * 2) + 170, ((offset * 3) + (30 * 2)) * 1, 170, 30);
        setTimeStepHalfHour.setBounds	((offset * 2) + 170, ((offset * 4) + (30 * 3)) * 1, 170, 30);
        setTimeStepHour.setBounds		((offset * 2) + 170, ((offset * 5) + (30 * 4)) * 1, 170, 30);      
        setTimeStepDay.setBounds		((offset * 2) + 170, ((offset * 6) + (30 * 5)) * 1, 170, 30);
        
        graphHeight.setBounds 		((offset * 3) + (170 * 2), offset, 170, 30);
        setGraphHeight.setBounds 	((offset * 3) + (170 * 2), ((offset * 2) + 30) * 1, 170, 30);
        
        setZoomDay.setBounds 		((offset * 3) + (170 * 2), ((offset * 3) + (30 * 2)) * 1, 170, 30);  
        setZoomWeek.setBounds 		((offset * 3) + (170 * 2), ((offset * 4) + (30 * 3)) * 1, 170, 30); 
        setZoomMonth.setBounds 		((offset * 3) + (170 * 2), ((offset * 5) + (30 * 4)) * 1, 170, 30);
        
        setVisible(true);
    }

    /**
     * @Override
     */
    public void actionPerformed(ActionEvent e) {
    	
    	/**
    	 * Toggle different graphs.
    	 */
    	if(e.getSource() == toggleOccupiedPlaces) graphLineView.toggleGraph(GraphName.OCCUPIED_PLACES);
    	if(e.getSource() == toggleTotalWaitingCars) graphLineView.toggleGraph(GraphName.TOTAL_WAITING_CARS);
    	if(e.getSource() == toggleTotalLeavingCars) graphLineView.toggleGraph(GraphName.TOTAL_LEAVING_CARS);
    	if(e.getSource() == toggleParkedPassHolders) graphLineView.toggleGraph(GraphName.PASS_HOLDERS);
    	
    	/**
    	 * Set the time for the horizontal line.
    	 */
    	if(e.getSource() == setTimeStepMin) graphLineView.setHorizontalStep(1);
    	if(e.getSource() == setTimeStep10Min) graphLineView.setHorizontalStep(10);
    	if(e.getSource() == setTimeStepQuarter) graphLineView.setHorizontalStep(15);
    	if(e.getSource() == setTimeStepHalfHour) graphLineView.setHorizontalStep(30);
    	if(e.getSource() == setTimeStepHour) graphLineView.setHorizontalStep(60);
    	if(e.getSource() == setTimeStepDay) graphLineView.setHorizontalStep(1440);
    	
    	if(e.getSource() == setGraphHeight) graphLineView.setGraphHeight(parseIntValue(graphHeight));;
    	
    	if(e.getSource() == setZoomDay) graphLineView.setHorizontalZoom(ZoomLevel.DAY);;
    	if(e.getSource() == setZoomWeek) graphLineView.setHorizontalZoom(ZoomLevel.WEEK);
    	if(e.getSource() == setZoomMonth) graphLineView.setHorizontalZoom(ZoomLevel.MONTH);
    }
    
    /**
     * Parses the value of an input field to an int
     * @param input The input field we need to reed our integers from
     */
    private int parseIntValue(JTextField input) throws NumberFormatException {
        return Integer.parseInt(input.getText());
    }
}
