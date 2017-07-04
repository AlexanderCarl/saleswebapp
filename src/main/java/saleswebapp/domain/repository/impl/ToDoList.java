package saleswebapp.domain.repository.impl;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Entity
@Table(name = "swa_todo_list")
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_request_typ_id")
    private ToDoListRequestTyp toDoRequestTyp;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sales_person_id")
    private SalesPerson salesPerson;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Type(type = "timestamp")
    private Date datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ToDoListRequestTyp getToDoRequestTyp() {
        return toDoRequestTyp;
    }

    public void setToDoRequestTyp(ToDoListRequestTyp toDoRequestTyp) {
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
}
