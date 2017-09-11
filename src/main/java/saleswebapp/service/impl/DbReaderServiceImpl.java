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
    private ToDoRepository toDoRepository;

    @Autowired
    private RestaurantTypeRepository restaurantTypeRepository;

    @Autowired
    private RestaurantKitchenTypeRepository restaurantKitchenTypeRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private AdditiveRepository additiveRepository;

    @Autowired
    private AllergenicRepository allergenicRepository;

    @Autowired
    private OfferPhotoRepository offerPhotoRepository;

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
    public List<ToDo> getAllToDosOfSalesPerson(String email) {
        return toDoRepository.getAllBySalesPersonEmail(email);
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

    @Override
    public List<Offer> getAllOffersOfRestaurant(int restaurantId) {
        return offerRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Offer> getAllOffersOfRestaurant(int restaurantId, String courseTypeAsString) {
        List<CourseType> courseTypeList = courseTypeRepository.getAllByNameAndRestaurantId(courseTypeAsString, restaurantId);

        //
        List<Offer> offerList = offerRepository.getAllByRestaurantIdAndCourseType(restaurantId, courseTypeList.get(0));

        return offerList;
    }

    @Override
    public List<Offer> getAllOffersOfRestaurantAndCourseTypeNull(int restaurantId) {
        return offerRepository.getAllByCourseTypeIsNullAndRestaurantId(restaurantId);
    }

    @Override
    public List<CourseType> getAllCourseTypesOfRestaurant(int restaurantId) {
        return courseTypeRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public Offer getOffer(int offerId) {
        return offerRepository.getById(offerId);
    }

    @Override
    public List<Additive> getAllAdditives() {
        return additiveRepository.findAll();
    }

    @Override
    public List<Allergenic> getAllAllergenic() {
        return allergenicRepository.findAll();
    }

    @Override
    public OfferPhoto getOfferPhoto(int offerPhotoId) {
        return offerPhotoRepository.getOfferPhotoById(offerPhotoId);
    }

    @Override
    public ToDo getToDoById(int toDoId) {
        return toDoRepository.getById(toDoId);
    }

}
