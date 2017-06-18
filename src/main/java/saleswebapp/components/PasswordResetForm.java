package saleswebapp.components;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 15.06.2017.
 * This class represents the form used for the PasswordReset
 */
public class PasswordResetForm implements Serializable {

    private static final long serialVersionUID = -3704533112076648467L;

    private String securityCode;
    private String newPassword;
    private String newPasswordRepeat;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
