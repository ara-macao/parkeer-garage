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
 * @author Thom
 */
public class Settings {
    // These three values are needed to calculate the size of the parking lot.
    private int parkingFloors = 3;
    private int parkingRows = 6;
    private int parkingPlacesPerRow = 30;
    
    // Settings of the main screen.
    public final Dimension screenDimension = new Dimension(1200, 800);
    public final boolean screenIsResizable = false;
    public final String screenName = "Parkeer Simulator";

    // Default position and size values for the screens.
    public final Point pieChartPosition = new Point(0, 0);
    public final Dimension pieChartDimensions = new Dimension(200, 350);
    public final String pieChartName = "Taart Grafiek";
    
    public final Point tickControllerPosition = new Point(1200, 0);
    public final Dimension tickControllerDimensions = new Dimension(300, 300);
    public final String tickControllerName = "Start";
    
    public final Point graphLinePosition = new Point(10, 460);
    public final Dimension graphLineDimensions = new Dimension(700, 500);
    public final String graphLineName = "Grafiek";
    
    public final Point carParkViewPosition = new Point(0, 50);
    public final Dimension carParkViewDimensions = new Dimension(1000, 400);
    
    public final Point textViewPosition = new Point(50, 0);
    public final Dimension textViewDimensions = new Dimension(300, 100);
    
    public final Point timeViewPosition = new Point(400, 0);
    public final Dimension timeViewDimensions = new Dimension(200, 50);
    
    
    public int getParkingFloors() { return parkingFloors; }
    public int getParkingRows() { return parkingRows; }
    public int getParkingPlacesPerRow() { return parkingPlacesPerRow; }
}

