package nl.parkingsimulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import nl.parkingsimulator.logic.*;

/**
 * Carparkview draws the parking garage.
 * 
 * @author Hanze
 * @author Jeroen Westers (Refactored to mvc)
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
     * @param model The model we get our data from
     * @param dimensions The dimension of the view
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
     */
    public void updateView() {

        CarParkModel parkModel = (CarParkModel)model;
        if(parkModel != null){
            if(parkModel.getHasReset())
                carParkImage = createImage(size.width, size.height);
        }

        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }

        CarParkModel model = (CarParkModel)getModel();

        Graphics graphics = carParkImage.getGraphics();

        drawLegend(graphics);

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
     * @param graphics The grapics
     * @param location The location for the grapics
     * @param color The color for the graphics
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * spacingBetweenFloors + (borderLeft + (int)Math.floor(location.getRow() * 0.5)) * spacingBetweenRows + (location.getRow() % 2) * parkingSpotWidth,
                borderTop + location.getPlace() * verticalOffset, parkingSpotWidth - spacingBetweenSpots, parkingSpotHeight  - spacingBetweenSpots); 
    }

    /**
     * Paint a legend on the graphic
     * @param graphics The grapics
     */
    private void drawLegend(Graphics graphics){
        int offsetColor = 75;
        int offsetString = 100;
        int colorHeight = 20;
        int offsetY = 10;

        addToLegend(graphics, Color.red, offsetString, offsetY, colorHeight, offsetColor, "Regulier");
        offsetString += 100;
        offsetColor += 100;
        addToLegend(graphics, Color.blue, offsetString, offsetY, colorHeight, offsetColor, "Pashouder");
        offsetString += 100;
        offsetColor += 100;
        addToLegend(graphics, Color.green, offsetString, offsetY, colorHeight, offsetColor, "Gereserveerd");
        offsetString += 120;
        offsetColor += 120;
        addToLegend(graphics, Color.yellow, offsetString, offsetY, colorHeight, offsetColor, "Fout geparkeerd");

        offsetString += 201;
        offsetColor += 201;

        addToLegend(graphics, Color.cyan, offsetString, offsetY, colorHeight, offsetColor, "Lege pas plek");
        offsetString += 120;
        offsetColor += 120;
        addToLegend(graphics, Color.pink, offsetString, offsetY, colorHeight, offsetColor, "Lege plek");

        graphics.setColor(Color.blue);

    }

    /**
     * Paint an item on the graphic
     * @param graphics The grapics
     */
    private void addToLegend(Graphics graphics, Color carColor, int offsetString, int offsetY, int colorHeight, int offsetColor, String carType){
        graphics.setColor(carColor);
        graphics.fillRect(offsetColor,offsetY - 16, 20, 20);
        graphics.setColor(Color.black);
        graphics.drawString(carType, offsetString, offsetY);
    }
}