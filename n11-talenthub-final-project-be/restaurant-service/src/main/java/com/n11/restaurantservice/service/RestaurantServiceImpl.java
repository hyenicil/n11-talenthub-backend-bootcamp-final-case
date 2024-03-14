package com.n11.restaurantservice.service;

import com.n11.restaurantservice.document.Restaurant;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import com.n11.restaurantservice.exception.RestaurantNotFoundException;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.StreamSupport;

import static com.n11.restaurantservice.common.error.ErrorMessages.RESTAURANT_NOT_FOUND;

/**
 * @author Mehmet Akif Tanisik
 */
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantResponse save(RestaurantSaveRequest request) {

        Restaurant restaurant = restaurantMapper.mapSaveRequestToRestaurant(request);
        restaurant.setCreatedAt(LocalDateTime.now());

        restaurantRepository.save(restaurant);

        return restaurantMapper.mapRestaurantToResponse(restaurant);
    }

    @Override
    public RestaurantResponse getById(String id) {

        Restaurant restaurant = getRestaurantById(id);

        return restaurantMapper.mapRestaurantToResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> getAll() {

        List<Restaurant> restaurantList = StreamSupport
                .stream(restaurantRepository.findAll().spliterator(), false)
                .toList();

        return restaurantMapper.mapRestaurantsToResponses(restaurantList);
    }

    @Override
    public RestaurantResponse update(String id, RestaurantUpdateRequest request) {

        Restaurant restaurant = getRestaurantById(id);

        Restaurant updatedRestaurant = restaurantMapper.mapUpdateRequestToRestaurant(restaurant, request);
        updatedRestaurant.setUpdatedAt(LocalDateTime.now());

        return restaurantMapper.mapRestaurantToResponse(restaurantRepository.save(updatedRestaurant));
    }

    @Override
    public RestaurantResponse deactivate(String id) {

        Restaurant restaurant = getRestaurantById(id);
        restaurant.setIsActive(false);

        return restaurantMapper.mapRestaurantToResponse(restaurant);
    }

    @Override
    public void delete(String id) {

        Restaurant restaurant = getRestaurantById(id);

        restaurantRepository.delete(restaurant);
    }

    public Restaurant getRestaurantById(String id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException(RESTAURANT_NOT_FOUND));
    }

}
