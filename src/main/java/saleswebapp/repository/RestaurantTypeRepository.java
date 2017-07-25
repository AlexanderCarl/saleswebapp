package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.RestaurantType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 11.07.2017.
 */
@Repository
public interface RestaurantTypeRepository extends JpaRepository<RestaurantType, Serializable> {

    List<RestaurantType> getAllBy();

    RestaurantType getByName(String name);
}
