package saleswebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Alexander Carl on 27.06.2017.
 */
@Controller
public class BootstrapsTutorialController {

    @RequestMapping(value = "/BootstrapsTutorial1")
    public String bootstrapsTutorialOne() {
        return "BootstrapsTutorial/BootstrapsTutorial1";
    }

    @RequestMapping(value = "/BootstrapsTutorial2")
    public String bootstrapsTutorialTwo() {
        return "BootstrapsTutorial/BootstrapsTutorial2";
    }

    @RequestMapping(value = "/BootstrapsTutorial3")
    public String bootstrapsTutorialThree() {
        return "BootstrapsTutorial/BootstrapsTutorial3";
    }

    @RequestMapping(value = "/BootstrapsTutorial4")
    public String bootstrapsTutorialFour() {
        return "BootstrapsTutorial/BootstrapsTutorial4";
    }
}
