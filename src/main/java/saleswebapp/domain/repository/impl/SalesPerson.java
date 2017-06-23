package saleswebapp.domain.repository.impl;

import saleswebapp.components.DTO.ProfileForm;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexander Carl on 04.06.2017.
 */
@Entity
@Table(name = "swa_sales_person")
public class SalesPerson{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    private String zip;

    private String city;

    private String phone;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_code")
    private Country country;

    private String email;

    private String iban;

    private String bic;

    @Column(name = "salary_percentage")
    private Double salaryPercentage;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        SalesPerson other = (SalesPerson) obj;
        if (Integer.valueOf(id) == null) {
            if (Integer.valueOf(other.id) != null)
                return false;
        }

        if (id != other.getId()) {
            return false;
        }

        if (!password.equals(other.getPassword())) {
            return false;
        }

        if (!firstName.equals(other.getFirstName())) {
            return false;
        }

        if (!secondName.equals(other.getSecondName())) {
            return false;
        }

        if (!street.equals(other.getStreet())) {
            return false;
        }

        if (!streetNumber.equals(other.getStreetNumber())) {
            return false;
        }

        if (!zip.equals(other.getZip())) {
            return false;
        }

        if (!city.equals(other.getCity())) {
            return false;
        }

        if (!phone.equals(other.getPhone())) {
            return false;
        }

        if (!country.getCountryCode().equals(other.getCountry().getCountryCode())) {
            return false;
        }

        if (!email.equals(other.getEmail())) {
            return false;
        }

        if (!iban.equals(other.getIban())) {
            return false;
        }

        if (!bic.equals(other.getBic())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Integer.valueOf(id) == null) ? 0 : (Integer.valueOf(id).hashCode()));

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Double getSalaryPercentage() {
        return salaryPercentage;
    }

    public void setSalaryPercentage(Double salaryPercentage) {
        this.salaryPercentage = salaryPercentage;
    }
}
