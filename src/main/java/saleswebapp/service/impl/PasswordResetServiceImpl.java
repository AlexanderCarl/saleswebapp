package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.PasswordRequestService;
import saleswebapp.service.PasswordResetService;

/**
 * Created by Alexander Carl on 18.06.2017.
 */

@Service
public class PasswordResetServiceImpl implements PasswordResetService{

    @Autowired
    private PasswordRequestService passwordRequestService;

    @Autowired
    private DbWriterService dbWriterService;

    private ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);

    @Override
    public void setNewPassword(String newPassword, String securityCode) {

        String userEmail =  passwordRequestService.getUserEmail(securityCode);
        dbWriterService.setNewPassword(userEmail, shaPasswordEncoder.encodePassword(newPassword, null));
        passwordRequestService.deletePasswordResetCode(securityCode);
    }
}

