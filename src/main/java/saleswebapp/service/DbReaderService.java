package saleswebapp.service;

import saleswebapp.domain.repository.impl.Country;
import saleswebapp.domain.repository.impl.SalesPerson;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
public interface DbReaderService {

    void testMethodSaveSalesPerson();

    SalesPerson getSalesPersonByEmail(String email);

    SalesPerson getSalesPersonById(int id);

    List<Country> getAllCountries();

}
