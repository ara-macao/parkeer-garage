package nl.parkingsimulator.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * CarParkModel
 * This model contains all data used for the simulation.
 *
 * @author Hanze
 * @author Jeroen Westers (Refactored to mvc)
 */
public class CarParkModel extends AbstractModel implements Runnable {

    public static void numberOfOpenSpots() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
     * The values of these variables are declared in the settings class.
     */
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    
    private int tickPause;
    private int amountOfTicks;

    private int enterSpeed; // number of cars that can enter per minute
    private int paymentSpeed; // number of cars that can pay per minute
    private int exitSpeed; // number of cars that can leave per minute
    
    
    private int numberOfOpenSpots;
    private Car[][][] cars;
    public Location[][][] locations;

    public static final int AD_HOC = 1;
    public static final int PASS = 2;
    public static final int RESERVED = 3;
    public static final int BAD_PARKING = 4;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;

    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    
    private int totalTicks = 0;

    private Thread simThread = null;
    private boolean running = false;
    private boolean pause = false;
    private int currentTick = 0;
    private Settings settings;

    private double hourPrice = 1.2;
    private double dayRevenue = 0;
    private double revenueNotPayed = 0;

    private HashMap<Integer, Double> weekRevenue = new HashMap<Integer, Double>();
    private int missedCarsMinute = 0;
    private int missedCarsHour = 0;
    private int missedCarsDay = 0;
    private int missedCarsWeek = 0;
    private ArrayList<TimeEvent> timeEvents;
    private String eventTitle = "";
    private float eventMultiplier = 1;

    private HashMap<Integer, Integer> currentTotalCars = new HashMap<Integer, Integer>();
    private boolean hasReset = false;


    /**
     * Constructor for objects of class CarParkModel
     *
     * @param settings The settings for this model
     */
    public CarParkModel(Settings settings) {
        initializeCarParkModel(settings);
    }

    private void initializeCarParkModel(Settings settings){
        ApplySettings(settings);

        tickPause = 0;
        amountOfTicks = 0;
        numberOfOpenSpots = 0;
        cars= null;
        locations= null;

        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();

        day = 0;
        hour = 0;
        minute = 0;
        totalTicks = 0;

        running = false;
        pause = false;
        currentTick = 0;
        hourPrice = 1.2;
        dayRevenue = 0;
        revenueNotPayed = 0;
        weekRevenue = new HashMap<Integer, Double>();
        missedCarsMinute = 0;
        missedCarsHour = 0;
        missedCarsDay = 0;
        missedCarsWeek = 0;
        eventTitle = "";
        eventMultiplier = 1;
        timeEvents = new ArrayList<>(); // initialize event
        currentTotalCars.clear();
        generateEvents();

        this.tickPause = settings.getTickPause();
        this.amountOfTicks = settings.getAmountOfTicks();

        generateParkingLot();
        initializeQueues();

    }

    /**
     * Generates the parking lot and it's size
     */
    private void generateParkingLot(){
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        locations = new Location[numberOfFloors][numberOfRows][numberOfPlaces];

        for (int floor = 0; floor < numberOfFloors; floor++) {
            for (int row = 0; row < numberOfRows; row++) {
                for (int place = 0; place < numberOfPlaces; place++) {
                    locations[floor][row][place] = new Location(floor, row, place);
                }
            }
        }
    }

    /**
     * Initializes the queues
     */
    private void initializeQueues(){
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simThread = new Thread(this);
    }

    /**
     * Generates the events like night, buy evening and concerts
     */
    private void generateEvents(){
        // 0 = m  1 = d 2 = w 3 = d 4 = v 5 = z 6 = z

        // Create all night
        for (int d = 0; d < 7; d++){
            timeEvents.add(new TimeEvent(d,23,0,d+1,7,0,0f, "Nacht"));
        }

        // Buy evening
        timeEvents.add(new TimeEvent(3, 17, 30, 3, 22, 30, 2f, "Koopavond"));

        // Add concerts
        timeEvents.add(new TimeEvent(4, 20, 00, 5, 0, 0, 2.3f, "Concert"));
        timeEvents.add(new TimeEvent(5, 20, 00, 6, 0, 0, 2.5f, "Concert"));
        timeEvents.add(new TimeEvent(6, 13, 00, 6, 17, 0, 18f, "Concert"));
    }

    /**
     * Returns the total cars
     * @param type The type of car you wan't to know the amount of
     */
    public int getCurrentTotalCars(int type){

        if(currentTotalCars.containsKey(type)){
            return currentTotalCars.get(type);
        }

        return 0;
    }

    /**
     * Resets the simulation to init variables
     */
    public void resetSimulation(){
        if(simThread.isAlive())
            stopSimulation();

        hasReset = true;
        initializeCarParkModel(settings);

        notifyViews();
    }

    /**
     * Applies new settings to the model
     * @param settings The settings that are going to be applied
     */
    private void ApplySettings(Settings settings){
        this.settings = settings;

        this.numberOfFloors = settings.getParkingFloors();
        this.numberOfRows = settings.getParkingRows();
        this.numberOfPlaces = settings.getParkingPlacesPerRow();

        this.enterSpeed = settings.getEnterSpeed();
        this.paymentSpeed = settings.getPaymentSpeed();
        this.exitSpeed = settings.getExitSpeed();
    }

    /**
     * Starts the simulation thread
     */
    public void startSimulation() {
        // check if the thread is running
        if(simThread.isAlive()) {
            System.out.println("Already running!");
            //running = false;
        }else{
            simThread = new Thread(this);
            simThread.start();
        }
    }

    /**
     * Stops the simulation thread
     */
    public void stopSimulation() {
        if(simThread.isAlive()) {
            running = false;
        }
    }

    /**
     * Sets the amount of tick the simulation should run
     * @param ticks The amount of ticks
     */
    public void setAmountOfTicks(int ticks) {
        amountOfTicks = ticks;
    }

    /**
     * Returns the current amount of ticks set
     * @return  amount of ticks
     */
    public int getAmountOfTicks() {
        return amountOfTicks;
    }

    /**
     * Returns the current tick progress
     * @return current tick
     */
    public int getTickProgress() {
        return currentTick;
    }

    /**
     * Sets the pause time between the ticks
     * @param tickPause Tick pause
     */
    public void setTickPause(int tickPause) {
        this.tickPause = tickPause;
        settings.setTickPause(tickPause);
    }

    //public int getMissedCars() {
        //return missedCars;
    //}

    /**
     * Gets the missed cars per minute
     * @return missed cars per minute
     */
    public int getMissedCarsMinute(){
        return missedCarsMinute;
    }

    /**
     * Gets the missed cars per hour
     * @return missed cars per hour
     */
    public  int getMissedCarsHour(){
        return missedCarsHour;
    }

    /**
     * Gets the missed cars per day
     * @return missed cars per day
     */
    public int getMissedCarsDay(){
        return missedCarsDay;
    }

    /**
     * Gets the missed cars per week
     * @return missed cars per week
     */
    public int getMissedCarsWeek() {
        return missedCarsWeek;
    }

    /**
     * Sets the simulation state (pause)
     * @param state The new state
     */
    public synchronized void setPauseState(boolean state) {
        pause = state;

        if(!pause) {
            notify();
        }
    }

    /**
     * Gets the total of cars in the entrance car queue
     * @return total cars
     */
    public int getEntranceCarQueue() {
    	return entranceCarQueue.carsInQueue();
    }

    /**
     * Gets the total of cars in the entrance pass queue
     * @return total cars
     */
    public int getEntrancePassQueue() {
    	return entrancePassQueue.carsInQueue();
    }

    /**
     * Gets the total of cars in the payment queue
     * @return total cars
     */
    public int getPaymentCarQueue() {
    	return paymentCarQueue.carsInQueue();
    }

    /**
     * Gets the total of cars in the exit car queue
     * @return total cars
     */
    public int getExitCarQueue() {
    	return exitCarQueue.carsInQueue();
    }

    /**
     * Gets the number of floors
     * @return amount
     */
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Gets the number of rows
     * @return amount
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Gets the number of places
     * @return amount
     */
    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Gets the number of openspots
     * @return amount
     */
    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }

    /**
     * Gets the number of total spots
     * @return amount
     */
    public int getNumberOfSpots() {
        return numberOfFloors * numberOfRows * numberOfPlaces;
    }

    /**
     * Gets the current day
     * @return day
     */
    public int getDay(){
        return day;
    }

    /**
     * Gets the current hour
     * @return hour
     */
    public int getHour(){
        return hour;
    }

    /**
     * Gets the current minute
     * @return minute
     */
    public int getMinute(){
        return minute;
    }

    /**
     * Gets the total ticks
     * @return total ticks
     */
    public int getTotalTicks() {
        return totalTicks;
    }

    /**
     * Gets the current day name
     * @return day name
     */
    public String getDayName(){

        String dayName = "";

        switch (day){
            case 0:
                dayName = "Maandag";
                break;
            case 1:
                dayName = "Dinsdag";
                break;
            case 2:
                dayName = "Woensdag";
                break;
            case 3:
                dayName = "Donderdag";
                break;
            case 4:
                dayName = "Vrijdag";
                break;
            case 5:
                dayName = "Zaterdag";
                break;
            case 6:
                dayName = "Zondag";
                break;
            default:
                dayName = "Error occured";
                break;
        }

        return dayName;
    }


    /**
     * Gets the car at the given location
     * @param location Location of the car
     * @return the car
     */
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    /**
     * Sets an car at the given location
     * @param location Location of the car
     * @param car The car
     * @return True if it succeed
     */
    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            
            int carType = car.getCarType();
            addCarToTotal(carType);

            return true;
        }
        return false;
    }

    /**
     * Removes the car at the given location
     * @param location The location where the car should be
     * @return the removed car
     */
    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }


    /**
     * Returns the first free location if exist
     * @param  car
     * @return The first free location found
     */
    public Location getFirstFreeLocation(Car car) {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = locations[floor][row][place];
                    if (getCarAt(location) == null) {
                        if(location.getReservation() == null) {
                            return location;
                        }
                        else if(location.getReservation().getCarType() == car.getCarType()){
                            return location;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the first car leaving
     * @return The car that want's to leave
     */
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = locations[floor][row][place];
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sets reservations
     * @param floor The floor to reserve
     * @param row The row to reserve
     * @param place  The place to reserve
     * @param reservation The reservation to set
     */
    public void setReservationAt(int floor, int row, int place, Reservation reservation) {
         locations[floor][row][place].setReservation(reservation);
    }

    /**
     * Advances the time
     */
    private void advanceTime() {
        // Advance the time by one minute.
        minute++;
        missedCarsHour += missedCarsMinute;
        missedCarsDay += missedCarsMinute;
        missedCarsWeek += missedCarsMinute;


        while (minute > 59) {
            minute -= 60;
            hour++;
            missedCarsHour = 0;
        }
        while (hour > 23) {
            hour -= 24;
            newDay();
            day++;
            missedCarsDay = 0;
        }
        while (day > 6) {
            day -= 7;
            missedCarsWeek = 0;
        }

        checkEvent();
    }

    /**
     * Handles all cars entering
     */
    private void handleEntrance() {
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }

    /**
     * Handles all cars that are trying to leave
     */
    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    /**
     * Updates all the views
     */
    private void updateViews() {
    	super.notifyViews();
        hasReset = false;
    }

    /**
     * Get boolean to check if the simulation has been reset
     */
    public boolean getHasReset(){
        return hasReset;
    }

    /**
     * Handles all cars that are arriving at the paring garage
     */
    private void carsArriving() {
        int numberOfCars = getNumberOfCars(settings.getWeekDayArrivals() * eventMultiplier, settings.getWeekendArrivals()* eventMultiplier);
        int totalWeekNumbers = (int)(settings.getWeekDayArrivals() * eventMultiplier);
        int totalWeekendNumbers = (int)(settings.getWeekendArrivals()* eventMultiplier);
        addArrivingCars(numberOfCars, AD_HOC);
    	numberOfCars = getNumberOfCars(settings.getWeekDayPassArrivals()* eventMultiplier, settings.getWeekendPassArrivals()* eventMultiplier);
        totalWeekNumbers += (int)(settings.getWeekDayPassArrivals() * eventMultiplier);
        totalWeekendNumbers += (int)(settings.getWeekendPassArrivals()* eventMultiplier);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars = getNumberOfCars(settings.getWeekDayReserved()* eventMultiplier, settings.getWeekendReserved()* eventMultiplier);
        addArrivingCars(numberOfCars, RESERVED);
        numberOfCars = getNumberOfCars(totalWeekNumbers / 8, totalWeekendNumbers / 88);
        addArrivingCars(numberOfCars, BAD_PARKING);

    }

    /**
     * Handles queues so cars can enter
     * @param queue The queue with cars
     */
    private void carsEntering(CarQueue queue) {
        int i = 0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue() > 0 && getNumberOfOpenSpots() > 0 && i < enterSpeed) {
            Car car = queue.removeCar();
            Location freeLocation = getFirstFreeLocation(car);
            setCarAt(freeLocation, car);

            i++;
        }
    }

    /**
     * Handles cars that are leaving
     */
    private void carsReadyToLeave() {
        
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()) {
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = getFirstLeavingCar();
        }
    }

    /**
     * Handles payment of cars
     */
    private void carsPaying() {
        // Let cars pay.
    	int i = 0;
    	while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            addRevenue(car);
            carLeavesSpot(car);
            i++;
    	}
    }

    /**
     * Handles the revenue from cars
     * @param  car The car that has too pay
     */
    private void addRevenue(Car car) {
        dayRevenue += calculatePrice(car);
    }

    /**
     * Calculate the prices
     * @param  car The car that has too pay
     */
    private float calculatePrice(Car car) {
        if(!car.getHasToPay())
            return 0.0f;

        return (float)(car.getTotalMinuteParked()) * (float)(hourPrice /60);
    }

    /**
     * Calculate how many revenue not have been payed yet
     */
    private void calculateRevenueNotPayed() {
        revenueNotPayed = 0;

        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = locations[floor][row][place];
                    Car car = getCarAt(location);
                    if (car != null) {
                        if(car.getHasToPay()){
                            revenueNotPayed += calculatePrice(car);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles leaving cars
     */
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
    	    Car car = exitCarQueue.removeCar();
            removeCarTotal(car.getCarType());
            i++;
    	}	
    }

    /**
     * Gets the number of cars
     * @param weekDay Amount of cars on a weekday
     *  @param weekend Amount of cars in the weekend
     */
    private int getNumberOfCars(float weekDay, float weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? Math.round(weekDay)
                : Math.round(weekend);

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }

    /**
     * Add ariving cars
     * @param numberOfCars The amount of cars
     * @param type Type of the car
     */
    private void addArrivingCars(int numberOfCars, int type){
        // Add the cars to the back of the queue.

        switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
                addCarsToQueue(entranceCarQueue, new AdHocCar(type), i);
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
                addCarsToQueue(entrancePassQueue, new ParkingPassCar(type), i);
            }
            break;	            
        case RESERVED:
            for (int i = 0; i < numberOfCars; i++) {
                addCarsToQueue(entrancePassQueue, new ReservedCar(type), i);
            }
            break;
        case BAD_PARKING:
            for (int i = 0; i < numberOfCars; i++) {
                addCarsToQueue(entrancePassQueue, new BadParkedCar(type), i);
            }
            break;
        }
    }

    /**
     * Adds cars to the queue
     * @param carQueue The car queue with cars
     * @param car The car to add
     * @param index Check if we haven't added too many cars
     */
    private void addCarsToQueue(CarQueue carQueue, Car car, int index){

        if(index < enterSpeed){
                carQueue.addCar(car);

            }else{
                missedCarsMinute++;
            }
    }

    /**
     * Adds cars to the total based on type.
     * @param carType The type of the car
     */
    private void addCarToTotal(int carType){
        int carIndex = 0;

        if(currentTotalCars.containsKey(carType)){
            carIndex = currentTotalCars.get(carType);
            carIndex++;
        }
        // add car to the totalCar hashmap
        currentTotalCars.put(carType, carIndex);
    }

    /**
     * Removed a car from the total based on type
     * @param carType The type of the car
     */
    private void removeCarTotal(int carType){
        int carIndex = 0;

        if(currentTotalCars.containsKey(carType)){
            carIndex = currentTotalCars.get(carType);
            carIndex--;
        }

        if(carIndex <= 0){
            carIndex = 0;
        }
        // remove car to the totalCar hashmap
        currentTotalCars.put(carType, carIndex);
    }

    /**
     * Adds the car to the exit queue
     * @param car The car that is leaving
     */
    private void carLeavesSpot(Car car){
    	removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    /**
     * Main logic loop, updates every data
     */
    public void tick(){
        advanceTime();
    	handleExit();
        calculateRevenueNotPayed();
        updateViews();
        missedCarsMinute = 0;


        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        handleEntrance();

        tickCars();
    }

    /**
     * Main logic loop for the cars, updates every data
     */
    public void tickCars() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = locations[floor][row][place];
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    /**
     * Clears all data that should be reset after a day
     */
    private void newDay(){
        if(day == 0)
            weekRevenue.clear();

        weekRevenue.put(day, dayRevenue);
        dayRevenue = 0;
    }

    /**
     * Returns revenue for a week
     * @return the total revenue per day for a week
     */
    public  HashMap<Integer, Double> getWeekRevenue(){
        return weekRevenue;
    }

    /**
     * Returns revenue for a day
     * @return the total revenue for a day
     */
    public double getRevenue(){
        return dayRevenue;
    }

    /**
     * Returns revenue for a day that isn't payed yet
     * @return the total revenue for a day not payed
     */
    public double getRevenueNotPayed(){
        return revenueNotPayed;
    }

    /**
     * Returns the simulation settings
     * @return The settings
     */
    public Settings getSettings() {
    	return settings;
    }

    /**
     * Sets the settings
     * @param  settings the settings that needs to be applied to the simulation
     */
    public void setSettings(Settings settings){
        ApplySettings(settings);
    }

    /**
     * Checks if a location is valid
     * @param location The location that needs to be validated
     * @return True if the location is valid
     */
    private boolean locationIsValid(Location location) {
        if(location != null) {
            int floor = location.getFloor();
            int row = location.getRow();
            int place = location.getPlace();
            if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if an event is happening
     */
    private void checkEvent(){
        boolean foundEvent = false;

        for (TimeEvent event : timeEvents) {
            if(event.checkEvent(day, hour, minute)){
                eventTitle = event.getEventTitle();
                // Easy debugging events
                //System.out.println("Event is happening at: "  + day + "-" + hour + "-" + minute);
                eventMultiplier = event.getCarsModifier();
                foundEvent = true;
            }
        }

        if(!foundEvent){
            eventMultiplier = 1;
            eventTitle = "Geen event";
        }
    }

    /**
     * Gets the current event title
     * @return the event tile
     */
    public String getEventTitle(){
        return eventTitle;
    }

    /**
     * Method called by thread
     */
    @Override
    public void run() {
        running = true;
        currentTick = 0;

        for (int i = 0; i < amountOfTicks; i++) {
            if(!running)
                return;

            synchronized (this){
                try {
                    while(pause){
                        wait();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            currentTick++;
            tick();
            totalTicks++;
        }

        running = false;
    }
}
