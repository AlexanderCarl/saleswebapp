package saleswebapp.service;

import saleswebapp.components.DTO.ProfileForm;
import saleswebapp.components.RestaurantAddCategory;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
public interface DbWriterService {

    void setNewPassword(String userEmail, String encodedPassword);

    void setNewPassword(Integer userId, String encodedPassword);

    void saveProfileChange(ProfileForm profileForm);

    void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory);

}
