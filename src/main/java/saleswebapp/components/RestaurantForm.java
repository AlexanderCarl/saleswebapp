package saleswebapp.components;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import saleswebapp.components.RestaurantTimeContainer;
import saleswebapp.repository.impl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Created by Alexander Carl on 07.07.2017.
 * NOT IN USE! Will be deleted soon.
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
    private Country country;
    private Float locationLatitude;
    private Float locationLongitude;
    private String email;
    private String phone;
    private String url;
    //private RestaurantType restaurantType; //Anbietertyp

    private List<String> restaurantKitchenTypesForm; //Küchentypen
    private List<String> restaurantCourseTypesForm; //Kategorien
    private List<RestaurantTimeContainer> openingTimes;
    private List<RestaurantTimeContainer> offerTimes;
    private boolean offerModifyPermission;
    private boolean blocked;
    private String restaurantUUID;
    private byte[] qrUuid;
    private String qrUuidBase64Encoded;

    //Generates a form for a new Restaurant
    public RestaurantForm() {
        super();
        this.openingTimes = populateRestaurantTimeDayNumber();
        this.offerTimes = populateRestaurantTimeDayNumber();
        this.restaurantUUID = UUID.randomUUID().toString();

        try {
            this.qrUuid = createQRCode(restaurantUUID);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }

        this.qrUuidBase64Encoded = Base64.getEncoder().encodeToString(qrUuid);
    }

    //Generates a form for a given Restaurant
    public RestaurantForm(Restaurant restaurant) throws Exception {
        this.id = restaurant.getId();
        this.customerId = restaurant.getCustomerId();
        this.name = restaurant.getName();
        this.street = restaurant.getStreet();
        this.streetNumber = restaurant.getStreetNumber();
        this.zip = restaurant.getZip();
        this.city = restaurant.getCity();

        //The CountryForm isn`t used here deliberately as it caused strange issues which are hard to fix.
        Country country = restaurant.getCountry();
        country.setRestaurants(null); //set null as the model would contain all restaurants otherwise
        country.setSalesPersons(null); //set null as the model would contain all salesPersons otherwise
        this.country = country;

        this.locationLatitude = restaurant.getLocationLatitude();
        this.locationLongitude = restaurant.getLocationLongitude();
        this.email = restaurant.getEmail();
        this.phone = restaurant.getPhone();
        this.url = restaurant.getUrl();

        //Fills the List<RestaurantKitchenType>
        List<KitchenType> kitchenTypes = restaurant.getKitchenTypes();
        List<String> restaurantKitchenTypeForms = new ArrayList<String>();
        for (KitchenType kitchenType : kitchenTypes) {
            restaurantKitchenTypeForms.add(kitchenType.getName());
        }
        this.restaurantKitchenTypesForm = restaurantKitchenTypeForms;

        //Fills the List<RestaurantCourseType>
        List<CourseType> courseTypes = restaurant.getCourseTypeList();
        List<String> restaurantCourseTypeForms = new ArrayList<String>();
        for (CourseType courseType : courseTypes) {
            restaurantCourseTypeForms.add(courseType.getName());
        }
        this.restaurantCourseTypesForm = restaurantCourseTypeForms;

        this.offerModifyPermission = restaurant.isOfferModifyPermission();
        this.blocked = restaurant.isBlocked();
        this.restaurantUUID = restaurant.getRestaurantUUID();

        this.qrUuidBase64Encoded = Base64.getEncoder().encodeToString(qrUuid);

        restaurantTimeFiller(restaurant);

        //CustomComparator to sort the dayNumbers of the time schedule. This ensures that the days will also be displayed in order (Mo - Su).
        openingTimes.sort(Comparator.comparingInt(RestaurantTimeContainer::getDayNumber));
        offerTimes.sort(Comparator.comparingInt(RestaurantTimeContainer::getDayNumber));
    }

    //The Html-Page does need the day numbers to be rendered correctly.
    private List<RestaurantTimeContainer> populateRestaurantTimeDayNumber() {
        List<RestaurantTimeContainer> times = new ArrayList<RestaurantTimeContainer>();

        for (int i = 1; i < 8; i++) {
            RestaurantTimeContainer restaurantTimeContainer = new RestaurantTimeContainer();
            restaurantTimeContainer.setDayNumber(i);
            times.add(restaurantTimeContainer);
        }

        return times;
    }

    private void restaurantTimeFiller(Restaurant restaurant) throws Exception {

        if(restaurant.getTimeScheduleList().size() > 7) {
            throw new Exception("Error - The Table Time_Schedule contains more than 7 entries per week for restaurant-ID: " + restaurant.getId());
        }

        int dayNumber;
        Date openingTime;
        Date closingTime;
        Date offerStartTime;
        Date offerEndTime;

        openingTimes = new ArrayList<RestaurantTimeContainer>();
        offerTimes = new ArrayList<RestaurantTimeContainer>();
        List<TimeSchedule> timeScheduleList = restaurant.getTimeScheduleList();

        for (TimeSchedule timeSchedule : timeScheduleList) {
            dayNumber = 0; //Valid day numbers range from 1 to 7.
            openingTime = null;
            closingTime = null;
            offerStartTime = null;
            offerEndTime = null;

            dayNumber = timeSchedule.getDayOfWeek().getDayNumber();
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

    //Creates tje QRCode as the in the findlunchApp but without saving it temporarely as a file on the hard drive.
    private byte[] createQRCode(String qrUuid) throws IOException, WriterException {

        //Creates a bitMatrix for the given String qrUuid
        Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUuid, BarcodeFormat.QR_CODE, 250, 250, hintMap);

        //Writes the BitMatrix to a (byte[])image
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
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

    public List<String> getRestaurantKitchenTypesForm() {
        return restaurantKitchenTypesForm;
    }

    public void setRestaurantKitchenTypesForm(List<String> restaurantKitchenTypesForm) {
        this.restaurantKitchenTypesForm = restaurantKitchenTypesForm;
    }

    public List<String> getRestaurantCourseTypesForm() {
        return restaurantCourseTypesForm;
    }

    public void setRestaurantCourseTypesForm(List<String> restaurantCourseTypesForm) {
        this.restaurantCourseTypesForm = restaurantCourseTypesForm;
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

    public String getQrUuidBase64Encoded() {
        return qrUuidBase64Encoded;
    }

    public void setQrUuidBase64Encoded(String qrUuidBase64Encoded) {
        this.qrUuidBase64Encoded = qrUuidBase64Encoded;
    }
}
