package nl.parkingsimulator.logic;

public class Reservation {

    private int id;
    private int carId;
    private int startTime;
    private int endTime;

    public Reservation(int carId) {
        this.carId = carId;
        this.id = hashCode();
        System.out.println("Reservation: " + id + " made for carID: " + carId);
    }

    public int getCarId() {
        return carId;
    }

    public int getId() {
        return id;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
