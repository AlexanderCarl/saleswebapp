package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import saleswebapp.repository.impl.ToDo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Serializable> {
        
    List<ToDo> getAllBySalesPersonEmail(String email);

    ToDo getById(int toDoId);

    @Modifying
    @Query("delete from ToDo where id = ?1")
    void deleteById(int toDoId);

}
