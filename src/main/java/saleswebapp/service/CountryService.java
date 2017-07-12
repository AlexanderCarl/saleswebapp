package saleswebapp.service;

import saleswebapp.components.DTO.CountryForm;

import java.util.List;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
public interface CountryService {

    List<CountryForm> getAllCountries();
}
