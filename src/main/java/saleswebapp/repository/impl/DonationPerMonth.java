package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexander Carl on 13.09.2017.
 * Donation per month contains the donations, which are have been done by restaurants.
 */
@Entity
@Table(name = "donation_per_month")
public class DonationPerMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date date;

    @Column(name = "amount")
    private double donationAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "datetime_of_update")
    private Date datetimeOfUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDatetimeOfUpdate() {
        return datetimeOfUpdate;
    }

    public void setDatetimeOfUpdate(Date datetimeOfUpdate) {
        this.datetimeOfUpdate = datetimeOfUpdate;
    }
}
