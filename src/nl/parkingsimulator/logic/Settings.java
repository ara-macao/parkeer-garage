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
    private int weekDayReserved = 25; // average number of arriving cars per hour
    private int weekendReserved = 35; // average number of arriving cars per hour

    private int enterSpeed = 3; // number of cars that can enter per minute
    private int paymentSpeed = 7; // number of cars that can pay per minute
    private int exitSpeed = 5; // number of cars that can leave per minute
    
    private float pricePerHour = 1.5f; // This is the price a customer pays per hour.
    private float pricePerPassHolder = 60f; // This is the price a passholder pays per month.

    /**
     *  Settings of the main screen.
     */
    private Dimension screenDimension = new Dimension(880, 500);
    private Point screenPosition = new Point(0, 0);
    private boolean screenIsResizable = false;
    private String screenName = "Parkeer Simulator";
    private boolean defaultLookAndFeel = true;

    /**
     *  Default position and size values for the screens.
     */
    private Point carParkViewPosition = new Point(0, 100); // positions all elements
    private Dimension carParkViewDimensions = new Dimension(screenDimension.width, screenDimension.height - 100);

    private Point tickControllerPosition = new Point(screenPosition.x + screenDimension.width, screenPosition.y);
    private Dimension tickControllerDimensions = new Dimension(705, 185);
    private String tickControllerName = "Start";

    private Point reservationsPosition = new Point(carParkViewPosition.x + carParkViewDimensions.width, carParkViewPosition.y + getTickControllerDimensions().height);
    private Dimension reservationsDimensions = new Dimension(350, 300);
    private String reservationsName = "Reserveringen";
    
    private Point settingsControllerPosition = new Point(tickControllerPosition.x, tickControllerPosition.y + tickControllerDimensions.height);
    private Dimension settingsControllerDimensions = new Dimension(230, (screenDimension.height + 450 - tickControllerDimensions.height) / 2);
    private String settingsControllerName = "Instellingen";
    
    private Point graphLinePosition = new Point(screenPosition.x, screenPosition.y + screenDimension.height);
    private Dimension graphLineDimensions = new Dimension(screenDimension.width, 450);
    private String graphLineName = "Grafiek";
    
    private Point graphLineControllerPosition = new Point(settingsControllerPosition.x, settingsControllerPosition.y + settingsControllerDimensions.height);
    private Dimension graphLineControllerDimensions = new Dimension(settingsControllerDimensions.width, settingsControllerDimensions.height);
    private String graphLineControllerName = "Grafiek Settings";

    private Point textViewPosition = new Point(50, 0);
    private Dimension textViewDimensions = new Dimension(300, 100);

    private Point timeViewPosition = new Point(310, 14);
    private Dimension timeViewDimensions = new Dimension(600, 100);

    private Point pieChartPosition = new Point(settingsControllerPosition.x + settingsControllerDimensions.width, settingsControllerPosition.y);
    private Dimension pieChartDimensions = new Dimension(tickControllerDimensions.width - settingsControllerDimensions.width, settingsControllerDimensions.height);
    private String pieChartName = "Taart Grafiek";

    private Point histogramPosition = new Point(pieChartPosition.x, pieChartPosition.y + pieChartDimensions.height);
    private Dimension histogramDimensions = new Dimension(pieChartDimensions);
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
    public int getWeekDayReserved() { return weekDayReserved; } // average number of arriving cars per hour
    public int getWeekendReserved() { return weekendReserved; } // average number of arriving cars per hour

    public int getEnterSpeed() { return enterSpeed; } // number of cars that can enter per minute
    public int getPaymentSpeed() { return paymentSpeed; } // number of cars that can pay per minute
    public int getExitSpeed() { return exitSpeed; } // number of cars that can leave per minute
    
    public float getPricePerHour() { return pricePerHour; }
    public float getPricePerPassHolder() { return pricePerPassHolder; }
    
    /**
     *  Getters for main screen.
     */
    public Dimension getScreenDimension() { return screenDimension; }
    public Point getScreenPosition() { return screenPosition; }
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
    public  void setTickPause(int tickPause){this.tickPause = tickPause; } // Set tickpause
    
    public void setWeekDayArrivals(int weekDayArrivals) { this.weekDayArrivals = weekDayArrivals; } // average number of arriving cars per hour
    public void setWeekendArrivals(int weekendArrivals) { this.weekendArrivals = weekendArrivals; } // average number of arriving cars per hour
    public void setWeekDayPassArrivals(int weekDayPassArrivals) { this.weekDayPassArrivals = weekDayPassArrivals; } // average number of arriving cars per hour
    public void setWeekendPassArrivals(int weekendPassArrivals) { this.weekendPassArrivals = weekendPassArrivals; } // average number of arriving cars per hour
    public void setWeekDayReserved(int weekDayReserved) { this.weekDayReserved = weekDayReserved; } // average number of arriving cars per hour
    public void setWeekendReserved(int weekendReserved) { this.weekendReserved = weekendReserved; } // average number of arriving cars per hour
    
    public void setPricePerHour(float pricePerHour) { this.pricePerHour = pricePerHour; }
    public void setPricePerPassHolder(float pricePerPassHolder) { this.pricePerPassHolder = pricePerPassHolder; }
}

