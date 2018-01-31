package nl.parkingsimulator.logic;

import java.awt.*;
import java.util.Random;

public class ReservedCar extends Car {

    /**
     * Constructor for objects of class Car
     *
     * @param carType
     */
    public ReservedCar(int carType) {
        super(carType);
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }
}
