package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import saleswebapp.domain.SalesPerson;
import saleswebapp.domain.repository.SalesPersonRepository;

import java.util.Arrays;

/**
 * Created by Alexander Carl on 07.06.2017.
 */

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

    @Autowired
    SalesPersonRepository salesPersonRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        SalesPerson salesPerson = salesPersonRepository.getByEmail(userEmail);

        if (salesPerson != null) {
            UserDetails userDetails = (UserDetails) new User(salesPerson.getEmail(), salesPerson.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            return  userDetails;
        } else {
            UserDetails userDetails = (UserDetails) new User(userEmail, "unknownEMail", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            //Place a logging-message here e.g.: An unknown user with the email useremail tryed to login.
            return userDetails;
            //As a sha256-Code is always longer than the 11 chars of the "unknownEMail" String, the else statement will never lead to a successfull log in.
        }
    }

}
