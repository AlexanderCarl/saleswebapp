package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.OfferService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Service
public class OfferServiceImpl implements OfferService {

    //DEV-Only
    String loggedInUser = "carl@hm.edu";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbReaderService dbReaderService;

    @Autowired
    private DbWriterService dbWriterService;

    //The offer id is the key.
    /*This Map is used to store the offer(data) which is send to the user.
    * When the user presses save, the offer(data) is loaded again from the DB und compared to the
    * offer(data) at the start (stored in the HashMap). If the offer(data) has been altered
    * on the server while the user worked on it, the save request is rejected. This logic
    * is used to ensure data consistency.
    * The word transaction is used twice here:
    * 1) Transaction: Start by the GET.Request for the restaurant model - End by the comparison check if the DB-Object has been altered.
    * 2) Transaction: Only used to save the restaurant(data) with @Transactional */
    private static HashMap<Integer, Offer> offerTransactionStore = new HashMap<Integer, Offer>();

    @Override
    public void addOfferToRestaurantTransaction(Offer offer) {
        offerTransactionStore.put(offer.getId(), offer);
    }

    @Override
    public boolean offerHasBeenAlteredMeanwhile(int offerId) {
        Offer offerTransactionEnd = dbReaderService.getOffer(offerId);
        Offer offerTransactionStart = offerTransactionStore.get(offerId);

        if(!offerTransactionEnd.equals(offerTransactionStart)) {
            return true;
        }

        offerTransactionStore.remove(offerId);
        return false;
    }

    @Override
    public void saveOffer(Offer offer) {
        dbWriterService.saveOffer(offer);
    }

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

    @Override
    public void deleteOffer(int offerId) {
        dbWriterService.deleteOffer(offerId);
    }

    @Override
    public Offer getOffer(int offerId) {
        return dbReaderService.getOffer(offerId);
    }

    @Override
    public List<Additive> getAllAdditives() {
        return dbReaderService.getAllAdditives();
    }

    @Override
    public List<Allergenic> getAllAllergenic() {
        return dbReaderService.getAllAllergenic();
    }

}
