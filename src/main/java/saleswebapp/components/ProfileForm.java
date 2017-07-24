package saleswebapp.components;

import saleswebapp.repository.impl.Country;
import saleswebapp.repository.impl.SalesPerson;
import saleswebapp.validator.profile.ProfilPassword;
import saleswebapp.validator.profile.ProfileEmailUnique;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Alexander Carl on 19.06.2017.
 */
public class ProfileForm implements Serializable {

    private static final long serialVersionUID = -4294454887497669957L;

    private int id;

    @Pattern(regexp = "^[a-zA-Z]{4,60}$", message = "{profile.validation.firstName}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{4,60}$", message = "{profile.validation.secondName}")
    private String secondName;

    @Pattern(regexp = "^[a-zA-Z]{4,60}$", message = "{profile.validation.street}")
    private String street;

    @Pattern(regexp = "^[^0]{1,6}$", message = "{profile.validation.streetNumber}")
    private String streetNumber;

    @Pattern(regexp = "^[0-9]{5}$", message = "{profile.validation.zip}")
    private String zip;

    @Pattern(regexp = "^[a-zA-Z]{3,60}$", message = "{profile.validation.city}")
    private String city;

    private Country country;

    @Pattern(regexp = "^[0][0-9/. \\-]{6,60}$", message = "{profile.validation.phone}")
    private String phone;

    @Pattern(regexp = ".+@.+\\..+", message = "{universal.validation.pattern.email}")
    @ProfileEmailUnique
    private String email;

    @Pattern(regexp = "^[A-Z]{2}([0-9a-zA-Z]{15,31})$" , message = "{profile.validation.iban}")
    private String iban;

    @Pattern(regexp = "^([a-zA-Z]){4}([a-zA-Z]){2}([0-9a-zA-Z]){2}([0-9a-zA-Z]{3})?$", message = "{profile.validation.bic}")
    private String bic;

    @ProfilPassword
    private String validPassword;

    private String newPassword;

    private String repeatNewPassword;

    public ProfileForm() {
        super();
    }

    public ProfileForm(SalesPerson salesPerson) {
       this.id = salesPerson.getId();
       this.firstName = salesPerson.getFirstName();
       this.secondName = salesPerson.getSecondName();
       this.street = salesPerson.getStreet();
       this.streetNumber = salesPerson.getStreetNumber();
       this.zip = salesPerson.getZip();
       this.city = salesPerson.getCity();
       this.phone = salesPerson.getPhone();
       this.country = salesPerson.getCountry();
       this.email = salesPerson.getEmail();
       this.iban = salesPerson.getIban();
       this.bic = salesPerson.getBic();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getValidPassword() {
        return validPassword;
    }

    public void setValidPassword(String validPassword) {
        this.validPassword = validPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }
}
