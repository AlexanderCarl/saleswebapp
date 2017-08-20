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

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-mm-yyyy");

        if(!offer.getStartDateAsString().equals("") && offer.getEndDateAsString().equals("")) {
            DateTime startDate = formatter.parseDateTime(offer.getStartDateAsString());
            DateTime endDate = formatter.parseDateTime(offer.getEndDateAsString());

            if (startDate.isAfter(endDate)) {
                errors.rejectValue("endDateAsString", "offer.validation.timeWindow");
            }
        }
    }
}
