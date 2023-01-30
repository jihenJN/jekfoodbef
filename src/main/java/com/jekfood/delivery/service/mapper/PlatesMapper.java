package com.jekfood.delivery.service.mapper;

import com.jekfood.delivery.domain.Plates;
import com.jekfood.delivery.domain.Restaurant;
import com.jekfood.delivery.service.dto.PlatesDTO;
import com.jekfood.delivery.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plates} and its DTO {@link PlatesDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlatesMapper extends EntityMapper<PlatesDTO, Plates> {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantName")
    PlatesDTO toDto(Plates s);

    @Named("restaurantName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RestaurantDTO toDtoRestaurantName(Restaurant restaurant);
}
