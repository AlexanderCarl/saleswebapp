package saleswebapp.repository.impl;

import saleswebapp.components.RestaurantTimeContainer;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Entity
public class Restaurant implements Serializable {

    private static final long serialVersionUID = -9222904263139207286L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    @Size(min=4, max=60, message = "{restaurant.validation.name}")
    private String name;

    @Pattern(regexp = "^[a-zA-ZäöüÄÖÜß]{4,60}$", message = "{restaurant.validation.street}")
    private String street;

    @Pattern(regexp = "^[^0]{1,11}$", message = "{restaurant.validation.streetNumber}")
    @Column(name = "street_number")
    private String streetNumber;

    @Pattern(regexp = "^[0-9]{5}$", message = "{restaurant.validation.zip}")
    private String zip;

    @Pattern(regexp = "^[a-zA-ZäöüÄÖÜ]{3,60}$", message = "{restaurant.validation.city}")
    private String city;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_code")
    private Country country;

    @Column(name = "location_latitude")
    private Float locationLatitude;

    @Column(name = "location_longitude")
    private Float locationLongitude;

    @Pattern(regexp = ".+@.+\\..+", message = "{universal.validation.pattern.email}")
    private String email;

    @Pattern(regexp = "^[0][0-9/. \\-]{6,60}$", message = "{restaurant.validation.phone}")
    private String phone;

    // Regex-Source: http://www.regexpal.com/93652
    @Pattern(regexp = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$|", message = "{restaurant.validation.url}")
    private String url;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_type_id")
    private RestaurantType restaurantType;

    @Column(name = "restaurant_uuid")
    private String restaurantUUID;

    @Lob
    @Column(name = "qr_uuid")
    private byte[] qrUUID;

    @Column(name = "swa_offer_modify_permission")
    private boolean offerModifyPermission;

    @Column(name = "swa_blocked")
    private boolean blocked;

    @ManyToOne(fetch = FetchType.EAGER)
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

    @Transient
    private String qrUuidBase64Encoded;

    @Transient
    private List<RestaurantTimeContainer> openingTimes;

    @Transient
    private List<RestaurantTimeContainer> offerTimes;

    @Transient
    private String restaurantTypeAsString;

    @Transient
    private List<String> kitchenTypesAsString;

    @Transient
    private int idOfSalesPerson; //The variable is named against the normal conventions because the variable name "salesPersonId" did mess up the Spring Bean Containers.

    //Needed for the Regex Validator needs a String
    //Regex-Sourece: https://stackoverflow.com/questions/3518504/regular-expression-for-matching-latitude-longitude-coordinates
    @Transient
    @Pattern(regexp = "(^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$)|", message = "{restaurant.validation.coordinatesLatitude}")
    private String locationLatitudeAsString;

    //Needed for the Regex Validator needs a String
    //Regex-Sourece: https://stackoverflow.com/questions/3518504/regular-expression-for-matching-latitude-longitude-coordinates
    @Transient
    @Pattern(regexp = "(^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$)|", message = "{restaurant.validation.coordinatesLongitude}")
    private String locationLongitudeAsString;

    public void restaurantKitchenTypesAsStringFiller() {
        kitchenTypesAsString = new ArrayList<String>();

        if(kitchenTypes != null) {
            for(KitchenType kitchenType : kitchenTypes) {
                kitchenTypesAsString.add(kitchenType.getName());
            }
        }
    }

    public void orderRestaurantTimeContainers() {
        openingTimes.sort(Comparator.comparingInt(RestaurantTimeContainer::getDayNumber));
        offerTimes.sort(Comparator.comparingInt(RestaurantTimeContainer::getDayNumber));
    }

    public void restaurantTimeContainerFiller() {

        if(timeScheduleList.size() > 7) {
            try {
                throw new Exception("Error - The Table Time_Schedule contains more than 7 entries per week for restaurant-ID: " + id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int dayNumber = 0;
        Date openingTime;
        Date closingTime;
        Date offerStartTime;
        Date offerEndTime;

        openingTimes = new ArrayList<RestaurantTimeContainer>();
        offerTimes = new ArrayList<RestaurantTimeContainer>();
        List<TimeSchedule> timeScheduleList = getTimeScheduleList();

        for (int i = 0; i < 7; i++) {
            TimeSchedule timeSchedule = new TimeSchedule();

            try {
                timeSchedule = timeScheduleList.get(i);
            } catch (Exception e) {
                //no entry in the db
            }

            openingTime = null;
            closingTime = null;

            try {
                dayNumber = timeSchedule.getDayOfWeek().getId();
            } catch (Exception e) {
                //new time schedule
            }
            if(dayNumber == 0) {
                dayNumber = i+1;
            }

            offerStartTime = timeSchedule.getOfferStartTime();
            offerEndTime = timeSchedule.getOfferEndTime();
            offerTimes.add(new RestaurantTimeContainer(offerStartTime, offerEndTime, dayNumber));

            //The DB allows for more than one time schedule entry per day. But the FindLunchApplication only allows one pair of opening times per day.
            //The SWApp handles this similar. If there is more than one pair of opening times per day it is ignored.
            if(timeSchedule.getOpeningTimes().size() > 0) {
                openingTime = timeSchedule.getOpeningTimes().get(0).getOpeningTime();
                closingTime = timeSchedule.getOpeningTimes().get(0).getClosingTime();
            }
            openingTimes.add(new RestaurantTimeContainer(openingTime, closingTime, dayNumber));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Restaurant other = (Restaurant) obj;
        if (Integer.valueOf(id) == null) {
            if (Integer.valueOf(other.id) != null)
                return false;
        }

        if (id != other.getId()) {
            return false;
        }

        if (customerId != other.getCustomerId()) {
            return false;
        }

        if (!name.equals(other.getName())) {
            return false;
        }

        if (!street.equals(other.getStreet())) {
            return false;
        }

        if (!streetNumber.equals(other.getStreetNumber())) {
            return false;
        }

        if (!zip.equals(other.getZip())) {
            return false;
        }

        if (!city.equals(other.getCity())) {
            return false;
        }

        if (!phone.equals(other.getPhone())) {
            return false;
        }

        if (!country.getCountryCode().equals(other.getCountry().getCountryCode())) {
            return false;
        }

        if (!email.equals(other.getEmail())) {
            return false;
        }

        if (!locationLatitude.equals(other.getLocationLatitude())) {
            return false;
        }

        if (!locationLongitude.equals(other.getLocationLongitude())) {
            return false;
        }

        if (!url.equals(other.getUrl())) {
            return false;
        }

        if (!restaurantType.getName().equals(other.getRestaurantType().getName())) {
            return false;
        }

        if (!restaurantUUID.equals(other.getRestaurantUUID())) {
            return false;
        }

        if (!offerModifyPermission == other.isOfferModifyPermission()) {
            return false;
        }

        if (!blocked == other.isBlocked()) {
            return false;
        }

        if (salesPerson.getId() != other.getSalesPerson().getId()) {
            return false;
        }

        if (!kitchenTypes.containsAll(other.getKitchenTypes())) {
            return false;
        }

        if (!courseTypeList.containsAll(other.getCourseTypeList())) {
            return false;
        }

        if (!timeScheduleList.containsAll(other.getTimeScheduleList())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Integer.valueOf(id) == null) ? 0 : (Integer.valueOf(id).hashCode()));

        return result;
    }

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

    public byte[] getQrUUID() {
        return qrUUID;
    }

    public void setQrUUID(byte[] qrUuid) {
        this.qrUUID = qrUuid;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getQrUuidBase64Encoded() {
        return qrUuidBase64Encoded;
    }

    public void setQrUuidBase64Encoded(String qrUuidBase64Encoded) {
        this.qrUuidBase64Encoded = qrUuidBase64Encoded;
    }

    public List<RestaurantTimeContainer> getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(List<RestaurantTimeContainer> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public List<RestaurantTimeContainer> getOfferTimes() {
        return offerTimes;
    }

    public void setOfferTimes(List<RestaurantTimeContainer> offerTimes) {
        this.offerTimes = offerTimes;
    }

    public String getRestaurantTypeAsString() {
        return restaurantTypeAsString;
    }

    public void setRestaurantTypeAsString(String restaurantTypeAsString) {
        this.restaurantTypeAsString = restaurantTypeAsString;
    }

    public List<String> getKitchenTypesAsString() {
        return kitchenTypesAsString;
    }

    public void setKitchenTypesAsString(List<String> kitchenTypesAsString) {
        this.kitchenTypesAsString = kitchenTypesAsString;
    }

    public int getIdOfSalesPerson() {
        return idOfSalesPerson;
    }

    public void setIdOfSalesPerson(int idOfSalesPerson) {
        this.idOfSalesPerson = idOfSalesPerson;
    }

    public String getLocationLatitudeAsString() {
        return locationLatitudeAsString;
    }

    public void setLocationLatitudeAsString(String locationLatitudeAsString) {
        this.locationLatitudeAsString = locationLatitudeAsString;
    }

    public String getLocationLongitudeAsString() {
        return locationLongitudeAsString;
    }

    public void setLocationLongitudeAsString(String locationLongitudeAsString) {
        this.locationLongitudeAsString = locationLongitudeAsString;
    }
}
