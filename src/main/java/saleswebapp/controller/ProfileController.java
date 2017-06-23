package saleswebapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.components.DTO.ProfileForm;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.DbWriterService;
import saleswebapp.validator.profile.ProfilePasswordEqualValidator;
import saleswebapp.validator.profile.ProfileValidator;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * Created by Alexander Carl on 19.06.2017.
 * Controller for the dialog to User-Profil-Settings
 */
@Controller
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbReaderService dbReaderService;

    @Autowired
    private DbWriterService dbWriterService;

    @Autowired
    private ProfileValidator profileValidator;

    private ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);

    private static HashMap<Integer, SalesPerson> salesPersonsTransactionStart = new HashMap<Integer, SalesPerson>();

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        SalesPerson salesPerson = dbReaderService.getSalesPersonByEmail(loggedInUser);
        salesPersonsTransactionStart.put(salesPerson.getId(), salesPerson);
        ProfileForm profileForm = new ProfileForm(salesPerson);

        model.addAttribute("profileForm", profileForm);
        model.addAttribute("countries", dbReaderService.getAllCountries());

        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String processProfile(Model model, @Valid ProfileForm profileForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //The attributes must be added to the profile.html. This can also be done using the @ModelAttribute Annotation
            model.addAttribute("profileForm", profileForm);
            model.addAttribute("countries", dbReaderService.getAllCountries());

            return "profile";
        }

        //Security check if the salesPerson id has been used in the form, which is not supposed to happen.
        String[] suppressedFields = bindingResult.getSuppressedFields();
        if (suppressedFields.length > 0) {
            logger.debug("ProfileForm error - User: " + profileForm.getEmail() + " - Security violation: ProfileForm field id has been accessed.");
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }

        //Security check if the concerning the DB-Object salesPerson has been altered during transaction.
        SalesPerson salesPersonTransactionEnd = dbReaderService.getSalesPersonById(profileForm.getId());
        if (!salesPersonTransactionEnd.equals(salesPersonsTransactionStart.get(profileForm.getId()))) {
            return "redirect:/home?profileWasChangedMeanwhile";
        }

        //SecurityContext check if the email of the concerning DB-Object salesPerson has changed.
        if (!salesPersonTransactionEnd.getEmail().equals(profileForm.getEmail())) {
            dbWriterService.saveProfileChange(profileForm);
            passwordSave(profileForm);
            logger.debug("Logout - User: " + salesPersonTransactionEnd.getEmail() + " logged out.");
            SecurityContextHolder.getContext().setAuthentication(null);
            return "redirect:/login?profileEmailWasChanged";
        }

        dbWriterService.saveProfileChange(profileForm);
        passwordSave(profileForm);
        salesPersonsTransactionStart.remove(profileForm.getId());
        return "redirect:/home?profileChangeSuccess";
    }

    /*
    Saves the new password if the conditions are correct.
    This function is always called when the user profile is saved. Therefore the function
    needs to check for itself if a password change is required or not.
     */
    private void passwordSave(ProfileForm profileForm) {

        if(!profileForm.getValidPassword().equals("")) {
            String encodedOldPassword = shaPasswordEncoder.encodePassword(profileForm.getValidPassword(), null); //Password before it gets changed

            if(salesPersonsTransactionStart.get(profileForm.getId()).getPassword().equals(encodedOldPassword)) {

                if(profileForm.getNewPassword().equals(profileForm.getRepeatNewPassword())) {
                    String encodedNewPassword = shaPasswordEncoder.encodePassword(profileForm.getNewPassword(), null);
                    dbWriterService.setNewPassword(profileForm.getId(), encodedNewPassword);
                }
            }
        }
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "id",
                "firstName",
                "secondName",
                "street",
                "streetNumber",
                "zip",
                "city",
                "phone",
                "country",
                "email",
                "iban",
                "bic",
                "validPassword",
                "newPassword",
                "repeatNewPassword"
        );

        binder.setValidator(profileValidator);
    }

}
