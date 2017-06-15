package saleswebapp.service.impl;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import saleswebapp.components.PasswordMapContainer;
import saleswebapp.service.PasswordResetCodeService;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by Alexander Carl on 15.06.2017.
 */
@Service
public class PasswordResetCodeServiceImpl implements PasswordResetCodeService {

    private static HashMap<String, PasswordMapContainer> activeResetCodes = new HashMap<String, PasswordMapContainer>();
    private ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);

    @Override
    public String createPasswordResetCode(String userEmail) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String resetCode = shaPasswordEncoder.encodePassword(timestamp.toString(), null);
        PasswordMapContainer container = new PasswordMapContainer(resetCode, timestamp);
        activeResetCodes.put(userEmail, container);

        return resetCode;
    }

    @Override
    public String getPasswordResetCode(String userEmail) {
        PasswordMapContainer container = activeResetCodes.get(userEmail);
        return container.getResetCode();
    }

    @Override
    public void deletePasswordResetCode(String userEmail) {
        activeResetCodes.remove(userEmail);
    }

}

/**

 //Deleting of old ResetCodes
 int timeIndexOfNow = Integer.parseInt(timestamp.toString());

 for (Map.Entry<String, PasswordMapContainer> entry : activeResetCodes.entrySet()) {
     String key = entry.getKey();
     int timeIndexOfEntry = Integer.parseInt(entry.getValue().getTimestamp().toString());

     //120000 milli. (2 min.)
     if (timeIndexOfEntry < timeIndexOfEntry - 120000) {

     }
 }

 */