package saleswebapp.components;

import saleswebapp.repository.impl.Restaurant;

/**
 * Created by Alexander Carl on 08.07.2017.
 * This class is used for the restaurant dropdown (list) at the beginning of the restaurant dialog.
 */
public class RestaurantListForm {

    private int id;
    private String name;

    public RestaurantListForm() {
        super();
    }

    public RestaurantListForm(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
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
}
