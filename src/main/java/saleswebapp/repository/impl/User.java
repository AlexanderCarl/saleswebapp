package saleswebapp.repository.impl;

import javax.persistence.*;

/**
 * Created by Alexander Carl on 06.07.2017.
 * The table user has a flaw. In the FindLunch Application the relation between user und restaurant is m:n. This makes it possible for a user to have several favorites.
 * But this does also means that a user with multiple "favorite restaurants" has multiple entries in the user table.
 * I leave the table as it is because I know that user table will be over-worked in the near future.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String username;

    private String password;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_type_id")
    private UserType userType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
