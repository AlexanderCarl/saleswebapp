package saleswebapp.domain.repository.impl;

import javax.persistence.*;

/**
 * Created by Alexander Carl on 25.06.2017.
 */
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    private String name;

    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    private String zip;

    private String city;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_code")
    private Country country;

    @Column(name = "location_latitude")
    private Float locationLatitude;

    @Column(name = "location_longitude")
    private Float locationLongitude;

    private String email;

    private String phone;

    private String url;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_type_id")
    private RestaurantType restaurantType;

    @Column(name = "restaurant_uuid")
    private String restaurantUUID;

    @Lob
    @Column(name = "qr_uuid")
    private byte[] qrUuid;

    @Column(name = "swa_offer_modify_permission")
    private boolean offerModifyPermission;

    @Column(name = "swa_blocked")
    private boolean blocked;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "swa_sales_person_id")
    private SalesPerson salesPerson;

}
