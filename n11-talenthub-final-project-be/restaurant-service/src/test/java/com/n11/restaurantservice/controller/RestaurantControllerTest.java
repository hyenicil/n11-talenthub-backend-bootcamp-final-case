package com.n11.restaurantservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.restaurantservice.dto.RestaurantResponse;
import com.n11.restaurantservice.dto.RestaurantSaveRequest;
import com.n11.restaurantservice.dto.RestaurantUpdateRequest;
import com.n11.restaurantservice.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService mockRestaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSaveValidRestaurantRequest() throws Exception {

        // given
        RestaurantSaveRequest request = new RestaurantSaveRequest("name", "42.45,42.45");

        String requestAsString = objectMapper.writeValueAsString(request);

        // when
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/restaurants")
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void shouldGetRestaurantById() throws Exception {

        // given
        RestaurantResponse restaurantResponse = new RestaurantResponse("id", "name", "42.45,42.45", 0.0);

        // when
        when(mockRestaurantService.getById("id")).thenReturn(restaurantResponse);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/restaurants/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldReturnAllRestaurants() throws Exception {

        // given
        List<RestaurantResponse> responseList = List.of(new RestaurantResponse("id", "name", "42.45,42.45", 0.0));

        // when
        when(mockRestaurantService.getAll()).thenReturn(responseList);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/restaurants")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldNotGetRestaurantsWhenRestaurantServiceReturnsNoItems() throws Exception {

        // given
        when(mockRestaurantService.getAll()).thenReturn(Collections.emptyList());

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/restaurants")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void shouldUpdateRestaurant() throws Exception {

        // given
        RestaurantUpdateRequest request = new RestaurantUpdateRequest("name", "42.45,42.45");

        String requestAsString = objectMapper.writeValueAsString(request);

        // when
        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/restaurants/{id}", "id")
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldDeactivateRestaurant() throws Exception {

        // given
        RestaurantResponse restaurantResponse = new RestaurantResponse("id", "name", "42.45,42.45", 0.0);
        when(mockRestaurantService.deactivate("id")).thenReturn(restaurantResponse);

        // when
        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/restaurants/deactivate/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldDeleteRestaurant() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/restaurants/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(mockRestaurantService).delete("id");
    }
}
