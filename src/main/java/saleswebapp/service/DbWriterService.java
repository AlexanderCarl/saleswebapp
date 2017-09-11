package saleswebapp.service;

import saleswebapp.components.ProfileForm;
import saleswebapp.components.RestaurantAddCategory;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Offer;
import saleswebapp.repository.impl.Restaurant;

/**
 * Created by Alexander Carl on 18.06.2017.
 */
public interface DbWriterService {

    void setNewPassword(String userEmail, String encodedPassword);

    void setNewPassword(Integer userId, String encodedPassword);

    void saveProfileChange(ProfileForm profileForm);

    void addCategoryToRestaurant(RestaurantAddCategory restaurantAddCategory);

    void deleteCategoryFromRestaurant(CourseType courseType);

    void saveRestaurant(Restaurant restaurant);

    void deleteOffer(int offerId);

    void saveOffer(Offer offer);

    void deleteOfferPhoto(int offerPhotoId);

    void deleteToDo(int toDoId);

    void deleteOfferChangeRequest(int offerToDeleteId, int offerToUpdateId, int ToDoId);

    void saveOfferChangeRequest(int offerChangeRequestId, Offer changedOffer, int toDoId);

}
