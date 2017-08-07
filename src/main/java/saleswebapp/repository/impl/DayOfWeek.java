package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "day_of_week")
public class DayOfWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(name = "day_number")
    private int dayNumber;

    @OneToMany(mappedBy = "dayOfWeek", fetch = FetchType.LAZY)
    private List<TimeSchedule> timeSchedules;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dayOfWeeks")
    private List<Offer> offerList;

    @PreRemove
    private void removeDayOfWeeksFromOffer() {
        for(Offer offer : offerList) {
            offer.getDayOfWeeks().remove(offer);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public List<TimeSchedule> getTimeSchedules() {
        return timeSchedules;
    }

    public void setTimeSchedules(List<TimeSchedule> timeSchedules) {
        this.timeSchedules = timeSchedules;
    }
}
