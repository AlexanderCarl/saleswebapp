package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.Additive;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 09.08.2017.
 */
@Repository
public interface AdditiveRepository extends JpaRepository<Additive, Serializable> {
}
