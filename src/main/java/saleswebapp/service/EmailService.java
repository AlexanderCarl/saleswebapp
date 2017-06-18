package saleswebapp.service;

import javax.mail.MessagingException;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
public interface EmailService {

    void generatePasswordRequest(String userEmail);

}
