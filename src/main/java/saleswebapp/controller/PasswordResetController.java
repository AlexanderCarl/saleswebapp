package saleswebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.components.PasswordResetForm;
import saleswebapp.service.PasswordResetService;
import saleswebapp.validator.passwordReset.PasswordResetValidator;

import javax.validation.Valid;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordResetValidator passwordResetValidator;

    @RequestMapping(value = "/passwordReset", method = RequestMethod.GET)
    public String passwordReset(Model model) {
        PasswordResetForm passwordResetForm = new PasswordResetForm();
        model.addAttribute("passwordResetForm", passwordResetForm);

        return "passwordReset";
    }

    @RequestMapping(value = "passwordReset", method = RequestMethod.POST)
    public String processPasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetForm passwordResetForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "passwordReset";
        }
        passwordResetService.setNewPassword(passwordResetForm.getNewPassword(), passwordResetForm.getSecurityCode());

        return "redirect:/login?passwordChangeSuccess";
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setValidator(passwordResetValidator);
    }
}
