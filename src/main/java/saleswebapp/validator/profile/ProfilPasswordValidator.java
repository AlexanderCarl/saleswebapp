package saleswebapp.validator.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import saleswebapp.service.DbReaderService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Alexander Carl on 23.06.2017.
 */
public class ProfilPasswordValidator implements ConstraintValidator<ProfilPassword, String> {

    @Autowired
    private DbReaderService dbReaderService;

    private ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

    @Override
    public void initialize(ProfilPassword constraintAnnotation) {
        // intentionally left blank: this is the place to initialize the constraint annotation for any sensible default values.
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String loggedInUser = "carl@hm.edu"; //Developing only

        //If the user doesn`t enter a password, the old one remains. Saving of the new password is handled by the controller.
        if (value.equals("")) {
            return true;
        } else {
            //Checks if the user entered a valid password
            String encodedPassword = encoder.encodePassword(value, null);
            if(encodedPassword.equals(dbReaderService.getSalesPersonByEmail(loggedInUser).getPassword())) {
                return true;
            }
        }

        return false;
    }
}
