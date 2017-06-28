package saleswebapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.domain.repository.impl.RestaurantType;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Repository
public interface RestaurantTypeRepository extends JpaRepository<RestaurantType, Serializable> {

    RestaurantType getRestaurantTypeById(int id);
}
