package saleswebapp.service;

import java.util.HashMap;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
public interface PasswordResetService {

    void setNewPassword(String newPassword, String securityCode);

}
