package saleswebapp.service;

import saleswebapp.domain.repository.impl.SalesPerson;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
public interface SalesPersonService {

    void testMethodSaveSalesPerson();

    SalesPerson getSalesPerson(String email);
}
