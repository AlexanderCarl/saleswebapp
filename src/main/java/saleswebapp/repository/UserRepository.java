package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.User;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {
}
