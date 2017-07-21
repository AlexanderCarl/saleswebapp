package saleswebapp.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.components.DTO.*;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.RestaurantService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private DbWriterService dbWriterService;

    //The restaurants id is the key.
    /*This Map is used to store the restaurant(data) which is then send to the user with the model.
    * When the user presses save the restaurant(data) is loaded again from the DB und compared to the
    * restaurant(data) at the start (stored in the HashMap). If the restaurant(data) has been altered
    * on the server while the user worked on it, the save request is rejected. This logic
    * is used to ensure data consistency.
    * The word transaction is used twice here:
    * 1) Transaction: Start by the GET.Request for the restaurant model - End by the comparison check if the DB-Object has been altered.
    * 2) Transaction: Only used to save the restaurant(data) with @Transactional */
    private static HashMap<Integer, Restaurant> restaurantTransactionStore = new HashMap<Integer, Restaurant>();

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

    @Override
    @Transactional
    public void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedInUser = "carl@hm.edu"; //Dev-Only

        //Security check to ensure that a salesPerson can only add categories to restaurants he is assigned to.
        Restaurant restaurant = dbReaderService.getRestaurantById(restaurantAddCategory.getRestaurantId());
        if(!restaurant.getSalesPerson().getEmail().equals(loggedInUser)) {
            logger.debug("Security Violation in the class RestaurantServiceImpl - user: " + loggedInUser + " tryed to add categories to a restaurant he is not assigned to.");

        } else {
            //Check if the String contains multiple course types. e.g. Vorspeise, Rotwein, Hauswein
            String fullString = restaurantAddCategory.getName();
            fullString = fullString.replaceAll("\\s+",""); //deletes whitespaces
            String[] singleCourseTypesStrings = fullString.split("\\,"); //cuts the String after every ","

            for (String subString : singleCourseTypesStrings) {
                RestaurantAddCategory singleRestaurantAddCategory = new RestaurantAddCategory();
                singleRestaurantAddCategory.setRestaurantId(restaurantAddCategory.getRestaurantId());
                singleRestaurantAddCategory.setName(subString);

                dbWriterService.addCategoryToRestaurant(singleRestaurantAddCategory);
            }
        }
    }

    @Override
    @Transactional
    public void deleteCategoryFromRestaurant(RestaurantDeleteCategory restaurantDeleteCategory) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        loggedInUser = "carl@hm.edu"; //Dev-Only

        //Security check to ensure that a salesPerson can only add categories to restaurants he is assigned to.
        Restaurant restaurant = dbReaderService.getRestaurantById(restaurantDeleteCategory.getRestaurantId());
        if(!restaurant.getSalesPerson().getEmail().equals(loggedInUser)) {
            logger.debug("Security Violation in the class RestaurantServiceImpl - user: " + loggedInUser + " tryed to delete categories from a restaurant he is not assigned to.");

        } else {
            //Check if the String contains multiple course types. e.g. Vorspeise, Rotwein, Hauswein
            String fullString = restaurantDeleteCategory.getName();
            fullString = fullString.replaceAll("\\s+",""); //deletes whitespaces
            String[] singleCourseTypesString = fullString.split("\\,"); //cuts the String after every ","
            List<CourseType> courseTypeList = dbReaderService.getRestaurantById(restaurantDeleteCategory.getRestaurantId()).getCourseTypeList();
            List<CourseType> courseTypesWithItemsToDelete = new ArrayList<CourseType>();

            for (String subString : singleCourseTypesString) {
                for (CourseType courseType : courseTypeList) {
                    if(subString.equals(courseType.getName())) {
                        dbWriterService.deleteCategoryFromRestaurant(courseType);
                    }
                }
            }
        }
    }

    @Override
    public void addRestaurantToRestaurantTransactionStore(Restaurant restaurant) {
        restaurantTransactionStore.put(restaurant.getId(), restaurant);
    }

    @Override
    //Security check if the concerning the DB-Object restaurant has been altered during transaction
    // and saves it if not.
    public boolean restaurantHasBeenAlteredMeanwhile(RestaurantForm restaurantForm) {
        Restaurant restaurantTransactionEnd = dbReaderService.getRestaurantById(restaurantForm.getId());
        Restaurant restaurantTransactionStart = restaurantTransactionStore.get(restaurantForm.getId());

        /*
        if (!restaurantTransactionEnd.equals(restaurantTransactionStart)) {
            return true;
        }
        */

        dbWriterService.saveRestaurantChange(restaurantForm);
        restaurantTransactionStore.remove(restaurantForm.getId());
        return false;
    }

    @Override
    public void addNewRestaurant(RestaurantForm restaurantForm) {
        dbWriterService.setNewRestaurant(restaurantForm);
    }
}
