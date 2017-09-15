package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import saleswebapp.components.Messages;
import saleswebapp.repository.impl.SalesPerson;
import saleswebapp.service.EmailService;
import saleswebapp.service.PasswordRequestService;
import saleswebapp.service.DbReaderService;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private DbReaderService DbReaderService;

    @Autowired
    private PasswordRequestService passwordRequestService;

    @Autowired
    private Messages messages;

    @Override
    public void generatePasswordRequest(String userEmail) {
        logger.debug("PasswordRequestForm success - User " + userEmail + " requested a PasswordResetMail.");
        String resetCode = passwordRequestService.createPasswordResetCode(userEmail);
        SalesPerson salesPerson = DbReaderService.getSalesPersonByEmail(userEmail);
        String localeAsString = LocaleContextHolder.getLocale().toString();
        Locale locale;

        if(localeAsString.equalsIgnoreCase("en")) {
            locale = Locale.ENGLISH;
        } else {
            locale = Locale.GERMAN;
        }

        String subject = messages.getMessage("mail.text.subject", locale);
        String mailText = messages.getMessage("mail.text.hello", locale) +
                " " + salesPerson.getFirstName() + " " + salesPerson.getSecondName() + ",\n\n" +
                messages.getMessage("mail.text.mailTextPart1", locale) + "\n" + userEmail + "\n\n" +
                messages.getMessage("mail.text.mailTextPart2", locale) + "\n" + resetCode + "\n\n" +
                messages.getMessage("mail.text.mailTextPart3", locale) + "\n" +
                messages.getMessage("mail.text.mailTextPart4", locale);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setFrom("no-reply@SalesWebApp.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(mailText);
        javaMailSender.send(mailMessage);

        logger.debug("PasswordRequestMail (User: " + userEmail + ") has been send.");
    }
}
