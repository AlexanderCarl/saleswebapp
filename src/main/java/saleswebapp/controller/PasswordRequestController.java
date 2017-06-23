package saleswebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.components.DTO.PasswordRequestForm;
import saleswebapp.service.EmailService;

import javax.validation.Valid;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
@Controller
public class PasswordRequestController {

    @Autowired
    private EmailService emailService;

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
}
