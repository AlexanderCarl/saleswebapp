package saleswebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Alexander Carl on 06.06.2017.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/home")
    public String home() {
        return "home";
    }
}
