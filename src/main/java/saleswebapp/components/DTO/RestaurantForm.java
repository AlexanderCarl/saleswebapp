package saleswebapp.components.DTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import saleswebapp.components.RestaurantDailyTimeContainer;
import saleswebapp.repository.impl.*;
import saleswebapp.service.RestaurantService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Carl on 07.07.2017.
 * This form is used to transport all information of one (only one!) restaurant to the dialog "restaurant".
 */
public class RestaurantForm implements Serializable {

    private static final long serialVersionUID = 2753301243029536438L;

    private int id;
    private int customerId;
    private String name;
    private String street;
    private String streetNumber;
    private String zip;
    private String city;
    private CountryForm countryForm;
    private Float locationLatitude;
    private Float locationLongitude;
    private String email;
    private String phone;
    private String url;
    private RestaurantTypeForm restaurantTypeForm; //Anbietertyp
    private List<String> restaurantKitchenTypesForm; //Küchentypen
    private List<RestaurantCourseTypeForm> restaurantCourseTypesForm; //Kategorien
    private HashMap<Integer, RestaurantDailyTimeContainer> restaurantTime; //The Hashmap key is the day number
    private boolean offerModifyPermission;
    private boolean blocked;
    private String salesPersonFirstname;
    private String salesPersonSecondname;
    private String restaurantUUID;
    private byte[] qrUuid;
    
    public RestaurantForm() {
        super();
    }
    
    public RestaurantForm(Restaurant restaurant) throws Exception {
        this.id = restaurant.getId();
        this.customerId = restaurant.getCustomerId();
        this.name = restaurant.getName();
        this.street = restaurant.getStreet();
        this.streetNumber = restaurant.getStreetNumber();
        this.zip = restaurant.getZip();
        this.city = restaurant.getCity();
        this.countryForm = new CountryForm(restaurant.getCountry());
        this.locationLatitude = restaurant.getLocationLatitude();
        this.locationLongitude = restaurant.getLocationLongitude();
        this.email = restaurant.getEmail();
        this.phone = restaurant.getPhone();
        this.url = restaurant.getUrl();
        this.restaurantTypeForm = new RestaurantTypeForm(restaurant.getRestaurantType());

        //Fills the List<RestaurantKitchenTypeForm>
        List<KitchenType> kitchenTypes = restaurant.getKitchenTypes();
        List<String> restaurantKitchenTypeForms = new ArrayList<String>();
        for (KitchenType kitchenType : kitchenTypes) {
            restaurantKitchenTypeForms.add(kitchenType.getName());
        }
        this.restaurantKitchenTypesForm = restaurantKitchenTypeForms;

        //Fills the List<RestaurantCourseTypeForm>
        List<CourseType> courseTypes = restaurant.getCourseTypeList();
        List<RestaurantCourseTypeForm> restaurantCourseTypeForms = new ArrayList<RestaurantCourseTypeForm>();
        for (CourseType courseType : courseTypes) {
            restaurantCourseTypeForms.add(new RestaurantCourseTypeForm(courseType));
        }
        this.restaurantCourseTypesForm = restaurantCourseTypeForms;

        DailyTimeContainerFiller(restaurant);

        this.offerModifyPermission = restaurant.isOfferModifyPermission();
        this.blocked = restaurant.isBlocked();
        this.salesPersonFirstname = restaurant.getSalesPerson().getFirstName();
        this.salesPersonSecondname = restaurant.getSalesPerson().getSecondName();
        this.restaurantUUID = restaurant.getRestaurantUUID();
        this.qrUuid = restaurant.getQrUuid();

    }

    private void DailyTimeContainerFiller(Restaurant restaurant) throws Exception {

        if(restaurant.getTimeScheduleList().size() > 7) {
            throw new Exception("Error - The Table Time_Schedule contains more than 7 entries per week for restaurant-ID: " + restaurant.getId());
        }

        int dayNumber;
        Date openingTime;
        Date closingTime;
        Date offerStartTime;
        Date offerEndTime;

        List<TimeSchedule> timeSchedules = restaurant.getTimeScheduleList();
        restaurantTime = new HashMap<Integer, RestaurantDailyTimeContainer>();

        for (TimeSchedule timeSchedule : timeSchedules) {
            dayNumber = 0; //Valid day numbers range from 1 to 7.
            openingTime = null;
            closingTime = null;
            offerStartTime = null;
            offerEndTime = null;

            dayNumber = timeSchedule.getDayOfWeek().getDayNumber();
            offerStartTime = timeSchedule.getOfferStartTime();
            offerEndTime = timeSchedule.getOfferEndTime();

            //The DB allows for more than one time schedule entry per day. But the FindLunchApplication only allows one pair of opening times per day.
            //There it is handled similar here. If there is more than one pair of opening times per day it is ignored.
            if(timeSchedule.getOpeningTimes().size() > 0) {
                openingTime = timeSchedule.getOpeningTimes().get(0).getOpeningTime();
                closingTime = timeSchedule.getOpeningTimes().get(0).getClosingTime();
            }

            restaurantTime.put(dayNumber, new RestaurantDailyTimeContainer(openingTime, closingTime, offerStartTime, offerEndTime));
        }
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryForm getCountryForm() {
        return countryForm;
    }

    public void setCountryForm(CountryForm countryForm) {
        this.countryForm = countryForm;
    }

    public Float getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Float locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Float getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Float locationLongitude) {
        this.locationLongitude = locationLongitude;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RestaurantTypeForm getRestaurantTypeForm() {
        return restaurantTypeForm;
    }

    public void setRestaurantTypeForm(RestaurantTypeForm restaurantTypeForm) {
        this.restaurantTypeForm = restaurantTypeForm;
    }

    public List<String> getRestaurantKitchenTypesForm() {
        return restaurantKitchenTypesForm;
    }

    public void setRestaurantKitchenTypesForm(List<String> restaurantKitchenTypesForm) {
        this.restaurantKitchenTypesForm = restaurantKitchenTypesForm;
    }

    public List<RestaurantCourseTypeForm> getRestaurantCourseTypesForm() {
        return restaurantCourseTypesForm;
    }

    public void setRestaurantCourseTypesForm(List<RestaurantCourseTypeForm> restaurantCourseTypesForm) {
        this.restaurantCourseTypesForm = restaurantCourseTypesForm;
    }

    public HashMap<Integer, RestaurantDailyTimeContainer> getRestaurantTime() {
        return restaurantTime;
    }

    public void setRestaurantTime(HashMap<Integer, RestaurantDailyTimeContainer> restaurantTime) {
        this.restaurantTime = restaurantTime;
    }

    public boolean isOfferModifyPermission() {
        return offerModifyPermission;
    }

    public void setOfferModifyPermission(boolean offerModifyPermission) {
        this.offerModifyPermission = offerModifyPermission;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getSalesPersonFirstname() {
        return salesPersonFirstname;
    }

    public void setSalesPersonFirstname(String salesPersonFirstname) {
        this.salesPersonFirstname = salesPersonFirstname;
    }

    public String getSalesPersonSecondname() {
        return salesPersonSecondname;
    }

    public void setSalesPersonSecondname(String salesPersonSecondname) {
        this.salesPersonSecondname = salesPersonSecondname;
    }

    public String getRestaurantUUID() {
        return restaurantUUID;
    }

    public void setRestaurantUUID(String restaurantUUID) {
        this.restaurantUUID = restaurantUUID;
    }

    public byte[] getQrUuid() {
        return qrUuid;
    }

    public void setQrUuid(byte[] qrUuid) {
        this.qrUuid = qrUuid;
    }
}
