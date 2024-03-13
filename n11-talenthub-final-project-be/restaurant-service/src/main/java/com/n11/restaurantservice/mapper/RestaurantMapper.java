package com.n11.restaurantservice.mapper;

import com.n11.restaurantservice.document.Restaurant;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "averageScore", constant = "0")
    @Mapping(target = "isActive", constant = "true")
    Restaurant mapSaveRequestToRestaurant(RestaurantSaveRequest request);

    RestaurantResponse mapRestaurantToResponse(Restaurant restaurant);

    List<RestaurantResponse> mapRestaurantsToResponses(List<Restaurant> restaurantList);

    Restaurant mapUpdateRequestToRestaurant(@MappingTarget Restaurant restaurant, RestaurantUpdateRequest request);
}
