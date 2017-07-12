package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Entity
@Table(name = "restaurant_type")
public class RestaurantType {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "restaurantType", fetch = FetchType.LAZY)
    private List<Restaurant> restaurants;

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

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
