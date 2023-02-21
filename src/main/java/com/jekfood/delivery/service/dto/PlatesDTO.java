package com.jekfood.delivery.service.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jekfood.delivery.domain.Plates} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlatesDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private byte[] photo;

    private String photoContentType;
    private String origin;

    @Max(value = 5)
    private Integer stars;

    private Boolean favorite;

    private Integer cookTime;

    private String idrestaurant;

    private RestaurantDTO restaurant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    //************ JN :  affectation of id restaurant starts***************//
    public String getIdrestaurant() {
        return this.idrestaurant = this.restaurant.getId();
    }

    public void setIdrestaurant(String idrestaurant) {
        this.idrestaurant = idrestaurant;
    }

    //************ JN :  affectation of id restaurant ends***************//

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlatesDTO)) {
            return false;
        }

        PlatesDTO platesDTO = (PlatesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, platesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "PlatesDTO [id=" +
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
