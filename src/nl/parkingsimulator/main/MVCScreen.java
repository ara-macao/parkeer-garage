package nl.parkingsimulator.main;

import javax.swing.*;
import nl.parkingsimulator.controller.*;
import nl.parkingsimulator.logic.*;
import nl.parkingsimulator.view.*;

public class MVCScreen {

    private Thread guiThread;
    private AbstractModel model;
    private Workspace workspace;
    private WorkspaceController workspaceController;
    private WorkspaceView workspaceView;
    private JFrame window;
    public MVCScreen() {
        System.out.println("gui init...");

        guiThread = new Thread() {

            public void run() {
                window = new JFrame();
                // implement interface view
                Settings settings = new Settings();
                model = new CarParkModel(settings);
                
                workspace = new Workspace();
                workspaceController = new WorkspaceController(model);
                workspaceView = new WorkspaceView(model);
                //window.getContentPane().add(new JPanel().add(new JLabel("kut mvc"))); // werkt gewoon
                window.getContentPane().add(workspaceView); //werkt niet hahaha

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.pack();

                window.setVisible(true);
            }
        };
        guiThread.start();
        try {
            guiThread.join();
        } catch (InterruptedException e) {

        }
    }
}
