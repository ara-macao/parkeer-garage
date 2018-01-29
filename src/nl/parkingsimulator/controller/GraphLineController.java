package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.view.GraphLineView;
import nl.parkingsimulator.view.GraphLineView.GraphName;

/**
 * This class controls the graphs of the GraphLineView class.
 * 
 * @author Thom van Dijk
 */
public class GraphLineController extends AbstractController implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton toggleOccupiedPlaces;
	private JButton toggleTotalWaitingCars;
	
    private GraphLineView graphLineView;
    
    public GraphLineController(AbstractModel model, Dimension dimensions, GraphLineView graphLineView) {    
        super(model);
        setSize(dimensions);
        
        this.graphLineView = graphLineView;

        setBackground(Color.ORANGE);

        toggleOccupiedPlaces = new JButton("Bezette plekken");
        toggleOccupiedPlaces.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleTotalWaitingCars = new JButton("Alle wachtende auto's");
        toggleTotalWaitingCars.setToolTipText("klik om de grafiek aan of uit te zetten.");
        
        toggleOccupiedPlaces.addActionListener(this);
        toggleTotalWaitingCars.addActionListener(this);

        setLayout(null);
        
        add(toggleOccupiedPlaces);
        add(toggleTotalWaitingCars);
        
        int offset = 10;

        toggleOccupiedPlaces.setBounds(offset, offset, 170, 30);
        toggleTotalWaitingCars.setBounds(offset, ((offset * 2) + 30) * 1, 170, 30);

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
    }
}
