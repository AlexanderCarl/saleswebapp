package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "kitchen_type")
public class KitchenType  {

    @Id
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "kitchenTypes")
    private List<Restaurant> restaurantList;

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

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
