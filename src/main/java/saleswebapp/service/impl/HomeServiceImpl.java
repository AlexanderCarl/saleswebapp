package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import saleswebapp.components.HomeRestaurantForm;
import saleswebapp.components.HomeToDoForm;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.HomeService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Service
public class HomeServiceImpl implements HomeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbReaderService dbReaderService;

    @Autowired
    private DbWriterService dbWriterService;

    @Override
    public List<HomeRestaurantForm> createHomeRestaurantFormListForSalesPerson(String email) {
        List<Restaurant> restaurantList = dbReaderService.getAllRestaurantsOfSalesPerson(email);
        List<HomeRestaurantForm> homeRestaurantFormList = new ArrayList<HomeRestaurantForm>();

        for (Restaurant restaurant : restaurantList) {
            homeRestaurantFormList.add(new HomeRestaurantForm(restaurant));
        }

        return homeRestaurantFormList;
    }

    @Override
    public List<HomeToDoForm> getAllToDosForSalesPerson(String email) {
        List<ToDo> toDos = dbReaderService.getAllToDosOfSalesPerson(email);
        List<HomeToDoForm> homeToDoForms = new ArrayList<HomeToDoForm>();

        for (ToDo toDo : toDos) {
            homeToDoForms.add(new HomeToDoForm(toDo));
        }

        return homeToDoForms;
    }

    @Override
    public boolean toDoAssignedToSalePerson(int toDoId) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String salesPersonOfToDo;

        try {
            salesPersonOfToDo = dbReaderService.getToDoById(toDoId).getSalesPerson().getEmail();
        } catch (NullPointerException e) {
            logger.debug("Error - User: " + loggedInUser + " - There is no toDo with the requested id: " + toDoId + " in the DB.");
            //The user will see the alert-danger box which tells him that he has no access to the restaurant with this (faulty) restaurantID.
            return false;
        }

        if(loggedInUser.equals(salesPersonOfToDo)) {
            return true;
        }

        logger.debug("Error - User: " + loggedInUser + " - Tryed to access a toDo (ToDoId: " + toDoId + ") which he is not assigned to.");
        return false;
    }

    @Override
    public void deleteToDo(int toDoId) {
        dbWriterService.deleteToDo(toDoId);
    }

    @Override
    public String getCurrentPaymentOfSalesPerson(String email) {
        SalesPerson salesPerson = dbReaderService.getSalesPersonByEmail(email);
        List<Restaurant> allRestaurantsOfSalesPerson = salesPerson.getRestaurants();

        double percentageOfDonation = salesPerson.getSalaryPercentage();
        List<DonationPerMonth> donationsOfAllMonths = new ArrayList<DonationPerMonth>();
        List<OfferReservation> offerReservationsOfAllMonths = new ArrayList<OfferReservation>();
        List<DonationPerMonth> donationsOfCurrentMonth = new ArrayList<DonationPerMonth>();
        List<OfferReservation> offerReservationsOfCurrentMonth = new ArrayList<OfferReservation>();
        double donationOfRestaurants = 0;
        double donationOfCustomers = 0;
        double currentPaymentOfSalesPerson = 0;

        for(Restaurant restaurant : allRestaurantsOfSalesPerson) {
            donationsOfAllMonths.addAll(dbReaderService.getAllDonationsByRestaurantId(restaurant.getId()));
            offerReservationsOfAllMonths.addAll(dbReaderService.getAllOfferReservationsByRestaurantId(restaurant.getId()));
        }

        //int month = calendar.get(Calendar.MONTH); //returns 8 for September

        //Sorting out of the ones which do not belong to the current month
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());
        Calendar loopDate = Calendar.getInstance();

        for(DonationPerMonth donationPerMonth : donationsOfAllMonths) {
            loopDate.setTime(donationPerMonth.getDate());

            if(loopDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)) {
                donationsOfCurrentMonth.add(donationPerMonth);
            }
        }

        for(OfferReservation offerReservation : offerReservationsOfAllMonths) {
            loopDate.setTime(offerReservation.getReservationTime());

            if(loopDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH)) {
                offerReservationsOfCurrentMonth.add(offerReservation);
            }
        }

        //Adding the donations together
        for(DonationPerMonth donationPerMonth : donationsOfCurrentMonth) {
            donationOfRestaurants = donationOfRestaurants + donationPerMonth.getDonationAmount();
        }

        for(OfferReservation offerReservation : offerReservationsOfCurrentMonth) {
            if(offerReservation.isConfirmed()) {
                donationOfCustomers = donationOfCustomers + offerReservation.getDonation();
            }
        }

        //Multiply with the sales persons percentage
        currentPaymentOfSalesPerson = (donationOfRestaurants + donationOfCustomers) * percentageOfDonation;
        currentPaymentOfSalesPerson = (double) Math.round(currentPaymentOfSalesPerson * 100) / 100;

        return "€ " + currentPaymentOfSalesPerson;
    }

}
