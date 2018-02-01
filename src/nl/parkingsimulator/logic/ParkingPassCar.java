package nl.parkingsimulator.logic;

import java.util.Random;
import java.awt.*;

/**
 * ParkingPassCar
 *
 * @author Hanze
 * @author Jeroen Westers (Refactored to mvc)
 */
public class ParkingPassCar extends Car {
    private static final Color COLOR=Color.blue;
	
    public ParkingPassCar(int carType) {
        super(carType);
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}