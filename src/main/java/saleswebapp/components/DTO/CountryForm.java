package saleswebapp.components.DTO;

import saleswebapp.repository.impl.Country;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
public class CountryForm implements Serializable {

    private static final long serialVersionUID = 8944560419855382135L;

    private String countryCode;
    private String name;

    public CountryForm () {
        super();
    }

    public CountryForm (Country country) {
        this.countryCode = country.getCountryCode();
        this.name = country.getName();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
