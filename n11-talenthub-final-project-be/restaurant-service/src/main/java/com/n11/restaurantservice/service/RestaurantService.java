package com.n11.restaurantservice.service;

import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;

import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */
public interface RestaurantService {

    RestaurantResponse save(RestaurantSaveRequest request);

    RestaurantResponse getById(String id);

    List<RestaurantResponse> getAll();

    RestaurantResponse update(String id, RestaurantUpdateRequest request);

    RestaurantResponse deactivate(String id);

    void delete(String id);

}
