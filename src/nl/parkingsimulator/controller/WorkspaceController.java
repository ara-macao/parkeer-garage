/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author emiel
 */
public class WorkspaceController extends AbstractController {

    //public JDesktopPane workspace;
    private JLabel testLabel;

    public WorkspaceController(AbstractModel model) {
        super(model);
        setSize(400, 400);
        setBackground(Color.green);

        //workspace = new JDesktopPane();
        testLabel = new JLabel("kut mvc");

        setLayout(null);
        add(testLabel);

        testLabel.setBounds(0, 0, 120, 30);


        setVisible(true);
    }

    public void injectInternalFrame(JInternalFrame frame) {

    }
}
