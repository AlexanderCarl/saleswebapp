package saleswebapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import saleswebapp.components.RestaurantDeleteCategory;
import saleswebapp.components.RestaurantListForm;
import saleswebapp.components.RestaurantAddCategory;
import saleswebapp.components.RestaurantTimeContainer;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.service.CountryService;
import saleswebapp.service.RestaurantService;
import saleswebapp.service.SalesPersonService;

import java.util.Base64;
import java.util.UUID;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Controller
public class RestaurantController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SalesPersonService salesPersonService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CountryService countryService;

    String loggedInUser = "carl@hm.edu"; //DEV-Only

    @RequestMapping(value = "/newRestaurant", method = RequestMethod.GET)
    public String emptyRestaurant(Model model, @ModelAttribute("selectedRestaurant") RestaurantListForm restaurantListForm) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Restaurant restaurant = new Restaurant();
        restaurant.setCustomerId(restaurantService.getUniqueCustomerId());

        String restaurantUUID = UUID.randomUUID().toString();
        restaurant.setRestaurantUUID(restaurantUUID);
        restaurant.setQrUUID(restaurantService.createQRCode(restaurantUUID));
        restaurant.setQrUuidBase64Encoded(Base64.getEncoder().encodeToString(restaurant.getQrUUID()));
        restaurant.setOpeningTimes(restaurantService.populateRestaurantTimeDayNumber());
        restaurant.setOfferTimes(restaurantService.populateRestaurantTimeDayNumber());
        restaurant.restaurantKitchenTypesAsStringFiller();
        restaurant.setIdOfSalesPerson(salesPersonService.getSalesPersonByEmail(loggedInUser).getId());

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("restaurantTypes", restaurantService.getAllRestaurantTypes());
        model.addAttribute("kitchenTypes", restaurantService.getAllKitchenTypes());
        model.addAttribute("restaurantAddCategory", new RestaurantAddCategory(0));
        model.addAttribute("restaurantDeleteCategory", new RestaurantDeleteCategory(0));

        return "restaurant";
    }

    //Loads the requested restaurant into the model, if the user has access.
    @RequestMapping(value = "/restaurant", method = RequestMethod.GET)
    public String getRestaurant(Model model, @RequestParam("id") int restaurantId) {
        //Checks if the user is allowed to see the requested restaurant. (security check, if the call parameter has been altered manually)
        if(!restaurantService.restaurantAssignedToSalesPerson(restaurantId)) {
            return "redirect:/home?noValidAccessToRestaurant";
        }
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        restaurant.setQrUuidBase64Encoded(Base64.getEncoder().encodeToString(restaurant.getQrUUID()));
        restaurant.restaurantTimeContainerFiller();
        restaurant.orderRestaurantTimeContainers(); //Ensure that the days of a week are shown in the correct order.
        restaurant.setRestaurantTypeAsString(restaurant.getRestaurantType().getName());
        restaurant.restaurantKitchenTypesAsStringFiller();

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("restaurantTypes", restaurantService.getAllRestaurantTypes());
        model.addAttribute("kitchenTypes", restaurantService.getAllKitchenTypes());
        model.addAttribute("restaurantAddCategory", new RestaurantAddCategory(restaurantId));
        model.addAttribute("restaurantDeleteCategory", new RestaurantDeleteCategory(restaurantId));

        restaurantService.addRestaurantToRestaurantTransactionStore(restaurant);

        return "restaurant";
    }

    @RequestMapping(value = "/restaurant/addCategory", method = RequestMethod.POST)
    public String processNewCategory(RestaurantAddCategory restaurantAddCategory) {
        int restaurantId = restaurantAddCategory.getRestaurantId();

        //Safety check if the parameter is altered manually to 0.
        if (restaurantId == 0) {
            return "redirect:/newRestaurant";
        } else {
            restaurantService.addCategoryToRestaurant(restaurantAddCategory);
            return "redirect:/restaurant?id=" + restaurantId;
        }
    }

    @RequestMapping(value = "/restaurant/deleteCategory", method = RequestMethod.POST)
    public String processDeleteCategory(RestaurantDeleteCategory restaurantDeleteCategory) {
        int restaurantId = restaurantDeleteCategory.getRestaurantId();

        //Safety check if the parameter is altered manually to 0.
        if (restaurantId == 0) {
            return "redirect:/newRestaurant";
        } else {
            restaurantService.deleteCategoryFromRestaurant(restaurantDeleteCategory);
            return "redirect:/restaurant?id=" + restaurantId;
        }
    }

    @RequestMapping(value = "/saveRestaurant", method = RequestMethod.POST)
    public String processRestaurant(Restaurant restaurant, BindingResult restaurantBinder) {
        //Security check for the bound restaurant fields
        String[] suppressedFields = restaurantBinder.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        int restaurantId = restaurant.getId();

        //Checks if it is a new Restaurant or a change to an existing one
        if (restaurantId == 0) {
            restaurantService.saveRestaurant(restaurant);
            return "redirect:/home?newRestaurantAddedSuccessfully";
        } else {
            if (restaurantService.restaurantHasBeenAlteredMeanwhile(restaurant)) {
                return "redirect:/home?restaurantWasChangedMeanwhile";
            } else {
                restaurantService.saveRestaurant(restaurant);
                return "redirect:/home?restaurantChangeSuccess";
            }
        }
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder restaurantBinder) {
        restaurantBinder.setAllowedFields(
                "id",
                "customerId",
                "name",
                "street",
                "streetNumber",
                "zip",
                "city",
                "country",
                "locationLatitude",
                "locationLongitude",
                "email",
                "phone",
                "url",
                "restaurantUUID",
                "qrUUID",
                "offerModifyPermission",
                "blocked",
                "openingTimes[0].startTime","openingTimes[0].endTime","openingTimes[1].startTime","openingTimes[1].endTime","openingTimes[2].startTime","openingTimes[2].endTime","openingTimes[3].startTime","openingTimes[3].endTime","openingTimes[4].startTime","openingTimes[4].endTime","openingTimes[5].startTime","openingTimes[5].endTime","openingTimes[6].startTime","openingTimes[6].endTime",
                "offerTimes[0].startTime","offerTimes[0].endTime","offerTimes[1].startTime","offerTimes[1].endTime","offerTimes[2].startTime","offerTimes[2].endTime","offerTimes[3].startTime","offerTimes[3].endTime","offerTimes[4].startTime","offerTimes[4].endTime","offerTimes[5].startTime","offerTimes[5].endTime","offerTimes[6].startTime","offerTimes[6].endTime",
                "restaurantTypeAsString",
                "kitchenTypesAsString",
                "idOfSalesPerson"
        );
    }
}