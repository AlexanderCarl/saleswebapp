package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.components.DTO.CountryForm;
import saleswebapp.repository.impl.Country;
import saleswebapp.service.CountryService;
import saleswebapp.service.DbReaderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private DbReaderService dbReaderService;

    @Override
    public List<CountryForm> getAllCountries() {
        List<Country> countryList = dbReaderService.getAllCountries();
        List<CountryForm> countryFormList = new ArrayList<CountryForm>();

        for (Country country : countryList) {
            countryFormList.add(new CountryForm(country));
        }

        return countryFormList;
    }
}
