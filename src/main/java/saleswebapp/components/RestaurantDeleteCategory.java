package saleswebapp.components;

/**
 * Created by Alexander Carl on 20.07.2017.
 */
public class RestaurantDeleteCategory {

    private String name;
    private int restaurantId;

    public RestaurantDeleteCategory() {
        super();
    }

    public RestaurantDeleteCategory(int restaurantId) {
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
