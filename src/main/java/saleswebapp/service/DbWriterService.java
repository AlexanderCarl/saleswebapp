package saleswebapp.service;

import saleswebapp.components.DTO.ProfileForm;
import saleswebapp.components.DTO.RestaurantAddCategory;
import saleswebapp.components.DTO.RestaurantDeleteCategory;
import saleswebapp.components.DTO.RestaurantForm;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Restaurant;

import java.util.List;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
public interface DbWriterService {

    void setNewPassword(String userEmail, String encodedPassword);

    void setNewPassword(Integer userId, String encodedPassword);

    void saveProfileChange(ProfileForm profileForm);

    void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory);

    void deleteCategoryFromRestaurant(CourseType courseType);

    void saveRestaurantChange(RestaurantForm restaurantForm);

    void setNewRestaurant(RestaurantForm restaurantForm);

}
