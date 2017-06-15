package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.service.EmailService;
import saleswebapp.service.PasswordResetCodeService;
import saleswebapp.service.SalesPersonService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SalesPersonService salesPersonService;

    @Autowired
    PasswordResetCodeService passwordResetCodeService;

    @Override
    public void generatePasswordResetCodeMail(String userEmail) throws MessagingException {
        logger.debug("PasswordRequestForm success - User " + userEmail + " requested a PasswordResetMail.");
        String resetCode = passwordResetCodeService.createPasswordResetCode(userEmail);
        SalesPerson salesPerson = salesPersonService.getSalesPerson(userEmail);
        String local = LocaleContextHolder.getLocale().toString();

        String subjectEN = "Reset your SalesWebApp password";
        String textEN = "Hi " + salesPerson.getFirstName() + ",\n\n" +
                "You've recently asked to reset the password for this SalesWebApp account:\n" + userEmail + "\n\n" +
                "To update your password, use the Reset-Code below:\n" + resetCode + "\n\n" +
                "Regards,\nThe SalesWebApp-Team";

        String subjectDE = "Zurücksetzen Ihres SalesWebApp Passworts";
        String textDE = "Hallo " + salesPerson.getFirstName() + " " + salesPerson.getSecondName() + ",\n\n" +
                "Sie haben vor kurzem einen Passwort-Reset angefordet für den SalesWebApp-Account " + userEmail  + "\n\n" +
                "Bitte nutzen Sie den Code unterhalb um Ihr Passwort zu aktualisieren:\n" + resetCode + "\n\n" +
                "Vielen Dank,\nIhr SalesWebApp-Team";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setFrom("no-reply@SalesWebApp.com");

        if(local.equalsIgnoreCase("en")) {
            mailMessage.setSubject(subjectEN);
            mailMessage.setText(textEN);
        } else {
            mailMessage.setSubject(subjectDE);
            mailMessage.setText(textDE);
        }
        javaMailSender.send(mailMessage);

        logger.debug("PasswordRequestForm success - User " + userEmail + " PasswordResetMail was send.");
    }
}
