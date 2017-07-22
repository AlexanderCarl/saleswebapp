package saleswebapp.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;
import saleswebapp.components.DTO.ProfileForm;
import saleswebapp.components.DTO.RestaurantAddCategory;
import saleswebapp.components.DTO.RestaurantDeleteCategory;
import saleswebapp.components.DTO.RestaurantForm;
import saleswebapp.components.RestaurantTimeContainer;
import saleswebapp.repository.*;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbWriterService;

import javax.management.Query;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
@Service
public class DbWriterServiceImpl  implements DbWriterService {

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private RestaurantKitchenTypeRepository restaurantKitchenTypeRepository;

    @Autowired
    private DayOfWeekRepository dayOfWeekRepository;

    private ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

    @Override
    public void setNewPassword(String userEmail, String encodedPassword) {
        SalesPerson salesPerson = salesPersonRepository.getByEmail(userEmail);
        salesPerson.setPassword(encodedPassword);
        salesPersonRepository.saveAndFlush(salesPerson);
    }

    @Override
    public void setNewPassword(Integer userId, String encodedPassword) {
        SalesPerson salesPerson = salesPersonRepository.getById(userId);
        salesPerson.setPassword(encodedPassword);
        salesPersonRepository.saveAndFlush(salesPerson);
    }

    @Override
    @Transactional
    public void saveProfileChange(ProfileForm profileForm) {
        SalesPerson salesPerson = salesPersonRepository.getById(profileForm.getId());
        salesPerson.setFirstName(profileForm.getFirstName());
        salesPerson.setSecondName(profileForm.getSecondName());
        salesPerson.setStreet(profileForm.getStreet());
        salesPerson.setStreetNumber(profileForm.getStreetNumber());
        salesPerson.setZip(profileForm.getZip());
        salesPerson.setCity(profileForm.getCity());
        salesPerson.setPhone(profileForm.getPhone());

        Country country = new Country();
        country.setCountryCode(profileForm.getCountry().getCountryCode());
        country.setName(profileForm.getCountry().getName());
        salesPerson.setCountry(country);

        salesPerson.setEmail(profileForm.getEmail());
        salesPerson.setIban(profileForm.getIban());
        salesPerson.setBic(profileForm.getBic());

        salesPersonRepository.saveAndFlush(salesPerson);
    }

    @Override
    public void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory) {
        Restaurant restaurant = restaurantRepository.getRestaurantById(restaurantAddCategory.getRestaurantId());
        CourseType courseType = new CourseType();
        courseType.setName(restaurantAddCategory.getName());
        courseType.setRestaurant(restaurant);
        restaurant.getCourseTypeList().add(courseType);

        restaurantRepository.saveAndFlush(restaurant);
    }

    @Override
    @Transactional
    public void deleteCategoryFromRestaurant(CourseType courseType) {
        courseTypeRepository.deleteById(courseType.getId());
    }

    @Override
    @Transactional
    public void saveRestaurantChange(RestaurantForm restaurantForm) {
        Restaurant restaurant = createOrGetRestaurant(restaurantForm);
        restaurantRepository.saveAndFlush(restaurant);
    }

    @Override
    public void setNewRestaurant(RestaurantForm restaurantForm) {
        Restaurant restaurant = createOrGetRestaurant(restaurantForm);
        restaurantRepository.saveAndFlush(restaurant);
    }

    private Restaurant createOrGetRestaurant(RestaurantForm restaurantForm) {
        Restaurant restaurant;

        if(restaurantForm.getId() == 0) {
            restaurant = new Restaurant();
        } else {
            restaurant = restaurantRepository.getRestaurantById(restaurantForm.getId());
        }

        restaurant.setCustomerId(restaurantForm.getCustomerId());
        restaurant.setName(restaurantForm.getName());
        restaurant.setStreet(restaurantForm.getStreet());
        restaurant.setStreetNumber(restaurantForm.getStreetNumber());
        restaurant.setZip(restaurantForm.getZip());
        restaurant.setCity(restaurantForm.getCity());

        Country country = restaurantRepository.getRestaurantById(restaurantForm.getId()).getCountry();
        country.setCountryCode(restaurantForm.getCountry().getCountryCode());
        country.setName(restaurantForm.getCountry().getName());
        restaurant.setCountry(country);

        restaurant.setLocationLatitude(restaurantForm.getLocationLatitude());
        restaurant.setLocationLongitude(restaurantForm.getLocationLongitude());
        restaurant.setEmail(restaurantForm.getEmail());
        restaurant.setPhone(restaurantForm.getPhone());
        restaurant.setUrl(restaurantForm.getUrl());

        //Restaurant types temporary
        RestaurantType restaurantType = restaurantRepository.getRestaurantById(restaurantForm.getId()).getRestaurantType();
        restaurant.setRestaurantType(restaurantType);

        /*
        RestaurantType restaurantType = new RestaurantType();
        restaurantType.setId(restaurantForm.getRestaurantType().getId());
        restaurantType.setName(restaurantForm.getRestaurantType().getName());
        restaurant.setRestaurantType(restaurantType);
        */

        //Kitchen types
        List<KitchenType> listOfAllKitchenTypes = restaurantKitchenTypeRepository.getAllBy(); //Used to get the ids for the kitchenTypes
        List<String> listOfRestaurantKitchenTypes = restaurantForm.getRestaurantKitchenTypesForm();
        List<KitchenType> listOfKitchenTypesToSubtract = new ArrayList<KitchenType>();

        for(KitchenType kitchenType : listOfAllKitchenTypes) {
            String kitchenTypeName = kitchenType.getName();
            if(!listOfRestaurantKitchenTypes.contains(kitchenTypeName)) {
                listOfKitchenTypesToSubtract.add(kitchenType);
            }
        }

        listOfAllKitchenTypes.removeAll(listOfKitchenTypesToSubtract);
        restaurant.setKitchenTypes(listOfAllKitchenTypes);

        //Course Types are handled separately by the functions add/delete-CategoryToRestaurant

        //Opening times/offer times
        List<TimeSchedule> listOfTimeSchedulesToBeSaved = new ArrayList<TimeSchedule>();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        for(RestaurantTimeContainer timeContainerOfferTimes : restaurantForm.getOfferTimes()) {
            RestaurantTimeContainer timeContainerOpeningTimes = restaurantForm.getOpeningTimes().get(timeContainerOfferTimes.getDayNumber());
            DayOfWeek dayOfWeek = dayOfWeekRepository.getById(timeContainerOfferTimes.getDayNumber());

            OpeningTime openingTime = new OpeningTime();
            try {
                openingTime.setOpeningTime(sdf.parse(timeContainerOpeningTimes.getStartTime()));
                openingTime.setClosingTime(sdf.parse(timeContainerOpeningTimes.getEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Setting the opening time as a list is strange as the programm (findLunch & SWA) only support one opening time per day.
            List<OpeningTime> openingTimesList = new ArrayList<OpeningTime>();
            openingTimesList.add(openingTime);

            TimeSchedule timeSchedule = new TimeSchedule();
            timeSchedule.setDayOfWeek(dayOfWeek);
            timeSchedule.setOpeningTimes(openingTimesList);

            try {
                timeSchedule.setOfferStartTime(sdf.parse(timeContainerOfferTimes.getStartTime()));
                timeSchedule.setOfferEndTime(sdf.parse(timeContainerOfferTimes.getEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            listOfTimeSchedulesToBeSaved.add(timeSchedule);
        }
        restaurant.setTimeScheduleList(listOfTimeSchedulesToBeSaved);

        restaurant.setOfferModifyPermission(restaurantForm.isOfferModifyPermission());
        restaurant.setBlocked(restaurantForm.isBlocked());
        restaurant.setRestaurantUUID(restaurantForm.getRestaurantUUID());
        restaurant.setQrUuid(restaurantForm.getQrUuid());

        //String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String loggedInUser = "carl@hm.edu"; //DEV ONLY
        restaurant.setSalesPerson(salesPersonRepository.getByEmail(loggedInUser));

        return restaurant;
    }
}
