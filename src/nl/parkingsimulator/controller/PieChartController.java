/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;

/**
 * PieChartController is used to swap the values for the pie chart.
 * @author Robin de Man
 */
public class PieChartController extends AbstractController implements ActionListener {
    private JButton ChangeViewButton;
    
    public PieChartController(AbstractModel model) {    
        super(model);
        setSize(140, 300);
        //setBackground(Color.green);

        ChangeViewButton = new JButton("Change View");
        ChangeViewButton.addActionListener(this);

        this.setLayout(null);

        add(ChangeViewButton);

        ChangeViewButton.setBounds(10, 50, 120, 30);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ChangeViewButton){
            
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
