package saleswebapp.controller;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import saleswebapp.repository.impl.*;
import saleswebapp.service.OfferChangeRequestService;
import saleswebapp.service.OfferService;
import saleswebapp.service.RestaurantService;
import saleswebapp.validator.offer.OfferValidator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Alexander Carl on 07.09.2017.
 */
@Controller
@Scope
public class OfferChangeRequestController {

    @Autowired
    private OfferChangeRequestService offerChangeRequestService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OfferValidator offerValidator;

    String loggedInUser = "carl@hm.edu"; //DEV-Only

    @RequestMapping(value = "/offerChangeRequest", method = RequestMethod.GET)
    public String getOfferChangeRequest(Model model, @RequestParam("id") int toDoId, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        /*
        The ToDo-Object contains the offerId for the offer which has a change Request. Therefore
        only one changeRequest per offer is possible. The offer-object contains the
        id (swa_change_request_id) for the offer-object which holds the information for
        the change request.
         */
        ToDo toDo = offerChangeRequestService.getToDoById(toDoId);
        Offer existingOffer = initializeAndUnproxy(toDo.getOffer());
        Restaurant restaurant = initializeAndUnproxy(toDo.getRestaurant());
        Offer changedOffer = offerService.getOffer(existingOffer.getChangeRequestId());

        request.getSession().setAttribute("commentOfLastChange", existingOffer.getCommentOfLastChange());
        request.getSession().setAttribute("existingOfferId", existingOffer.getId()); //Id of the offer to which to change request belongs
        request.getSession().setAttribute("changedOfferId", changedOffer.getId()); //Id of the change request
        request.getSession().setAttribute("restaurantId", restaurant.getId());
        request.getSession().setAttribute("toDoId", toDoId);

        offerService.addOfferToTransactionStore(initializeAndUnproxy(existingOffer));
        Offer preparedChangedOffer = offerService.prepareExistingOffer(changedOffer, restaurant);
        Offer preparedExistingOffer = offerService.prepareExistingOffer(existingOffer, restaurant);
        preparedChangedOffer = offerChangeRequestService.prepareKeepImagesTags(preparedExistingOffer, preparedChangedOffer);

        model.addAttribute("offer", preparedChangedOffer);
        model.addAttribute("existingOffer", preparedExistingOffer);
        model = offerService.prepareOfferPictures(model, changedOffer); //prepares the images for the changedOffer
        model = offerChangeRequestService.prepareOfferPicturesForExistingOffer(model, existingOffer); //prepares the images for the existingOffer
        model.addAttribute("restaurantName", restaurantService.getRestaurantById(restaurant.getId()).getName());
        model.addAttribute("allergenicsList", offerService.getAllAllergenic());
        model.addAttribute("additivesList", offerService.getAllAdditives());
        model.addAttribute("courseTypesList", offerChangeRequestService.getCourseTypes(restaurant.getId()));
        model.addAttribute("toDoId", toDoId);
        model = offerChangeRequestService.addAttribtueChangesToModel(model, false, preparedExistingOffer, preparedChangedOffer);

        return "offerChangeRequest";
    }

    @RequestMapping(value = "/saveOfferChangeRequest", method = RequestMethod.POST)
    public String saveOfferChangeRequest(Model model, @Valid Offer changedOffer, BindingResult changedOfferBinder, HttpServletRequest request) {
        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        int toDoId;
        if(request.getSession().getAttribute("toDoId") == null) {
            //The user used the forth and back buttons of the browser to navigate through to the page. Therefore no session attributes are set.
            return "redirect:/home?doNotUseForthAndBackOfTheBrowserToNavigate";
        }

        toDoId = (int) request.getSession().getAttribute("toDoId");
        int existingOfferId = (int) request.getSession().getAttribute("existingOfferId");
        int changedOfferId = (int) request.getSession().getAttribute("changedOfferId");
        int restaurantId = (int) request.getSession().getAttribute("restaurantId");
        String commentOfLastChange = (String) request.getSession().getAttribute("commentOfLastChange");
        Offer existingOffer = offerService.getOffer(existingOfferId);

        //Security check for the bound offer fields
        String[] suppressedFields = changedOfferBinder.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }

        //Validator
        if (changedOfferBinder.hasErrors()) {

            ToDo toDo = offerChangeRequestService.getToDoById(toDoId);
            Restaurant restaurant = toDo.getRestaurant();

            model.addAttribute("offer", changedOffer);
            model.addAttribute("existingOffer", offerService.prepareExistingOffer(existingOffer, restaurant));
            model = offerService.prepareOfferPictures(model, changedOffer);
            model = offerChangeRequestService.prepareOfferPicturesForExistingOffer(model, existingOffer);
            model.addAttribute("restaurantName", restaurantService.getRestaurantById(restaurantId).getName());
            model.addAttribute("allergenicsList", offerService.getAllAllergenic());
            model.addAttribute("additivesList", offerService.getAllAdditives());
            model.addAttribute("courseTypesList", offerChangeRequestService.getCourseTypes(restaurantId));
            model.addAttribute("toDoId", toDoId);
            model = offerChangeRequestService.addAttribtueChangesToModel(model, true, null, null);

            return "offerChangeRequest";
        }

        //Keeps the former change comment if no new change comment has been entered.
        if (changedOffer.getNewChangeComment().equals("")) {
            changedOffer.setCommentOfLastChange(commentOfLastChange);
        }

        //Checks if the offer of the offerChangeRequest has been altered while the user worked on it.
        if(offerService.offerHasBeenAlteredMeanwhile(existingOfferId)) {
            return "redirect:/home?offerWasChangedMeanwhile";
        } else {
            changedOffer.setId(existingOfferId);
            changedOffer.setIdOfRestaurant(restaurantId);
            offerChangeRequestService.saveOfferChangeRequest(changedOfferId, changedOffer, existingOffer, toDoId);
            return "redirect:/home?offerChangeRequestSuccess";
        }
    }

    @RequestMapping(value = "/offerChangeRequest/remove")
    public String deleteOfferChangeRequest(@RequestParam("toDoId") int toDoId, HttpServletRequest request) {

        ToDo toDo = offerChangeRequestService.getToDoById(toDoId);
        int restaurantId = toDo.getRestaurant().getId();
        int offerToUpdateId = toDo.getOffer().getId();
        int offerToDeleteId = toDo.getOffer().getChangeRequestId();

        //Checks if the user is allowed to see the requested offer which belongs to a restaurant he has access to. (security check, if the call parameter has been altered manually)
        if(!restaurantService.restaurantAssignedToSalesPerson(restaurantId)) {
            return "redirect:/home?noValidAccessToRestaurant";
        }

        offerChangeRequestService.deleteOfferChangeRequest(offerToDeleteId, offerToUpdateId, toDoId);

        return "redirect:/home?changeRequestDeleted";
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder changedOfferBinder) {
        changedOfferBinder.setAllowedFields(
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
                "keepFirstImage",
                "keepSecondImage",
                "keepThirdImage",
                "offerPhotos"
        );
        changedOfferBinder.setValidator(offerValidator);
    }

    private <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new
                    NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }
}
