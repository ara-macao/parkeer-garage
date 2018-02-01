package nl.parkingsimulator.logic;

import java.awt.*;
import java.util.Random;

/**
 * Abstract Car
 *
 * @author Hanze
 * @author Jeroen Westers (Refactored to mvc)
 */
public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private int totalMinuteParket;
    protected int id;
    private int carType;
    private Color color;
    private int delay;
    private boolean arrived = true;

    /**
     * Constructor for objects of class Car
     * @param carType The cartype
     */
    public Car(int carType) {
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

    public int getTotalMinuteParked(){
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
        if(delay > 0) {
            delay--;
        }
        else if(!arrived) {
            arrived = true;
        }
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getCarType(){
        return carType;
    }

    public void setDelay(int delay) {
        arrived = false;
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public boolean getArrived() {
        return arrived;
    }
}