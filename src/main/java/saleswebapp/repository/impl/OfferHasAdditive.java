package saleswebapp.repository.impl;

import javax.persistence.*;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Entity
@Table(name = "offer_has_additives")
public class OfferHasAdditive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "additives_id")
    private Additive additive;

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

    public Additive getAdditive() {
        return additive;
    }

    public void setAdditive(Additive additive) {
        this.additive = additive;
    }
}
