package saleswebapp.config;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.UrlPathHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import saleswebapp.validator.offer.DateValidator;
import saleswebapp.validator.offer.ImageValidator;
import saleswebapp.validator.offer.OfferValidator;
import saleswebapp.validator.passwordReset.PasswordResetPasswordEqualValidator;
import saleswebapp.validator.passwordReset.PasswordResetValidator;
import saleswebapp.validator.profile.ProfilePasswordEqualValidator;
import saleswebapp.validator.profile.ProfilePasswordSetValidator;
import saleswebapp.validator.profile.ProfileValidator;
import saleswebapp.validator.restaurant.KitchenTypeValidator;
import saleswebapp.validator.restaurant.OfferTimesValidator;
import saleswebapp.validator.restaurant.OpeningTimesValidator;
import saleswebapp.validator.restaurant.RestaurantValidator;

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
        resolver.setPrefix("/templates/**");
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

    @Bean
    public RestaurantValidator restaurantValidator() {
        Set<Validator> springValidators = new HashSet<Validator>();
        springValidators.add(new OpeningTimesValidator());
        springValidators.add(new OfferTimesValidator());
        springValidators.add(new KitchenTypeValidator());
        RestaurantValidator restaurantValidator = new RestaurantValidator();
        restaurantValidator.setSpringValidators(springValidators);
        return restaurantValidator;
    }

    @Bean
    public OfferValidator offerValidator() {
        Set<Validator> springValidators = new HashSet<Validator>();
        springValidators.add(new DateValidator());
        springValidators.add(new ImageValidator());
        OfferValidator offerValidator = new OfferValidator();
        offerValidator.setSpringValidators(springValidators);
        return offerValidator;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);

        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

}
