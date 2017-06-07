package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.domain.Country;
import saleswebapp.domain.SalesPerson;
import saleswebapp.domain.repository.CountryRepository;
import saleswebapp.domain.repository.SalesPersonRepository;
import saleswebapp.service.SalesPersonService;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
@Service
public class SalesPersonServiceImpl implements SalesPersonService {

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void testMethodSaveSalesPerson() {
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setPassword("PW1234");
        salesPerson.setFirstName("FirstName1234");
        salesPerson.setSecondName("SecondName1234");
        salesPerson.setStreet("Street1234");
        salesPerson.setStreetNumber("S-Nr 1234");
        salesPerson.setZip("Z1234");
        salesPerson.setCity("City1234");
        salesPerson.setPhone("Phone1234");
        salesPerson.setCountry(countryRepository.getOne("DE"));
        salesPerson.setEmail("EMAIL1234");
        salesPerson.setIban("IBAN1234");
        salesPerson.setBic("BIC124");
        salesPerson.setSalaryPercentage(0.3);

        salesPersonRepository.save(salesPerson);
    }
}
