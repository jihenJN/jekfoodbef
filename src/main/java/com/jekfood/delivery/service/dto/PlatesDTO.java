package com.jekfood.delivery.service.dto;

import java.io.Serializable;
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

    private String photos;

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

    public String getIdrestaurant() {
        return idrestaurant;
    }

    public void setIdrestaurant(String idrestaurant) {
        this.idrestaurant = idrestaurant;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

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

    // prettier-ignore
    @Override
    public String toString() {
        return "PlatesDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", photo='" + getPhoto() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", stars=" + getStars() +
            ", favorite='" + getFavorite() + "'" +
            ", cookTime=" + getCookTime() +
            ", idrestaurant='" + getIdrestaurant() + "'" +
            ", photos='" + getPhotos() + "'" +
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
