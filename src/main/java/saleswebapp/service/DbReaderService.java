package saleswebapp.service;

import saleswebapp.domain.repository.impl.Country;
import saleswebapp.domain.repository.impl.Restaurant;
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

    //Returns all Restaurants which the SalesPeron is responsible for, plus all Restaurants which have no SalesPerson assigned to them.
    List<Restaurant> getAllRestaurantsOfSalesPerson(int salesPersonId);

}
