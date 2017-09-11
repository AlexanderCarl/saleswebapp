package saleswebapp.validator.offer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import saleswebapp.repository.impl.Offer;


/**
 * Created by Alexander Carl on 11.08.2017.
 */
public class DateValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Offer.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-M-yyyy");

        if(!offer.getStartDateAsString().equals("") && !offer.getEndDateAsString().equals("")) {
            DateTime startDate = null;
            DateTime endDate = null;

            try {
                startDate = formatter.parseDateTime(offer.getStartDateAsString());
            } catch (Exception e) {
                //The regex pattern is not 100% safe
            }

            try {
                endDate = formatter.parseDateTime(offer.getEndDateAsString());
            } catch (Exception e) {
                //The regex pattern is not 100% safe
            }

            if(startDate != null && endDate != null) {
                if (startDate.isAfter(endDate)) {
                    errors.reject("offer.validation.timeWindow");
                }
            }

            if(startDate == null || endDate == null) {
                errors.reject("offer.validation.dates");
            }
        }
    }
}
