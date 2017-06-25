package saleswebapp.validator.profile;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ProfileEmailUniqueValidator.class)
@Documented
public @interface ProfileEmailUnique {

    String message() default "{profile.validation.emailUnique}";

    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}
