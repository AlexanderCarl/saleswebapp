package saleswebapp.service;

import org.springframework.ui.Model;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Offer;
import saleswebapp.repository.impl.ToDo;

import java.util.List;

/**
 * Created by Alexander Carl on 07.09.2017.
 */
public interface OfferChangeRequestService {

    ToDo getToDoById(int id);

    List<CourseType> getCourseTypes(int restaurantId);

    void deleteOfferChangeRequest(int offerToDeleteId, int offerToUpdateId, int toDoId);

    void saveOfferChangeRequest(int offerChangeRequestId, Offer changedOffer, Offer existingOffer, int toDoId);

    Model addAttribtueChangesToModel (Model model, boolean allFalse, Offer preparedExistingOffer, Offer preparedChangedOffer);

    Model prepareOfferPicturesForExistingOffer(Model model, Offer offer);

    Offer prepareKeepImagesTags(Offer preparedExistingOffer, Offer preparedChangedOffer);

}
