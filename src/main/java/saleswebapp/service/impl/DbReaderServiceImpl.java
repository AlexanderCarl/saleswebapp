package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.domain.repository.RestaurantRepository;
import saleswebapp.domain.repository.ToDoListRepository;
import saleswebapp.domain.repository.impl.Country;
import saleswebapp.domain.repository.impl.Restaurant;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.domain.repository.CountryRepository;
import saleswebapp.domain.repository.SalesPersonRepository;
import saleswebapp.domain.repository.impl.ToDoList;
import saleswebapp.service.DbReaderService;

import java.util.List;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
@Service
public class DbReaderServiceImpl implements DbReaderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Override
    public SalesPerson getSalesPersonByEmail(String email) {
        return salesPersonRepository.getByEmail(email);
    }

    @Override
    public SalesPerson getSalesPersonById(int id) {
        return salesPersonRepository.getById(id);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<Restaurant> getAllRestaurantsOfSalesPerson(String email) {
        int salesPersonId = salesPersonRepository.getByEmail(email).getId();
        return restaurantRepository.getAllBySalesPersonIdOrSalesPersonIdNull(salesPersonId);
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return restaurantRepository.getRestaurantById(id);
    }

    @Override
    public List<ToDoList> getAllToDosOfSalesPerson(String email) {
        return toDoListRepository.getAllBySalesPersonEmail(email);
    }

}
