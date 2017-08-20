package saleswebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Offer;
import saleswebapp.service.OfferService;
import saleswebapp.service.RestaurantService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Controller
@Scope("session")
public class OfferOverviewController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private RestaurantService restaurantService;

    String loggedInUser = "carl@hm.edu"; //DEV-Only

    @RequestMapping(value = "/emptyOfferOverview")
    public String emptyOfferOverview(Model model, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        request.getSession().setAttribute("idForCancelButton", 0);
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));

        return "offerOverview";
    }

    //Loads the requested offers into the model, if the user has access.
    @RequestMapping(value = "/offerOverviewByRestaurant")
    public String getOffersOfRestaurant(Model model, @RequestParam("id") int restaurantId, HttpServletRequest request) {
        //Checks if the user is allowed to see the requested restaurant. (security check, if the call parameter has been altered manually)
        if(!restaurantService.restaurantAssignedToSalesPerson(restaurantId)) {
            return "redirect:/home?noValidAccessToRestaurant";
        }

        request.getSession().setAttribute("idForCancelButton", 0);
        request.getSession().setAttribute("restaurantId", restaurantId);
        request.getSession().setAttribute("pageStatus", "filteredByRestaurantId");
        model = prepareModel(model, restaurantId, loggedInUser);
        model.addAttribute("offerList", getOfferList(restaurantId));

        return "offerOverview";
    }

    //Loads the requested offers into the model, filtered by course type
    @RequestMapping(value = "/offerOverviewByCourseType")
    public String getOffersForCourseType(Model model, @RequestParam("courseType") String courseTypeAsString, HttpServletRequest request) {
        int restaurantId = (int) request.getSession().getAttribute("restaurantId");

        //Checks if the user is allowed to see the requested restaurant. (security check, if the call parameter has been altered manually)
        if(!restaurantService.restaurantAssignedToSalesPerson(restaurantId)) {
            return "redirect:/home?noValidAccessToRestaurant";
        }

        request.getSession().setAttribute("pageStatus", "filteredByRestaurantIdAndCourseType");
        request.getSession().setAttribute("courseType", courseTypeAsString);
        request.getSession().setAttribute("idForCancelButton", restaurantId);
        model = prepareModel(model, restaurantId, loggedInUser);
        model.addAttribute("offerList", getOfferList(restaurantId, courseTypeAsString));

        return "offerOverview";
    }

    //Custom cancel button
    @RequestMapping(value = "/cancelOfferOverview")
    public String cancelOfferOverview(HttpServletRequest request) {
        int id = (int) request.getSession().getAttribute("idForCancelButton");

        if(id == 0) {
            return "redirect:/home";
        } else {
            return "redirect:/offerOverviewByRestaurant?id=" + id;
        }
    }

    //Delete a offer
    @RequestMapping(value = "/offerOverview/remove")
    public String deleteOffer(@RequestParam("offerId") int offerId, HttpServletRequest request) {

        String pageStatus = (String) request.getSession().getAttribute("pageStatus");
        int restaurantId = (int) request.getSession().getAttribute("restaurantId");

        //Checks if the user is allowed to see the requested offer which belongs to a restaurant he has access to. (security check, if the call parameter has been altered manually)
        if(!restaurantService.restaurantAssignedToSalesPerson(restaurantId)) {
            return "redirect:/home?noValidAccessToRestaurant";
        }

        offerService.deleteOffer(offerId);

        if(pageStatus.equals("filteredByRestaurantId")) {
            return "redirect:/offerOverviewByRestaurant?id=" + restaurantId;
        } else {
            String courseTypeAsString = (String) request.getSession().getAttribute("courseType");
            return "redirect:/offerOverviewByCourseType" + courseTypeAsString;
        }
    }

    private Model prepareModel(Model model, int restaurantId, String loggedInUser) {

        List<CourseType> courseTypeList = null;
        try {
            courseTypeList = restaurantService.getAllCourseTypesOfRestaurant(restaurantId);
        } catch (Exception e) {
            // Restaurant has no course types so far.
        }

        if(courseTypeList == null) {
            courseTypeList = new ArrayList<CourseType>();
        }
        courseTypeList.add(new CourseType("..."));

        model.addAttribute("restaurantName", restaurantService.getRestaurantById(restaurantId).getName());
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("courseTypeList", courseTypeList);

        return model;
    }

    private List<Offer> getOfferList(int restaurantId, String courseTypeAsString) {
        List<Offer> offerList = null;

        if(courseTypeAsString.equals("...")) {
            try {
                offerList = offerService.getAllOffersOfRestaurantAndCourseTypeNull(restaurantId);
            } catch (Exception e) {
                // The restaurant has no offers where the course type is null
            }
        } else {
            try {
                offerList = offerService.getAllOffersOfRestaurant(restaurantId, courseTypeAsString);
            } catch (Exception e) {
                // The restaurant has no offers so far
            }
        }

        if(offerList != null) {
            offerList = setStringsForDisplaying(offerList);
        }

        return offerList;
    }

    private List<Offer> getOfferList(int restaurantId) {
        List<Offer> offerList = null;

        try {
            offerList = offerService.getAllOffersOfRestaurant(restaurantId);
        } catch (Exception e) {
            // The restaurant has no offers so far
        }

        if(offerList != null) {
            offerList = setStringsForDisplaying(offerList);
        }

        return offerList;
    }

    private List<Offer> setStringsForDisplaying(List<Offer> offerList) {

        if(offerList != null) {
            for (Offer offer : offerList) {
                try {
                    offer.setCourseTypeAsString(offer.getCourseType().getName());
                } catch (Exception e) {
                    // The offer has no assigned course type
                }

                try {
                    String time = offer.getStartDate().toString();
                    offer.setStartDateAsString(time.substring(0, time.length() - 11));
                } catch (Exception e) {
                    // The offer has no assigned start date
                }

                try {
                    String time = offer.getEndDate().toString();
                    offer.setEndDateAsString(time.substring(0, time.length() - 11));
                } catch (Exception e) {
                    // The offer has no assigned end date
                }
            }
        }
        return offerList;
    }

}