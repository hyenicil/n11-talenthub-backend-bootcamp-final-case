package com.n1.recommendationservice.controller;

import com.n1.recommendationservice.dto.RestaurantResponse;
import com.n1.recommendationservice.service.RecommendationService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService mockRecommendationServiceImpl;

    @Test
    void shouldGetRecommendedRestaurants() throws Exception {

        // given
        List<RestaurantResponse> responseList = List.of(new RestaurantResponse("id", "name", "42.45,42.45", 0.0));

        // when
        when(mockRecommendationServiceImpl.getRecommendedRestaurants(0L)).thenReturn(responseList);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/recommendation/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetNoRestaurantsWhenServiceReturnsNoItems() throws Exception {

        // when
        when(mockRecommendationServiceImpl.getRecommendedRestaurants(0L)).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/recommendation/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
