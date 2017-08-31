package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import saleswebapp.repository.impl.OfferPhoto;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.08.2017.
 */
public interface OfferPhotoRepository extends JpaRepository<OfferPhoto, Serializable> {

    @Modifying
    @Query("delete from OfferPhoto where offer_id = ?1")
    void deleteByOfferId(int offerId);

    @Modifying
    @Query("delete from OfferPhoto where id = ?1")
    void deleteById(int offerPhotoId);

    OfferPhoto getOfferPhotoById(int offerPhotoId);

}
