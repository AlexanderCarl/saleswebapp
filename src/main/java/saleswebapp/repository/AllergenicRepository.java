package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.Allergenic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 09.08.2017.
 */
@Repository
public interface AllergenicRepository extends JpaRepository<Allergenic, Serializable> {

    List<Allergenic> getAllBy();
}
