package saleswebapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import saleswebapp.components.DTO.RestaurantDeleteCategory;
import saleswebapp.components.DTO.RestaurantForm;
import saleswebapp.components.DTO.RestaurantListForm;
import saleswebapp.components.DTO.RestaurantAddCategory;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.service.CountryService;
import saleswebapp.service.RestaurantService;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Controller
public class RestaurantController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CountryService countryService;

    String loggedInUser = "carl@hm.edu"; //DEV-Only

    //Adds an empty RestaurantForm to the model
    @RequestMapping(value = "/newRestaurant", method = RequestMethod.GET)
    public String emptyRestaurant(Model model, @ModelAttribute("selectedRestaurant") RestaurantListForm restaurantListForm) throws Exception {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        RestaurantForm restaurantForm = new RestaurantForm();
        restaurantForm.setCustomerId(restaurantService.getUniqueCustomerId());

        model.addAttribute("restaurantForm", restaurantForm);
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("restaurantTypes", restaurantService.getAllRestaurantTypes());
        model.addAttribute("restaurantKitchenTypes", restaurantService.getAllKitchenTypes());
        model.addAttribute("restaurantAddCategory", new RestaurantAddCategory(0));
        model.addAttribute("restaurantDeleteCategory", new RestaurantDeleteCategory(0));

        return "restaurant";
    }

    //Loads the requested restaurant into the model, if the user has access.
    @RequestMapping(value = "/restaurant", method = RequestMethod.GET)
    public String getRestaurant(Model model, @RequestParam("id") int restaurantId) throws Exception {
        //Checks if the user is allowed to see the requested restaurant. (security check, if the call parameter has been altered manually)
        if(!restaurantService.restaurantAssignedToSalesPerson(restaurantId)) {
            return "redirect:/home?noValidAccessToRestaurant";
        }
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        model.addAttribute("restaurantForm", new RestaurantForm(restaurant));
        model.addAttribute("restaurantList", restaurantService.getAllRestaurantNamesForSalesPerson(loggedInUser));
        model.addAttribute("countries", countryService.getAllCountries());
        model.addAttribute("restaurantTypes", restaurantService.getAllRestaurantTypes());
        model.addAttribute("restaurantKitchenTypes", restaurantService.getAllKitchenTypes());
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
    public String processRestaurant(RestaurantForm restaurantForm, BindingResult bindingResult) {
        int restaurantId = restaurantForm.getId();

        //Checks if it is a new Restaurant or a change to an existing one
        if (restaurantId == 0) {
            restaurantService.addNewRestaurant(restaurantForm);
            return "redirect:/home?newRestaurantAddedSuccessfully";
        } else {
            if (restaurantService.restaurantHasBeenAlteredMeanwhile(restaurantForm)) {
                return "redirect:/home?restaurantWasChangedMeanwhile";
            } else {
                return "redirect:/home?restaurantChangeSuccess";
            }
        }
    }
}
