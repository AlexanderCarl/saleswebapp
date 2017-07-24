package saleswebapp.service;

import saleswebapp.components.RestaurantAddCategory;
import saleswebapp.components.RestaurantDeleteCategory;
import saleswebapp.components.RestaurantListForm;
import saleswebapp.components.RestaurantTimeContainer;
import saleswebapp.repository.impl.KitchenType;
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

    int getUniqueCustomerId();

    void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory);

    void deleteCategoryFromRestaurant(RestaurantDeleteCategory restaurantDeleteCategory);

    void addRestaurantToRestaurantTransactionStore(Restaurant restaurant);

    boolean restaurantHasBeenAlteredMeanwhile(Restaurant restaurant);

    void saveRestaurant(Restaurant restaurant);

    byte[] createQRCode(String restaurantUUID);

    List<RestaurantType> getAllRestaurantTypes();

    List<KitchenType> getAllKitchenTypes();

    List<RestaurantTimeContainer> populateRestaurantTimeDayNumber();
}
