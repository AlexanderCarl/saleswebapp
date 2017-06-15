package saleswebapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.components.PasswordRequestForm;
import saleswebapp.service.EmailService;

import javax.mail.MessagingException;
import javax.validation.Valid;

/**
 * Created by Alexander Carl on 04.06.2017.
 */

@Controller
public class LoginController {

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginPasswordMail", method = RequestMethod.GET)
    public String passwordMail(Model model) {
        PasswordRequestForm passwordResetCodeRequest = new PasswordRequestForm();
        model.addAttribute("passwordResetCodeRequest", passwordResetCodeRequest);
        return "loginPasswordMail";
    }

    @RequestMapping(value = "/loginPasswordMail", method = RequestMethod.POST)
    public String processPasswordResetCodeRequest(@ModelAttribute("passwordResetCodeRequest") @Valid PasswordRequestForm passwordResetCodeRequest, BindingResult bindingResult) throws InterruptedException, MessagingException {
        if(bindingResult.hasErrors()) {
            return "loginPasswordMail";
        }
        emailService.generatePasswordResetCodeMail(passwordResetCodeRequest.getEmail());
        return "redirect:/loginPasswordReset";
    }

    @RequestMapping(value = "/loginPasswordReset")
    public String passwordRecovery() {
        return "loginPasswordReset";
    }

}
