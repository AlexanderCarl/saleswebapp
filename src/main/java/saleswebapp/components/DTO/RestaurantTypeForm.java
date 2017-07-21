package saleswebapp.components.DTO;

import saleswebapp.repository.impl.RestaurantType;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
public class RestaurantTypeForm implements Serializable {

    private static final long serialVersionUID = 2306551381018384853L;

    private int id;
    private String name;

    public RestaurantTypeForm() {
        super();
    }

    public RestaurantTypeForm(RestaurantType restaurantType) {
        this.id = restaurantType.getId();
        this.name = restaurantType.getName();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
