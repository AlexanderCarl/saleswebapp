package saleswebapp.repository.impl;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "opening_time")
public class OpeningTime {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "opening_time")
    private Date openingTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "closing_time")
    private Date closingTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "time_schedule_id")
    private TimeSchedule timeSchedule;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        OpeningTime other = (OpeningTime) obj;
        if (Integer.valueOf(id) == null) {
            if (Integer.valueOf(other.id) != null)
                return false;
        }

        if (id != other.getId()) {
            return false;
        }

        if (!openingTime.equals(other.getOpeningTime())) {
            return false;
        }

        if (!closingTime.equals(other.getClosingTime())) {
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

    public TimeSchedule getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(TimeSchedule timeSchedule) {
        this.timeSchedule = timeSchedule;
    }
}
