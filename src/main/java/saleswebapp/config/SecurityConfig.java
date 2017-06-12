package saleswebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import saleswebapp.service.impl.AuthenticationServiceImpl;

/**
 * Created by Alexander Carl on 06.06.2017.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        auth.userDetailsService(authenticationService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin().loginPage("/login")
                .usernameParameter("userId")
                .passwordParameter("password");

        httpSecurity.formLogin().defaultSuccessUrl("/home")
                .failureUrl("/login?error");

        httpSecurity.logout().logoutSuccessUrl("/login?logout");

        httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");

        httpSecurity.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/home/**").access("hasRole('USER')");

        httpSecurity.csrf().disable();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); //equals default

        httpSecurity.sessionManagement().maximumSessions(1); //Allows only one login per user. If a user logs in again the first login will be invalid.

        httpSecurity.sessionManagement().invalidSessionUrl("/login"); //The user is send to this page after he closed the browser and opens the SalesWebApp again. Counts also for expired sessions.

    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher(); //Ensure that the Spring Security session registry is notified when the session is destroyed.
    }

}
