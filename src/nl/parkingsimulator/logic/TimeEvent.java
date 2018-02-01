package nl.parkingsimulator.logic;

/**
 * TimeEvent
 * This class is used to create an event scheduled at an certain time.
 *
 * @author Jeroen Westers
 */
public class TimeEvent {
    private int startDay;
    private int startHour;
    private int startMinute;
    private int endDay;
    private int endHour;
    private int endMinute;
    private String eventTitle;
    private float carsModifier;


    /**
     * Constructor for objects of class TimeEvent
     *
     * @param startDay The day the event starts
     * @param startHour The hour the event starts
     * @param startMinute The minute the event starts
     * @param endDay The day the event ends
     * @param endHour The hour the event ends
     * @param endMinute The minute the event ends
     * @param carsModifier The multiplier for the event
     * @param eventTile The event title
     */
    public TimeEvent(int startDay, int startHour, int startMinute, int endDay, int endHour, int endMinute, float carsModifier, String eventTile){
        this.startDay = startDay;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endDay = endDay;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.eventTitle = eventTile;
        this.carsModifier = carsModifier;

    }

    /**
     * Checks if the event is happening
     * @param currentDay The current day
     * @param currentHour The current hour
     * @param currentMinute The current minute
     * @return True or false, depends on the condition
     */
    public boolean checkEvent(int currentDay, int currentHour, int currentMinute){

        if(checkDay(currentDay) && checkHours(currentDay, currentHour) && checkMinutes(currentMinute)){
            return true;
        }

        return false;
    }

    /**
     * Checks if the current day matches the event
     * @param currentDay The current day
     * @return True or false, if the time matches
     */
    private boolean checkDay(int currentDay){
        if(currentDay == 0 && startDay == 6 && endDay == 7){
            return true;
        }

        if(currentDay >= startDay && currentDay <= endDay) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the current hour matches the event
     * @param currentDay The current day
     * @param currentHour The current hour
     * @return True or false, if the time matches
     */
    private boolean checkHours(int currentDay, int currentHour){

        if(currentDay == 0 && startDay == 6 && endDay == 7){
            currentDay = 7;
        }


        if(startDay == endDay && currentDay >= startDay && currentDay <= endDay){
            if(currentHour >= startHour && currentHour <= endHour){
                return true;
            }
        }else if(currentDay == startDay){
            if(currentHour >= startHour){
                return true;
            }
        }else if(currentDay > startDay && currentDay < endDay){
            return true;
        }else if(currentDay == endDay){
            if(currentHour < endHour){
                return true;
            }
        }else{
            System.out.println("Something went wrong again");
        }
        return false;
    }

    /**
     * Checks if the current minute matches the event
     * @param currentMinutes The current minute
     * @return True or false, if the time matches
     */
    private boolean checkMinutes(int currentMinutes){
        if(currentMinutes >= startMinute)
            return true;

        return false;
    }

    /**
     * Returns the event title
     * @return The title of the event
     */
    public String getEventTitle(){
        return eventTitle;
    }

    /**
     * Returns the multiplier for the event
     * @return The multiplier for the event
     */
    public float getCarsModifier(){
        return carsModifier;
    }
}
