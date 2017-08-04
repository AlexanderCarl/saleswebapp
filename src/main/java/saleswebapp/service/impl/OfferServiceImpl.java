package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.repository.impl.Offer;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.OfferService;

import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private DbReaderService dbReaderService;

    @Override
    public List<Offer> getAllOffersOfRestaurant(int restaurantId) {
        return dbReaderService.getAllOffersOfRestaurant(restaurantId);
    }

    @Override
    public List<Offer> getAllOffersOfRestaurant(int restaurantId, String courseTypeAsString) {
        return dbReaderService.getAllOffersOfRestaurant(restaurantId, courseTypeAsString);
    }

    @Override
    public List<Offer> getAllOffersOfRestaurantAndCourseTypeNull(int restaurantId) {
        return dbReaderService.getAllOffersOfRestaurantAndCourseTypeNull(restaurantId);
    }
}
