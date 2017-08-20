package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import saleswebapp.repository.impl.OfferHasAllergenic;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.08.2017.
 */
public interface OfferHasAllergenicRepository extends JpaRepository<OfferHasAllergenic, Serializable> {

    @Modifying
    @Query("delete from OfferHasAllergenic where offer_id = ?1")
    void deleteByOfferId(int offerId);
}