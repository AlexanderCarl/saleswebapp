package saleswebapp.components;

import saleswebapp.validator.passwordRequest.EmailUnknown;

import javax.validation.constraints.Pattern;

/**
 * Created by Alexander Carl on 13.06.2017.
 */
public class PasswordRequestForm {

    @Pattern(regexp = ".+@.+\\..+", message = "{universal.validation.pattern.email}")
    @EmailUnknown
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
