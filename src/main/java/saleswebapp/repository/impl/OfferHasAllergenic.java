package saleswebapp.repository.impl;

import javax.persistence.*;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Entity
@Table(name = "offer_has_allergenic")
public class OfferHasAllergenic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "allergenic_id")
    private Allergenic allergenic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Allergenic getAllergenic() {
        return allergenic;
    }

    public void setAllergenic(Allergenic allergenic) {
        this.allergenic = allergenic;
    }
}
