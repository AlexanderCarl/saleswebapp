package saleswebapp.validator.passwordReset;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import saleswebapp.components.PasswordMapContainer;
import saleswebapp.service.DbReaderService;
import saleswebapp.service.PasswordRequestService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
public class PasswordResetCodeValidator implements ConstraintValidator<PasswordResetCode, String> {

    @Autowired
    private DbReaderService dbReaderService;

    @Autowired
    private PasswordRequestService passwordRequestService;

    public void initialize(PasswordResetCode constraintAnnotation) {
        // intentionally left blank: this is the place to initialize the constraint annotation for any sensible default values.
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {

        HashMap<String, PasswordMapContainer> resetCodes = passwordRequestService.getResetCodes();
        DateTime currentTime = DateTime.now();
        DateTime entryTime;

        for (Map.Entry<String, PasswordMapContainer> entry : resetCodes.entrySet()) {
            String savedCode = entry.getKey();
            entryTime = entry.getValue().getDateTime();

            if(savedCode.equals(value)) {

                //The security code is invalid after 2 min. (2min. = 120 000)
                if(entryTime.isAfter(currentTime.minusMinutes(2))) {
                    return true;
                }
            }
        }

        return false;
    }
}
