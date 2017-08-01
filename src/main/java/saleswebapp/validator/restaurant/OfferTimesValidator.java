package saleswebapp.validator.restaurant;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import saleswebapp.components.RestaurantTimeContainer;
import saleswebapp.repository.impl.Restaurant;

import java.util.List;

/**
 * Created by Alexander Carl on 31.07.2017.
 */
public class OfferTimesValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        //return Restaurant.class.isAssignableFrom(aClass);
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;

        List<RestaurantTimeContainer> openingTimes = restaurant.getOpeningTimes();
        List<RestaurantTimeContainer> offerTimes = restaurant.getOfferTimes();
        //Regex-Source: https://stackoverflow.com/questions/7536755/regular-expression-for-matching-hhmm-time-format
        String stringPattern = "(^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$)|"; //pattern for hh:mm
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        boolean timeFormatValid = true;
        boolean offerTimeFormatValid = true;
        boolean timeWindowValid = true;

        //Format check: "hh:mm"
        for (RestaurantTimeContainer offerTime : offerTimes) {
            String startTime = offerTime.getStartTime();
            String endTime = offerTime.getEndTime();

            if(!(startTime.matches(stringPattern) && endTime.matches(stringPattern))) {
                offerTimeFormatValid = false;
                timeFormatValid = false;
            }
        }

        //The opening times format is also checked as the format is necesary for the time comparison
        for (RestaurantTimeContainer openingTime : openingTimes) {
            String startTime = openingTime.getStartTime();
            String endTime = openingTime.getEndTime();

            if(!(startTime.matches(stringPattern) && endTime.matches(stringPattern))) {
                timeFormatValid = false;
            }
        }

        if(!offerTimeFormatValid) {
            errors.rejectValue("offerTimes", "restaurant.validation.offerTimes.format");
        }

        //Check if the offer time is within the opening times
        if(timeFormatValid) {
            for (int i = 0; i < 7; i++) {
                if(offerTimes.get(i).getStartTime() != "" && offerTimes.get(i).getEndTime() != "" && openingTimes.get(i).getStartTime() != "" && openingTimes.get(i).getEndTime() != "") {

                DateTime offerStartTime = formatter.parseDateTime(offerTimes.get(i).getStartTime());
                DateTime offerEndTime = formatter.parseDateTime(offerTimes.get(i).getEndTime());
                DateTime openingStartTime = formatter.parseDateTime(openingTimes.get(i).getStartTime());
                DateTime openingEndTime = formatter.parseDateTime(openingTimes.get(i).getEndTime());

                    if(offerStartTime.isBefore(openingStartTime.minusMinutes(1))) {
                        timeWindowValid = false;
                    }

                    if(offerEndTime.isAfter(openingEndTime.plusMinutes(1))) {
                        timeWindowValid = false;
                    }
                }
            }
        }

        if(!timeWindowValid) {
            errors.rejectValue("offerTimes", "restaurant.validation.offerTimes.timeWindow");
        }
    }
}
