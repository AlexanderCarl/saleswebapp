package saleswebapp.components;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Alexander Carl on 18.07.2017.
 */
public class RestaurantAddCategory {

    private String name; //Name of the category
    private int restaurantId;

    public RestaurantAddCategory() {
        super();
    }

    public RestaurantAddCategory(int restaurantId) {
        this.name = null;
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

}
