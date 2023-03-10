package com.jekfood.delivery.domain;

import java.io.Serializable;
import java.util.Arrays;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Plates.
 */
@Document(collection = "plates")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("price")
    private Double price;

    @Field("photo")
    private byte[] photo;

    @NotNull
    @Field("photo_content_type")
    private String photoContentType;

    @Field("origin")
    private String origin;

    @Max(value = 5)
    @Field("stars")
    private Integer stars;

    @Field("favorite")
    private Boolean favorite;

    @Field("cook_time")
    private Integer cookTime;

    @Field("idrestaurant")
    private String idrestaurant;

    @DBRef
    @Field("restaurant")
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Plates id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Plates name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public Plates price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Plates photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Plates photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getOrigin() {
        return this.origin;
    }

    public Plates origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getStars() {
        return this.stars;
    }

    public Plates stars(Integer stars) {
        this.setStars(stars);
        return this;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }

    public Plates favorite(Boolean favorite) {
        this.setFavorite(favorite);
        return this;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getCookTime() {
        return this.cookTime;
    }

    public Plates cookTime(Integer cookTime) {
        this.setCookTime(cookTime);
        return this;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    //************ JN :  affectation of id restaurant starts***************//
    public String getIdrestaurant() {
        return this.idrestaurant = this.restaurant.getId();
    }

    public Plates idrestaurant(String idrestaurant) {
        this.setIdrestaurant(idrestaurant);
        return this;
    }

    public void setIdrestaurant(String idrestaurant) {
        this.idrestaurant = idrestaurant;
    }

    //************ JN :  affectation of id restaurant ends  ***************//

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Plates restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plates)) {
            return false;
        }
        return id != null && id.equals(((Plates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Plates [id=" +
            id +
            ", name=" +
            name +
            ", price=" +
            price +
            ", photo=" +
            Arrays.toString(photo) +
            ", photoContentType=" +
            photoContentType +
            ", origin=" +
            origin +
            ", stars=" +
            stars +
            ", favorite=" +
            favorite +
            ", cookTime=" +
            cookTime +
            ", idrestaurant=" +
            idrestaurant +
            ", restaurant=" +
            restaurant +
            ", getId()=" +
            getId() +
            ", getName()=" +
            getName() +
            ", getPrice()=" +
            getPrice() +
            ", getPhoto()=" +
            Arrays.toString(getPhoto()) +
            ", getPhotoContentType()=" +
            getPhotoContentType() +
            ", getOrigin()=" +
            getOrigin() +
            ", getStars()=" +
            getStars() +
            ", getFavorite()=" +
            getFavorite() +
            ", getCookTime()=" +
            getCookTime() +
            ", getIdrestaurant()=" +
            getIdrestaurant() +
            ", getRestaurant()=" +
            getRestaurant() +
            ", hashCode()=" +
            hashCode() +
            ", getClass()=" +
            getClass() +
            ", toString()=" +
            super.toString() +
            "]"
        );
    }
}
