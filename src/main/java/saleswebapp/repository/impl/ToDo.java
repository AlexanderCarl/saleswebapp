package saleswebapp.repository.impl;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Entity
@Table(name = "swa_todo_list")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_request_typ_id")
    private ToDoRequestTyp toDoRequestTyp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_person_id")
    private SalesPerson salesPerson;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Type(type = "timestamp")
    private Date datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ToDoRequestTyp getToDoRequestTyp() {
        return toDoRequestTyp;
    }

    public void setToDoRequestTyp(ToDoRequestTyp toDoRequestTyp) {
        this.toDoRequestTyp = toDoRequestTyp;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
