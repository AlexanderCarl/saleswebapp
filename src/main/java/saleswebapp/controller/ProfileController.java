package saleswebapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.components.ProfileForm;
import saleswebapp.repository.impl.SalesPerson;
import saleswebapp.service.CountryService;
import saleswebapp.service.ProfileService;
import saleswebapp.validator.profile.ProfileValidator;

import javax.validation.Valid;

/**
 * Created by Alexander Carl on 19.06.2017.
 * Controller for the dialog to User-Profil-Settings
 */
@Controller
public class ProfileController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileValidator profileValidator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        SalesPerson salesPerson = profileService.getSalesByEmail(loggedInUser);
        profileService.addSalesPersonToTransactionStore(salesPerson);

        model.addAttribute("profileForm", new ProfileForm(salesPerson));
        model.addAttribute("countries", countryService.getAllCountries());

        return "profile";
    }

    @RequestMapping(value = "/saveProfile", method = RequestMethod.POST)
    public String saveProfile(Model model, @Valid ProfileForm profileForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //The attributes must be added again to the profile. This can also be done using the @ModelAttribute Annotation
            model.addAttribute("profileForm", profileForm);
            model.addAttribute("countries", countryService.getAllCountries());

            return "profile";
        }

        //Security check if the concerning the DB-Object salesPerson has been altered during transaction.
        if (profileService.salesPersonHasBeenAlteredMeanwhile(profileForm.getId())) {
            return "redirect:/home?profileWasChangedMeanwhile";
        } else {
            if (profileService.emailOfSalesPersonHasBeenAltered(profileForm.getId())) {
                profileService.saveSalesPerson(profileForm);
                profileService.setNewPassword(profileForm);
                logger.debug("User (User-ID: " + profileForm.getId() + ") logged out successfully.");
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/login?profileEmailWasChanged";
            } else {
                profileService.saveSalesPerson(profileForm);
                profileService.setNewPassword(profileForm);
                return "redirect:/home?profileChangeSuccess";
            }
        }
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setValidator(profileValidator);
    }

}
