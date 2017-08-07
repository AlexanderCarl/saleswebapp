package saleswebapp.service;

import saleswebapp.repository.impl.Offer;

import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
public interface OfferService {

    List<Offer> getAllOffersOfRestaurant(int restaurantId);

    List<Offer> getAllOffersOfRestaurant(int restaurantId, String courseTypeAsString);

    List<Offer> getAllOffersOfRestaurantAndCourseTypeNull(int restaurantId);

    void deleteOffer(int offerId);
}
