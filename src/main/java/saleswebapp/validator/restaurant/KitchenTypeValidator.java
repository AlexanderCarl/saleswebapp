package saleswebapp.validator.restaurant;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import saleswebapp.repository.impl.Restaurant;

import java.util.List;

/**
 * Created by Alexander Carl on 31.07.2017.
 */
public class KitchenTypeValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        //return Restaurant.class.isAssignableFrom(aClass);
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;

        String kitchenType = null;

        try {
            kitchenType = restaurant.getKitchenTypesAsString().get(0);
        } catch (Exception e) {
            //kitchenTypesAsString is empty
        }

        if(kitchenType == null) {
            errors.rejectValue("offerTimes", "restaurant.validation.kitchenTypes");
        }
    }
}
