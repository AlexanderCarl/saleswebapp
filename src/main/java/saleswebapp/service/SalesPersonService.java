package saleswebapp.service;

import saleswebapp.repository.impl.SalesPerson;

/**
 * Created by Alexander Carl on 24.07.2017.
 */
public interface SalesPersonService {

    SalesPerson getSalesPersonByEmail(String email);
}
