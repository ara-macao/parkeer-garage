/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.logic;

import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Thom van Dijk
 */
public class Settings {
    // These three values are needed to calculate the size of the parking lot.
    private int parkingFloors = 3;
    private int parkingRows = 6;
    private int parkingPlacesPerRow = 30;
    
    // Settings of the main screen.
    private Dimension screenDimension = new Dimension(1200, 800);
    private boolean screenIsResizable = false;
    private String screenName = "Parkeer Simulator";

    // Default position and size values for the screens.
    private Point pieChartPosition = new Point(0, 0);
    private Dimension pieChartDimensions = new Dimension(200, 350);
    private String pieChartName = "Taart Grafiek";

    private Point tickControllerPosition = new Point(1200, 0);
    private Dimension tickControllerDimensions = new Dimension(300, 300);
    private String tickControllerName = "Start";

    private Point graphLinePosition = new Point(10, 460);
    private Dimension graphLineDimensions = new Dimension(700, 500);
    private String graphLineName = "Grafiek";

    private Point carParkViewPosition = new Point(0, 50);
    private Dimension carParkViewDimensions = new Dimension(1000, 400);

    private Point textViewPosition = new Point(50, 0);
    private Dimension textViewDimensions = new Dimension(300, 100);

    private Point timeViewPosition = new Point(400, 0);
    private Dimension timeViewDimensions = new Dimension(200, 50);
    
    // Getters for CarParkModel.
    public int getParkingFloors() { return parkingFloors; }
    public int getParkingRows() { return parkingRows; }
    public int getParkingPlacesPerRow() { return parkingPlacesPerRow; }
    
    // Getters for main screen.
    public Dimension getScreenDimension() { return screenDimension; }
    public boolean getScreenIsResizable() { return screenIsResizable; }
    public String getScreenName() { return screenName; }
    
    // Getters for sub screens.
    public Point getPieChartPosition() { return pieChartPosition; };
    public Dimension getPieChartDimensions() { return pieChartDimensions; };
    public String getPieChartName() { return pieChartName; };

    public Point getTickControllerPosition() { return tickControllerPosition; };
    public Dimension getTickControllerDimensions() { return tickControllerDimensions; };
    public String getTickControllerName() { return tickControllerName; };

    public Point getGraphLinePosition() { return graphLinePosition; };
    public Dimension getGraphLineDimensions() { return graphLineDimensions; };
    public String getGraphLineName() { return graphLineName; };

    public Point getCarParkViewPosition() { return carParkViewPosition; };
    public Dimension getCarParkViewDimensions() { return carParkViewDimensions; };

    public Point getTextViewPosition() { return textViewPosition; };
    public Dimension getTextViewDimensions() { return textViewDimensions; };

    public Point getTimeViewPosition() { return timeViewPosition; };
    public Dimension getTimeViewDimensions() { return timeViewDimensions; };
}

