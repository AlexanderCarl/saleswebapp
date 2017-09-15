package saleswebapp.components;

import saleswebapp.validator.passwordReset.PasswordResetCode;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
public class PasswordResetForm {

    @PasswordResetCode
    private String securityCode;

    @Pattern(regexp = "^.{4,25}$", message = "{universal.validation.pattern.pwGuideline}")
    private String newPassword;

    private String newPasswordRepeat;

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordRepeat() {
        return newPasswordRepeat;
    }

    public void setNewPasswordRepeat(String newPasswordRepeat) {
        this.newPasswordRepeat = newPasswordRepeat;
    }
}
