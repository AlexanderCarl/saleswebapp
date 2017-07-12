package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "user_type")
public class UserType {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy="userType", fetch = FetchType.LAZY)
    private List<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
