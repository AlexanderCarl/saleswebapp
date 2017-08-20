package saleswebapp.repository.impl;

import org.springframework.web.multipart.MultipartFile;
import saleswebapp.components.RestaurantTimeContainer;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "swa_change_request_id")
    private int changeRequestId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Size(min=4, max=60, message = "{offer.validation.title}")
    private String title;

    @Size(min=4, max=255, message = "{offer.validation.description}")
    private String description;

    private Double price;

    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "needed_points")
    private int neededPoints;

    @Column(name = "sold_out")
    private boolean soldOut;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CourseType courseType;

    private int order;

    @Column(name = "swa_comment_of_last_change")
    private String commentOfLastChange;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "swa_last_changed_by")
    private SalesPerson salesPerson;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "offer_has_day_of_week",
            joinColumns = {@JoinColumn(name = "offer_id")},
            inverseJoinColumns = {@JoinColumn(name = "day_of_week_id")}
    )
    private List<DayOfWeek> dayOfWeeks;

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER)
    private List<OfferHasAdditive> offerHasAdditives;

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER)
    private List<OfferHasAllergenic> offerHasAllergenics;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferPhoto> offerPhotos;

    @Transient
    private String courseTypeAsString;

    @Transient
    private List<String> additivesAsString;

    @Transient
    private List<String> allergenicsAsString;

    @Transient
    private List<RestaurantTimeContainer> offerTimes;

    @Transient
    private List<String> validnessDaysOfWeekAsString;

    @Transient
    private String newChangeComment;

    //The number of images per offer is limited to four
    @Transient
    private MultipartFile firstOfferImage;

    @Transient
    private MultipartFile secondOfferImage;

    @Transient
    private MultipartFile thirdOfferImage;

    // Regex-Source: http://www.regexpal.com/93999
    // The bean-validation via regex requires a String but it is much better than the standard @Digits validation.
    @Pattern(regexp = "(\\d+\\.\\d{1,2})", message = "{offer.validation.price}")
    @Transient
    private String priceAsString;

    // The bean-validation via regex requires a String but it is much better than the standard @Digits validation.
    @Pattern(regexp = "^[0-9]{1,2}$", message = "{offer.validation.preparationTime}")
    @Transient
    private String preparationTimeAsString;

    // Regex-Source: https://stackoverflow.com/questions/8937408/regular-expression-for-date-format-dd-mm-yyyy-in-javascript
    @Pattern(regexp = "(^(((0[1-9]|1[0-9]|2[0-8])[-](0[1-9]|1[012]))|((29|30|31)[-](0[13578]|1[02]))|((29|30)[-](0[4,6,9]|11)))[-](19|[2-9][0-9])\\d\\d$)|(^29[-]02[-](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)", message = "{offer.validation.startDate}")
    @Transient
    private String startDateAsString;

    // Regex-Source: https://stackoverflow.com/questions/8937408/regular-expression-for-date-format-dd-mm-yyyy-in-javascript
    @Pattern(regexp = "(^(((0[1-9]|1[0-9]|2[0-8])[-](0[1-9]|1[012]))|((29|30|31)[-](0[13578]|1[02]))|((29|30)[-](0[4,6,9]|11)))[-](19|[2-9][0-9])\\d\\d$)|(^29[-]02[-](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)", message = "{offer.validation.endDate}")
    @Transient
    private String endDateAsString;

    @Pattern(regexp = "^[0-9]{1,3}$", message = "{offer.validation.neededPoints}")
    @Transient
    private String neededPointsAsString;

    @Transient
    private int idOfRestaurant;
    //The variable is named against the normal conventions because the variable name "restaurantId" does mess up hibernate as it is already used in an related other object.

    //Loads the value of the "offerHasAdditives/Allergenics" in different format which is better to handle with Thymeleaf.
    public void allergenicFiller() {
        allergenicsAsString = new ArrayList<String>();

        if(offerHasAllergenics != null) {
            for(OfferHasAllergenic offerHasAllergenic : offerHasAllergenics) {
                allergenicsAsString.add(offerHasAllergenic.getAllergenic().getName());
            }
        }
    }

    //changes the the date format (String) from 2017-08-31 (yyyy-mm-dd) to 31-08-2017 (dd-mm-yyyy) to better match the regex validation
    public String reOrderDate(String date) {
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        return day + "-" + month + "-" + year;
    }

    public void additivesFiller() {
        additivesAsString = new ArrayList<String>();

        if(offerHasAdditives != null) {
            for(OfferHasAdditive offerHasAdditive : offerHasAdditives) {
                additivesAsString.add(offerHasAdditive.getAdditive().getName());
            }
        }
    }

    public void daysOfWeekAsStringFiller() {
        validnessDaysOfWeekAsString = new ArrayList<String>();

        if(dayOfWeeks != null) {
            for(DayOfWeek dayOfWeek : dayOfWeeks) {
                validnessDaysOfWeekAsString.add(String.valueOf(dayOfWeek.getId()));
            }
        }
    }

    public void offerTimesContainerFiller(Restaurant restaurant) {
        List<TimeSchedule> timeScheduleList = new ArrayList<TimeSchedule>();

        try {
            timeScheduleList = restaurant.getTimeScheduleList();
        } catch (Exception e) {
            try {
                throw new Exception("Error - The Restaurant (ID: " + restaurant.getId() + ") has no time schedule.");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        int dayNumber = 0;
        Date offerStartTime;
        Date offerEndTime;

        offerTimes = new ArrayList<RestaurantTimeContainer>();

        for (int i = 0; i < 7; i++) {
            TimeSchedule timeSchedule = new TimeSchedule();

            try {
                timeSchedule = timeScheduleList.get(i);
            } catch (Exception e) {
                //no entry in the db
            }

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
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChangeRequestId() {
        return changeRequestId;
    }

    public void setChangeRequestId(int changeRequestId) {
        this.changeRequestId = changeRequestId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNeededPoints() {
        return neededPoints;
    }

    public void setNeededPoints(int neededPoints) {
        this.neededPoints = neededPoints;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getCommentOfLastChange() {
        return commentOfLastChange;
    }

    public void setCommentOfLastChange(String commentOfLastChange) {
        this.commentOfLastChange = commentOfLastChange;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public List<DayOfWeek> getDayOfWeeks() {
        return dayOfWeeks;
    }

    public void setDayOfWeeks(List<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    public String getCourseTypeAsString() {
        return courseTypeAsString;
    }

    public void setCourseTypeAsString(String courseTypeAsString) {
        this.courseTypeAsString = courseTypeAsString;
    }

    public String getStartDateAsString() {
        return startDateAsString;
    }

    public void setStartDateAsString(String startDateAsString) {
        this.startDateAsString = startDateAsString;
    }

    public String getEndDateAsString() {
        return endDateAsString;
    }

    public void setEndDateAsString(String endDateAsString) {
        this.endDateAsString = endDateAsString;
    }

    public List<String> getAdditivesAsString() {
        return additivesAsString;
    }

    public void setAdditivesAsString(List<String> additivesAsString) {
        this.additivesAsString = additivesAsString;
    }

    public List<String> getAllergenicsAsString() {
        return allergenicsAsString;
    }

    public void setAllergenicsAsString(List<String> allergenicsAsString) {
        this.allergenicsAsString = allergenicsAsString;
    }

    public List<OfferHasAdditive> getOfferHasAdditives() {
        return offerHasAdditives;
    }

    public void setOfferHasAdditives(List<OfferHasAdditive> offerHasAdditives) {
        this.offerHasAdditives = offerHasAdditives;
    }

    public List<OfferHasAllergenic> getOfferHasAllergenics() {
        return offerHasAllergenics;
    }

    public void setOfferHasAllergenics(List<OfferHasAllergenic> offerHasAllergenics) {
        this.offerHasAllergenics = offerHasAllergenics;
    }

    public List<RestaurantTimeContainer> getOfferTimes() {
        return offerTimes;
    }

    public void setOfferTimes(List<RestaurantTimeContainer> offerTimes) {
        this.offerTimes = offerTimes;
    }

    public List<String> getValidnessDaysOfWeekAsString() {
        return validnessDaysOfWeekAsString;
    }

    public void setValidnessDaysOfWeekAsString(List<String> validnessDaysOfWeekAsString) {
        this.validnessDaysOfWeekAsString = validnessDaysOfWeekAsString;
    }

    public String getNewChangeComment() {
        return newChangeComment;
    }

    public void setNewChangeComment(String newChangeComment) {
        this.newChangeComment = newChangeComment;
    }

    public List<OfferPhoto> getOfferPhotos() {
        return offerPhotos;
    }

    public void setOfferPhotos(List<OfferPhoto> offerPhotos) {
        this.offerPhotos = offerPhotos;
    }

    public MultipartFile getFirstOfferImage() {
        return firstOfferImage;
    }

    public void setFirstOfferImage(MultipartFile firstOfferImage) {
        this.firstOfferImage = firstOfferImage;
    }

    public MultipartFile getSecondOfferImage() {
        return secondOfferImage;
    }

    public void setSecondOfferImage(MultipartFile secondOfferImage) {
        this.secondOfferImage = secondOfferImage;
    }

    public MultipartFile getThirdOfferImage() {
        return thirdOfferImage;
    }

    public void setThirdOfferImage(MultipartFile thirdOfferImage) {
        this.thirdOfferImage = thirdOfferImage;
    }

    public int getIdOfRestaurant() {
        return idOfRestaurant;
    }

    public void setIdOfRestaurant(int idOfRestaurant) {
        this.idOfRestaurant = idOfRestaurant;
    }

    public String getPriceAsString() {
        return priceAsString;
    }

    public void setPriceAsString(String priceAsString) {
        this.priceAsString = priceAsString;
    }

    public String getPreparationTimeAsString() {
        return preparationTimeAsString;
    }

    public void setPreparationTimeAsString(String preparationTimeAsString) {
        this.preparationTimeAsString = preparationTimeAsString;
    }

    public String getNeededPointsAsString() {
        return neededPointsAsString;
    }

    public void setNeededPointsAsString(String neededPointsAsString) {
        this.neededPointsAsString = neededPointsAsString;
    }
}
