package saleswebapp.validator.profile;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import saleswebapp.components.ProfileForm;

/**
 * Created by Alexander Carl on 25.06.2017.
 */

public class ProfilePasswordSetValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ProfileForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileForm profileForm = (ProfileForm) target;

        String validPassword = profileForm.getValidPassword();
        String newPassword = profileForm.getNewPassword();
        String newPasswordRepeat = profileForm.getRepeatNewPassword();

        //The validation is true if none of the 3 passwords fields is used or if every of the 3 passwords fields is used.
        if  ((validPassword.equals("") && newPassword.equals("") && newPasswordRepeat.equals("")) || (!validPassword.equals("") && !newPassword.equals("") && !newPasswordRepeat.equals(""))) {
            //empty
        } else {
            errors.reject("profile.validation.passwordSet");
        }
    }
}
