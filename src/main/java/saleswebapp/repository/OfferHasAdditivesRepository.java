package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import saleswebapp.repository.impl.OfferHasAdditives;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.08.2017.
 */
public interface OfferHasAdditivesRepository extends JpaRepository<OfferHasAdditives, Serializable> {

    @Modifying
    @Query("delete from OfferHasAdditives where offer_id = ?1")
    void deleteByOfferId(int offerId);
}
