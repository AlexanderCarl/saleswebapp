package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    private String name;

    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    private String zip;

    private String city;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_code")
    private Country country;

    @Column(name = "location_latitude")
    private Float locationLatitude;

    @Column(name = "location_longitude")
    private Float locationLongitude;

    private String email;

    private String phone;

    private String url;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_type_id")
    private RestaurantType restaurantType;

    @Column(name = "restaurant_uuid")
    private String restaurantUUID;

    @Lob
    @Column(name = "qr_uuid")
    private byte[] qrUuid;

    @Column(name = "swa_offer_modify_permission")
    private boolean offerModifyPermission;

    @Column(name = "swa_blocked")
    private boolean blocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swa_sales_person_id")
    private SalesPerson salesPerson;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "restaurant_has_kitchen_type",
                joinColumns = {@JoinColumn(name = "restaurant_id") },
                inverseJoinColumns = {@JoinColumn(name = "kitchen_type_id")}
    )
    private List<KitchenType> kitchenTypes;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CourseType> courseTypeList;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TimeSchedule> timeScheduleList;

    public List<TimeSchedule> getTimeScheduleList() {
        return timeScheduleList;
    }

    public void setTimeScheduleList(List<TimeSchedule> timeScheduleList) {
        this.timeScheduleList = timeScheduleList;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
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

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public List<KitchenType> getKitchenTypes() {
        return kitchenTypes;
    }

    public void setKitchenTypes(List<KitchenType> kitchenTypes) {
        this.kitchenTypes = kitchenTypes;
    }

    public List<CourseType> getCourseTypeList() {
        return courseTypeList;
    }

    public void setCourseTypeList(List<CourseType> courseTypeList) {
        this.courseTypeList = courseTypeList;
    }

}
