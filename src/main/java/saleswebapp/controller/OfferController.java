package saleswebapp.controller;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Offer;
import saleswebapp.repository.impl.OfferPhoto;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.service.OfferService;
import saleswebapp.service.RestaurantService;
import saleswebapp.validator.offer.OfferValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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
        model = offerService.prepareOfferPictures(model, new Offer());

        return "offer";
    }

    @RequestMapping(value = "/newOfferForRestaurant", method = RequestMethod.GET)
    public String newOfferForRestaurant(Model model, @RequestParam("id") int restaurantId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        request.getSession().setAttribute("newOffer", true);
        request.getSession().setAttribute("offerId", 0);
        request.getSession().setAttribute("restaurantId", restaurantId);
        request.getSession().setAttribute("changeRequestId" , 0);

        Offer offer = new Offer();
        offer.setIdOfRestaurant(restaurantId);
        offer.offerTimesContainerFiller(restaurantService.getRestaurantById(restaurantId));

        model = prepareModelForGivenRestaurant(model, restaurantId);
        model = offerService.prepareOfferPictures(model, offer);
        model.addAttribute("offer", offer);

        return "offer";
    }

    @RequestMapping(value = "/offer", method = RequestMethod.GET)
    public String getExistingOffer(Model model, @RequestParam("id") int offerId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Offer offer = offerService.getOffer(offerId);
        Restaurant restaurant = offer.getRestaurant();
        int restaurantId = restaurant.getId();
        int changeRequestId = offer.getChangeRequestId();

        request.getSession().setAttribute("newOffer", false);
        request.getSession().setAttribute("offerId", offerId);
        request.getSession().setAttribute("restaurantId", restaurantId);
        request.getSession().setAttribute("commentOfLastChange", offer.getCommentOfLastChange());
        request.getSession().setAttribute("changeRequestId" , changeRequestId);

        offerService.addOfferToTransactionStore(offer);
        Offer preparedExistingOffer = offerService.prepareExistingOffer(offer, restaurant);

        model = prepareModelForGivenRestaurant(model, restaurantId);
        model = offerService.prepareOfferPictures(model, preparedExistingOffer);
        model.addAttribute("offer", preparedExistingOffer);

        return "offer";
    }

    @RequestMapping(value = "/offer/remove")
    public String deleteOfferImage(@RequestParam("offerPhotoId") int offerPhotoId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int offerId;
        if(request.getSession().getAttribute("offerId") == null) {
            //The user used the forth and back buttons of the browser to navigate through to the page. Therefore no session attributes are set.
            return "redirect:/home?doNotUseForthAndBackOfTheBrowserToNavigate";
        }

        offerId = (int) request.getSession().getAttribute("offerId");
        offerService.deleteOfferPhoto(offerPhotoId);

        return "redirect:/offer?id=" + offerId;
    }

    @RequestMapping(value = "/saveOffer", method = RequestMethod.POST)
    public String saveOffer(Model model, @Valid Offer offer, BindingResult offerBinder, HttpServletRequest request,
                            @RequestParam(required = false, value = "home") String homeFlag,
                            @RequestParam(required = false, value = "offerOverview") String offerOverviewFlag) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int restaurantId;
        if(request.getSession().getAttribute("restaurantId") == null) {
            //The user used the forth and back buttons of the browser to navigate through to the page. Therefore no session attributes are set.
            return "redirect:/home?doNotUseForthAndBackOfTheBrowserToNavigate";
        }

        restaurantId = (int) request.getSession().getAttribute("restaurantId");
        int offerId = (int) request.getSession().getAttribute("offerId");
        boolean newOffer = (boolean) request.getSession().getAttribute("newOffer");
        String commentOfLastChange = (String) request.getSession().getAttribute("commentOfLastChange");
        int changeRequestId = (int) request.getSession().getAttribute("changeRequestId");

        //Security check for the bound offer fields
        String[] suppressedFields = offerBinder.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }

        if (offerBinder.hasErrors()) {
            offer.setIdOfRestaurant(restaurantId);
            offer.offerTimesContainerFiller(restaurantService.getRestaurantById(restaurantId));
            offer.setId(offerId);

            model = prepareModelForGivenRestaurant(model, restaurantId);
            model = offerService.prepareOfferPictures(model, offer);
            model.addAttribute("offer", offer);

            return "offer";
        }

        //Keeps the former change comment if no new change comment has been entered.
        if (offer.getNewChangeComment().equals("")) {
            offer.setCommentOfLastChange(commentOfLastChange);
        }

        /*
        * 1) Distinguish between a new offer and an offer update.
        * 2) Checks if the offer has been altered while the user worked on it.
         */
        if (newOffer == true && offerId == 0) {
            offer.setIdOfRestaurant(restaurantId);
            offerService.saveOffer(offer);

            if(offerOverviewFlag != null) {
                return "redirect:/offerOverviewByRestaurant?id=" + restaurantId;
            } else {
                //homeFlag is set now
                return "redirect:/home?newOfferAddedSuccessfully";
            }
        } else {
            if (offerService.offerHasBeenAlteredMeanwhile(offerId)) {
                return "redirect:/home?offerWasChangedMeanwhile";
            } else if(newOffer == false && offerId != 0) {
                offer.setIdOfRestaurant(restaurantId);
                offer.setId(offerId);
                offer.setChangeRequestId(changeRequestId);
                offerService.saveOffer(offer);

                if(offerOverviewFlag != null) {
                    return "redirect:/offerOverviewByRestaurant?id=" + restaurantId;
                }

                if(homeFlag != null) {
                    return "redirect:/home?offerChangeSuccess";
                }
            }
            return "redirect:/home?offerControllerError";
        }
    }

    private Model prepareModelForGivenRestaurant(Model model, int restaurantId) {
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
                "newChangeComment",
                "home",
                "offerOverview"
        );
        offerBinder.setValidator(offerValidator);
    }

}
