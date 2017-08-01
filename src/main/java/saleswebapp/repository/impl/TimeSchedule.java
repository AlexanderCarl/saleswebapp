package saleswebapp.repository.impl;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "time_schedule")
public class TimeSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "offer_start_time")
    private Date offerStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "offer_end_time")
    private Date offerEndTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "day_of_week_id")
    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "timeSchedule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OpeningTime> openingTimes;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        TimeSchedule other = (TimeSchedule) obj;
        if (Integer.valueOf(id) == null) {
            if (Integer.valueOf(other.id) != null)
                return false;
        }

        if (id != other.getId()) {
            return false;
        }

        if(offerStartTime != null) {
            if (!offerStartTime.equals(other.getOfferStartTime())) {
                return false;
            }
        }

        if(offerEndTime != null) {
            if (!offerEndTime.equals(other.getOfferEndTime())) {
                return false;
            }
        }

        if(openingTimes != null) {
            if (!openingTimes.containsAll(other.getOpeningTimes())) {
                return false;
            }
        }

        if (dayOfWeek.getId() != (other.getDayOfWeek().getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Integer.valueOf(id) == null) ? 0 : (Integer.valueOf(id).hashCode()));

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<OpeningTime> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<OpeningTime> openingTimes) {
        this.openingTimes = openingTimes;
    }
}
