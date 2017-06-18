package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import saleswebapp.components.PasswordMapContainer;
import saleswebapp.service.DbWriterService;
import saleswebapp.service.PasswordRequestService;
import saleswebapp.service.PasswordResetService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

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
    @Transactional
    public void setNewPassword(String newPassword, String securityCode) {

        String userEmail =  passwordRequestService.getUserEmail(securityCode);
        dbWriterService.setNewPassword(userEmail, shaPasswordEncoder.encodePassword(newPassword, null));
        passwordRequestService.deletePasswordResetCode(securityCode);
    }
}

/**
  @Override //Returns the Account-ID (Email) for which the securityCode is valid or null if the code isn`t valid.
public boolean validateSecurityCode(String formCode) {

HashMap<String, PasswordMapContainer> resetCodes = passwordRequestService.getResetCodes();
Integer timeIndexOfNow = Integer.parseInt(new Timestamp((System.currentTimeMillis())).toString());
Integer timeIndexOfEntry;

for (Map.Entry<String, PasswordMapContainer> entry : resetCodes.entrySet()) {
String savedCode = entry.getKey();

if(savedCode.equals(formCode)) {
timeIndexOfEntry = Integer.parseInt(entry.getValue().getTimestamp().toString());

//The security code is invalid after 2 min. (2min. = 120 000)
if(timeIndexOfEntry > timeIndexOfNow - 120000) {
return true;
}
}
}

return false;
}

 */
