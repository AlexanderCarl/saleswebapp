package saleswebapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.repository.impl.SalesPerson;
import saleswebapp.service.SalesPersonService;

/**
 * Created by Alexander Carl on 24.07.2017.
 */
@Service
public class SalesPersonServiceImpl implements SalesPersonService {

    @Autowired
    private DbReaderServiceImpl dbReaderService;

    @Override
    public SalesPerson getSalesPersonByEmail(String email) {
        return dbReaderService.getSalesPersonByEmail(email);
    }
}
