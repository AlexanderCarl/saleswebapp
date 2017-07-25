package saleswebapp.service.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.mysql.jdbc.log.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import saleswebapp.components.ProfileForm;
import saleswebapp.components.RestaurantAddCategory;
import saleswebapp.components.RestaurantTimeContainer;
import saleswebapp.repository.*;
import saleswebapp.repository.impl.*;
import saleswebapp.service.DbWriterService;

import javax.transaction.Transactional;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
@Service
public class DbWriterServiceImpl implements DbWriterService {

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private RestaurantKitchenTypeRepository kitchenTypeRepository;

    @Autowired
    private RestaurantTypeRepository typeRepository;

    @Autowired
    private DayOfWeekRepository dayOfWeekRepository;

    private ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public void setNewPassword(String userEmail, String encodedPassword) {
        SalesPerson salesPerson = salesPersonRepository.getByEmail(userEmail);
        salesPerson.setPassword(encodedPassword);
        salesPersonRepository.saveAndFlush(salesPerson);
    }

    @Override
    @Transactional
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
    @Transactional
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
    public void saveRestaurant(Restaurant restaurantData) {
        int restaurantId = restaurantData.getId();
        Restaurant restaurantToSave;

        if (restaurantId == 0) {
            restaurantToSave = new Restaurant();
            restaurantToSave.setSalesPerson(salesPersonRepository.getById(restaurantData.getIdOfSalesPerson()));
        } else {
            restaurantToSave = restaurantRepository.getRestaurantById(restaurantId);
        }

        restaurantToSave.setCustomerId(restaurantData.getCustomerId());
        restaurantToSave.setName(restaurantData.getName());
        restaurantToSave.setStreet(restaurantData.getStreet());
        restaurantToSave.setStreetNumber(restaurantData.getStreetNumber());
        restaurantToSave.setZip(restaurantData.getZip());
        restaurantToSave.setCity(restaurantData.getCity());
        restaurantToSave.setCountry(restaurantData.getCountry());
        restaurantToSave.setEmail(restaurantData.getEmail());
        restaurantToSave.setPhone(restaurantData.getPhone());
        restaurantToSave.setUrl(restaurantData.getUrl());
        restaurantToSave.setRestaurantUUID(restaurantData.getRestaurantUUID());
        restaurantToSave.setQrUUID(restaurantData.getQrUUID());
        restaurantToSave.setOfferModifyPermission(restaurantData.isOfferModifyPermission());
        restaurantToSave.setBlocked(restaurantData.isBlocked());

        //Sets the restaurant type
        RestaurantType restaurantType = typeRepository.getByName(restaurantData.getRestaurantTypeAsString());
        restaurantToSave.setRestaurantType(restaurantType);

        //Sets the kitchen types
        List<KitchenType> restaurantKitchenTypes = new ArrayList<KitchenType>();

        for (String kitchenTypeName : restaurantData.getKitchenTypesAsString()) {
            restaurantKitchenTypes.add(kitchenTypeRepository.getByName(kitchenTypeName));
        }
        restaurantToSave.setKitchenTypes(restaurantKitchenTypes);

        //Sets the coordinates with GoogleMaps if left empty
        if (restaurantData.getLocationLatitude() == null || restaurantData.getLocationLongitude() == null) {
            HashMap<String, Float> googleMapsLocationValues = getLocationOfRestaurant(restaurantData);

            if(googleMapsLocationValues == null) {
                restaurantToSave.setLocationLatitude(restaurantData.getLocationLatitude());
                restaurantToSave.setLocationLongitude(restaurantData.getLocationLatitude());
            }

            if (restaurantData.getLocationLongitude() == null) {
                restaurantToSave.setLocationLongitude(googleMapsLocationValues.get("locationLongitude"));
            } else {
                restaurantToSave.setLocationLongitude(restaurantData.getLocationLatitude());
            }

            if (restaurantData.getLocationLatitude() == null) {
                restaurantToSave.setLocationLatitude(googleMapsLocationValues.get("locationLatitude"));
            } else {
                restaurantToSave.setLocationLatitude(restaurantData.getLocationLatitude());
            }
        } else {
            restaurantToSave.setLocationLatitude(restaurantData.getLocationLatitude());
            restaurantToSave.setLocationLongitude(restaurantData.getLocationLatitude());
        }

        //Offer/Opening times
        restaurantToSave.setTimeScheduleList(getTimeScheduleList(restaurantData, restaurantToSave));

        restaurantRepository.saveAndFlush(restaurantToSave);
        logger.debug("Restaurant (Customer-ID: " + restaurantToSave.getCustomerId() + ") wurde erfolgreich gespeichert.");
    }

    private List<TimeSchedule> getTimeScheduleList(Restaurant restaurantData, Restaurant restaurantToSave) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        List<TimeSchedule> timeSchedulesToBeSaved = restaurantToSave.getTimeScheduleList();
        if (timeSchedulesToBeSaved == null) {timeSchedulesToBeSaved = new ArrayList<TimeSchedule>(); }

        for(int i = 1; i < 8; i++) {
            //The TimeContainers are ordered and the list.index reflects there day number. Therefore we have to transfer this information to the variable dayNumber.
            RestaurantTimeContainer timeContainerOfferTimes = restaurantData.getOfferTimes().get(i-1);
            RestaurantTimeContainer timeContainerOpeningTimes = restaurantData.getOpeningTimes().get(i-1);
            DayOfWeek dayOfWeek = dayOfWeekRepository.getById(i);

            TimeSchedule timeSchedule = timeSchedulesToBeSaved.get(i-1);
            if (timeSchedule == null) {timeSchedule = new TimeSchedule(); }
            timeSchedule.setDayOfWeek(dayOfWeek);
            timeSchedule.setRestaurant(restaurantData);

            if(timeContainerOfferTimes.getStartTime() != "") {
                try {
                    timeSchedule.setOfferStartTime(sdf.parse(timeContainerOfferTimes.getStartTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                timeSchedule.setOfferStartTime(null);
            }

            if(timeContainerOfferTimes.getEndTime() != "") {
                try {
                    timeSchedule.setOfferEndTime(sdf.parse(timeContainerOfferTimes.getEndTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                timeSchedule.setOfferStartTime(null);
            }

            OpeningTime openingTime;
            if(timeSchedulesToBeSaved.get(i-1).getOpeningTimes().size() == 0) {
                openingTime = new OpeningTime();
            } else {
                openingTime = timeSchedulesToBeSaved.get(i-1).getOpeningTimes().get(0);
            }

            if (timeContainerOpeningTimes.getStartTime() != "") {
                try {
                    openingTime.setOpeningTime(sdf.parse(timeContainerOpeningTimes.getStartTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    openingTime.setOpeningTime(sdf.parse("00:00"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (timeContainerOpeningTimes.getEndTime() != "") {
                try {
                    openingTime.setClosingTime(sdf.parse(timeContainerOpeningTimes.getEndTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    openingTime.setClosingTime(sdf.parse("00:00"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //Setting the opening time as a list is questionable as the program (findLunch & SWA) only support one opening time per day.
            List<OpeningTime> openingTimesList = timeSchedule.getOpeningTimes();
            if (openingTimesList == null) {openingTimesList = new ArrayList<OpeningTime>(); }

            openingTime.setTimeSchedule(timeSchedule);
            openingTimesList.add(openingTime);
            timeSchedule.setOpeningTimes(openingTimesList);

            timeSchedulesToBeSaved.add(timeSchedule);
        }
        return timeSchedulesToBeSaved;
    }

    private HashMap<String, Float> getLocationOfRestaurant(Restaurant restaurantData) {

        // Replace the API key below with a valid API key.
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAvO9bl1Yi2hn7mkTSniv5lXaPRii1JxjI");
        GeocodingApiRequest req = GeocodingApi.newRequest(context).address(String.format("%1$s %2$s, %3$s %4$s", restaurantData.getStreetNumber(), restaurantData.getStreet(), restaurantData.getZip(), restaurantData.getCity()));
        HashMap<String, Float> googleMapsLocationValues = new HashMap<String, Float>();

        try {
            GeocodingResult[] result = req.await();
            if (result != null && result.length > 0) {
                // Handle successful request.
                GeocodingResult firstMatch = result[0];
                if (firstMatch.geometry != null && firstMatch.geometry.location != null) {
                    googleMapsLocationValues.put("locationLatitude", (float) firstMatch.geometry.location.lat);
                    googleMapsLocationValues.put("locationLongitude", (float) firstMatch.geometry.location.lng);
                } else {
                    logger.debug("Restaurant address for restaurantID: " + restaurantData.getId() + " could not be found be GoogleMaps. Location Latitude/Longitude are not set by Google.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return googleMapsLocationValues;
    }
}

