package saleswebapp.service;

import saleswebapp.components.DTO.HomeRestaurantForm;
import saleswebapp.components.DTO.HomeToDoForm;
import saleswebapp.domain.repository.impl.Restaurant;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.domain.repository.impl.ToDoList;

import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
public interface HomeService {

    List<HomeRestaurantForm> createHomeRestaurantFormListForSalesPerson(String email);

    List<HomeToDoForm> getAllToDosForSalesPerson(String email);
}
