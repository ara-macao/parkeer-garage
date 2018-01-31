package nl.parkingsimulator.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

    private void initializeQueues(){
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simThread = new Thread(this);
    }

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

    public int getCurrentTotalCars(int type){

        if(currentTotalCars.containsKey(type)){
            return currentTotalCars.get(type);
        }

        return 0;
    }

    public void resetSimulation(){
        if(simThread.isAlive())
            stopSimulation();

        hasReset = true;
        initializeCarParkModel(settings);

        notifyViews();
    }


    private void ApplySettings(Settings settings){
        this.settings = settings;

        this.numberOfFloors = settings.getParkingFloors();
        this.numberOfRows = settings.getParkingRows();
        this.numberOfPlaces = settings.getParkingPlacesPerRow();

        this.enterSpeed = settings.getEnterSpeed();
        this.paymentSpeed = settings.getPaymentSpeed();
        this.exitSpeed = settings.getExitSpeed();
    }
    
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

    public void stopSimulation() {
        if(simThread.isAlive()) {
            running = false;
        }
    }

    public void setAmountOfTicks(int ticks) {
        amountOfTicks = ticks;
    }

    public int getAmountOfTicks() {
        return amountOfTicks;
    }

    public int getTickProgress() {
        return currentTick;
    }

    public void setTickPause(int tickPause) {
        this.tickPause = tickPause;
        settings.setTickPause(tickPause);
    }

    //public int getMissedCars() {
        //return missedCars;
    //}

    public int getMissedCarsMinute(){
        return missedCarsMinute;
    }

    public  int getMissedCarsHour(){
        return missedCarsHour;
    }

    public int getMissedCarsDay(){
        return missedCarsDay;
    }

    public int getMissedCarsWeek() {
        return missedCarsWeek;
    }

    public synchronized void setPauseState(boolean state) {
        pause = state;

        if(!pause) {
            notify();
        }
    }
    
    public int getEntranceCarQueue() {
    	return entranceCarQueue.carsInQueue();
    }
    
    public int getEntrancePassQueue() {
    	return entrancePassQueue.carsInQueue();
    }
    
    public int getPaymentCarQueue() {
    	return paymentCarQueue.carsInQueue();
    }
    
    public int getExitCarQueue() {
    	return exitCarQueue.carsInQueue();
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }
    
    public int getNumberOfSpots() {
        return numberOfFloors * numberOfRows * numberOfPlaces;
    }

    public int getDay(){
        return day;
    }
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    
    public int getTotalTicks() {
        return totalTicks;
    }

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


    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

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

    public void setReservationAt(int floor, int row, int place, Reservation reservation) {
         locations[floor][row][place].setReservation(reservation);
    }
    
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

    private void handleEntrance() {
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);  	
    }
    
    private void handleExit() {
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews() {
    	super.notifyViews();
        hasReset = false;
    }

    public boolean getHasReset(){
        return hasReset;
    }
    
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

    private void carsPaying() {
        // Let cars pay.
    	int i = 0;
    	while (paymentCarQueue.carsInQueue() > 0 && i < paymentSpeed) {
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            addRevenue(car);
            carLeavesSpot(car);
            i++;
    	}
    }

    private void addRevenue(Car car) {
        dayRevenue += calculatePrice(car);
    }

    private float calculatePrice(Car car) {
        if(!car.getHasToPay())
            return 0.0f;

        return (float)(car.getTotalMinuteParked()) * (float)(hourPrice /60);
    }

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
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
    	    Car car = exitCarQueue.removeCar();
            removeCarTotal(car.getCarType());
            i++;
    	}	
    }
    
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
    
    private void addArrivingCars(int numberOfCars, int type){
        // Add the cars to the back of the queue.

        // TO-DO: Add missed cars
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

    private void addCarsToQueue(CarQueue carQueue, Car car, int index){

        if(index < enterSpeed){
                carQueue.addCar(car);

            }else{
                missedCarsMinute++;
            }
    }

    private void addCarToTotal(int carType){
        int carIndex = 0;

        if(currentTotalCars.containsKey(carType)){
            carIndex = currentTotalCars.get(carType);
            carIndex++;
        }
        // add car to the totalCar hashmap
        currentTotalCars.put(carType, carIndex);
    }

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
    
    private void carLeavesSpot(Car car){
    	removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }
    
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

    /*
    * Clears the revenue to calculate the new day
     */
    private void newDay(){
        if(day == 0)
            weekRevenue.clear();

        weekRevenue.put(day, dayRevenue);
        dayRevenue = 0;
    }

    public  HashMap<Integer, Double> getWeekRevenue(){
        return weekRevenue;
    }

    public double getRevenue(){
        return dayRevenue;
    }

    public double getRevenueNotPayed(){
        return revenueNotPayed;
    }
    
    public Settings getSettings() {
    	return settings;
    }

    public void setSettings(Settings settings){
        ApplySettings(settings);
    }

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

    /*
     * Checks if there is an event happening at the current time
     *
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

    public String getEventTitle(){
        return eventTitle;
    }

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
