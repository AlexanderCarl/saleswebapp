package saleswebapp.components;

import java.sql.Timestamp;

/**
 * Created by Alexander Carl on 15.06.2017.
 * This class is used in the PasswordRequestService HashMap.
 */
public class PasswordMapContainer {

    private String userEmail;
    private Timestamp timestamp;

    public PasswordMapContainer(String userEmail, Timestamp timestamp) {
        this.userEmail = userEmail;
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
