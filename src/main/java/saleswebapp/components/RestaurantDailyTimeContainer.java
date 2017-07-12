package saleswebapp.components;

import org.joda.time.DateTime;
import saleswebapp.repository.impl.TimeSchedule;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
public class RestaurantDailyTimeContainer {

    private Date openingTime;
    private Date closingTime;

    private Date offerStartTime;
    private Date offerEndTime;

    public  RestaurantDailyTimeContainer() {
        super();
    }

    public RestaurantDailyTimeContainer(Date openingTime, Date closingTime, Date offerStartTime, Date offerEndTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.offerStartTime = offerStartTime;
        this.offerEndTime = offerEndTime;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public Date getOfferStartTime() {
        return offerStartTime;
    }

    public void setOfferStartTime(Date offerStartTime) {
        this.offerStartTime = offerStartTime;
    }

    public Date getOfferEndTime() {
        return offerEndTime;
    }

    public void setOfferEndTime(Date offerEndTime) {
        this.offerEndTime = offerEndTime;
    }
}
