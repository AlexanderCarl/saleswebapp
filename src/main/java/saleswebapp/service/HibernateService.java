package saleswebapp.service;

/**
 * Created by Alexander Carl on 21.10.2017.
 */
public interface HibernateService {

    public <T> T initializeAndUnproxy(T entity);
}
