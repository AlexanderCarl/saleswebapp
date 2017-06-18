package saleswebapp.validator.passwordReset;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import saleswebapp.components.PasswordResetForm;

/**
 * Created by Alexander Carl on 18.06.2017.
 */

@Component
public class PasswordEqualValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return PasswordResetForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordResetForm passwordResetForm = (PasswordResetForm) target;

        if(!passwordResetForm.getNewPassword().equals(passwordResetForm.getNewPasswordRepeat())) {
            errors.rejectValue("newPassword", "passwordReset.validation.passwordNotEqual");
        }
    }


}
