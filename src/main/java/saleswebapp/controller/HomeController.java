package saleswebapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import saleswebapp.service.DbReaderService;

/**
 * Created by Alexander Carl on 06.06.2017.
 */
@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbReaderService dbReaderService;

    @RequestMapping(value = "/home")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/home/logout")
    public String logout() {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.debug("Logout - User " + loggedInUser + " logged out.");
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/login?logout";
    }
}
