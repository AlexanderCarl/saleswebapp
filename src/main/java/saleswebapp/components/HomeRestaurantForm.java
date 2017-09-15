package saleswebapp.components;

import saleswebapp.repository.impl.Restaurant;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
public class HomeRestaurantForm {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String street;
    private String streetNumber;
    private String city;
    private String zip;

    public HomeRestaurantForm() {
        super();
    }

    public HomeRestaurantForm(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.email = restaurant.getEmail();
        this.phone = restaurant.getPhone();
        this.street = restaurant.getStreet();
        this.streetNumber = restaurant.getStreetNumber();
        this.city = restaurant.getCity();
        this.zip = restaurant.getZip();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
