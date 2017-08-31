package saleswebapp.controller;

import com.google.cloud.sql.jdbc.internal.Url;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import saleswebapp.repository.impl.*;
import saleswebapp.service.OfferService;
import saleswebapp.service.RestaurantService;
import saleswebapp.validator.offer.DateValidator;
import saleswebapp.validator.offer.ImageValidator;
import saleswebapp.validator.offer.OfferValidator;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Carl on 08.08.2017.
 */
@Controller
@Scope("session")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OfferValidator offerValidator;

    String loggedInUser = "carl@hm.edu"; //DEV-Only

    @RequestMapping(value = "/emptyOffer")
    public String emptyOffer(Model model, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        request.getSession().setAttribute("newOffer", false);
        request.getSession().setAttribute("offerId", 0);
        request.getSession().setAttribute("restaurantId", 0);
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("offer", new Offer());
        model.addAttribute("dataInputDisabled", true);
        model.addAttribute("restaurantName", "");
        model.addAttribute("allergenicsList", offerService.getAllAllergenic());
        model.addAttribute("additivesList", offerService.getAllAdditives());
        model = preparePictures(model, new Offer());

        return "offer";
    }

    @RequestMapping(value = "/newOfferForRestaurant", method = RequestMethod.GET)
    public String newOfferForRestaurant(Model model, @RequestParam("id") int restaurantId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        request.getSession().setAttribute("newOffer", true);
        request.getSession().setAttribute("offerId", 0);
        request.getSession().setAttribute("restaurantId", restaurantId);

        Offer offer = new Offer();
        offer.setIdOfRestaurant(restaurantId);
        offer.offerTimesContainerFiller(restaurantService.getRestaurantById(restaurantId));

        model = prepareModelForChosenRestaurant(model, restaurantId);
        model = preparePictures(model, offer);
        model.addAttribute("offer", offer);

        return "offer";
    }

    @RequestMapping(value = "/offer", method = RequestMethod.GET)
    public String getExistingOffer(Model model, @RequestParam("id") int offerId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Offer offer = offerService.getOffer(offerId);
        Restaurant restaurant = offer.getRestaurant();
        int restaurantId = restaurant.getId();

        request.getSession().setAttribute("newOffer", false);
        request.getSession().setAttribute("offerId", offerId);
        request.getSession().setAttribute("restaurantId", restaurantId);
        request.getSession().setAttribute("commentOfLastChange", offer.getCommentOfLastChange());

        offerService.addOfferToRestaurantTransaction(offer);
        Offer preparedExistingOffer = prepareExistingOffer(offer, restaurant);

        model = prepareModelForChosenRestaurant(model, restaurantId);
        model = preparePictures(model, preparedExistingOffer);
        model.addAttribute("offer", preparedExistingOffer);

        return "offer";
    }

    @RequestMapping(value = "/offer/remove")
    public String deleteOfferImage(@RequestParam("offerPhotoId") int offerPhotoId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        OfferPhoto offerPhoto = offerService.getOfferPhoto(offerPhotoId);
        int offerId = offerPhoto.getOffer().getId();
        offerService.deleteOfferPhoto(offerPhotoId);

        return "redirect:/offer?id=" + offerId;
    }

    @RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
    public String saveOffer (Model model, @Valid Offer offer, BindingResult offerBinder, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int restaurantId = (int) request.getSession().getAttribute("restaurantId");
        int offerId = (int) request.getSession().getAttribute("offerId");
        boolean newOffer = (boolean) request.getSession().getAttribute("newOffer");
        String commentOfLastChange = (String) request.getSession().getAttribute("commentOfLastChange");

        if (offerBinder.hasErrors()) {
            offer.setIdOfRestaurant(restaurantId);
            offer.offerTimesContainerFiller(restaurantService.getRestaurantById(restaurantId));
            offer.setId(offerId);

            model = prepareModelForChosenRestaurant(model, restaurantId);
            model = preparePictures(model, offer);
            model.addAttribute("offer", offer);

            return "offer";
        }

        //Security check for the bound offer fields
        String[] suppressedFields = offerBinder.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }

        //comment of last change persists if the new comment is empty
        offer.setCommentOfLastChange(commentOfLastChange);

        //Checks if it is a new offer or a change to an existing one
        if (newOffer == true && offerId == 0) {
            offer.setIdOfRestaurant(restaurantId);
            offerService.saveOffer(offer);
            return "redirect:/home?newOfferAddedSuccessfully";
        } else {
            if (offerService.offerHasBeenAlteredMeanwhile(offerId)) {
                return "redirect:/home?offerWasChangedMeanwhile";
            } else if(newOffer == false && offerId != 0) {
                offer.setIdOfRestaurant(restaurantId);
                offer.setId(offerId);
                offerService.saveOffer(offer);
                return "redirect:/home?offerChangeSuccess";
            } else {
                return "redirect:/home?offerControllerError";
            }
        }
    }

    private Offer prepareExistingOffer(Offer offer, Restaurant restaurant) {

        try {
            String startDate = offer.getStartDate().toString();
            startDate = startDate.substring(0, startDate.length() - 11);
            offer.setStartDateAsString(offer.reOrderDate(startDate));
        } catch (Exception e) {
            // The offer has no assigned start date
        }

        try {
            String endDate = offer.getEndDate().toString();
            endDate = endDate.substring(0, endDate.length() - 11);
            offer.setEndDateAsString(offer.reOrderDate(endDate));
        } catch (Exception e) {
            // The offer has no assigned end date
        }

        offer.setNeededPointsAsString(String.valueOf(offer.getNeededPoints()));
        offer.setPriceAsString(String.valueOf(offer.getPrice()));
        offer.setPreparationTimeAsString(String.valueOf(offer.getPreparationTime()));
        offer.setIdOfRestaurant(offer.getRestaurant().getId());
        offer.daysOfWeekAsStringFiller();
        offer.offerTimesContainerFiller(restaurant);
        offer.allergenicFiller();
        offer.additivesFiller();

        try {
            offer.setCourseTypeAsString(offer.getCourseType().getName());
        } catch (Exception e) {
            // offer has no course type (courseType is null because the courseType has been deleted).
        }

        return offer;
    }

    private Model prepareModelForChosenRestaurant(Model model, int restaurantId) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("offerList", offerService.getAllOffersOfRestaurant(restaurantId));
        model.addAttribute("dataInputDisabled", false);
        model.addAttribute("restaurantName", restaurantService.getRestaurantById(restaurantId).getName());
        model.addAttribute("allergenicsList", offerService.getAllAllergenic());
        model.addAttribute("additivesList", offerService.getAllAdditives());

        List<CourseType> courseTypes = null;
        try {
            courseTypes = restaurantService.getAllCourseTypesOfRestaurant(restaurantId);
        } catch (Exception e) {
            // Restaurant has no course types so far.
        }

        if(courseTypes == null) {
            courseTypes = new ArrayList<CourseType>();
        }

        model.addAttribute("courseTypes", courseTypes);

        return model;
    }

    private Model preparePictures(Model model, Offer offer) {
        int numberOfExistingPictures = 0;
        List<OfferPhoto> offerPhotos = offer.getOfferPhotos();

        try {
            numberOfExistingPictures = offerPhotos.size();
        } catch (Exception e) {
            //zero existing pictures
        }

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

    @InitBinder
    public void initialiseBinder(WebDataBinder offerBinder) {
        offerBinder.setAllowedFields(
                "title",
                "priceAsString",
                "neededPointsAsString",
                "preparationTimeAsString",
                "description",
                "startDateAsString",
                "endDateAsString",
                "courseTypeAsString",
                "additivesAsString",
                "allergenicsAsString",
                "validnessDaysOfWeekAsString",
                "firstOfferImage",
                "secondOfferImage",
                "thirdOfferImage",
                "newChangeComment"
        );
        offerBinder.setValidator(offerValidator);
    }
}
