package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.Restaurant;
import saleswebapp.repository.impl.RestaurantType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Serializable> {

    Restaurant getRestaurantById(int id);

    List<Restaurant> getAllBySalesPersonId(int id);

    Restaurant getRestaurantByCustomerId(int id);

}
