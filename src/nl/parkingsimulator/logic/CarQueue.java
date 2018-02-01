package nl.parkingsimulator.logic;

import java.util.LinkedList;
import java.util.Queue;


/**
 * CarQueue
 * Holds cars that are queued
 *
 * @author Jeroen Westers (Refactored to mvc)
 */
public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue() {
    	return queue.size();
    }
}
