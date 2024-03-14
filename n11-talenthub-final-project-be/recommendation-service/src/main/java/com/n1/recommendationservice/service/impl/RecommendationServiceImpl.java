package com.n1.recommendationservice.service.impl;

import com.n1.recommendationservice.client.AddressClient;
import com.n1.recommendationservice.dto.AddressResponse;
import com.n1.recommendationservice.dto.RestaurantResponse;
import com.n1.recommendationservice.exception.AddressNotFoundException;
import com.n1.recommendationservice.service.RecommendationService;
import com.n1.recommendationservice.service.SolrClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.n1.recommendationservice.common.error.ErrorMessages.ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE;

/**
 * @author Mehmet Akif Tanisik
 */

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final AddressClient addressClient;
    private final SolrClientService solrClientService;

    public List<RestaurantResponse> getRecommendedRestaurants(Long userId) {

        List<RestaurantResponse> responseList = new ArrayList<>();
        try {
            var responseEntity = addressClient.getByUserId(userId);
            var baseRestResponse = responseEntity.getBody();
            String userLocation;

            if (baseRestResponse != null) {
                List<AddressResponse> addressList = baseRestResponse.getData();
                if (addressList.isEmpty()) {
                    throw new AddressNotFoundException(ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);
                }
                userLocation = addressList.get(0).location();
            } else {
                throw new AddressNotFoundException(ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);
            }

            responseList.addAll(solrClientService.getRestaurantsFromSolr(userLocation));

        } catch (AddressNotFoundException e) {
            throw new AddressNotFoundException(ADDRESS_NOT_FOUND_EXCEPTION_MESSAGE);
        }

        return responseList;
    }


}
