package nl.parkingsimulator.logic;

import java.awt.*;
import java.util.Random;

/**
 * BadParkedCar
 *
 * @author Emiel van Essen
 */
public class BadParkedCar extends Car {

    /**
     * Constructor for objects of class BadParkedCar
     * @param carType the type of the car
     */
    public BadParkedCar(int carType) {
        super(carType);
        Random random = new Random();
        int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
}
