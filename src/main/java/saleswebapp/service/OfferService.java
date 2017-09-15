package saleswebapp.service;

import org.springframework.ui.Model;
import saleswebapp.repository.impl.*;

import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
public interface OfferService {

    List<Offer> getAllOffersOfRestaurant(int restaurantId);

    List<Offer> getAllOffersOfRestaurant(int restaurantId, String courseTypeAsString);

    List<Offer> getAllOffersOfRestaurantAndCourseTypeNull(int restaurantId);

    void deleteOffer(int offerId);

    Offer getOffer(int offerId);

    List<Additive> getAllAdditives();

    List<Allergenic> getAllAllergenic();

    void addOfferToTransactionStore(Offer offer);

    boolean offerHasBeenAlteredMeanwhile(int offerId);

    void saveOffer(Offer offer);

    OfferPhoto getOfferPhoto(int offerPhotoId);

    void deleteOfferPhoto(int offerPhotoId);

    Offer prepareExistingOffer(Offer offer, Restaurant restaurant);

    Model prepareOfferPictures(Model model, Offer offer);

    String getDefaultOfferImageBase64();

    boolean toDoEntryWithOfferExists(int offerId);
}
