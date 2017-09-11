package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.OfferService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
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

    /* The offer id is used as the map key.
    * This Map is used to compare offers and offerChangeRequests. An offer is put into the
    * transaction store when the user takes a look at an offer or an offerChangeRequest. When
    * the user presses save, the offer(data) is loaded again from the DB und compared to the
    * offer(data) at the start (stored in the HashMap). If the offer(data) has been altered
    * while the user worked on it, the save request is rejected. This logic is used to ensure
    * data consistency.
    * The word transaction is used twice here:
    * 1) Transaction: Start by the GET.Request for the offer model - End by the comparison check if the DB-Object has been altered.
    * 2) Transaction: Only used to save the restaurant(data) with @Transactional */
    private static HashMap<Integer, Offer> offerTransactionStore = new HashMap<Integer, Offer>();

    @Override
    public void addOfferToTransactionStore(Offer offer) {
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
    public OfferPhoto getOfferPhoto(int offerPhotoId) {
        return dbReaderService.getOfferPhoto(offerPhotoId);
    }

    @Override
    public void deleteOfferPhoto(int offerPhotoId) {
        dbWriterService.deleteOfferPhoto(offerPhotoId);
    }

    @Override
    public Offer prepareExistingOffer(Offer offer, Restaurant restaurant) {
        offer.setPriceAsString(String.valueOf(offer.getPrice()));
        offer.setNeededPointsAsString(String.valueOf(offer.getNeededPoints()));
        offer.setPreparationTimeAsString(String.valueOf(offer.getPreparationTime()));
        offer.setIdOfRestaurant(offer.getRestaurant().getId());
        offer.daysOfWeekAsStringFiller();
        offer.offerTimesContainerFiller(restaurant);
        offer.allergenicFiller();
        offer.additivesFiller();

        //StartDateAsString
        try {
            String startDate = offer.getStartDate().toString();
            startDate = startDate.substring(0, startDate.length() - 11);
            offer.setStartDateAsString(offer.reOrderDate(startDate));
        } catch (Exception e) {
            // The offer has no assigned start date
        }

        //EndDateAsString
        try {
            String endDate = offer.getEndDate().toString();
            endDate = endDate.substring(0, endDate.length() - 11);
            offer.setEndDateAsString(offer.reOrderDate(endDate));
        } catch (Exception e) {
            // The offer has no assigned end date
        }

        //CourseTypeAsString
        try {
            offer.setCourseTypeAsString(offer.getCourseType().getName());
        } catch (Exception e) {
            // offer has no course type (courseType is null because the courseType has been deleted).
        }

        return offer;
    }

    @Override
    public String getDefaultOfferImageBase64() {
        BufferedImage defaultImage;
        byte[] defaultImageAsByte = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = null;

        try {
            file = new ClassPathResource("static/defaultOfferImage.jpg").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            defaultImage = ImageIO.read(file);
            ImageIO.write(defaultImage, "jpg", baos);
            defaultImageAsByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String defaultImageBase64 = Base64.getEncoder().encodeToString(defaultImageAsByte);

        return defaultImageBase64;
    }

    @Override
    public Model prepareOfferPictures(Model model, Offer offer) {
        int numberOfExistingPictures = 0;
        List<OfferPhoto> offerPhotos = offer.getOfferPhotos();

        try {
            numberOfExistingPictures = offerPhotos.size();
        } catch (Exception e) {
            //zero existing pictures
        }

        String defaultImageBase64 = getDefaultOfferImageBase64();

        switch (numberOfExistingPictures) {
            case 0:
                model.addAttribute("firstPicture", defaultImageBase64);
                model.addAttribute("firstPictureDeleteDisabled", true);

                model.addAttribute("secondPicture", defaultImageBase64);
                model.addAttribute("secondPictureDeleteDisabled", true);

                model.addAttribute("thirdPicture", defaultImageBase64);
                model.addAttribute("thirdPictureDeleteDisabled", true);
                break;

            case 1:
                model.addAttribute("firstPicture", Base64.getEncoder().encodeToString(offerPhotos.get(0).getThumbnail()));
                model.addAttribute("idOfFirstPicture", offerPhotos.get(0).getId());
                model.addAttribute("firstPictureDeleteDisabled", false);

                model.addAttribute("secondPicture", defaultImageBase64);
                model.addAttribute("secondPictureDeleteDisabled", true);

                model.addAttribute("thirdPicture", defaultImageBase64);
                model.addAttribute("thirdPictureDeleteDisabled", true);
                break;

            case 2:
                model.addAttribute("firstPicture", Base64.getEncoder().encodeToString(offerPhotos.get(0).getThumbnail()));
                model.addAttribute("idOfFirstPicture", offerPhotos.get(0).getId());
                model.addAttribute("firstPictureDeleteDisabled", false);

                model.addAttribute("secondPicture", Base64.getEncoder().encodeToString(offerPhotos.get(1).getThumbnail()));
                model.addAttribute("idOfSecondPicture", offerPhotos.get(1).getId());
                model.addAttribute("secondPictureDeleteDisabled", false);

                model.addAttribute("thirdPicture", defaultImageBase64);
                model.addAttribute("thirdPictureDeleteDisabled", true);
                break;

            default: // 3 and more pics
                model.addAttribute("firstPicture", Base64.getEncoder().encodeToString(offerPhotos.get(0).getThumbnail()));
                model.addAttribute("idOfFirstPicture", offerPhotos.get(0).getId());
                model.addAttribute("firstPictureDeleteDisabled", false);

                model.addAttribute("secondPicture", Base64.getEncoder().encodeToString(offerPhotos.get(1).getThumbnail()));
                model.addAttribute("idOfSecondPicture", offerPhotos.get(1).getId());
                model.addAttribute("secondPictureDeleteDisabled", false);

                model.addAttribute("thirdPicture", Base64.getEncoder().encodeToString(offerPhotos.get(2).getThumbnail()));
                model.addAttribute("idOfThirdPicture", offerPhotos.get(2).getId());
                model.addAttribute("thirdPictureDeleteDisabled", false);
        }

        return model;
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
