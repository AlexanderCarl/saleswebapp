package saleswebapp.service;

import saleswebapp.components.DTO.*;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.repository.impl.RestaurantType;

import java.util.List;

/**
 * Created by Alexander Carl on 08.07.2017.
 */
public interface RestaurantService {

    List<RestaurantListForm> getAllRestaurantNamesForSalesPerson(String email);

    Restaurant getRestaurantById(int id);

    boolean restaurantAssignedToSalesPerson(int id);

    List<RestaurantTypeForm> getAllRestaurantTypes();

    int getUniqueCustomerId();

    List<String> getAllKitchenTypes();

    void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory);

    void deleteCategoryFromRestaurant(RestaurantDeleteCategory restaurantDeleteCategory);

    void addRestaurantToRestaurantTransactionStore(Restaurant restaurant);

    boolean restaurantHasBeenAlteredMeanwhile(RestaurantForm restaurantForm);

    void addNewRestaurant(RestaurantForm restaurantForm);
}
