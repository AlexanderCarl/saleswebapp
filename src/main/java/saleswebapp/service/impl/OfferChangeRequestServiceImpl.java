package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Offer;
import saleswebapp.repository.impl.OfferPhoto;
import saleswebapp.repository.impl.ToDo;
import saleswebapp.service.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by Alexander Carl on 07.09.2017.
 */
@Service
public class OfferChangeRequestServiceImpl implements OfferChangeRequestService {

    @Autowired
    private DbReaderService dbReaderService;

    @Autowired
    private DbWriterService dbWriterService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OfferService offerService;

    @Override
    public ToDo getToDoById(int id) {
        return dbReaderService.getToDoById(id);
    }

    @Override
    public List<CourseType> getCourseTypes(int restaurantId) {
        List<CourseType> courseTypes = null;
        try {
            courseTypes = restaurantService.getAllCourseTypesOfRestaurant(restaurantId);
        } catch (Exception e) {
            // Restaurant has no course types so far.
        }

        if(courseTypes == null) {
            courseTypes = new ArrayList<CourseType>();
        }

        return courseTypes;
    }

    @Override
    public Model prepareOfferPicturesForExistingOffer(Model model, Offer offer) {
        int numberOfExistingPictures = 0;
        List<OfferPhoto> offerPhotos = offer.getOfferPhotos();

        try {
            numberOfExistingPictures = offerPhotos.size();
        } catch (Exception e) {
            //zero existing pictures
        }

        String defaultImageBase64 = offerService.getDefaultOfferImageBase64();

        switch (numberOfExistingPictures) {
            case 0:
                model.addAttribute("existingOfferFirstPicture", defaultImageBase64);
                model.addAttribute("existingOfferSecondPicture", defaultImageBase64);
                model.addAttribute("existingOfferThirdPicture", defaultImageBase64);
                break;

            case 1:
                model.addAttribute("existingOfferFirstPicture", Base64.getEncoder().encodeToString(offerPhotos.get(0).getThumbnail()));
                model.addAttribute("existingOfferSecondPicture", defaultImageBase64);
                model.addAttribute("existingOfferThirdPicture", defaultImageBase64);
                break;

            case 2:
                model.addAttribute("existingOfferFirstPicture", Base64.getEncoder().encodeToString(offerPhotos.get(0).getThumbnail()));
                model.addAttribute("existingOfferSecondPicture", Base64.getEncoder().encodeToString(offerPhotos.get(1).getThumbnail()));
                model.addAttribute("existingOfferThirdPicture", defaultImageBase64);
                break;

            default: // 3 and more pics
                model.addAttribute("existingOfferFirstPicture", Base64.getEncoder().encodeToString(offerPhotos.get(0).getThumbnail()));
                model.addAttribute("existingOfferSecondPicture", Base64.getEncoder().encodeToString(offerPhotos.get(1).getThumbnail()));
                model.addAttribute("existingOfferThirdPicture", Base64.getEncoder().encodeToString(offerPhotos.get(2).getThumbnail()));
        }

        return model;
    }

    @Override
    public void deleteOfferChangeRequest(int offerToDeleteId, int offerToUpdateId, int toDoId) {
        dbWriterService.deleteOfferChangeRequest(offerToDeleteId, offerToUpdateId, toDoId);
    }

    @Override
    public void saveOfferChangeRequest(int offerChangeRequestId, Offer changedOffer, int toDoId) {
        dbWriterService.saveOfferChangeRequest(offerChangeRequestId, changedOffer, toDoId);
    }

    @Override
    public Model addAttribtueChangesToModel(Model model, boolean allFalse, Offer preparedExistingOffer, Offer preparedChangedOffer) {

        Offer existingOffer = preparedExistingOffer;
        Offer changedOffer = preparedChangedOffer;

        if (allFalse == true) {
            model.addAttribute("titleChanged", false);
            model.addAttribute("priceChanged", false);
            model.addAttribute("neededPointsChanged", false);
            model.addAttribute("preparationTimeChanged", false);
            model.addAttribute("startDateChanged", false);
            model.addAttribute("endDateChanged", false);
            model.addAttribute("descriptionChanged", false);

            return model;
        }

        //Compares the attributes of the two offer objects
        if (allFalse == false) {
            if (!existingOffer.getTitle().equals(changedOffer.getTitle())) {
                model.addAttribute("titleChanged", true);
            } else {
                model.addAttribute("titleChanged", false);
            }

            if (!existingOffer.getPriceAsString().equals(changedOffer.getPriceAsString())) {
                model.addAttribute("priceChanged", true);
            } else {
                model.addAttribute("priceChanged", false);
            }

            if (!existingOffer.getNeededPointsAsString().equals(changedOffer.getNeededPointsAsString())) {
                model.addAttribute("neededPointsChanged", true);
            } else {
                model.addAttribute("neededPointsChanged", false);
            }

            if (!existingOffer.getPreparationTimeAsString().equals(changedOffer.getPreparationTimeAsString())) {
                model.addAttribute("preparationTimeChanged", true);
            } else {
                model.addAttribute("preparationTimeChanged", false);
            }

            if (!existingOffer.getDescription().equals(changedOffer.getDescription())) {
                model.addAttribute("descriptionChanged", true);
            } else {
                model.addAttribute("descriptionChanged", false);
            }

            //Start date
            if (existingOffer.getStartDateAsString() != null && changedOffer.getStartDateAsString() == null
                    || existingOffer.getStartDateAsString() == null && changedOffer.getStartDateAsString() != null) {
                        model.addAttribute("startDateChanged", true);
            } else {
                model.addAttribute("startDateChanged", false);
            }

            if (existingOffer.getStartDateAsString() != null && changedOffer.getStartDateAsString() != null) {
                if (!existingOffer.getStartDateAsString().equals(changedOffer.getStartDateAsString())) {
                        model.addAttribute("startDateChanged", true);
                }
            } else {
                model.addAttribute("startDateChanged", false);
            }

            //End date
            if (existingOffer.getEndDateAsString() != null && changedOffer.getEndDateAsString() == null
                    || existingOffer.getEndDateAsString() == null && changedOffer.getEndDateAsString() != null) {
                        model.addAttribute("endDateChanged", true);
            } else {
                model.addAttribute("endDateChanged", false);
            }

            if (existingOffer.getEndDateAsString() != null && changedOffer.getEndDateAsString() != null) {
                if (!existingOffer.getEndDateAsString().equals(changedOffer.getEndDateAsString())) {
                        model.addAttribute("endDateChanged", true);
                } else {
                    model.addAttribute("endDateChanged", false);
                }
            }
        }
        return model;
    }
}
