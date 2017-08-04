package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import saleswebapp.repository.impl.CourseType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 20.07.2017.
 */
public interface CourseTypeRepository extends JpaRepository<CourseType, Serializable> {

    List<CourseType> getAllByNameAndRestaurantId(String courseTypeAsString, int restaurantId);

    List<CourseType> getAllByRestaurantId(int restaurantId);

    /*
    The delete is written as a query because Hibernate would require additional config
    to set the fk "course_type" in offer null. The DB itself is configured correctly and
    does set the fk null if the corresponding entry in the table Course_Type is deleted.
     */
    @Modifying
    @Query("delete from CourseType where id = ?1")
    void deleteById(int id);

}
