package saleswebapp.service;

import saleswebapp.components.PasswordMapContainer;

import java.util.HashMap;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
public interface PasswordRequestService {

    String createPasswordResetCode(String userEmail);

    String getUserEmail(String resetCode);

    void deletePasswordResetCode(String resetCode);

    HashMap<String, PasswordMapContainer> getResetCodes();

}
