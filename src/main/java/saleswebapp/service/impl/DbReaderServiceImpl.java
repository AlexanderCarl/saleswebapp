package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.repository.*;
import saleswebapp.repository.impl.*;
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

    @Autowired
    private RestaurantTypeRepository restaurantTypeRepository;

    @Autowired
    private RestaurantKitchenTypeRepository restaurantKitchenTypeRepository;

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
        return restaurantRepository.getAllBySalesPersonId(salesPersonId);
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        return restaurantRepository.getRestaurantById(id);
    }

    @Override
    public List<ToDoList> getAllToDosOfSalesPerson(String email) {
        return toDoListRepository.getAllBySalesPersonEmail(email);
    }

    @Override
    public List<RestaurantType> getAllRestaurantTypes() {
        return restaurantTypeRepository.getAllBy();
    }

    @Override
    public Restaurant getRestaurantByCustomerId(int id) {
        return restaurantRepository.getRestaurantByCustomerId(id);
    }

    @Override
    public List<KitchenType> getAllKitchenTypes() {
        return restaurantKitchenTypeRepository.getAllBy();
    }
}
