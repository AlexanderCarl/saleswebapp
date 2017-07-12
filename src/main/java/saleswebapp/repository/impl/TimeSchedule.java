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

    @OneToMany(mappedBy = "timeSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OpeningTime> openingTimes;

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
