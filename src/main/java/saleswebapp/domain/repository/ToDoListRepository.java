package saleswebapp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import saleswebapp.domain.repository.impl.SalesPerson;
import saleswebapp.domain.repository.impl.ToDoList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Serializable> {
        
    public List<ToDoList> getAllBySalesPersonEmail(String email);

}
