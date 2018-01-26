package nl.parkingsimulator.logic;

public class TimeEvent {
    private int startDay;
    private int startHour;
    private int startMinute;
    private int endDay;
    private int endHour;
    private int endMinute;
    private String eventTitle;

    public TimeEvent(int startDay, int startHour, int startMinute, int endDay, int endHour, int endMinute, int carsModifier, String eventTile){
        this.startDay = startDay;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endDay = endDay;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.eventTitle = eventTile;
    }

    public boolean checkEvent(int currentDay, int currentHour, int currentMinute){
        if(currentDay >= startDay && currentDay <= endDay){
            if(currentHour >= startHour || currentHour < endHour){
                // TODO: add minutes
                return true;
            }
        }

        return false;
    }

    public String getEventTitle(){
        return eventTitle;
    }
}
