package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.components.DTO.HomeRestaurantForm;
import saleswebapp.components.DTO.HomeToDoForm;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.repository.impl.ToDoList;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.HomeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private DbReaderService dbReaderService;

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
        List<ToDoList> toDoLists = dbReaderService.getAllToDosOfSalesPerson(email);
        List<HomeToDoForm> homeToDoForms = new ArrayList<HomeToDoForm>();

        for (ToDoList toDoList : toDoLists) {
            homeToDoForms.add(new HomeToDoForm(toDoList));
        }

        return homeToDoForms;
    }
}
