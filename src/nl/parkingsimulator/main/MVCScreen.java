package nl.parkingsimulator.main;

import javax.swing.*;


public class MVCScreen {
   public Thread guiThread;
   
    public MVCScreen(){
        System.out.println("Emiel zijn screen zooi");
        guiThread = new Thread() {
            public void run() {
                JFrame window = new JFrame();
                JPanel content = new JPanel();
                // implement interface view
                window.add(content);
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

