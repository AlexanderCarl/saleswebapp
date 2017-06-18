package saleswebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.components.PasswordRequestForm;
import saleswebapp.components.PasswordResetForm;
import saleswebapp.service.EmailService;
import saleswebapp.service.PasswordResetService;

import javax.validation.Valid;

/**
 * Created by Alexander Carl on 04.06.2017.
 * This Controller handles the requests for the Dialogs login.html, passwordRequest.html and passwordReset.html.
 */

@Controller
public class LoginController {

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordResetService passwordResetService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/passwordRequest", method = RequestMethod.GET)
    public String passwordRequest(Model model) {
        PasswordRequestForm passwordRequestForm = new PasswordRequestForm();
        model.addAttribute("passwordRequestForm", passwordRequestForm);

        return "passwordRequest";
    }

    @RequestMapping(value = "/passwordRequest", method = RequestMethod.POST)
    public String processPasswordRequest(@ModelAttribute("passwordRequestForm") @Valid PasswordRequestForm passwordRequestForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "passwordRequest";
        }
        emailService.generatePasswordRequest(passwordRequestForm.getEmail());
        return "redirect:/passwordReset";
    }

    @RequestMapping(value = "/passwordReset", method = RequestMethod.GET)
    public String passwordReset(Model model) {
        PasswordResetForm passwordResetForm = new PasswordResetForm();
        model.addAttribute("passwordResetForm", passwordResetForm);
        return "passwordReset";
    }

    @RequestMapping(value = "passwordReset", method = RequestMethod.POST)
    public String processPasswordReset(@ModelAttribute("passwordResetForm") PasswordResetForm passwordResetForm) {
        passwordResetService.setNewPassword(passwordResetForm.getNewPassword(), passwordResetForm.getSecurityCode());
        return "redirect:/login?passwordChangeSuccess";
    }

}
