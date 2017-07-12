package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.components.DTO.RestaurantListForm;
import saleswebapp.components.DTO.RestaurantTypeForm;
import saleswebapp.repository.impl.KitchenType;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.repository.impl.RestaurantType;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alexander Carl on 08.07.2017.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    //DEV-Only
    String loggedInUser = "carl@hm.edu";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbReaderService dbReaderService;

    @Override
    public List<RestaurantListForm> getAllRestaurantNamesForSalesPerson(String email) {

        List<Restaurant> restaurantList = dbReaderService.getAllRestaurantsOfSalesPerson(email);
        List<RestaurantListForm> restaurantListForms = new ArrayList<RestaurantListForm>();

        for (Restaurant restaurant : restaurantList) {
            restaurantListForms.add(new RestaurantListForm(restaurant));
        }

        return restaurantListForms;
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return dbReaderService.getRestaurantById(id);
    }

    //Ensures that a salesPerson can only see his Restaurants. Also if he changes the call parameter manually.
    @Override
    public boolean restaurantAssignedToSalesPerson(int id) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String salesPersonOfRestaurant;

        try {
            salesPersonOfRestaurant = dbReaderService.getRestaurantById(id).getSalesPerson().getEmail();
        } catch (NullPointerException e) {
            logger.debug("Error - User: " + loggedInUser + " - There is no restaurant with the requested id: " + id + " in the DB.");
            //The user will see the alert-danger box which tells him that he has no access to the restaurant with this (faulty) restaurantID.
            return false;
        }

        if(loggedInUser.equals(salesPersonOfRestaurant)) {
            return true;
        }
        return false;
    }

    @Override
    public List<RestaurantTypeForm> getAllRestaurantTypes() {
        List<RestaurantType> restaurantTypes = dbReaderService.getAllRestaurantTypes();
        List<RestaurantTypeForm> restaurantTypeFormList= new ArrayList<RestaurantTypeForm>();

        for (RestaurantType restaurantType : restaurantTypes) {
            restaurantTypeFormList.add(new RestaurantTypeForm(restaurantType));
        }

        return restaurantTypeFormList;
    }

    /* 11.07.2017
    The way the customerID is created here is deliberately done different than in the findLunchApp.
    The id in the findLunchApp is created in an deprecated way and not checked for uniqueness.
     */
    @Override
    public int getUniqueCustomerId() {
        Random random = new Random();
        Restaurant restaurant = null;
        Integer newCustomerId = null;
        boolean unique = false;

        while(unique == false) {
            newCustomerId = 100000000 + random.nextInt(900000000);
            try {
                restaurant = dbReaderService.getRestaurantByCustomerId(newCustomerId);
            } catch (NullPointerException e) {
                //The new CustomerId is unqiue.
            }

            if (restaurant == null) {
                unique = true;
            }
        }

        return newCustomerId;
    }

    @Override
    public List<String> getAllKitchenTypes() {
        List<KitchenType> kitchenTypeList = dbReaderService.getAllKitchenTypes();
        List<String> allKitchenTypes = new ArrayList<String>();

        for (KitchenType kitchenType : kitchenTypeList) {
            allKitchenTypes.add(kitchenType.getName());
        }

        return  allKitchenTypes;
    }

}
