/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;

import javax.swing.*;

/**
 *
 * @author emiel
 */
public class WorkspaceController extends AbstractController {

    //public JDesktopPane workspace;
    private JLabel testLabel;

    public WorkspaceController(AbstractModel model) {
        super(model);
        setSize(100, 300);

        //workspace = new JDesktopPane();
        testLabel = new JLabel("kut mvc");
        setLayout(null);
        add(testLabel);
        
        setVisible(true);
    }

    public void injectInternalFrame(JInternalFrame frame) {

    }
}
