package saleswebapp.service;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
public interface EmailService {

    void generatePasswordRequestMail(String userEmail);

    boolean sendMail();

}
