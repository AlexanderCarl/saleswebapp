package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.domain.repository.SalesPersonRepository;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.service.DbWriterService;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
@Service
public class DbWriterServiceImpl  implements DbWriterService {

    @Autowired
    SalesPersonRepository salesPersonRepository;

    @Override
    public void setNewPassword(String userEmail, String encodedPassword) {

        SalesPerson salesPerson = salesPersonRepository.getByEmail(userEmail);
        salesPerson.setPassword(encodedPassword);
        salesPersonRepository.saveAndFlush(salesPerson);
    }
}
