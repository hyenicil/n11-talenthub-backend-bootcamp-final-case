package com.n1.recommendationservice.service.impl;

import com.n1.recommendationservice.client.AddressClient;
import com.n1.recommendationservice.common.base.BaseRestResponse;
import com.n1.recommendationservice.dto.AddressResponse;
import com.n1.recommendationservice.dto.RestaurantResponse;
import com.n1.recommendationservice.service.SolrClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    private AddressClient mockAddressClient;
    @Mock
    private SolrClientService mockSolrClientService;

    private RecommendationServiceImpl recommendationServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        recommendationServiceImplUnderTest = new RecommendationServiceImpl(mockAddressClient, mockSolrClientService);
    }

    @Test
    void shouldGetRecommendedRestaurants() {

        // given
        List<RestaurantResponse> expectedResult = List.of(new RestaurantResponse("id", "name", "location", 0.0));

        ResponseEntity<BaseRestResponse<List<AddressResponse>>> baseRestResponseEntity = new ResponseEntity<>(
                new BaseRestResponse<>(List.of(new AddressResponse("city", "district", "street", "location")), false),
                HttpStatusCode.valueOf(200));

        List<RestaurantResponse> responseList = List.of(new RestaurantResponse("id", "name", "location", 0.0));

        // when
        when(mockAddressClient.getByUserId(0L)).thenReturn(baseRestResponseEntity);

        when(mockSolrClientService.getRestaurantsFromSolr("location")).thenReturn(responseList);

        List<RestaurantResponse> result = recommendationServiceImplUnderTest.getRecommendedRestaurants(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }


    @Test
    void shouldGetRecommendedRestaurantsWhenAddressClientReturnsNoItems() {

        // given
        List<RestaurantResponse> expectedResult = List.of(new RestaurantResponse("id", "name", "location", 0.0));

        AddressResponse adr1 = new AddressResponse("city","district","street","location");

        AddressResponse adr2 = new AddressResponse("city","district","street","location");

        ResponseEntity<BaseRestResponse<List<AddressResponse>>> baseRestResponseEntity = ResponseEntity.ok(
                BaseRestResponse.of(List.of(adr1,adr2)));

        List<RestaurantResponse> responseList = List.of(new RestaurantResponse("id", "name", "location", 0.0));

        // when
        when(mockAddressClient.getByUserId(0L)).thenReturn(baseRestResponseEntity);

        when(mockSolrClientService.getRestaurantsFromSolr("location")).thenReturn(responseList);

        when(recommendationServiceImplUnderTest.getRecommendedRestaurants(0L)).thenReturn(expectedResult);

        List<RestaurantResponse> result = recommendationServiceImplUnderTest.getRecommendedRestaurants(0L);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldGetRecommendedRestaurantsSolrClientServiceReturnsNoItems() {

        // given
        ResponseEntity<BaseRestResponse<List<AddressResponse>>> baseRestResponseEntity = new ResponseEntity<>(
                new BaseRestResponse<>(List.of(new AddressResponse("city", "district", "street", "location")), false),
                HttpStatusCode.valueOf(200));

        // when
        when(mockAddressClient.getByUserId(0L)).thenReturn(baseRestResponseEntity);

        when(mockSolrClientService.getRestaurantsFromSolr("location")).thenReturn(Collections.emptyList());

        List<RestaurantResponse> result = recommendationServiceImplUnderTest.getRecommendedRestaurants(0L);

        // then
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
