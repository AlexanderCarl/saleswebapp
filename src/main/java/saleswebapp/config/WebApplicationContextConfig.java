package saleswebapp.config;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import saleswebapp.validator.passwordReset.PasswordResetPasswordEqualValidator;
import saleswebapp.validator.passwordReset.PasswordResetValidator;
import saleswebapp.validator.profile.ProfilePasswordEqualValidator;
import saleswebapp.validator.profile.ProfilePasswordSetValidator;
import saleswebapp.validator.profile.ProfileValidator;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
@Configuration
@EnableWebMvc
public class WebApplicationContextConfig extends WebMvcAutoConfigurationAdapter {

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine((SpringTemplateEngine) templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    private TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setTemplateMode("HTML5");
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
        resource.setBasename("messages");
        return resource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("de"));
        return  resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localChangeInterceptor = new LocaleChangeInterceptor();
        localChangeInterceptor.setParamName("language");
        registry.addInterceptor(localChangeInterceptor);
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public PasswordResetValidator productValidator() {
        Set<Validator> springValidators = new HashSet<Validator>();
        springValidators.add(new PasswordResetPasswordEqualValidator());
        PasswordResetValidator productValidator = new PasswordResetValidator();
        productValidator.setSpringValidators(springValidators);
        return productValidator;
    }

    @Bean
    public ProfileValidator profileValidator() {
        Set<Validator> springValidators = new HashSet<Validator>();
        springValidators.add(new ProfilePasswordEqualValidator());
        springValidators.add(new ProfilePasswordSetValidator());
        ProfileValidator profileValidator = new ProfileValidator();
        profileValidator.setSpringValidators(springValidators);
        return profileValidator;
    }

}
