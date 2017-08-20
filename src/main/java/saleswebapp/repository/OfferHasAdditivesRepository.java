package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import saleswebapp.repository.impl.OfferHasAdditive;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.08.2017.
 */
public interface OfferHasAdditivesRepository extends JpaRepository<OfferHasAdditive, Serializable> {

    @Modifying
    @Query("delete from OfferHasAdditive where offer_id = ?1")
    void deleteByOfferId(int offerId);
}