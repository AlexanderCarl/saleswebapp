package saleswebapp.repository.impl;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * Created by Alexander Carl on 02.08.2017.
 */
@Entity
@Table(name = "offer_photo")
public class OfferPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Lob
    private byte[] photo;

    @Lob
    private byte[] thumbnail;

    @Transient
    private String photoBase64Encoded;

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPhotoBase64Encoded() {
        return photoBase64Encoded;
    }

    public void setPhotoBase64Encoded(String photoBase64Encoded) {
        this.photoBase64Encoded = photoBase64Encoded;
    }

}
