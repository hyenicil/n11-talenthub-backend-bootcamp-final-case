package com.n11.restaurantservice.service;

import com.n11.restaurantservice.document.Restaurant;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import com.n11.restaurantservice.mapper.RestaurantMapper;
import com.n11.restaurantservice.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository mockRestaurantRepository;
    @Mock
    private RestaurantMapper mockRestaurantMapper;

    private RestaurantServiceImpl restaurantServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        restaurantServiceImplUnderTest = new RestaurantServiceImpl(mockRestaurantRepository, mockRestaurantMapper);
    }

    @Test
    void shouldSaveRestaurant() {

        // given
        RestaurantSaveRequest request = new RestaurantSaveRequest("name", "location");

        RestaurantResponse expectedResult = new RestaurantResponse("id", "name", "location", 0.0);

        Restaurant restaurant = new Restaurant("id", "name", "location", 0.0, false);

        RestaurantResponse restaurantResponse = new RestaurantResponse("id", "name", "location", 0.0);

        // when
        when(mockRestaurantMapper.mapSaveRequestToRestaurant(new RestaurantSaveRequest("name", "location"))).thenReturn(restaurant);

        when(mockRestaurantMapper.mapRestaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantServiceImplUnderTest.save(request);

        // then
        assertThat(result).isEqualTo(expectedResult);

        verify(mockRestaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void shouldGetRestaurantById() {

        // given
        RestaurantResponse expectedResult = new RestaurantResponse("id", "name", "location", 0.0);

        Optional<Restaurant> restaurant = Optional.of(new Restaurant("id", "name", "location", 0.0, false));

        RestaurantResponse restaurantResponse = new RestaurantResponse("id", "name", "location", 0.0);

        // when
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        when(mockRestaurantMapper.mapRestaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantServiceImplUnderTest.getById("id");

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldGetAllRestaurants() {

        // given
        List<RestaurantResponse> expectedResult = List.of(new RestaurantResponse("id", "name", "location", 0.0));

        List<Restaurant> restaurants = List.of(new Restaurant("id", "name", "location", 0.0, false));

        List<RestaurantResponse> responseList = List.of(new RestaurantResponse("id", "name", "location", 0.0));

        // when
        when(mockRestaurantRepository.findAll()).thenReturn(restaurants);

        when(mockRestaurantMapper.mapRestaurantsToResponses(restaurants)).thenReturn(responseList);

        List<RestaurantResponse> result = restaurantServiceImplUnderTest.getAll();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldUpdateRestaurant() {

        // given
        RestaurantUpdateRequest request = new RestaurantUpdateRequest("name", "location");

        RestaurantResponse expectedResult = new RestaurantResponse("id", "name", "location", 0.0);

        Optional<Restaurant> restaurant = Optional.of(new Restaurant("id", "name", "location", 0.0, false));

        Restaurant restaurant1 = new Restaurant("id", "name", "location", 0.0, false);

        Restaurant restaurant2 = new Restaurant("id", "name", "location", 0.0, false);

        RestaurantResponse restaurantResponse = new RestaurantResponse("id", "name", "location", 0.0);

        // when
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        when(mockRestaurantMapper.mapUpdateRequestToRestaurant(any(Restaurant.class), eq(new RestaurantUpdateRequest("name", "location")))).thenReturn(restaurant1);

        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant2);

        when(mockRestaurantMapper.mapRestaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantServiceImplUnderTest.update("id", request);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldDeactivateRestaurant() {

        // given
        RestaurantResponse expectedResult = new RestaurantResponse("id", "name", "location", 0.0);

        Optional<Restaurant> restaurant = Optional.of(new Restaurant("id", "name", "location", 0.0, false));

        RestaurantResponse restaurantResponse = new RestaurantResponse("id", "name", "location", 0.0);

        // when
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        when(mockRestaurantMapper.mapRestaurantToResponse(any(Restaurant.class))).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantServiceImplUnderTest.deactivate("id");

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldDeleteRestaurant() {

        // given
        Optional<Restaurant> restaurant = Optional.of(new Restaurant("id", "name", "location", 0.0, false));

        // when
        when(mockRestaurantRepository.findById("id")).thenReturn(restaurant);

        restaurantServiceImplUnderTest.delete("id");

        // then
        verify(mockRestaurantRepository).delete(any(Restaurant.class));
    }


    @Test
    void shouldGetRestaurantEntityById() {

        // given
        Restaurant restaurant = new Restaurant("id", "name", "location", 0.0, false);

        when(mockRestaurantRepository.findById("id")).thenReturn(Optional.of(restaurant));

        // when
        Restaurant result = restaurantServiceImplUnderTest.getRestaurantById("id");

        // then
        assertThat(result).isEqualTo(restaurant);
    }


}
