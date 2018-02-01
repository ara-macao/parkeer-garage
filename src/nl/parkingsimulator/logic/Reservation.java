package nl.parkingsimulator.logic;

/**
 * Reservation
 *
 * @author Emiel van Essen
 */
public class Reservation {

    private int id;
    private int carType;
    private int ticksLeft;
    private Location location;
    private Car car;

    /**
     * Constructor for objects of class Reservation
     * @param location The location on where to set the reservation
     * @param carType The car type who can access the reservation
     */
    public Reservation(Location location, int carType) {
        this.carType = carType;
        this.id = hashCode();
        this.location = location;
        ticksLeft = Integer.MAX_VALUE;
        System.out.println("Reservation: " + id + " made for carID: " + carType);
    }

    /**
     * Constructor for objects of class Reservation
     * @param location The location on where to set the reservation
     * @param car The car who can access the reservation
     * @param duration The duration of the reservation
     */
    public Reservation(Location location, Car car, int duration) {
        this.carType = car.getCarType();
        this.id = hashCode();
        this.location = location;
        this.car = car;
        ticksLeft = duration;
        System.out.println("Reservation: " + id + " made for carID: " + carType);
    }

    public void tick() {
        ticksLeft--;
        if (ticksLeft <= 0) {
            location.setReservation(null);
        }
    }

    public int getCarType() {
        return carType;
    }

    public int getId() {
        return id;
    }

    public void setCarType(int carId) {
        this.carType = carId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
