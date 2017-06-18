package saleswebapp.service;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
public interface DbWriterService {

    void setNewPassword(String userEmail, String encodedPassword);
}
