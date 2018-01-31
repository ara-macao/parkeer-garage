package nl.parkingsimulator.logic;

public class Reservation {

    private int id;
    private int carType;
    private int startTime;
    private int endTime;

    public Reservation(int carType) {
        this.carType = carType;
        this.id = hashCode();
        System.out.println("Reservation: " + id + " made for carID: " + carType);
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
