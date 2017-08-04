package saleswebapp.repository.impl;

import javax.persistence.*;
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

    private String title;

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

    @Transient
    private String courseTypeAsString;

    @Transient
    private String startDateAsString;

    @Transient
    private String endDateAsString;

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
}
