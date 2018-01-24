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
    private int parkingFloors;
    private int parkingRows;
    private int parkingPlacesPerRows;
    
    // Settings of the main screen.
    private Dimension screenDimension;
    private boolean screenIsResizable;
    private String screenName;

    // Default position and size values for the screens.
    private Point pieChartPosition;
    private Dimension pieChartDimensions;
    
    private Point tickControllerPosition;
    private Dimension tickControllerDimensions;
    
    private Point graphLinePosition;
    private Dimension graphLineDimensions;
    
    private Point carParkViewPosition;
    private Dimension carParkViewDimensions;
    
    private Point textViewPosition;
    private Dimension textViewDimensions;
    
    private Point timeViewPosition;
    private Dimension timeViewDimensions;

}

