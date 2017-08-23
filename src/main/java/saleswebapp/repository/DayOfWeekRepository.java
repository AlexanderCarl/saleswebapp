package saleswebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saleswebapp.repository.impl.DayOfWeek;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 21.07.2017.
 */
public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Serializable> {

    /* The table day_of_week is filled up with wrong initial data.
     * 1) The DayNumbers are set wrong
     * 2) There is no need for 2 extra field for the dayNumber besides the id
     * 3) There is no need for the whole table. The dayNumber could simple be stored as an int value.
     * 4) Storing the days as Strings has the strong disadvantage that it makes more effort to internationalize the site later.
     *      4.1) Solution 1) Dont store the strings in the DB
     *      4.2) Dont use a Day_of_week table at all. It just makes it unstable, complicated und costs a lot of time.
     */
    DayOfWeek getById(int id);

    List<DayOfWeek> getAllBy();
}
