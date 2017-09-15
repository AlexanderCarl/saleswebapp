package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.OfferReservation;
import saleswebapp.repository.impl.Restaurant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 13.09.2017.
 */
@Repository
public interface OfferReservationRepository extends JpaRepository<OfferReservation, Serializable> {

    List<OfferReservation> getAllByRestaurantId(int restaurantId);
}
