package saleswebapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleswebapp.repository.*;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbReaderService;

import java.util.ArrayList;
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

    @Autowired
    private DonationPerMonthRepository donationPerMonthRepository;

    @Autowired
    private OfferReservationRepository offerReservationRepository;

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

        List<Offer> offerList = offerRepository.getAllByRestaurantId(restaurantId);
        List<Offer> offerListToReturn = new ArrayList<Offer>();

        for(Offer offer : offerList) {
            if(offer.isChangeRequest() == false) {
                offerListToReturn.add(offer);
            }
        }

        return offerListToReturn;
    }

    @Override
    public List<Offer> getAllOffersOfRestaurant(int restaurantId, String courseTypeAsString) {
        List<CourseType> courseTypeList = courseTypeRepository.getAllByNameAndRestaurantId(courseTypeAsString, restaurantId);
        List<Offer> offerList = offerRepository.getAllByRestaurantIdAndCourseType(restaurantId, courseTypeList.get(0));
        List<Offer> offerListToReturn = new ArrayList<Offer>();

        for(Offer offer : offerList) {
            if(offer.isChangeRequest() == false) {
                offerListToReturn.add(offer);
            }
        }

        return offerListToReturn;
    }

    @Override
    public List<Offer> getAllOffersOfRestaurantAndCourseTypeNull(int restaurantId) {
        List<Offer> offerList = offerRepository.getAllByCourseTypeIsNullAndRestaurantId(restaurantId);
        List<Offer> offerListToReturn = new ArrayList<Offer>();

        for(Offer offer : offerList) {
            if(offer.isChangeRequest() == false) {
                offerListToReturn.add(offer);
            }
        }

        return offerListToReturn;
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

    @Override
    public List<OfferReservation> getAllOfferReservationsByRestaurantId(int restaurantId) {
        return offerReservationRepository.getAllByRestaurantId(restaurantId);
    }

    @Override
    public List<DonationPerMonth> getAllDonationsByRestaurantId(int restaurantId) {
        return donationPerMonthRepository.getAllByRestaurantId(restaurantId);
    }

}
