package saleswebapp.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleswebapp.service.SalesPersonService;

/**
 * Created by Alexander Carl on 04.06.2017.
 */

@Controller
public class LoginController {

    @Autowired
    private SalesPersonService salesPersonService;
    //salesPersonService.testMethodSaveSalesPerson();
    // String sha256hex = DigestUtils.sha256Hex("devPW");

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

}
