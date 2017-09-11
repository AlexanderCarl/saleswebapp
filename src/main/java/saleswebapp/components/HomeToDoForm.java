package saleswebapp.components;

import saleswebapp.repository.impl.ToDo;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 04.07.2017.
 * This form is used in the dialog home.
 */
public class HomeToDoForm implements Serializable {

    private static final long serialVersionUID = -456375631841131140L;

    private int id;
    private int restaurantId;
    private String requestTyp;
    private String restaurantName;
    private String timestamp;

    public HomeToDoForm() {
        super();
    }

    public HomeToDoForm(ToDo toDo) {
        this.id = toDo.getId();
        this.restaurantId = toDo.getRestaurant().getId();
        this.requestTyp = toDo.getToDoRequestTyp().getName();
        this.restaurantName = toDo.getRestaurant().getName();

        String time = toDo.getDatetime().toString();
        this.timestamp = time.substring(0, time.length() - 5);
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

    public String getRequestTyp() {
        return requestTyp;
    }

    public void setRequestTyp(String requestTyp) {
        this.requestTyp = requestTyp;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
