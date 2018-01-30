/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;

import javax.swing.*;
import java.awt.*;
import nl.parkingsimulator.view.PieChartView;

/**
 *
 * @author emiel
 */
public class WorkspaceController extends AbstractController {

    //public JDesktopPane workspace;
    public JDesktopPane workspace;

    public WorkspaceController(AbstractModel model) {
        super(model);
        setSize(3840, 2160);
        setBackground(Color.green);

        workspace = new JDesktopPane();
        workspace.setPreferredSize(new Dimension(3840, 2160));
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        gridbag.setConstraints(workspace, c);
        add(workspace);
        for (int i = 0; i < 5; i++) {
            if(true) {
                break;
            }
            JInternalFrame demo = new JInternalFrame("demo sub window " + Integer.toString(i), false, false, false, false);
            workspace.add(demo);
            demo.setBounds(0, 200, 200, 200);
            demo.setVisible(true);
            PieChartView panel;
            panel = new PieChartView(model);
            panel.setBounds(0, 200, 200, 200);
            demo.add(panel);
            //demo.add(pieChartController);
            //demo.setBounds(0, 200, 200, 200);
        }

        setVisible(true);
    }

    ;

    public void injectInternalFrame(JInternalFrame frame) {
        //workspace.add(frame);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              workspace.add(frame);
            }
          });
    }
}
