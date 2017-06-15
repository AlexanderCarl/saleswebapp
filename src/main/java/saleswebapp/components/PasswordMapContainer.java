package saleswebapp.components;

import java.sql.Timestamp;

/**
 * Created by Alexander Carl on 15.06.2017.
 * This class is used in the PasswordResetCodeService HashMap.
 */
public class PasswordMapContainer {

    private String resetCode;
    private Timestamp timestamp;

    public PasswordMapContainer(String resetCode, Timestamp timestamp) {
        this.resetCode = resetCode;
        this.timestamp = timestamp;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
