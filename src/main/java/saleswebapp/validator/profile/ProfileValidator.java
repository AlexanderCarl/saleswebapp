package saleswebapp.validator.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import saleswebapp.components.DTO.PasswordResetForm;
import saleswebapp.components.DTO.ProfileForm;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Carl on 23.06.2017.
 */
public class ProfileValidator  implements Validator {

    @Autowired
    private javax.validation.Validator beanValidator;

    private Set<Validator> springValidators;

    public ProfileValidator() {
        springValidators = new HashSet<Validator>();
    }

    //Used in the WebAppContextConfig to add classic Spring Validators
    public void setSpringValidators(Set<Validator> springValidators) {
        this.springValidators = springValidators;
    }

    public boolean supports(Class<?> clazz) {
        return ProfileForm.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> constraintViolations = beanValidator.validate(target);

        //Handles all violations of the Bean-Validation
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }

        //Handles all violations for the classic Spring Validatiors
        //In this example, the UnitsInStockValidator is added through WebAppContextConfig
        for (Validator validator : springValidators) {
            validator.validate(target, errors);
        }
    }

}
