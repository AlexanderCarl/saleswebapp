package saleswebapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Alexander Carl on 04.08.2017.
 */
@RestController
@RequestMapping(value = "offerOverview/rest")
public class OfferOverviewRestController {

    @RequestMapping(value = "/remove/{offerId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void removeOffer(@PathVariable String offerId) {
        System.out.println("Rest-Befehl empfangen - offerId: " + offerId);
    }
}
