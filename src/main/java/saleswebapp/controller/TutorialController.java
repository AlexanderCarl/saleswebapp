package saleswebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.service.RestaurantService;

import javax.validation.Valid;

/**
 * Created by Alexander Carl on 27.06.2017.
 */
@Controller
public class TutorialController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(value = "/BootstrapsTutorial1")
    public String bootstrapsTutorialOne() {
        return "BootstrapsTutorial/BootstrapsTutorial1";
    }

    @RequestMapping(value = "/BootstrapsTutorial2")
    public String bootstrapsTutorialTwo() {
        return "BootstrapsTutorial/BootstrapsTutorial2";
    }

    @RequestMapping(value = "/BootstrapsTutorial3")
    public String bootstrapsTutorialThree() {
        return "BootstrapsTutorial/BootstrapsTutorial3";
    }

    @RequestMapping(value = "/BootstrapsTutorial4")
    public String bootstrapsTutorialFour() {
        return "BootstrapsTutorial/BootstrapsTutorial4";
    }

    @RequestMapping(value = "/EditableTable", method = RequestMethod.GET)
    public String editableTableTutorial(Model model) throws Exception {
        model.addAttribute("restaurant", restaurantService.getRestaurantById(11));
        return "BootstrapsTutorial/EditableTable";
    }

    @RequestMapping(value = "/EditableTable", method = RequestMethod.POST)
    public String processEditedTable(Restaurant restaurant) {
        return "redirect:/home";
    }
}
