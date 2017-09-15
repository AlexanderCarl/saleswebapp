package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import saleswebapp.repository.impl.CourseType;
import saleswebapp.repository.impl.Offer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Serializable> {

    List<Offer> getAllByRestaurantId(int restaurantId);

    List<Offer> getAllByRestaurantIdAndCourseType(int restaurantId, CourseType courseType);

    List<Offer> getAllByCourseTypeIsNullAndRestaurantId(int restaurantId);

    Offer getById(int offerId);

    @Modifying
    @Query("delete from Offer where id = ?1")
    void deleteById(int offerId);

}
