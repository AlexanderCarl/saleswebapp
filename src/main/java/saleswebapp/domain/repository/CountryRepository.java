package saleswebapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.domain.repository.impl.Country;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 05.06.2017.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Serializable> {

}
