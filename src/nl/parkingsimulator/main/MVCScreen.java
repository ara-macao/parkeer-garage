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

                window = new JFrame("MVC Parking sim");
                window.setSize(400, 400);
                window.setResizable(false);
                window.setLayout(null);

                // implement interface view
                Settings settings = new Settings();
                model = new CarParkModel(settings);
                
                workspace = new Workspace();
                workspaceController = new WorkspaceController(model);
                workspaceView = new WorkspaceView(model);

                //window.getContentPane().add(new JPanel().add(new JLabel("kut mvc"))); // werkt gewoon
                window.getContentPane().add(workspaceController);
                window.getContentPane().add(workspaceView);
                //workspaceView.setBounds(0,0, workspaceView.getSize().width, workspaceView.getSize().height);
                workspaceController.setBounds(0,0, workspaceController.getSize().width, workspaceController.getSize().height);


                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


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
