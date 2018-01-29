package nl.parkingsimulator.logic;

import java.awt.*;
import java.util.Random;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private int totalMinuteParket;
    protected int id;
    private String carType;

    /**
     * Constructor for objects of class Car
     */
    public Car(String carType) {
        this.carType = carType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
        totalMinuteParket = minutesLeft;
    }

    public int getTotalMinuteParket(){
        return totalMinuteParket;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();

    public int getId() {
        return id;
    }

    public String getCarType(){
        return carType;
    }
}