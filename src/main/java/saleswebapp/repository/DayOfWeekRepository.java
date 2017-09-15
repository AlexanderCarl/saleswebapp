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
     * 4) Storing the days as Strings in the DB makes internationalisation of the page harder.
     *      Proposal:
     *      4.1) Don`t store the strings in the DB
     *      4.2) Don`t use a Day_of_week table. It just makes it unstable, complicated und costs a lot of time. Just use the integers from 1 to 7 to identify the days of the week.
     */
    DayOfWeek getById(int id);

    List<DayOfWeek> getAllBy();
}
