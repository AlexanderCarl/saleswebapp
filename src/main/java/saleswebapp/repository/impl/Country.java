package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 05.06.2017.
 */

@Entity
public class Country {

    @Id
    @Column(name = "country_code")
    private String countryCode;

    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<SalesPerson> salesPersons;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<Restaurant> restaurants;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
