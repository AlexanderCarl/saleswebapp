package saleswebapp.service;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
public interface PasswordResetCodeService {

    String createPasswordResetCode(String userEmail);

    String getPasswordResetCode(String userEmail);

    void deletePasswordResetCode(String userEmail);

}
