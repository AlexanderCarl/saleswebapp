package saleswebapp.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Created by Alexander Carl on 12.09.2017.
 */
@Component
public class Messages {

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor messageSourceAccessor;

    @PostConstruct
    private void init() {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    public String getMessage(String code, Locale locale) {
        return messageSourceAccessor.getMessage(code, locale);
    }

}
