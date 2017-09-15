package saleswebapp.service;

import saleswebapp.components.HomeRestaurantForm;
import saleswebapp.components.HomeToDoForm;

import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
public interface HomeService {

    List<HomeRestaurantForm> createHomeRestaurantFormListForSalesPerson(String email);

    List<HomeToDoForm> getAllToDosForSalesPerson(String email);

    boolean toDoAssignedToSalePerson(int toDoId);

    void deleteToDo(int toDoId);

    String getCurrentPaymentOfSalesPerson(String email);
}
