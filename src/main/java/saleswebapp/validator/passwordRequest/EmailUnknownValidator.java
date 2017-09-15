package saleswebapp.validator.passwordRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import saleswebapp.repository.impl.SalesPerson;
import saleswebapp.service.DbReaderService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Alexander Carl on 14.06.2017.
 */
public class EmailUnknownValidator implements ConstraintValidator<EmailUnknown, String> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DbReaderService DbReaderService;

    public void initialize(EmailUnknown constraintAnnotation) {
        // intentionally left blank; this is the place to initialize the constraint annotation for any logical default values.
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        SalesPerson salesPerson;

        try {
            salesPerson = DbReaderService.getSalesPersonByEmail(value);
        } catch (Exception ignore) {
            logger.debug("PasswordRequestForm failure - NullPointerAcception - getSalesPersonByEmail(value) was executed with value = null.");
            salesPerson = null;
        }

        if(salesPerson == null) {
            logger.debug("PasswordRequestForm failure - User: " + value + " is not in the Database.");
            return false;
        }

        return true;
    }
}
