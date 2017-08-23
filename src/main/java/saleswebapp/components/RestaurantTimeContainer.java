package saleswebapp.components;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexander Carl on 14.07.2017.
 */
public class RestaurantTimeContainer {

    private String startTime;
    private String endTime;
    private int dayNumber; //0=Monday
    private String dayNumberAsString; //Needed for the dialog "offer"

    public RestaurantTimeContainer() {
        super();
    }

    public RestaurantTimeContainer(Date startTime, Date endTime, int dayNumber) {
        if(!(startTime == null)) {
            this.startTime = new SimpleDateFormat("HH:mm").format(startTime);
        } else {
            this.startTime = null;
        }

        if(!(endTime == null)) {
            this.endTime = new SimpleDateFormat("HH:mm").format(endTime);
        } else {
            this.endTime = null;
        }

        this.dayNumber = dayNumber;
        this.dayNumberAsString = String.valueOf(dayNumber);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayNumberAsString() {
        return dayNumberAsString;
    }

    public void setDayNumberAsString(String dayNumberAsString) {
        this.dayNumberAsString = dayNumberAsString;
    }
}
