package nl.parkingsimulator.logic;

import java.awt.*;
import java.util.Random;

/**
 * ReservedCar
 *
 * @author Emiel van Essen
 */
public class ReservedCar extends Car {

    /**
     * Constructor for objects of class ReservedCar
     *
     * @param carType the type of the car
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
