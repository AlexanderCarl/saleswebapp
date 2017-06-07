package saleswebapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.domain.SalesPerson;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
@Repository
public interface SalesPersonRepository extends JpaRepository<SalesPerson, Serializable> {

    public SalesPerson getByEmail(String userEmail);
}
