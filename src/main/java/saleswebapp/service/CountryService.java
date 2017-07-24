package saleswebapp.service;

import saleswebapp.repository.impl.Country;

import java.util.List;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
public interface CountryService {

    List<Country> getAllCountries();
}
