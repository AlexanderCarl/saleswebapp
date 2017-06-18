package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.service.DbReaderService;

import java.util.Arrays;

/**
 * Created by Alexander Carl on 07.06.2017.
 */
@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DbReaderService DbReaderService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        SalesPerson salesPerson = DbReaderService.getSalesPerson(userEmail);

        if (salesPerson != null) {
            UserDetails userDetails = (UserDetails) new User(salesPerson.getEmail(), salesPerson.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            logger.debug("Login success - User: " + userEmail + " identified and logged in.");
            return  userDetails;
        } else {
            UserDetails userDetails = (UserDetails) new User(userEmail, "unknownEMail", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            logger.debug("Login failure - User: " + userEmail + " unknown.");
            return userDetails;
            //The sha256-Code is always longer than the 11 chars of the "unknownEMail" String, therefore the else statement will never lead to a successfull log in.
        }
    }

}
