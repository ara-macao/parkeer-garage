package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.Car;
import nl.parkingsimulator.logic.CarParkModel;
import nl.parkingsimulator.logic.Location;

/**
 * Carparkview draws the parking garage.
 * 
 * @author Hanze
 * @author Thom van Dijk (removed hardcoded values)
 */
public class CarParkView extends AbstractView {
	private Dimension size;
    private Image carParkImage;

    private int borderTop;
    private int borderLeft;
    
    private int spacingBetweenFloors;
    private int spacingBetweenRows;
    private int spacingBetweenSpots;
    
    private int verticalOffset;
    
    private int parkingSpotWidth;
    private int parkingSpotHeight;
    
    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(AbstractModel model, Dimension dimensions) {
        super(model);
        setSize(dimensions);
        
        size = new Dimension(0, 0);
        
        borderTop = 20;
        borderLeft = 1;
        
        spacingBetweenFloors = 260;
        spacingBetweenRows = 75;
        spacingBetweenSpots = 1;
        
        verticalOffset = 10;
        parkingSpotWidth = 20;
        parkingSpotHeight = 10;        
    }

    /**
     * Overridden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     * @Override
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Overridden. Tells to update the labels and updates the view
     * @Override
     */
    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }

        CarParkModel model = (CarParkModel)getModel();

        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < model.getNumberOfFloors(); floor++) {
            for(int row = 0; row < model.getNumberOfRows(); row++) {
                for(int place = 0; place < model.getNumberOfPlaces(); place++) {
                    Location location = model.locations[floor][row][place];
                    Car car = model.getCarAt(location);
                    Color color = car == null ? location.getColor() : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }

        // Update the view (repaint)
        super.updateView();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * spacingBetweenFloors + (borderLeft + (int)Math.floor(location.getRow() * 0.5)) * spacingBetweenRows + (location.getRow() % 2) * parkingSpotWidth,
                borderTop + location.getPlace() * verticalOffset, parkingSpotWidth - spacingBetweenSpots, parkingSpotHeight  - spacingBetweenSpots); 
    }
}