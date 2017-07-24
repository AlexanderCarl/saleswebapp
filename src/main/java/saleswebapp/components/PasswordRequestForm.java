package saleswebapp.components;

import saleswebapp.validator.passwordRequest.EmailUnknown;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Alexander Carl on 13.06.2017.
 * This class represents the form used for the PasswordResetCodeRequest
 */
public class PasswordRequestForm implements Serializable {

    private static final long serialVersionUID = 5615550315753342L;

    @Pattern(regexp = ".+@.+\\..+", message = "{universal.validation.pattern.email}")
    @EmailUnknown
    private String email;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
