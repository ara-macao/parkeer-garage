/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.parkingsimulator.logic;

import java.awt.Dimension;
import java.awt.Point;

/**
 * This class contains all variables needed in more than 
 * one place or variables which are changed during runtime.
 * 
 * @author Thom van Dijk
 */
public class Settings {
    /**
     *  Variables for CarParkModel.
     */
    private int parkingFloors = 3;
    private int parkingRows = 6;
    private int parkingPlacesPerRow = 30;
    
    private int tickPause = 100;
    private int amountOfTicks = 1000;
    
    private int weekDayArrivals= 100; // average number of arriving cars per hour
    private int weekendArrivals = 200; // average number of arriving cars per hour
    private int weekDayPassArrivals = 50; // average number of arriving cars per hour
    private int weekendPassArrivals = 5; // average number of arriving cars per hour
    private int weekDayReservated = 25; // average number of arriving cars per hour
    private int weekendReservated = 35; // average number of arriving cars per hour

    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute

    /**
     *  Settings of the main screen.
     */
    private Dimension screenDimension = new Dimension(880, 500);
    private boolean screenIsResizable = false;
    private String screenName = "Parkeer Simulator";
    private boolean defaultLookAndFeel = true;

    /**
     *  Default position and size values for the screens.
     */
    private Point carParkViewPosition = new Point(0, 50); // positions all elements
    private Dimension carParkViewDimensions = new Dimension(screenDimension.width, screenDimension.height);

    private Point tickControllerPosition = new Point(carParkViewPosition.x + carParkViewDimensions.width, carParkViewPosition.y);
    private Dimension tickControllerDimensions = new Dimension(750, 200);
    private String tickControllerName = "Start";

    private Point reservationsPosition = new Point(carParkViewPosition.x + carParkViewDimensions.width, carParkViewPosition.y + getTickControllerDimensions().height);
    private Dimension reservationsDimensions = new Dimension(350, 300);
    private String reservationsName = "Reserveringen";
    
    private Point settingsControllerPosition = new Point(reservationsPosition.x + reservationsDimensions.width, reservationsPosition.y);
    private Dimension settingsControllerDimensions = new Dimension(400, reservationsDimensions.height);
    private String settingsControllerName = "Instellingen";
    
    private Point graphLinePosition = new Point(carParkViewPosition.x, carParkViewPosition.y + carParkViewDimensions.height);
    private Dimension graphLineDimensions = new Dimension(880, 400);
    private String graphLineName = "Grafiek";
    
    private Point graphLineControllerPosition = new Point(graphLinePosition.x + graphLineDimensions.width, carParkViewPosition.y + carParkViewDimensions.height);
    private Dimension graphLineControllerDimensions = new Dimension(560, graphLineDimensions.height);
    private String graphLineControllerName = "Grafiek Settings";

    private Point textViewPosition = new Point(50, 0);
    private Dimension textViewDimensions = new Dimension(300, 150);

    private Point timeViewPosition = new Point(400, 0);
    private Dimension timeViewDimensions = new Dimension(300, 120);

    private Point pieChartPosition = new Point(graphLineControllerPosition.x + graphLineControllerDimensions.width, carParkViewPosition.y +carParkViewDimensions.height);
    private Dimension pieChartDimensions = new Dimension(400, 400);
    private String pieChartName = "Taart Grafiek";

    private Point histogramPosition = new Point(pieChartPosition.x + pieChartDimensions.width, carParkViewPosition.y +carParkViewDimensions.height);
    private Dimension histogramDimensions = new Dimension(400, 300);
    private String histogramName = "Wachtrijen";
    
    /**
     *  Getters for CarParkModel.
     */
    public int getParkingFloors() { return parkingFloors; }
    public int getParkingRows() { return parkingRows; }
    public int getParkingPlacesPerRow() { return parkingPlacesPerRow; }
    
    public int getTickPause() { return tickPause; }
    public int getAmountOfTicks() { return amountOfTicks; }
    
    public int getWeekDayArrivals() { return weekDayArrivals; } // average number of arriving cars per hour
    public int getWeekendArrivals() { return weekendArrivals; } // average number of arriving cars per hour
    public int getWeekDayPassArrivals() { return weekDayPassArrivals; } // average number of arriving cars per hour
    public int getWeekendPassArrivals() { return weekendPassArrivals; } // average number of arriving cars per hour
    public int getWeekDayReserved() { return weekDayReservated; } // average number of arriving cars per hour
    public int getWeekendReserved() { return weekendReservated; } // average number of arriving cars per hour

    public int getEnterSpeed() { return enterSpeed; } // number of cars that can enter per minute
    public int getPaymentSpeed() { return paymentSpeed; } // number of cars that can pay per minute
    public int getExitSpeed() { return exitSpeed; } // number of cars that can leave per minute
    
    /**
     *  Getters for main screen.
     */
    public Dimension getScreenDimension() { return screenDimension; }
    public boolean getScreenIsResizable() { return screenIsResizable; }
    public String getScreenName() { return screenName; }
    public boolean getDefaultLookAndFeel() { return defaultLookAndFeel; }
    
    /**
     *  Getters for sub screens.
     */
    public Point getPieChartPosition() { return pieChartPosition; }
    public Dimension getPieChartDimensions() { return pieChartDimensions; }
    public String getPieChartName() { return pieChartName; }
    
    public Point getHistogramPosition() { return histogramPosition; }
    public Dimension getHistogramDimensions() { return histogramDimensions; }
    public String getHistogramName() { return histogramName; }

    public Point getTickControllerPosition() { return tickControllerPosition; }
    public Dimension getTickControllerDimensions() { return tickControllerDimensions; }
    public String getTickControllerName() { return tickControllerName; }

    public Point getGraphLinePosition() { return graphLinePosition; }
    public Dimension getGraphLineDimensions() { return graphLineDimensions; }
    public String getGraphLineName() { return graphLineName; }
    
    public Point getGraphLineControllerPosition() { return graphLineControllerPosition; }
    public Dimension getGraphLineControllerDimensions() { return graphLineControllerDimensions; }
    public String getGraphLineControllerName() { return graphLineControllerName; }

    public String getReservationsName() { return reservationsName; }
    public Dimension getReservationsDimensions() { return reservationsDimensions; }
    public Point getReservationsPosition() { return reservationsPosition; }
    
    public Point getSettingsControllerPosition() { return settingsControllerPosition; }
    public Dimension getSettingsControllerDimensions() { return settingsControllerDimensions; }
    public String getSettingsControllerName() { return settingsControllerName; }
    
    public Point getCarParkViewPosition() { return carParkViewPosition; }
    public Dimension getCarParkViewDimensions() { return carParkViewDimensions; }

    public Point getTextViewPosition() { return textViewPosition; }
    public Dimension getTextViewDimensions() { return textViewDimensions; }

    public Point getTimeViewPosition() { return timeViewPosition; }
    public Dimension getTimeViewDimensions() { return timeViewDimensions; }
    
    /**
     * Setters for CarParkModel settings.
     */
    public void setWeekDayArrivals(int weekDayArrivals) { this.weekDayArrivals = weekDayArrivals; } // average number of arriving cars per hour
    public void setWeekendArrivals(int weekendArrivals) { this.weekendArrivals = weekendArrivals; } // average number of arriving cars per hour
    public void setWeekDayPassArrivals(int weekDayPassArrivals) { this.weekDayPassArrivals = weekDayPassArrivals; } // average number of arriving cars per hour
    public void setWeekendPassArrivals(int weekendPassArrivals) { this.weekendPassArrivals = weekendPassArrivals; } // average number of arriving cars per hour
    public  void setTickPause(int tickPause){this.tickPause = tickPause; } // Set tickpause
}

