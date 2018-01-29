package nl.parkingsimulator.logic;

public class TimeEvent {
    private int startDay;
    private int startHour;
    private int startMinute;
    private int endDay;
    private int endHour;
    private int endMinute;
    private String eventTitle;
    private float carsModifier;

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

    public boolean checkEvent(int currentDay, int currentHour, int currentMinute){

        if(checkDay(currentDay) && checkHours(currentDay, currentHour) && checkMinutes(currentMinute)){
            return true;
        }

        return false;
    }

    private boolean checkDay(int currentDay){
        if(currentDay == 0 && startDay == 6 && endDay == 7){
            return true;
        }

        if(currentDay >= startDay && currentDay <= endDay) {
            return true;
        }

        return false;
    }

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

    private boolean checkMinutes(int currentMinutes){
        // TODO: Implement minutes;
        if(true)
            return true;

        return false;
    }

    public String getEventTitle(){
        return eventTitle;
    }

    public float getCarsModifier(){
        return carsModifier;
    }
}
