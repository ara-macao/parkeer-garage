package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * 
 * @author Thom van Dijk
 *
 */
public class GraphLineController extends AbstractController implements ActionListener {
    private JButton ChangeViewButton;
    private CarParkModel model;
    private Dimension dimensions;
    
    public GraphLineController(AbstractModel model, Dimension dimensions) {    
        super(model);
        setSize(dimensions);
        
        this.dimensions = dimensions;

        setBackground(Color.ORANGE);

        ChangeViewButton = new JButton("Change View");
        ChangeViewButton.addActionListener(this);

        setLayout(null);
        add(ChangeViewButton);

        ChangeViewButton.setBounds(0, 0, 120, 30);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
