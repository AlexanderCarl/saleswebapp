package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.repository.impl.Country;
import saleswebapp.service.CountryService;
import saleswebapp.service.DbReaderService;

import java.util.List;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private DbReaderService dbReaderService;

    @Override
    public List<Country> getAllCountries() {
        return dbReaderService.getAllCountries();
    }

}
