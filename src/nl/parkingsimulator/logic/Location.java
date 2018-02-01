package nl.parkingsimulator.logic;

import java.awt.*;

import static nl.parkingsimulator.logic.CarParkModel.PASS;
import static nl.parkingsimulator.logic.CarParkModel.RESERVED;

/**
 * Location
 * Holds information about the place
 *
 * @author Jeroen Westers (Refactored to mvc)
 */
public class Location {

    private int floor;
    private int row;
    private int place;
    private Reservation reservation;
    private Color color;

    /**
     * Constructor for objects of class Location
     * @param floor The floor
     * @param row The row
     * @param place The place
     */
    public Location(int floor, int row, int place) {
        this.floor = floor;
        this.row = row;
        this.place = place;
        this.color = Color.pink;
    }

    /**
     * Implement content equality.
     * @param obj The object
     */
    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return floor == other.getFloor() && row == other.getRow() && place == other.getPlace();
        }
        else {
            return false;
        }
    }

    /**
     * Return a string of the form floor,row,place.
     * @return A string representation of the location.
     */
    public String toString() {
        return floor + "," + row + "," + place;
    }

    /**
     * Use the 10 bits for each of the floor, row and place
     * values. Except for very big car parks, this should give
     * a unique hash code for each (floor, row, place) tupel.
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (floor << 20) + (row << 10) + place;
    }

    /**
     * @return The floor.
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return The place.
     */
    public int getPlace() {
        return place;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public boolean hasReservation() {
        return reservation != null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets a location and applies the color of the reservation to the location.
     * @param reservation the reservation to add.
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        if(reservation != null) {
            switch (reservation.getCarType()) {
                case PASS:
                    setColor(Color.CYAN);
                    break;
                case RESERVED:
                    setColor(new Color(200,255,200, 255));
                    break;
            }
        }
        else {
            setColor(Color.pink);
        }
    }
}