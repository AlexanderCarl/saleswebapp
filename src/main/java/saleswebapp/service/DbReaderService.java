package saleswebapp.service;

import saleswebapp.repository.impl.*;

import java.util.List;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
public interface DbReaderService {

    SalesPerson getSalesPersonByEmail(String email);

    SalesPerson getSalesPersonById(int id);

    List<Country> getAllCountries();

    List<Restaurant> getAllRestaurantsOfSalesPerson(String email);

    Restaurant getRestaurantById(int id);

    List<ToDoList> getAllToDosOfSalesPerson(String email);

    List<RestaurantType> getAllRestaurantTypes();

    Restaurant getRestaurantByCustomerId(int id);

    List<KitchenType> getAllKitchenTypes();

    List<Offer> getAllOffersOfRestaurant(int restaurantId);

    List<Offer> getAllOffersOfRestaurant(int restaurantId, String courseType);

    List<Offer> getAllOffersOfRestaurantAndCourseTypeNull(int restaurantId);

    List<CourseType> getAllCourseTypesOfRestaurant(int restaurantId);

    Offer getOffer(int offerId);

    List<Additive> getAllAdditives();

    List<Allergenic> getAllAllergenic();

}
