package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.components.HomeRestaurantForm;
import saleswebapp.components.HomeToDoForm;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.repository.impl.ToDo;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.HomeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Service
public class HomeServiceImpl implements HomeService {

    //DEV-Only
    String loggedInUser = "carl@hm.edu";

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
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
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
        return false;
    }

    @Override
    public void deleteToDo(int toDoId) {
        dbWriterService.deleteToDo(toDoId);
    }

}
